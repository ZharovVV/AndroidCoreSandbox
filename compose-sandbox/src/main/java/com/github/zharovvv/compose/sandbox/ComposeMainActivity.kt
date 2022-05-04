package com.github.zharovvv.compose.sandbox

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.zharovvv.common.di.releaseFeature
import com.github.zharovvv.compose.sandbox.di.api.ComposeSandboxApi
import com.github.zharovvv.compose.sandbox.models.presentation.Message

class ComposeMainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MessageCard(
                message = Message(
                    author = "John Smith",
                    body = "Test body \nAnd some text else"
                )
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isFinishing) {
            releaseFeature<ComposeSandboxApi>()
        }
    }

    @Composable
    fun MessageCard(message: Message) {
        //В случае данной компоновки будет наложение текста друг на друга
//        Text(text = message.author)
//        Text(message.body)
        //Чтобы такого не было используются Row, Box и Column, которые отвечают за расположение
        //компонентов.
        Row(
            modifier = Modifier.padding(all = 8.dp)
        ) { //Размещение дочерних элементов, объявленных в теле лямбда выражения, "в одну строку"
            Image(
                painter = painterResource(R.drawable.ic_launcher_background),
                contentDescription = "Contact profile picture",
                modifier = Modifier
                    // Set image size to 40 dp
                    .size(40.dp)
                    // Clip image to be shaped as a circle
                    .clip(CircleShape)
                    .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
            )
            // Add a horizontal space between the image and the column
            Spacer(modifier = Modifier.width(8.dp))
            Column { //Размещение дочерних элементов, объявленных в теле лямбда выражения,
                // "в один столбец"
                Text(
                    text = message.author,
                    color = MaterialTheme.colors.secondaryVariant,
                    style = MaterialTheme.typography.subtitle2
                )
                // Add a vertical space between the author and message texts
                Spacer(modifier = Modifier.height(4.dp))
                Surface(shape = MaterialTheme.shapes.medium, elevation = 1.dp) {
                    Text(
                        text = message.body,
                        modifier = Modifier.padding(all = 4.dp),
                        style = MaterialTheme.typography.body2
                    )
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

    companion object {
        internal fun newIntent(context: Context): Intent =
            Intent(context, ComposeMainActivity::class.java)
    }
}