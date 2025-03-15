package com.reishandy.chatroom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.reishandy.chatroom.ui.ChatroomApp
import com.reishandy.chatroom.ui.theme.ChatroomTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChatroomTheme {
                ChatroomApp()
            }
        }
    }
}
