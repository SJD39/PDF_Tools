package com.sanjiudao.pdftools

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Pdf_to_img(modifier: Modifier = Modifier){
    Box(modifier.fillMaxSize()){
        Column{
            Top_Title("PDF 转 图片")
        }
    }
}