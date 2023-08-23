package test1

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
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
            //on {  }
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

    @Test
    fun test1() {
        assertEquals(true, true)
    }
}