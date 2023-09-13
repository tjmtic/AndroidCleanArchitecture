package com.farhan.tanvir.rain

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.util.JsonReader
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.tiphubapps.ax.rain.testWebviews.ArticleData
import com.tiphubapps.ax.rain.testWebviews.ArticleViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.InputStreamReader


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @get:Rule
    val instantExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var articleViewModel: ArticleViewModel

    @Before
    fun setup(){
        articleViewModel = ArticleViewModel(Dispatchers.Unconfined)
    }
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.tiphubapps.ax.rain", appContext.packageName)

        val inputStream = ExampleInstrumentedTest::class.java.classLoader?.getResourceAsStream("article_response.json")
        val inputStreamReader = InputStreamReader(inputStream)
        val jsonReader = JsonReader(inputStreamReader)

        articleViewModel.ingestArticle(jsonReader)

        Assert.assertEquals(articleViewModel.articleList.value?.first(), sampleData.first())
    }

    companion object{
        val sampleData = listOf(ArticleData("Journalist Quits Kenosha Paper in Protest of Its Jacob Blake Rally Coverage","https://www.nytimes.com/2020/08/31/business/media/kenosha-newspaper-editor-quits.html"))
    }
}