package ru.dinarastepina.nivkh.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.seiko.imageloader.rememberAsyncImagePainter

@Composable
fun TopicCard(
    modifier: Modifier = Modifier,
    title: String,
    imgUrl: String,
    onSelected: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val topicPressed = interactionSource.collectIsPressedAsState()

    Card(modifier = modifier.height(80.dp).fillMaxWidth().clickable(
        interactionSource = interactionSource,
        indication = null
    ) {
        onSelected()
    },
        elevation = CardDefaults.cardElevation(
            4.dp
        )) {
            Row(
                modifier = modifier.background(
                    if (topicPressed.value.not()) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.tertiary
                ),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        letterSpacing = 0.15.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    ),
                    modifier = Modifier.padding(horizontal = 16.dp).weight(1f)
                )
                val painter =
                    rememberAsyncImagePainter(imgUrl)
                    Image(
                        modifier = Modifier.fillMaxHeight().width(80.dp),
                        painter = painter,
                        contentDescription = title,
                        contentScale = ContentScale.Crop
                    )
                }
            }

}