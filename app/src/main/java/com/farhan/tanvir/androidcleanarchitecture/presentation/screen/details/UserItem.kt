package com.farhan.tanvir.androidcleanarchitecture.presentation.screen.details

import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import coil.size.Scale
import com.farhan.tanvir.androidcleanarchitecture.BuildConfig
import com.farhan.tanvir.androidcleanarchitecture.presentation.components.ButtonComponent
import com.farhan.tanvir.androidcleanarchitecture.presentation.components.RatingComponent
import com.farhan.tanvir.androidcleanarchitecture.presentation.components.WebViewComponent
import com.farhan.tanvir.androidcleanarchitecture.presentation.navigation.Screen
import com.farhan.tanvir.androidcleanarchitecture.ui.theme.ItemBackgroundColor
import com.google.gson.JsonObject


@Composable
fun UserItem(user: JsonObject?,
             navController: NavHostController,
             viewModel: UserDetailsViewModel = hiltViewModel()) {


    var showWebView = remember {mutableStateOf(false)}
    
    fun showWebview(){
        showWebView.value = true
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
    Card(
        modifier = Modifier
            .padding(top = 8.dp)
            .fillMaxHeight()
            .fillMaxWidth(),
        elevation = 4.dp,
        backgroundColor = MaterialTheme.colors.ItemBackgroundColor
    ) {
        Row(
            modifier = Modifier
                .height(IntrinsicSize.Max)
                .fillMaxWidth()
                .clickable {
                    Log.d("TIME123", "Console Log Clicked on USER CARD!" + user);
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                Modifier
                    .height(IntrinsicSize.Max)
                    .padding(
                        end = 2.dp,
                    )
                    .verticalScroll(rememberScrollState())
            ) {
                if(showWebView.value){
                    WebViewComponent(url = "www.google.com")
                }
                else {
                    Text(text = "Test Text", style = MaterialTheme.typography.body1)
                    user?.get("name")?.asString?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.body1
                        )
                    }
                    ButtonComponent(text = "TOKENS", {showWebview()}, true)
                    Spacer(modifier = Modifier.height(4.dp))
                    user?.get("payerEmail")?.asString?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.body2,
                            maxLines = 4,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    user?.get("id")?.asString?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.body1
                        )
                    }
                    user?.get("name")?.asString?.let { RatingComponent(rating = it) }

                    user?.get("images")?.asJsonArray.let {
                        it?.let {
                            for (image in it) {
                                if (image.asJsonObject.get("type").asString.equals("cover")) {
                                    Image(
                                        painter = rememberImagePainter(
                                            data = image.asJsonObject.get("url").asString,
                                            builder = {
                                                crossfade(true)
                                                scale(Scale.FIT)
                                            }),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(250.dp),
                                        contentScale = ContentScale.FillWidth
                                    )
                                } else if (image.asJsonObject.get("type").asString.equals("profile")) {
                                    Image(
                                        painter = rememberImagePainter(
                                            data = image.asJsonObject.get("url").asString,
                                            builder = {
                                                crossfade(true)
                                                scale(Scale.FIT)
                                            }),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(125.dp),
                                        contentScale = ContentScale.FillWidth
                                    )
                                } else {
                                    Text(
                                        text = "ELSE NO IMAGES???? ${image.asJsonObject.get("type")}",
                                        style = MaterialTheme.typography.body1
                                    )

                                }
                            }
                        }
                    } ?: run {
                        Text(text = "NO IMAGES????", style = MaterialTheme.typography.body1)
                    }

                    user?.get("email")?.asString?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.body1
                        )
                    }
                    // user?.get("images")?.asString?.let { Text(text = it, style = MaterialTheme.typography.body1) }
                    user?.get("payerBalance")?.asString?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.body1
                        )
                    }
                    user?.get("receiverBalance")?.asString?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.body1
                        )
                    }
                    user?.get("available")?.asString?.let {
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
                    }
                    user?.get("socketId")?.asString?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.body1
                        )
                    }
                    user?.get("firebaseDeviceToken")?.asString?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.body1
                        )
                    }
                    user?.get("contacts")?.toString()
                        ?.let { Text(text = it, style = MaterialTheme.typography.body1) }
                    user?.get("favorites")?.toString()
                        ?.let { Text(text = it, style = MaterialTheme.typography.body1) }
                    user?.get("history")?.toString()
                        ?.let { Text(text = it, style = MaterialTheme.typography.body1) }
                    user?.get("contributors")?.toString()
                        ?.let { Text(text = it, style = MaterialTheme.typography.body1) }
                    user?.get("sponsors")?.toString()
                        ?.let { Text(text = it, style = MaterialTheme.typography.body1) }

                    ButtonComponent(text = "Logout", { onLogoutClick() }, true)
                }
            }
        }
    }
}