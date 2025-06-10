package ru.dinarastepina.nivkh.platform

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import platform.UIKit.UIApplication
import platform.UIKit.endEditing

actual class KeyboardManager {
    
    actual fun hideKeyboard() {
        UIApplication.sharedApplication.keyWindow?.endEditing(true)
    }
    
    actual fun showKeyboard() {

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
    return remember { KeyboardManager() }
} 