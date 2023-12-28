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
                    text =
                    "Приложение создано по инициативе РОО \"Ассоциация Коренных малочисленных народов Севера Ульчского района\" при финансовой поддержке Федерального агентства по делам молодёжи (Росмолодёжь) в рамках форума молодёжи коренных малочисленных народов Севера, Сибири и Дальнего Востока Российской Федерации \"Российский Север\" 2022 года.\n" +
                            "Пассар Пётр Андреевич - руководитель проекта, представление и защита проекта, победитель грантового конкурса. Заместитель председателя РОО \"Ассоциация КМНС Ульчского района\"\n" +
                            "Тэмина Марина Григорьевна - озвучка и доработка разговорника, разработка обложки и названия приложения, идея дизайна кнопки. Преподаватель родного (нивхского) языка КГБ ПОУ \"Николаевский-на-Амуре промышленно-гуманитарный техникум\", кандидат исторических наук\n" +
                            "Оксана Кучекта / Голдан - дизайн кнопки приложения\n" +
                            "Динара Степина - разработка приложения, программирование\n" +
                            "Артём Ринчинов - дизайн приложения\n" +
                            "Василий Харитонов - техническая работа с текстом и звуком\n\n" +
                            "Создано на основе: \n" +
                            "- Таксами Ч. М. Нивхско-русский и русско-нивхский словарь: Пособие для уч-ся нач. шк.— 2-е изд., дораб.— СПб.: отд-ние изд-ва «Просвещение», 1996.— 207 с.\n" +
                            "- Пухта М.Н. Русско-нивхский разговорник. Пособие для воспитателей детского сада и учителей начальной школы. Сост. Пухта М.Н., под ред. Кудри А.А. Институт национальных проблем образования Минобразования России и Минфедерации России. Федеральная целевая программа \"Дети Севера\". Москва, 2001\n" +
                            "- Пухта М.Н. Нивхско-русский разговорник и тематический словарь. Под ред. Лок Г.Д. и Т.Канэко. Endangered languages of the Pacific RIM (ELPR), 2002\n" +
                            "- Русско-нивхский разговорник (амурский диалект) / Хайлова Р. П., Лютова З. И., Хурьюн А.В., Иванова Л.Л. - Южно-Сахалинск : Сахалинское книжное издательство, 2007. - 79 с.\n" +
                            "\n" +
                            "Отзывы и предложения: vasiliysan@list.ru"
                )
            }
        }
    )
}
