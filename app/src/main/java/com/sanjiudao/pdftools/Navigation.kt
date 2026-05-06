package com.sanjiudao.pdftools

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList

object GlobalNav{
    val backStack: SnapshotStateList<String> = mutableStateListOf("main")
}