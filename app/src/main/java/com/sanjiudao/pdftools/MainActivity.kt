package com.sanjiudao.pdftools

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import com.sanjiudao.pdftools.ui.theme.PDFToolsTheme

object GlobalNav{
    val backStack: SnapshotStateList<String> = mutableStateListOf("main")
}


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


@Composable
fun MainPage(modifier: Modifier = Modifier){
    Box(modifier.fillMaxSize()){
        Column{
            Top_Title("PDF Tools")
            Top_Tools_Area()
        }
    }
}

@Composable
fun Top_Title(text: String){
    Text(text = text, color =  Color.White, modifier = Modifier.background(Color.Blue).fillMaxWidth().padding(12.dp))
}

@Composable
fun Top_Tools_Item(text: String, onClick:()-> Unit){
    Button(onClick = {onClick()}, Modifier.padding(3.dp)) {
        Text(text)
    }
}

fun rememberLauncherForActivityResult(contract: Any, onResult: Any) {}

@Composable
fun Top_Tools_Area(){
    Row{
        Top_Tools_Item("图片转PDF") { GlobalNav.backStack.add("img_to_pdf") }
        Top_Tools_Item("PDF转图片") { GlobalNav.backStack.add("pdf_to_img") }
    }
}




@Composable
fun Img_to_pdf(modifier: Modifier = Modifier){
    Box(modifier.fillMaxSize()){
        Column{
            Top_Title("图片 转 PDF")
            Button(onClick = {}, Modifier.padding(3.dp)) {
                Text("选择图片")
            }
        }
    }
}

@Composable
fun Pdf_to_img(modifier: Modifier = Modifier){
    Box(modifier.fillMaxSize()){
        Column{
            Top_Title("PDF 转 图片")
        }
    }
}