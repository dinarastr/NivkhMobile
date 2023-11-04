package ru.dinarastepina.nivkh.presentation.models

import androidx.compose.runtime.Immutable
import ru.dinarastepina.nivkh.data.models.NivkhWord
import ru.dinarastepina.nivkh.data.models.RussianWord

@Immutable
data class UINivkhWord(
    val id: Int,
    val content: List<Article>
)

@Immutable
data class UIRussianWord(
    val id: Int,
    val content: List<Article>
)

sealed class Article(open val content: String) {
    data class Original(override val content: String): Article(content)
    data class Translation(override val content: String): Article(content)
    data class Comment(override val content: String): Article(content)
}

fun NivkhWord.toArticle(): Article {
    return when (this.type) {
        "нивхский" -> Article.Original(this.content.orEmpty())
        "русский" -> Article.Translation(this.content.orEmpty())
        "коммент" -> Article.Comment(this.content.orEmpty())
        else -> throw IllegalArgumentException("incorrect article type")
    }
}

fun RussianWord.toArticle(): Article {
    return when (this.type) {
        "русский" -> Article.Original(this.content.orEmpty())
        "нивхский" -> Article.Translation(this.content.orEmpty())
        "коммент" -> Article.Comment(this.content.orEmpty())
        else -> throw IllegalArgumentException("incorrect article type")
    }
}