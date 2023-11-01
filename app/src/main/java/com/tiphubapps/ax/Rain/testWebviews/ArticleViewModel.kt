package com.tiphubapps.ax.Rain.testWebviews


import android.util.JsonReader
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection



class ArticleViewModel(private val cd : CoroutineDispatcher = Dispatchers.IO) : ViewModel() {
    companion object {
        private val TAG = ArticleViewModel::class.java.simpleName
    }

    val articleList : MutableLiveData<List<ArticleData>> by lazy {
        MutableLiveData<List<ArticleData>>()
    }

    /**
     * Fetch articles from [NYT Article Search API](https://developer.nytimes.com/docs/articlesearch-product/1/routes/articlesearch.json/get)
     */
    //"https://api.nytimes.com/svc/search/v2/articlesearch.json"
    fun fetchArticles(url: String) {
        val url = URL(url)//"https://api.nytimes.com/svc/search/v2/articlesearch.json?api-key=oFjnnSAIsADysrOAdAF5ihGlecFPNLU4")
        viewModelScope.launch(cd) {
            val connection = (withContext(cd) {
                url.openConnection()
            } as? HttpsURLConnection)
            try {
                connection?.run {
                    readTimeout = 3000
                    connectTimeout = 3000
                    requestMethod = "GET"
                    doInput = true
                    if (responseCode != HttpsURLConnection.HTTP_OK) {
                        Log.e(TAG, "HTTP error code: edit")
                        articleList.postValue(emptyList())
                    }
                    inputStream?.let { stream ->
                        //val fetchedArticles = mutableListOf<ArticleData>()
                        val reader = JsonReader(BufferedReader(InputStreamReader(stream)))
                        ingestArticle(reader)
                    }
                }
            } finally {
                connection?.disconnect()
            }
        }
    }

    internal fun ingestArticle(reader: JsonReader)
    {
        val fetchedArticles = mutableListOf<ArticleData>()
        reader.beginObject()
        var status: String

        while (reader.hasNext()) {
            when (reader.nextName()) {
                "status" -> {
                    status = reader.nextString()
                    Log.d(TAG, "status $status")
                }
                "response" -> {
                    ingestResponse(reader)?.let{
                        println("Added in RESONSE: $it")
                        fetchedArticles.addAll(it)
                    }
                }
                else -> reader.skipValue()
            }
        }
        reader.endObject()

        articleList.postValue(fetchedArticles)
    }

    private fun ingestResponse(reader: JsonReader) : List<ArticleData>? {
        var fetchedArticles : List<ArticleData>? = null

        /////
        reader.beginObject()
        //////
        while (reader.hasNext()) {
            when (reader.nextName()) {
                "docs" -> {
                    ingestDocs(reader)?.let{
                        fetchedArticles = it
                    }
                }
                else -> reader.skipValue()
            }
        }
        return fetchedArticles
    }

    private fun ingestDocs(reader: JsonReader): List<ArticleData>?{
        var fetchedArticles: List<ArticleData>? = null

        reader.beginArray()
        //var articleTitle: String? = null
        //var articleUrl: String?
        while (reader.hasNext()) {
            reader.beginObject()
            var articleTitle: String? = null
            var articleUrl: String? = null
            while (reader.hasNext()) {

                when (reader.nextName()) {
                    "headline" -> {
                        ingestHeadline(reader)?.let{ headline ->
                            articleTitle = headline
                            articleUrl?.let { url ->

                                val d = ingestData(headline, url)
                                fetchedArticles?.let{
                                    fetchedArticles = it.plus(d)
                                } ?: run {
                                    fetchedArticles = mutableListOf<ArticleData>().apply{
                                        add(d)
                                    }
                                }
                            }
                        }
                    }

                    "web_url" -> {
                        ingestUrl(reader)?.let{ url ->
                            articleUrl = url
                            articleTitle?.let{ title ->
                                val d = ingestData(title, url)
                                fetchedArticles?.let{
                                    fetchedArticles = it.plus(d)
                                } ?: run {
                                    fetchedArticles = mutableListOf<ArticleData>().apply{
                                        add(d)
                                    }
                                }
                            }
                        }
                    }
                    else -> reader.skipValue()
                }
            }
            reader.endObject()
        }
        reader.endArray()

        return fetchedArticles
    }

    private fun ingestUrl(reader: JsonReader): String?{
        return reader.nextString()
    }

    private fun ingestHeadline(reader: JsonReader) : String? {
        var articleTitle: String? = null

        reader.beginObject()
        while (reader.hasNext()) {
            when (reader.nextName()) {
                "main" -> articleTitle = reader.nextString()
                else -> reader.skipValue()
            }
        }
        reader.endObject()

        return articleTitle
    }

    private fun ingestData(title: String, url: String): ArticleData{
        return ArticleData(title, url)
    }

    private fun syncData(title: String, url: String, curArticles: List<ArticleData>?) : List<ArticleData>?{
        //fetchedArticles = mutableListOf<ArticleData>()
        val d = ingestData(title, url)
        curArticles?.let{
            it.plus(d)
        } ?: run {
            //curArticles = mutableListOf<ArticleData>().apply{
            //    add(d)
            //}
        }

        return null
    }



    fun fetchArticlesTest(jsonReader: JsonReader) {
        //viewModelScope.launch(cd) {
                ingestArticle(jsonReader)
        //}
    }
}