package ru.dinarastepina.nivkh.data.models

open class Word(
    open val id: Int,
    open val wordId: Int,
    open val type: String,
    open val content: String?
)

data class NivkhWord(
    override val id: Int,
    override val wordId: Int,
    override val type: String,
    override val content: String?
): Word(
    id = id,
    wordId = wordId,
    type = type,
    content = content
)

data class RussianWord(
    override val id: Int,
    override val wordId: Int,
    override val type: String,
    override val content: String?
): Word(
    id = id,
    wordId = wordId,
    type = type,
    content = content
)