package com.sanjiudao.pdftools

import android.content.ContentValues
import android.content.Context
import android.graphics.Canvas
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.imageLoader
import coil.request.ImageRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.OutputStream

@Composable
fun Img_to_pdf(modifier: Modifier = Modifier){
    // 获取上下文
    val context = LocalContext.current

    // 选择图片
    val selectedImageUris = remember { mutableStateListOf<Uri>() }

    // 弹窗提示
    val snackbarScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    // 生成PDF
    val genPdfFromImgScope = rememberCoroutineScope()

    // 创建获取多个内容启动器
    val multiplePhotoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()
    ) { uris ->
        selectedImageUris.addAll(uris)
        Log.i("log", selectedImageUris.toString())
    }

    // 页面布局
    Box(modifier.fillMaxSize()){
        val scrollState = rememberScrollState()

        Column{
            Top_Title("图片 转 PDF")

            Row{
                Button(onClick = { multiplePhotoPicker.launch("image/*")}, Modifier.padding(3.dp)) {
                    Text("选择图片")
                }

                // 生成PDF
                Button(onClick = {
                    // 如果没有选择图片就直接退出
                    if (selectedImageUris.isEmpty()) return@Button

                    // 启动后台线程
                    genPdfFromImgScope.launch(Dispatchers.IO) {

                        // 运行 generatePdfFromImages ，并传入一个回调，提供弹窗功能
                        generatePdfFromImages(context, selectedImageUris) { text: String ->
                            snackbarScope.launch {
                                snackbarHostState.showSnackbar(
                                    text
                                )
                            }
                        }
                    }
                }, Modifier.padding(3.dp)) {
                    Text("生成PDF")
                }
            }

            Column(modifier
                .verticalScroll(scrollState)
                .fillMaxWidth()){
                // 遍历显示选择的图片
                selectedImageUris.forEachIndexed {index, uri ->
                    Row {
                        Image(
                            painter = rememberAsyncImagePainter(uri),
                            contentDescription = null,
                            modifier = Modifier
                                .size(200.dp)
                                .padding(8.dp)
                        )
                        Button(onClick = {selectedImageUris.removeAt(index)}, Modifier.padding(3.dp)) {
                            Text("移除")
                        }
                    }
                }
            }
        }

        // 提示插槽
        SnackbarHost(hostState = snackbarHostState, modifier = Modifier
            .align(Alignment.BottomCenter)
            .padding(16.dp))
    }
}

suspend fun generatePdfFromImages(
    context: Context,
    imageUris: List<Uri>,
    callback: (text: String) -> Job
) {
    // 1. 创建 PDF 文档
    val pdfDocument = PdfDocument()
    val imageLoader = context.imageLoader

    for (uri in imageUris) {
        // 2. 通过 Coil 获取图片 Bitmap
        val request = ImageRequest.Builder(context)
            .data(uri)
            .allowHardware(false)
            .build()

        val result = (imageLoader.execute(request).drawable) as? android.graphics.drawable.BitmapDrawable
        val bitmap = result?.bitmap ?: continue

        // 3. 创建 PDF 页面
        val pageInfo = PdfDocument.PageInfo.Builder(
            bitmap.width,
            bitmap.height,
            1
        ).create()

        val page = pdfDocument.startPage(pageInfo)
        val canvas: Canvas = page.canvas

        // 4. 把图片画到 PDF 页面
        canvas.drawBitmap(bitmap, 0f, 0f, null)
        pdfDocument.finishPage(page)
//        bitmap.recycle() // 释放内存
    }

    // 5. 保存 PDF 到手机
    savePdfToFile(context, pdfDocument, callback)
    pdfDocument.close()
}

// 保存 PDF 到本地
private fun savePdfToFile(
    context: Context,
    pdfDocument: PdfDocument,
    callback: (text: String) -> Job
) {
    val fileName = "图片转PDF_${System.currentTimeMillis()}.pdf"
    var outputStream: OutputStream? = null

    try {
        // 准备文件信息
        val values = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
            put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf")
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOCUMENTS)
        }

        // 插入记录
        val uri = context.contentResolver.insert(
            MediaStore.Files.getContentUri("external"),
            values
        )

        // 打开写入流
        outputStream = context.contentResolver.openOutputStream(uri!!)

        // 写入文件
        pdfDocument.writeTo(outputStream)
        callback("PDF 生成成功：$fileName")
    } catch (e: Exception) {
        e.printStackTrace()
        callback("PDF 生成失败：${e.message}")
    } finally {
        outputStream?.close()
    }
}