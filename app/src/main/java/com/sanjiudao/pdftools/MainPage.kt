package com.sanjiudao.pdftools

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

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
    Text(text = text, color =  Color.White, modifier = Modifier
        .background(Color.Blue)
        .fillMaxWidth()
        .padding(12.dp))
}

@Composable
fun Top_Tools_Item(text: String, onClick:()-> Unit){
    Button(onClick = {onClick()}, Modifier.padding(3.dp)) {
        Text(text)
    }
}

@Composable
fun Top_Tools_Area(){
    Row{
        Top_Tools_Item("图片转PDF") { GlobalNav.backStack.add("img_to_pdf") }
        Top_Tools_Item("PDF转图片") { GlobalNav.backStack.add("pdf_to_img") }
    }
}

