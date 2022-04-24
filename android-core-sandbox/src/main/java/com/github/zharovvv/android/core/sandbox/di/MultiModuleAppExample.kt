package com.github.zharovvv.android.core.sandbox.di

/**
 * # Правила модуляризации
 * 1. __Feature модули__ ничего не знают друг о друге
 * 2. __App модуль__ соединяет между собой __Feature modules__
 * 3. Коммуникация между Feature модулями происходит через app модуль
 * 4. __App модуль__ предоставляет зависимости для Feature модулей
 */