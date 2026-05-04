package com.sanjiudao.pdftools

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sanjiudao.pdftools.ui.theme.PDFToolsTheme

data object ProductList
data class ProductDetail(val id: String)

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
                    Top_Title("PDF Tools")
                    Top_Tools_Area()
                }
            }
        }
    }
}



@Composable
fun Top_Title(text: String){
    Text(text = text, color =  Color.White, modifier = Modifier.background(Color.Blue).fillMaxWidth().padding(12.dp))
}

@Composable
fun Top_Tools_Item(text: String, onClick:()-> Unit){
    Button(onClick = {onClick()}, modifier = Modifier.padding(3.dp)) {
        Text(text)
    }
}

@Composable
fun Top_Tools_Area(){
    Row{
        Top_Tools_Item("图片转PDF", {})
        Top_Tools_Item("PDF转图片", {})
    }
}