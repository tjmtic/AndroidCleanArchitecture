package test1

import android.content.Context
import android.content.SharedPreferences
import com.tiphubapps.ax.domain.repository.AppError
import com.tiphubapps.ax.domain.repository.Result
import com.tiphubapps.ax.domain.repository.UserRepository
import com.tiphubapps.ax.domain.useCase.UserUseCases
import com.tiphubapps.ax.rain.presentation.screen.details.LoginInnerViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
//import com.tiphubapps.ax.rain.presentation.screen.login.LoginViewModel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import com.tiphubapps.ax.rain.R
import com.tiphubapps.ax.rain.Rain
import com.tiphubapps.ax.rain.presentation.screen.details.LoginViewModel
import com.tiphubapps.ax.rain.util.SessionManager
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class LoginInnerViewModelTest {
    private lateinit var mockUseCases : UserUseCases
    private lateinit var mockUserRepository : UserRepository


    private lateinit var viewModel : LoginViewModel

    @Before
    fun setupDependencies() {

        //val application =  Mockito.mock(Application::class.java)

        val sharedPrefMock = mock<SharedPreferences>{}
        val context = Mockito.mock(Context::class.java)
        val con2 = mock<Context>{
           // on { getString(R.string.app_name) } doReturn("Rain by TipHub")
           // on { getSharedPreferences("Rain by TipHub", Context.MODE_PRIVATE) } doReturn(sharedPrefMock)
        }
        val app2 = mock<Rain>{
           on { applicationContext } doReturn(con2)
        }
       // Mockito.`when`(app2.applicationContext).thenReturn(con2)

        mockUseCases = mock<UserUseCases>{
           // on { it.useCaseLogin("","") } doReturn(Result.Success(JsonObject()))
        }
        mockUserRepository = mock<UserRepository>{
           // on {  }
        }

        val mockSessionManager = mock<SessionManager>{
            on { getEncryptedPreferencesValue("userToken") } doReturn("testToken123")
        }
        val mockViewModel = mock<LoginViewModel>{

        }
        viewModel = LoginViewModel(mockUseCases, mockUserRepository, mockSessionManager, app2)
    }


    ////////////////////////ViewState
    //Boundary Path
    @Test
    fun test0() {
        val expected = LoginViewModel.LoginUiState.Default

        val actual = viewModel.state.value.viewState

        assertEquals(expected, actual)
    }

    //Success Path
    @Test
    fun test1() {
        viewModel.showLogin()

        val expected = viewModel.state.value.viewState
        val actual = LoginViewModel.LoginUiState.Login
        assertEquals(expected, actual)
    }

    @Test
    fun test2() {
        val expected0 = viewModel.state.value.viewState
        val actual0 = LoginViewModel.LoginUiState.Default
        assertEquals(expected0, actual0)

        viewModel.showSignup()

        val expected = viewModel.state.value.viewState
        val actual = LoginViewModel.LoginUiState.Signup
        assertEquals(expected, actual)
    }

    @Test
    fun test3() {
        val expected0 = viewModel.state.value.viewState
        val actual0 = LoginViewModel.LoginUiState.Default
        assertEquals(expected0, actual0)

        viewModel.showForgot()

        val expected = viewModel.state.value.viewState
        val actual = LoginViewModel.LoginUiState.Forgot
        assertEquals(expected, actual)
    }

    //Error Path
    //...

    //Boundary Path
    @Test
    fun test4() {
        val expected0 = viewModel.state.value.viewState
        val actual0 = LoginViewModel.LoginUiState.Default
        assertEquals(expected0, actual0)

        viewModel.showForgot()
        viewModel.showSignup()
        viewModel.showLogin()

        val expected = viewModel.state.value.viewState
        val actual = LoginViewModel.LoginUiState.Login
        assertEquals(expected, actual)
    }



    ////////////////////////////////////Error Message
    @Test
    fun test5(){
        val expected0 = viewModel.state.value.error
        val actual0 = ""
        assertEquals(expected0, actual0)

        viewModel.updateErrorMessage(AppError.NetworkError)

        val expected = viewModel.state.value.error
        val actual = "Service Issue."
        assertEquals(expected, actual)
    }

    @Test
    fun test6(){
        val expected0 = viewModel.state.value.error
        val actual0 = ""
        assertEquals(expected0, actual0)

        viewModel.updateErrorMessage(AppError.InputError("Input Error"))

        val expected = viewModel.state.value.error
        val actual = "Input Error."
        assertEquals(expected, actual)
    }

    @Test
    fun test7(){
        val expected0 = viewModel.state.value.error
        val actual0 = ""
        assertEquals(expected0, actual0)

        val msg = "Error."
        viewModel.updateErrorMessage(AppError.CustomError(msg))

        val expected = viewModel.state.value.error
        val actual = msg
        assertEquals(expected, actual)
    }


////////////////////////////////////////Events
    @Test
    fun test8() {
        val expected0 = ""
        val actual0 = viewModel.state.value.name
        assertEquals(expected0, actual0)

        val testValue = "Default Value"
        val event = LoginViewModel.LoginViewEvent.NameChanged(testValue)
        viewModel.onEvent(event)

        val expected = testValue
        val actual = viewModel.state.value.name
        assertEquals(testValue, actual)
    }

    @Test
    fun test9() {
        val expected0 = ""
        val actual0 = viewModel.state.value.email
        assertEquals(expected0, actual0)

        val testValue = "Default Value"
        val event = LoginViewModel.LoginViewEvent.EmailChanged(testValue)
        viewModel.onEvent(event)

        val expected = testValue
        val actual = viewModel.state.value.email
        assertEquals(testValue, actual)
    }

    @Test
    fun test10() {
        val expected0 = ""
        val actual0 = viewModel.state.value.password
        assertEquals(expected0, actual0)

        val testValue = "Default Value"
        val event = LoginViewModel.LoginViewEvent.PasswordChanged(testValue)
        viewModel.onEvent(event)

        val expected = testValue
        val actual = viewModel.state.value.password
        assertEquals(testValue, actual)
    }

   /* @Test
    fun test11() = runBlockingTest{

    }*/
}