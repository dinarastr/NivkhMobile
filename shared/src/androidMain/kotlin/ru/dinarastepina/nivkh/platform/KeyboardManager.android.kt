package ru.dinarastepina.nivkh.platform

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

actual class KeyboardManager(private val context: Context) {
    
    private val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    
    actual fun hideKeyboard() {
        val currentFocus = (context as? android.app.Activity)?.currentFocus
        currentFocus?.let { view ->
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
    
    actual fun showKeyboard() {
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }
}

@Composable
actual fun Modifier.hideKeyboardOnTap(keyboardManager: KeyboardManager): Modifier {
    return this.clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() }
    ) {
        keyboardManager.hideKeyboard()
    }
}

@Composable
actual fun rememberKeyboardManager(): KeyboardManager {
    val context = LocalContext.current
    return remember { KeyboardManager(context) }
} 