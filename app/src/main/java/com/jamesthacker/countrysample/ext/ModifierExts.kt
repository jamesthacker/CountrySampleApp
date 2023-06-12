package com.jamesthacker.countrysample.ext

import androidx.compose.ui.Modifier

/**
 * Applies a modifier conditionally based on the value of [condition].
 * Helps keep the Modifier method chain clean.
 */
inline fun Modifier.on(condition: Boolean, then: Modifier.() -> Modifier): Modifier {
    return if (condition) {
        this.then()
    } else {
        this
    }
}
