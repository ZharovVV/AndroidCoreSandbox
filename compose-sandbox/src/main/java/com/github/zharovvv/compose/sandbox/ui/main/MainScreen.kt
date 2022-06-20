package com.github.zharovvv.compose.sandbox.ui.main

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.github.zharovvv.compose.sandbox.models.ui.Message
import com.github.zharovvv.compose.sandbox.ui.message.MessageCardList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    //Scaffold реализует базовую структуру визуального макета Material Design.
    //
    //Этот компонент предоставляет API для объединения нескольких материальных
    // компонентов для создания вашего экрана, обеспечивая для них правильную стратегию
    // компоновки и собирая необходимые данные,
    // чтобы эти компоненты работали вместе правильно.
    Scaffold { contentPadding ->
        //если не использвать функцию remember для сохранения стейта компонуемой функции,
        //то LazyColumn в MessageCardList будет дико лагать.
        //Это связано с особенностями рекомпозиции.
        val messages by remember {
            derivedStateOf {
                listOf(
                    Message(
                        author = "John Smith",
                        body = "Test body \nAnd some text else"
                    ),
                    Message(
                        author = "John Smith",
                        body = "Test body \nAnd some text else"
                    ),
                    Message(
                        author = "John Smith",
                        body = "Test body very very very very very very very very very long text\nAnd some text else"
                    ),
                    Message(
                        author = "John Smith",
                        body = "Test body \nAnd some text else"
                    ),
                    Message(
                        author = "John Smith",
                        body = "Test body \nAnd some text else"
                    ),
                    Message(
                        author = "John Smith",
                        body = "Test body \nAnd some text else"
                    ),
                    Message(
                        author = "John Smith",
                        body = "Test body \nAnd some text else"
                    ),
                    Message(
                        author = "John Smith",
                        body = "Test body \nAnd some text else"
                    ),
                    Message(
                        author = "John Smith",
                        body = "Test body \nAnd some text else"
                    ),
                    Message(
                        author = "John Smith",
                        body = "Test body \nAnd some text else"
                    ),
                    Message(
                        author = "John Smith",
                        body = "Test body \nAnd some text else"
                    ),
                    Message(
                        author = "John Smith",
                        body = "Test body \nAnd some text else"
                    ),
                    Message(
                        author = "John Smith",
                        body = "Test body \nAnd some text else"
                    )
                )
            }
        }
        MessageCardList(
            messages = messages,
            contentPadding = contentPadding
        )
    }
}