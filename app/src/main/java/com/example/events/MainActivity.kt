package com.example.events

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.events.ui.theme.EventsTheme
import com.example.events.view.PageEvent
import com.example.events.viewmodel.TestViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = TestViewModel()
        lifecycle.addObserver(viewModel)
        enableEdgeToEdge()
        setContent {
            EventsTheme {
                Scaffold { paddingValues ->
                    Row(modifier = Modifier.padding(paddingValues)) {
                        PageEvent(viewModel = viewModel)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    EventsTheme {
        PageEvent(viewModel = TestViewModel())
    }
}