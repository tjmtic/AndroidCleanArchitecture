package com.tiphubapps.ax.rain.presentation.screen.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tiphubapps.ax.rain.R
import com.tiphubapps.ax.rain.BuildConfig
import com.tiphubapps.ax.rain.presentation.components.*
import com.tiphubapps.ax.rain.presentation.screen.login.LoginItem
import com.tiphubapps.ax.rain.presentation.components.LoginHeaderComponent
import com.tiphubapps.ax.rain.presentation.components.TextButtonComponent

@Composable
fun LoginDetailsContent(
    onLoginClick: () -> Unit,
    showSignupClick: () -> Unit,
    showForgotClick: () -> Unit,
    enabled: Boolean,
) {
    val scrollState = rememberScrollState()
    Card(
        elevation = 0.dp,
    ) {
        Image(painter = painterResource(id = R.mipmap.rain_raindrop_barcode),
            contentDescription = null,
            contentScale = ContentScale.FillHeight,
            modifier = Modifier
                .fillMaxHeight()
                /*.blur(
                    radiusX = 10.dp,
                    radiusY = 10.dp,
                    edgeTreatment = BlurredEdgeTreatment(RoundedCornerShape(8.dp))
                )*/)


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            /*Image(
                painter = rememberImagePainter(
                    data = BuildConfig.POSTER_URL, builder = {
                        crossfade(true)
                        scale(Scale.FIT)
                    }),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                contentScale = ContentScale.FillWidth
            )

            Text(
                text = "RAIN",
                style = MaterialTheme.typography.h2
            )*/
            LoginHeaderComponent("RAIN", BuildConfig.POSTER_URL)
            //input
            //input
            //button - login -
                        // viewModel.postLogin(input1, input2)


            LoginItem( onLoginClick, enabled)

            Column(
                modifier = Modifier
                    .height(IntrinsicSize.Max)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                TextButtonComponent(text = "Don't have an account? Sign up here", { showSignupClick() }, enabled)
            }

            Column(
                modifier = Modifier
                    .height(IntrinsicSize.Max)
                    .fillMaxWidth(),
                //verticalAlignment = Alignment.CenterVertically,
                //verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                TextButtonComponent(text = "Forgot Password", { showForgotClick() }, enabled)
            }
        }
    }
}

@Composable
@Preview
fun previewLoginDetailsContent(){
    LoginDetailsContent(
        onLoginClick = { },
        showSignupClick = { /*TODO*/ },
        showForgotClick = { /*TODO*/ },
        enabled = true
    )
}