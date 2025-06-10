package ru.dinarastepina.nivkh.platform

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Platform-specific keyboard manager for hiding keyboards
 */
expect class KeyboardManager {
    /**
     * Hides the software keyboard
     */
    fun hideKeyboard()
    
    /**
     * Shows the software keyboard
     */
    fun showKeyboard()
}

/**
 * Modifier that hides keyboard when tapped outside input fields
 */
@Composable
expect fun Modifier.hideKeyboardOnTap(keyboardManager: KeyboardManager): Modifier

/**
 * Composable function to get platform-specific keyboard manager
 */
@Composable
expect fun rememberKeyboardManager(): KeyboardManager 