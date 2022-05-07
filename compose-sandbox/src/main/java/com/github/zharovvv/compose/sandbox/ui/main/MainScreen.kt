package com.github.zharovvv.compose.sandbox.ui.main

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
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
        MessageCardList(
            messages = listOf(
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
                ),
            ),
            contentPadding = contentPadding
        )
    }
}