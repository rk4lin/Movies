package com.roman_kalinin.movies

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

object EventBus {

    private var _events = MutableSharedFlow<AppEvent>()
    val eventsFlow: SharedFlow<AppEvent> = _events

    suspend fun invokeEvent(event: AppEvent) = _events.emit(event)

}
