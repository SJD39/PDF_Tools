package com.sanjiudao.pdftools

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import com.sanjiudao.pdftools.ui.theme.PDFToolsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {

            PDFToolsTheme {
                Column{
                    Spacer(
                        modifier = Modifier.statusBarsPadding()
                    )
                    MyApp()
                }
            }
        }
    }
}

@Composable
fun MyApp(){
    NavDisplay(GlobalNav.backStack){key ->
        when(key){
            "main" -> NavEntry(key) {MainPage()}
            "pdf_to_img" -> NavEntry(key){Pdf_to_img()}
            "img_to_pdf" -> NavEntry(key){Img_to_pdf()}
            else -> NavEntry(key){}
        }
    }
}
