package com.example.events

sealed class PageEventKey : AppEvent {
    data object WelcomeUserEvent : PageEventKey()
}