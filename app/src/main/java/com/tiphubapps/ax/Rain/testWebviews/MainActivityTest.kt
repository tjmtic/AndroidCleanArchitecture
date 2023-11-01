package com.tiphubapps.ax.Rain.testWebviews

import android.util.JsonReader
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.io.InputStreamReader


@ExperimentalCoroutinesApi
//@RunWith(AndroidJUnit4::class)
//@MediumTest
class MainActivityTest {

    //@get:Rule
    //val instantTaskExecutorRule = InstantTaskExecutorRule()

    val articleViewModel: ArticleViewModel = ArticleViewModel(Dispatchers.Unconfined)

    @Before
    fun setupDependencies() {
    }

    @After
    fun cleanup() {
    }

    @Test
    fun viewModel_will_load() {

        //articleViewModel.fetchArticlesTest()
        //val inputStream = this.resources.openRawResource(R.raw.article_response)
        //val inputStreamReader = InputStreamReader(inputStream)
        //val jsonReader = JsonReader(inputStreamReader)

       // val ist: InputStream = context.getAssets().open("file_name.json")

        //classLoader.getResource("test.json")

        //val is2 = getClass().getClassLoader().getResourceAsStream("myFile.txt")

        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val inputStream = context.classLoader.getResourceAsStream("article_response.json")

        //val inputStream = MainActivityTest::class.java.classLoader?.getResourceAsStream("/article_response.json")
        val inputStreamReader = InputStreamReader(inputStream)
        val jsonReader = JsonReader(inputStreamReader)

        //coroutineScope {
        //    launch {
                articleViewModel.ingestArticle(jsonReader)
        //    }
        //}

        //assert on hardcoded pair data -- Pair(title1, url1)

        Assert.assertEquals(true, true)
    }

}





