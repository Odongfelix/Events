package com.example.events.viewmodel

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.events.PageEventKey
import com.example.events.PageEventsHandler
import com.example.events.WelcomeUserImpl
import com.example.events.WelcomeUserUseCase

class TestViewModel : ViewModel(),
    WelcomeUserUseCase by WelcomeUserImpl(), LifecycleEventObserver {
    var pageEventsHandler: PageEventsHandler? = null

    init {
        pageEventsHandler = PageEventsHandler(viewModelScope)
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        //add welcome event in on create of the activity life cycle
        if (event == Lifecycle.Event.ON_CREATE) {
            pageEventsHandler?.addEvent(PageEventKey.WelcomeUserEvent)
        }

    }
}