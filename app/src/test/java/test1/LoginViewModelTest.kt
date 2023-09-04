package test1

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.JsonObject
import com.tiphubapps.ax.data.util.CoroutineContextProvider
import com.tiphubapps.ax.data.util.TestCoroutineContextProvider
import com.tiphubapps.ax.domain.repository.DefaultUsersRepository
import com.tiphubapps.ax.domain.repository.UseCaseResult
import com.tiphubapps.ax.domain.repository.UserRepository
import com.tiphubapps.ax.domain.useCase.CreateSessionByUsersUseCase
import com.tiphubapps.ax.domain.useCase.GetAllUsersUseCase
import com.tiphubapps.ax.domain.useCase.GetAllUsersWithTokenUseCase
import com.tiphubapps.ax.domain.useCase.GetCurrentUserUseCase
import com.tiphubapps.ax.domain.useCase.GetCurrentUserWithTokenUseCase
import com.tiphubapps.ax.domain.useCase.GetUserByIdUseCase
import com.tiphubapps.ax.domain.useCase.GetUsersByIdUseCase
import com.tiphubapps.ax.domain.useCase.GetUsersFromDBUseCase
import com.tiphubapps.ax.domain.useCase.LoginUseCases
import com.tiphubapps.ax.domain.useCase.PostLoginUseCase
import com.tiphubapps.ax.domain.useCase.UseCaseLogin
import com.tiphubapps.ax.domain.useCase.UserUseCases
import com.tiphubapps.ax.domain.useCase.users.UseCaseUserGetValue
import com.tiphubapps.ax.domain.useCase.users.UseCaseUserSetValue
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import com.tiphubapps.ax.rain.Rain
import com.tiphubapps.ax.rain.presentation.screen.details.LoginViewModel
import com.tiphubapps.ax.data.util.SessionManager
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.runner.RunWith
import javax.inject.Inject

//@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class LoginViewModelTest {
    private var fakeDefaultUsersRepository : DefaultUsersRepository = FakeDefaultUsersRepository()
    private var fakeUserRepository : UserRepository = FakeUserRepository()
    private var fakeUseCases : LoginUseCases = useCaseBuilder(fakeUserRepository)
    private var coroutineContextProvider : CoroutineContextProvider = TestCoroutineContextProvider()
    private lateinit var viewModel : LoginViewModel

    @Before
    fun setupDependencies() {

        //This makes me think it shouldn't be in the ViewModel
        val mockSessionManager = mock<SessionManager>{
          //  on { getEncryptedPreferencesValue("userToken") } doReturn("testToken123")
        }

        viewModel = LoginViewModel(fakeUseCases,
                                    mockSessionManager,
                                    coroutineContextProvider)
    }



    @Test
    fun testLogin() = runBlockingTest {
        val res = fakeUseCases.useCaseLogin("","")
        //viewModel.postLogin("","")

        val actual = UseCaseResult.UseCaseSuccess("1")

        Assert.assertEquals(res, actual)
    }

    @Test
    fun testGetValue() = runBlockingTest {
        val res = fakeUseCases.useCaseUserGetValue()

        val log = flowOf("Initial Value")

        val actual = UseCaseResult.UseCaseSuccess(log)

        Assert.assertEquals(res, actual)
    }

    @Test
    fun testSetValue() = runBlockingTest {
        val res = fakeUseCases.useCaseUserGetValue()
        val log = flowOf("Initial Value")
        val actual = UseCaseResult.UseCaseSuccess(log)

        fakeUseCases.useCaseUserSetValue!!("Next Value")
        val res2 = fakeUseCases.useCaseUserGetValue()
        val log2 = flowOf("Next Value")
        val actual2 = UseCaseResult.UseCaseSuccess(log2)

        Assert.assertEquals(res2, actual2)
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

        viewModel.updateErrorMessage("Service Issue.")

        val expected = viewModel.state.value.error
        val actual = "Service Issue."
        assertEquals(expected, actual)
    }

    @Test
    fun test6(){
        val expected0 = viewModel.state.value.error
        val actual0 = ""
        assertEquals(expected0, actual0)

        viewModel.updateErrorMessage("Input Error.")

        val expected = viewModel.state.value.error
        val actual = "Input Error."
        assertEquals(expected, actual)
    }

    @Test
    fun test7(){
        val expected0 = viewModel.state.value.error
        val actual0 = ""
        assertEquals(expected0, actual0)

        val msg = "Network Error."
        viewModel.updateErrorMessage(msg)

        val expected = viewModel.state.value.error
        val actual = "Network Error"
        assertEquals(expected, actual)
    }

    @Test
    fun test7a(){
        val expected0 = viewModel.state.value.error
        val actual0 = ""
        assertEquals(expected0, actual0)

        val msg = "Error."
        viewModel.updateErrorMessage(msg)

        val expected = viewModel.state.value.error
        val actual = "Unknown Error"
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
        assertEquals(expected, actual)
    }

    @Test
    fun testEventLoginClick() {
        val expected0 = ""
        val actual0 = viewModel.state.value.password
        assertEquals(expected0, actual0)

        val testValue = "Default Value"
        val event = LoginViewModel.LoginViewEvent.EmailChanged(testValue)
        viewModel.onEvent(event)
        val testValue1 = "Default Value1"
        val event1 = LoginViewModel.LoginViewEvent.PasswordChanged(testValue1)
        viewModel.onEvent(event1)

        val event2 = LoginViewModel.LoginViewEvent.LoginClicked
        viewModel.onEvent(event2)

        val expected = testValue1
        val actual = viewModel.state.value.password
        assertEquals(expected, actual)
    }

    @Test
    fun testEventSignupClick() {
        val expected0 = ""
        val actual0 = viewModel.state.value.password
        assertEquals(expected0, actual0)

        val testValue = "Default Value"
        val event = LoginViewModel.LoginViewEvent.EmailChanged(testValue)
        viewModel.onEvent(event)
        val testValue1 = "Default Value1"
        val event1 = LoginViewModel.LoginViewEvent.PasswordChanged(testValue1)
        viewModel.onEvent(event1)

        val event2 = LoginViewModel.LoginViewEvent.SignupClicked
        viewModel.onEvent(event2)

        val expected = testValue1
        val actual = viewModel.state.value.password
        assertEquals(expected, actual)
    }

    @Test
    fun testEventForgotClick() {
        val expected0 = ""
        val actual0 = viewModel.state.value.email
        assertEquals(expected0, actual0)

        val testValue = "Default Value"
        val event = LoginViewModel.LoginViewEvent.EmailChanged(testValue)
        viewModel.onEvent(event)

        val event2 = LoginViewModel.LoginViewEvent.ForgotClicked
        viewModel.onEvent(event2)

        val expected = testValue
        val actual = viewModel.state.value.email
        assertEquals(expected, actual)
    }
/*
    @Test
    fun test11() = runBlockingTest{

    }*/




    companion object {
        fun useCaseBuilder (userRepository: UserRepository): LoginUseCases{
            return LoginUseCases(
                useCaseLogin = UseCaseLogin(userRepository = userRepository),
                useCaseUserGetValue = UseCaseUserGetValue(userRepository = userRepository),
                useCaseUserSetValue = UseCaseUserSetValue(userRepository = userRepository)
            )
        }
    }
}