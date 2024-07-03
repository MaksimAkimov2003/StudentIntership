package ru.hits.studentintership.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

/**
 * Queue to handle one-time events.
 *
 * It buffers events and emits them [EventQueue].
 * Events queue can be used to show messages or errors to a user once.
 * @see Event
 */
class EventQueue {

  private val eventsQueue = MutableSharedFlow<Event>(
    onBufferOverflow = BufferOverflow.DROP_LATEST,
    replay = 0,
    extraBufferCapacity = 32
  )

  /** Adds given [event] to the queue. */
  fun offerEvent(event: Event) {
    if (!eventsQueue.tryEmit(event)) {
      CoroutineScope(Dispatchers.IO).launch {
        eventsQueue.emit(event)
      }
    }
  }

  /**
   * Collect `EventQueue` events with your custom handler
   */
  @Suppress("ComposableEventParameterNaming")
  @Composable
  fun CollectEvent(eventHandler: (Event) -> Unit) {
    LaunchedEffect(key1 = Unit, block = {
      eventsQueue.collect {
        eventHandler(it)
      }
    })
  }
}
