package com.example.events

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.util.LinkedList


interface AppEvent

object NoAppEvent : AppEvent

class PageEventsHandler(private val scope: CoroutineScope) {
    private val eventsList = LinkedList<AppEvent>()
    private var currentEvent: AppEvent = NoAppEvent

    private val _events =
        MutableSharedFlow<AppEvent>(replay = Int.MAX_VALUE, extraBufferCapacity = Int.MAX_VALUE)
    val events: SharedFlow<AppEvent> = _events

    private val eventChannel = Channel<Unit>(Channel.CONFLATED)

    init {
        scope.launch {
            while (isActive) {
                eventChannel.receive() // Suspend until a new event is added or current event is completed
                // Process events as long as there are events in the list and no current event is being processed
                while (eventsList.isNotEmpty() && currentEvent == NoAppEvent) {
                    val event = eventsList.poll() ?: break
                    currentEvent = event
                    _events.emit(event)
                }
            }
        }
    }

    fun addEvent(event: AppEvent) {
        eventsList.offer(event) // Use offer to maintain FIFO order
        eventChannel.trySend(Unit) // Signal that a new event has been added
    }

    fun completeEvent() {
        currentEvent = NoAppEvent
        scope.launch { _events.emit(NoAppEvent) }
        eventChannel.trySend(Unit) // Signal that the current event has been completed
    }
}
