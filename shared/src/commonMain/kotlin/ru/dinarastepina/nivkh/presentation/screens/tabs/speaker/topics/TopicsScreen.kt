package ru.dinarastepina.nivkh.presentation.screens.tabs.speaker.topics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import ru.dinarastepina.nivkh.presentation.models.Topic
import ru.dinarastepina.nivkh.presentation.screens.tabs.speaker.phrases.PhrasesScreen
import ru.dinarastepina.nivkh.presentation.ui.components.TopicCard
import ru.dinarastepina.nivkh.presentation.utils.Tags

object TopicsScreen : Screen {

    override val key: ScreenKey = Tags.TOPICS_SCREEN_TITLE.tag

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val topicsVm = rememberScreenModel { TopicsVM() }
        val topicsState by topicsVm.state.collectAsState()

        LifecycleEffect(
            onStarted = {
                topicsVm.onEvent(
                    TopicsEvents.LoadTopics
                )
            }
        )

        val navigator = LocalNavigator.currentOrThrow

        when (val state = topicsState) {
            is TopicsState.TopicsLoaded ->

                Scaffold(
                    topBar = {
                        TopAppBar(
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            ),
                            title = {
                                Text(
                                    text = "Темы",
                                    color = MaterialTheme.colorScheme.onPrimary
                                )
                            },
                            actions = {
                                IconButton(
                                    onClick = {
                                        //todo: add search
                                        //navigator.push(null)
                                    }
                                ) {
                                    Icon(Icons.Filled.Search, contentDescription = null)
                                }
                            }
                        )
                    }
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize().padding(
                            it
                        ),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        TopicsList(
                            topics = state.topics,
                            onTopicSelected = {
                                navigator.push(PhrasesScreen(it))
                            }
                        )
                    }
                }
        }
    }
}

@Composable
fun TopicsList(
    topics: List<Topic>,
    onTopicSelected: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        topics.forEach { topic ->
            item(key = topic.title) {
                TopicCard(
                    imgUrl = topic.imageUrl,
                    title = topic.title
                ) {
                    onTopicSelected(topic.title)
                }
            }
        }
    }
}