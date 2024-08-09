package com.example.events.view

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.example.events.NoAppEvent
import com.example.events.PageEventKey
import com.example.events.viewmodel.TestViewModel

@Composable
fun PageEvent(viewModel: TestViewModel) {
    val event = viewModel.pageEventsHandler?.events?.collectAsState(NoAppEvent)
    when (event?.value) {
        NoAppEvent -> Text(text = "No event")
        PageEventKey.WelcomeUserEvent -> Text(text = "Welcome")//have not called PageEventHandler.completeEvent() in this demo
    }
}