package com.tiphubapps.ax.rain.testWebviews


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.JsonReader
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.tiphubapps.ax.rain.R
import kotlinx.coroutines.Dispatchers
import java.io.InputStream
import java.io.InputStreamReader


class WebActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val articleViewModel: ArticleViewModel by viewModels()

        setContentView(R.layout.activity_main)
        val articleList = findViewById<RecyclerView>(R.id.article_list)
        val articleAdapter = ArticleAdapter()
        articleList.adapter = articleAdapter
        articleList.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        articleAdapter.itemClickListener = object: ArticleAdapter.ItemClickListener {
            override fun onClicked(articleData: ArticleData) {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(articleData.url))
                startActivity(browserIntent)
            }
        }

        articleViewModel.articleList.observe(this) {
            articleAdapter.articleList = it
        }
        //val `is`: InputStream = this.getAssets().open("file_name.json")
        ///////////////////////
        val inputStream = this.resources.openRawResource(R.raw.article_response)
        val inputStreamReader = InputStreamReader(inputStream)
        val jsonReader = JsonReader(inputStreamReader)
        ///////////////////
        articleViewModel.fetchArticlesTest(jsonReader)
    }
}