package ru.dinarastepina.nivkh.presentation.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.cash.paging.compose.LazyPagingItems
import ru.dinarastepina.nivkh.presentation.models.Article

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DictionaryContent(
    startLanguageContent: @Composable () -> Unit,
    targetLanguageContent: @Composable () -> Unit,
    items: LazyPagingItems<Article>,
    onLanguageChange: () -> Unit,
    onSearch: (String) -> Unit,
    onEmptySearch: () -> Unit,
    query: MutableState<TextFieldValue>,
    additionalKeys: @Composable () -> Unit = {},
) {
    Scaffold(
        topBar = {
            LanguageBar(
                startContent = startLanguageContent,
                endContent = targetLanguageContent,
                onChangeLanguageClick = onLanguageChange
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.padding(
                top = padding.calculateTopPadding(),
                start = padding.calculateLeftPadding(LayoutDirection.Ltr),
                end = padding.calculateRightPadding(LayoutDirection.Ltr))
        ) {
            NivkhSearchBar(
                modifier = Modifier.padding(horizontal = 16.dp),
                query = query,
                onClearSearch = onEmptySearch,
                onValueChanged = {
                    query.value = query.value
                    onSearch(it)
                },
                hint = "Введите слово"
            )
            LazyColumn(
                modifier = Modifier.padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 16.dp
                ).fillMaxWidth()
            ) {
                stickyHeader {
                   additionalKeys()
                }
                items(items.itemCount) { position ->
                    val word = items[position]
                    word?.let { article ->
                        if (article.content.isNotBlank()) {
                            SelectionContainer {
                                when (article) {
                                    is Article.Original -> Text(
                                        text = article.content,
                                        style = MaterialTheme.typography.headlineSmall.copy(
                                            fontSize = 20.sp,
                                        )
                                    )

                                    is Article.Translation -> Text(
                                        text = article.content
                                    )

                                    is Article.Comment -> Text(
                                        text = article.content,
                                        style = MaterialTheme.typography.displaySmall
                                    )

                                    is Article.Separator -> Text(
                                        text = ""
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
