package com.github.zharovvv.compose.sandbox.ui.message

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.zharovvv.compose.sandbox.R
import com.github.zharovvv.compose.sandbox.models.ui.Message

@Composable
fun MessageCardList(messages: List<Message>, contentPadding: PaddingValues = PaddingValues(0.dp)) {
    LazyColumn(
        modifier = Modifier.padding(top = 48.dp, bottom = 16.dp),
        contentPadding = contentPadding
    ) {
        items(messages) { message ->
            MessageCard(message)
        }
    }
}


@Composable
fun MessageCard(message: Message) {
    //В случае данной компоновки будет наложение текста друг на друга
//        Text(text = message.author)
//        Text(message.body)
    //Чтобы такого не было используются Row, Box и Column, которые отвечают за расположение
    //компонентов.
    Box(
        modifier = Modifier
            .fillMaxWidth()
//        .border(1.5.dp, MaterialTheme.colorScheme.secondaryContainer, RectangleShape),
    ) {
        Row(
            modifier = Modifier.padding(all = 8.dp)
        ) { //Размещение дочерних элементов, объявленных в теле лямбда выражения, "в одну строку"
            //this is RowScope
            Image(
                painter = painterResource(R.drawable.ic_launcher_background),
                contentDescription = "Contact profile picture",
                modifier = Modifier
                    // Set image size to 40 dp
                    .size(40.dp)
                    // Clip image to be shaped as a circle
                    .clip(CircleShape)
                    .border(1.5.dp, MaterialTheme.colorScheme.secondaryContainer, CircleShape)
            )
            // Add a horizontal space between the image and the column
            Spacer(modifier = Modifier.width(8.dp))
            Column { //Размещение дочерних элементов, объявленных в теле лямбда выражения,
                // "в один столбец"
                //this is ColumnScope
                Text(
                    text = message.author,
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.titleMedium
                )
                // Add a vertical space between the author and message texts
                Spacer(modifier = Modifier.height(4.dp))
                Surface(
                    shape = MaterialTheme.shapes.medium,
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    elevation = 1.dp
                ) {
                    Text(
                        text = message.body,
                        modifier = Modifier.padding(all = 4.dp),
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

/**
 * Аннотация @Preview позволяет предварительно просматривать составные функции в Android Studio
 * без необходимости сборки и установки приложения на устройство Android или в эмулятор.
 * Аннотация должна использоваться в составной функции, которая не принимает параметры.
 * По этой причине вы не можете просмотреть MessageCard функцию напрямую.
 * Вместо этого создайте вторую функцию с именем PreviewMessageCard,
 * которая вызывается MessageCard с соответствующим параметром.
 * Добавьте @Preview аннотацию перед @Composable.
 */
@Preview
@Composable
fun PreviewMessageCard() {
    MessageCard(
        message = Message(
            author = "John Smith",
            body = "Test body \nAnd some text else"
        )
    )
}