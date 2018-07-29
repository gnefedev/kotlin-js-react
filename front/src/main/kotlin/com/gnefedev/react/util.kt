package com.gnefedev.react

import kotlinext.js.clone
import kotlinx.coroutines.experimental.await
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.JSON
import react.Component
import react.RState
import kotlin.browser.window

private val serializer: JSON = JSON()

suspend fun <T> fetchJson(url: String, kSerializer: KSerializer<T>): T {
    val json = window.fetch(url).await().text().await()
    return serializer.parse(kSerializer, json)
}

inline fun <S : RState> Component<*, S>.updateState(action: S.() -> Unit) {
    setState(clone(state).apply(action))
}

class RProperty<T>(
    val value: T,
    val onChange: (T) -> Unit
)

infix fun <T> T.onChange(
  onChange: (T) -> Unit
): RProperty<T> = RProperty(this, onChange)
