package com.tiphubapps.ax.rain.presentation.screen.details

import android.app.Application
import android.content.Context
import android.content.res.Resources
import android.util.Log
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.capitalize
import androidx.lifecycle.*
import com.tiphubapps.ax.rain.Rain
import com.tiphubapps.ax.domain.repository.UserRepository
import com.tiphubapps.ax.domain.useCase.GetCurrentUserUseCase
import com.tiphubapps.ax.domain.useCase.UserUseCases
import com.google.gson.JsonObject
import com.tiphubapps.ax.data.util.CoroutineContextProvider
import com.tiphubapps.ax.domain.repository.AndroidFrameworkRepository
import com.tiphubapps.ax.domain.repository.AppError
import com.tiphubapps.ax.domain.repository.UseCaseResult
import com.tiphubapps.ax.domain.useCase.LoginUseCases
import com.tiphubapps.ax.rain.R
import com.tiphubapps.ax.rain.presentation.helper.performVibration
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import com.tiphubapps.ax.data.util.SessionManager
import com.tiphubapps.ax.domain.useCase.AuthUseCases
import com.tiphubapps.ax.domain.useCase.SplashUseCases
import com.tiphubapps.ax.rain.presentation.screen.login.AuthedViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.util.Locale
import javax.inject.Named
import kotlin.math.absoluteValue

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val splashUseCases: SplashUseCases,
    private val coroutineContextProvider: CoroutineContextProvider
) : ViewModel() {

    //TODO: implement injection (and use!) of this?
    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        val errorMessage = throwable.message ?: "An error occurred"
        //showToast(errorMessage)
    }

    ///////// ViewModel State setup ///////////
    //(OPEN) slug value
    private val _state = MutableStateFlow(SplashState())
    val state : StateFlow<SplashState> = _state

    //private val _localValueFlow = MutableStateFlow("")
    //val localValueFlow: StateFlow<String> = _localValueFlow

    //(MIX) Transform Data as appropriate
    // ...


    var splashJob : Job?

    ///////////////////////////////////////////


    /////////////event bus////////////
    val _eventBus = Channel<SplashEvent>()
    val eventBus = _eventBus.receiveAsFlow()

    sealed class SplashEvent {
        object SPLASHING: SplashEvent()
        object SPLASHED: SplashEvent()
        data class Error(val msg: String = "Error"): SplashEvent()
    }

    /////////////

    init {
        println("TIME123 SplashViewModel Init Start")

        onEvent(SplashViewEvent.LoadingChanged(isLoading = true))
        //Init Firebase
        //Init Analytics/Logging?
        //Init Background Services
        //Init Websocket
        //Init UserData


        //(PIPE) Expose/Stream values as Flows
        splashJob = viewModelScope.launch (
            coroutineContextProvider.io + coroutineExceptionHandler, CoroutineStart.DEFAULT
        ) {

            _eventBus.send(SplashEvent.SPLASHING)

            try {
                when (val value = splashUseCases.useCaseAuthGetToken()) {
                    is UseCaseResult.UseCaseSuccess -> {
                        onEvent(SplashViewEvent.TokenLoaded)
                        initUserData()
                    }

                    else -> {
                        onEvent(SplashViewEvent.LoginFinished(isLoggedIn = false))
                    }
                }

            } catch(e: Exception){
                e.message?.let {
                    _eventBus.send(SplashEvent.Error(msg = it))
                } ?: run {_eventBus.send(SplashEvent.Error(msg = "Error 0")) }
                return@launch
            }


            val bbcArticles = scrapeArticlesFromBBCNews()

            bbcArticles.forEachIndexed { index, article ->
                println("Article ${index + 1}:")
                println("Title: ${article.title}")
                println("Description: ${article.description}")
                println("Link: ${article.link}")
                println("header: ${article.header}")
                println("image: ${article.image}")
                println("par: ${article.desc}")

                println()
            }


            onEvent(SplashViewEvent.LoadingChanged(isLoading = false))

            _eventBus.send(SplashEvent.SPLASHED)
            }
            /////////////////////////////////////////////////////////////////////////////////
        //}
        println("TIME123 SplashViewModel Init End")
    }

    data class Article(
        val title: String,
        val description: String,
        val link: String,
        val header: String,
        val image: String,
        val desc: String
    )


    fun scrapeArticlesFromBBCNews(): List<Article> {
        val url = "https://www.bbc.com/news"
        val articles = mutableListOf<Article>()

        val fullArticles = mutableListOf<Article>()

        try {
            val doc: Document = Jsoup.connect(url).get()
            val elements = doc.select("div.gs-c-promo")

            for (element in elements) {
                val title = element.select("h3.gs-c-promo-heading").text()
                val description = element.select("p.gs-c-promo-summary").text()
                val link = element.select("a.gs-c-promo-heading").attr("href")

                val fullLink = if(link.contains("www")) link else "https://www.bbc.com$link"

                val doc2: Document = Jsoup.connect(fullLink).get()
                val elements2 = doc2.select("#main-content")

                val header = elements2.select("#main-heading").text()
                val image = elements2.select("picture source").attr("srcset")

                val desc = elements2.select("p b").joinToString { it.text() }

                val article = Article(title, description, fullLink, header, image, desc)
                articles.add(article)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return articles
    }

    override fun onCleared() {
        //TODO: How to test?
        viewModelScope.cancel()

        splashJob?.cancel()
    }

    suspend fun initUserData(){
        //TODO: Convert to flow, emit status.LOADING
        when(val response = splashUseCases.useCaseUserGetCurrentUser()){
            is UseCaseResult.UseCaseSuccess -> {
                /*state.finishedLoading = true*/
                onEvent(SplashViewEvent.StatusChanged(viewState = SplashUiState.Ready, isLoading = false))
               // onEvent(SplashViewEvent.LoadingChanged(isLoading = false))
            }
            is UseCaseResult.Loading -> {
                /*state.finishedLoading = false*/
                onEvent(SplashViewEvent.LoadingChanged(isLoading = true))
            }
            else -> {
                /*state.finishedLoading = true*/
                onEvent(SplashViewEvent.StatusChanged(viewState = SplashUiState.Error(Exception("Loading Error")), isLoading = false))
            }
        }
    }

    /////////////////////View Events  --- View-ViewModel State Changes/////////////////////////
    fun onEvent(event: SplashViewEvent) {
        when (event) {
            is SplashViewEvent.ViewChanged -> {
                _state.value = _state.value.copy(viewState = event.viewState)
            }
            is SplashViewEvent.LoadingChanged -> {
                _state.value = _state.value.copy(isLoading = event.isLoading)
            }
            is SplashViewEvent.StatusChanged -> {
                _state.value = _state.value.copy(viewState = event.viewState, isLoading = event.isLoading)
            }

            is SplashViewEvent.TokenLoaded -> {
                _state.value = _state.value.copy(isLoggedIn = true)
            }

            is SplashViewEvent.LoginFinished -> {
                _state.value = _state.value.copy(isLoggedIn = event.isLoggedIn, viewState = SplashUiState.Ready, isLoading = false)
            }


            else -> { println("Unknown Event Called") }
        }
    }
    ////////////////////////////////////////////////////////


    //ViewModel State Classes
    data class SplashState(
        val isLoggedIn: Boolean = false,
        val isLoading: Boolean = false,
        val viewState: SplashUiState = SplashUiState.Default
    )

    sealed class SplashUiState {
        object Default: SplashUiState()
        object Ready: SplashUiState()
        data class Error(val exception: Throwable): SplashUiState()
    }


    //ViewModel Events
    sealed class SplashViewEvent {
        //GENERIC VIEWMODEL
        data class ViewChanged(val viewState: SplashUiState) : SplashViewEvent()
        data class LoadingChanged(val isLoading: Boolean) : SplashViewEvent()

        data class StatusChanged(val viewState: SplashUiState, val isLoading: Boolean) : SplashViewEvent()
        data class LoginChanged(val isLoggedIn: Boolean) : SplashViewEvent()

        //TokenLoaded
        //LoginFinished
        object TokenLoaded: SplashViewEvent()
        data class LoginFinished(val isLoggedIn: Boolean) : SplashViewEvent()

    }



}