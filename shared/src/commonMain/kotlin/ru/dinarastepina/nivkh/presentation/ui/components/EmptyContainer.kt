package ru.dinarastepina.nivkh.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import nivkhmobile.shared.generated.resources.Res
import nivkhmobile.shared.generated.resources.ic_empty_search
import org.jetbrains.compose.resources.painterResource


@Composable
fun EmptyContainer(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground),
                modifier = Modifier.size(80.dp),
                painter = painterResource(Res.drawable.ic_empty_search),
                contentDescription = "Ничего не найдено"
            )
            Text(text = "Ничего не найдено")
        }
    }
}