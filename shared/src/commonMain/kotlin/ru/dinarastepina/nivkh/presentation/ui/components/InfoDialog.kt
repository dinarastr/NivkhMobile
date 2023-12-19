package ru.dinarastepina.nivkh.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoDialog(
    onShow: (Boolean) -> Unit
) {

    AlertDialog(
        onDismissRequest = {
            onShow(false)
        },
        content = {
            SelectionContainer {
                Text(
                    modifier = Modifier.background(
                        MaterialTheme.colorScheme.background
                    ).padding(16.dp).verticalScroll(
                        rememberScrollState()
                    ),
                    text = "Приложение создано по инициативе РОО \"Ассоциация КМНС Хабаровского края\" при поддержке ООО \"Амур минералс\". В разработке приняли участие:\n" +
                            "Любовь Александровна Одзял - инициатива проекта, снабжение литературой\n" +
                            "Екатерина Андреевна Кя - языковой эксперт, озвучка\n" +
                            "Валентина Владимировна Габова - языковой эксперт, озвучка\n" +
                            "Григорий Коротких - техническая работа с текстом и аудиофайлами\n" +
                            "Динара Степина - разработка приложения, программирование\n" +
                            "Оксана Кучекта / Голдан - дизайн обложки и кнопки приложения\n" +
                            "Василий Харитонов - организация, методическое сопровождение и техническая работа\n" +
                            "Артём Ринчинов - дизайн приложения\n\n" +
                            "Отзывы и предложения: vasiliysan@list.ru"
                )
            }
        }
    )
}