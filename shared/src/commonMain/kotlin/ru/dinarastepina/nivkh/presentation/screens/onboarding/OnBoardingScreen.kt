package ru.dinarastepina.nivkh.presentation.screens.onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import ru.dinarastepina.nivkh.presentation.models.OnBoardingPage
import ru.dinarastepina.nivkh.presentation.screens.home.TabsScreen
import ru.dinarastepina.nivkh.presentation.utils.Tags

object OnBoardingScreen : Screen {
    override val key: ScreenKey
        get() = Tags.ONBOARDING_TITLE.tag

    @Composable
    override fun Content() {
        val vm = rememberScreenModel { OnBoardingVM() }

        OnBoardingContent(
            onFinish = {
                vm.onEvent(OnBoardingEvents.FinishEvent)
            }
        )
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalResourceApi::class)
@Composable
fun OnBoardingContent(
    onFinish: () -> Unit
) {
    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        4
    }

    val pages = listOf(
        OnBoardingPage(
            imagePath = "images/ob_dictionary.webp",
            content = "Добро пожаловать в приложение Нивх диф!\n Найдите нужное вам слово в словарях, используя дополнительные символы",
            title = "Словари"
        ),
        OnBoardingPage(
            imagePath = "images/ob_dialects.webp",
            content = "Для многих слов доступен поиск по разным диалектам (первый вариант - амурский нивхский, далее сахалинский нивхский, а также различные диалектные варианты.)",
            title = "Диалекты"
        ),
        OnBoardingPage(
            imagePath = "images/ob_topics.webp",
            content = "Выучите множество фраз из самых актуальных тем",
            title = "Фразы"
        ),
        OnBoardingPage(
            imagePath = "images/ob_phrases.webp",
            content = "Прослушайте и поделитесь ими с друзьями!",
            title = "Поделись"
        )
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            state = pagerState
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                with(pages[it]) {
                    val painter =
                        painterResource(imagePath)
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.background(
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        ).size(240.dp)
                    ) {
                        Image(
                            modifier = Modifier.fillMaxSize().padding(16.dp),
                            painter = painter,
                            contentDescription = "title",
                            contentScale = ContentScale.Crop
                        )
                    }
                    Text(
                        modifier = Modifier.padding(16.dp),
                        text = title,
                        style = MaterialTheme.typography.headlineMedium,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        modifier = Modifier.padding(16.dp),
                        text = content,
                        style = MaterialTheme.typography.headlineSmall,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        PagerIndicator(
            pagerState = pagerState
        )

        val navigator = LocalNavigator.currentOrThrow

        Button(
            modifier = Modifier.padding(16.dp),
            onClick = {
                onFinish()
                navigator.replace(TabsScreen)
            }) {
            Text(text = "Понятно!")
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PagerIndicator(
    pagerState: PagerState
) {
    Row(
        Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        horizontalArrangement = Arrangement.Center,
    ) {
        repeat(pagerState.pageCount) { iteration ->
            val color =
                if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
            Box(
                modifier = Modifier
                    .padding(2.dp)
                    .clip(CircleShape)
                    .background(color)
                    .size(8.dp)
            )
        }
    }
}