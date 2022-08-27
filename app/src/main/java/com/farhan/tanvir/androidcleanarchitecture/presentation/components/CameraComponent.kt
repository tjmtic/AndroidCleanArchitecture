package com.farhan.tanvir.androidcleanarchitecture.presentation.components

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.farhan.tanvir.androidcleanarchitecture.presentation.screen.home.CameraPreview
import com.farhan.tanvir.androidcleanarchitecture.util.Permission
import com.google.accompanist.permissions.ExperimentalPermissionsApi

@ExperimentalPermissionsApi
@Composable
fun CameraComponent(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Permission(
        permission = Manifest.permission.CAMERA,
        rationale = "You said you wanted a picture, so I'm going to have to ask for permission.",
        permissionNotAvailableContent = {
            Column(modifier) {
                Text("O noes! No Camera!")
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        context.startActivity(
                            Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                                data = Uri.fromParts("package", context.packageName, null)
                            }
                        )
                    }
                ) {
                    Text("Open Settings")
                }
            }
        }
    ) {
        CameraPreview(modifier)
    }
}