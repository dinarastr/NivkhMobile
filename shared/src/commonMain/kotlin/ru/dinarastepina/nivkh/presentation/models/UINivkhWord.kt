package ru.dinarastepina.nivkh.presentation.models

import androidx.compose.runtime.Immutable
import ru.dinarastepina.nivkh.data.models.NivkhWord
import ru.dinarastepina.nivkh.data.models.RussianWord

@Immutable
sealed class Article(
    open val wordId: Int = 0,
    open val content: String
) {
    data class Original(
        override val wordId: Int,
        override val content: String
    ): Article(wordId, content)
    data class Translation(
        override val wordId: Int,
        override val content: String
    ): Article(wordId, content)
    data class Comment(
        override val wordId: Int,
        override val content: String
    ): Article(wordId, content)
    data class Separator(
        override val content: String
    ): Article(content = content)
}

fun NivkhWord.toArticle(): Article {
    return when (this.type) {
        "нивхский" -> Article.Original(
            this.wordId,
            this.content.orEmpty())
        "русский" -> Article.Translation(
            this.wordId,
            this.content.orEmpty())
        "коммент" -> Article.Comment(
            this.wordId,
            this.content.orEmpty()
        )
        else -> throw IllegalArgumentException("incorrect article type")
    }
}

fun RussianWord.toArticle(): Article {
    return when (this.type) {
        "русский" -> Article.Original(
            this.wordId,
            this.content.orEmpty())
        "нивхский" -> Article.Translation(
            this.wordId,
            this.content.orEmpty())
        "коммент" -> Article.Comment(
            this.wordId,
            this.content.orEmpty())
        else -> throw IllegalArgumentException("incorrect article type")
    }
}