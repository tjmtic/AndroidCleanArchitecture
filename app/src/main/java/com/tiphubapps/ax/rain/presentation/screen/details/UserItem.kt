package com.tiphubapps.ax.rain.presentation.screen.details

import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.tiphubapps.ax.rain.R
import com.tiphubapps.ax.rain.presentation.components.ButtonComponent
import com.tiphubapps.ax.rain.presentation.components.CreateImageProfile
import com.tiphubapps.ax.rain.presentation.components.WebViewComponent
import com.google.gson.JsonObject


@Composable
fun UserItem(user: JsonObject?,
             extra: String,
             viewModel: UserDetailsViewModel = hiltViewModel()) {

    val interactionSource = remember { MutableInteractionSource() }
    var buttonScale by remember { mutableStateOf(1f) }

    var showWebView1 = remember {mutableStateOf(false)}
    var showWebView2 = remember {mutableStateOf(false)}
    var showWebView3 = remember {mutableStateOf(false)}
    
    fun showWebview1(){
        showWebView1.value = true
    }

    fun showWebview2(){
        showWebView2.value = true
    }

    fun showWebview3(){
        showWebView3.value = true
    }

    fun onLogoutClick(){
        viewModel.logout();
    }
    
    
    @Composable
    fun loadWebUrl(url: String) {
        AndroidView(factory = {
            WebView(it).apply {
                webViewClient = WebViewClient()

                loadUrl(url)
            }
        })
    }
    Box(
        modifier = Modifier.fillMaxSize()
            .background(Color(0xFF000000))
    ) {
        // Background image
        /*Image(
            painter = painterResource(id = R.drawable.background_image),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds,
        )*/

            Column(
                Modifier
                    .height(IntrinsicSize.Max)
                    .padding(
                        end = 2.dp,
                    )
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if(showWebView1.value){
                    Log.d("TIME123", "Showing webview1:"+extra)
                    WebViewComponent(url = "https://tiphubapps.com/webview/checkout/?token=" + extra)
                }
                else if(showWebView2.value){
                    Log.d("TIME123", "Showing webview2:"+extra)
                    WebViewComponent(url = "https://tiphubapps.com/webview/payout/?token=" + extra)
                }
                if(showWebView3.value){
                    Log.d("TIME123", "Showing webview3:"+extra)
                    WebViewComponent(url = "https://tiphubapps.com/webview/avatar/?token=" + extra)
                }
                else {

                    user?.get("name")?.asString?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.body1.merge(TextStyle(color = Color.White))
                        )
                    }

                    user?.get("images")?.asJsonArray.let {
                        it?.let {
                            for (image in it) {
                                if (image.asJsonObject.get("type").asString.equals("profile")) {
                                    CreateImageProfile(Modifier.clickable { showWebview3() }, url = image.asJsonObject.get("url").asString)
                                    /*Image(
                                        painter = rememberImagePainter(
                                            data = image.asJsonObject.get("url").asString,
                                            builder = {
                                                crossfade(true)
                                                scale(Scale.FIT)
                                            }),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .width(125.dp)
                                            .height(125.dp),
                                        contentScale = ContentScale.FillWidth
                                    )*/
                                }
                            }
                        }
                    }

                    Text(
                        text = "TIP BALANCE",
                        style = MaterialTheme.typography.body1.merge(TextStyle(color = Color.White))
                    )
                    user?.get("payerBalance")?.asString?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.body1.merge(TextStyle(color = Color.White))
                        )
                    }
                    ButtonComponent(text = "BUY TOKENS", {showWebview1()}, true)

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Earnings BALANCE",
                        style = MaterialTheme.typography.body1.merge(TextStyle(color = Color.White))
                    )
                    user?.get("receiverBalance")?.asString?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.body1.merge(TextStyle(color = Color.White))
                        )
                    }



                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Earnings balance will be transferred to the linked account.",
                        style = MaterialTheme.typography.body1.merge(TextStyle(color = Color.White))
                    )

                    user?.get("payerEmail")?.asString?.let {
                        /*Text(
                            text = it,
                            style = MaterialTheme.typography.body2,
                            maxLines = 4,
                            overflow = TextOverflow.Ellipsis
                        )*/
                        ButtonComponent(text = "REQUEST PAYOUT", {showWebview2()}, true)

                    }
                    Spacer(modifier = Modifier.height(8.dp))


                   /* user?.get("email")?.asString?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.body1
                        )
                    }*/
                    // user?.get("images")?.asString?.let { Text(text = it, style = MaterialTheme.typography.body1) }


                   /* user?.get("available")?.asString?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.body1
                        )
                    }
                    user?.get("balance")?.asString?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.body1
                        )
                    }*/


                    //ButtonComponent(text = "Logout", { onLogoutClick() }, true)

                    Button(
                        onClick = { onLogoutClick() },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Magenta),
                        modifier = Modifier
                            .fillMaxWidth()
                            .scale(buttonScale)
                            .pointerInput(interactionSource) {
                                detectTapGestures(
                                    onPress = { buttonScale = 0.95f },
                                    //l = { buttonScale = 1f }
                                )
                                //onPointerUp { buttonScale = 1f }
                                //onPointerCancel { buttonScale = 1f }
                            }
                            .padding(vertical = 16.dp)
                            .background(color = Color.Magenta, shape = RoundedCornerShape(25.dp))
                    ) {
                        Text(
                            text = "Logout",
                            style = MaterialTheme.typography.button,
                            modifier = Modifier.padding(horizontal = 8.dp),
                            color = Color.White
                        )
                    }
                }
            }

    }
}