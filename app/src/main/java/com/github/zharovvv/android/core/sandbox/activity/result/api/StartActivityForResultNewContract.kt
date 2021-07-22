package com.github.zharovvv.android.core.sandbox.activity.result.api

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract

class StartActivityForResultNewContract : ActivityResultContract<String, String?>() {

    companion object {
        const val START_ACTIVITY_FOR_RESULT_CONTRACT_INPUT_CODE = "INPUT_CODE"
        const val START_ACTIVITY_FOR_RESULT_CONTRACT_OUTPUT_CODE = "OUTPUT_CODE"
    }

    override fun createIntent(context: Context, input: String?): Intent {
        return Intent(context, StartForResultNewActivity::class.java)
                .putExtra(START_ACTIVITY_FOR_RESULT_CONTRACT_INPUT_CODE, input)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): String? {
        return when (resultCode) {
            RESULT_OK -> intent?.getStringExtra(START_ACTIVITY_FOR_RESULT_CONTRACT_OUTPUT_CODE)
            else -> null
        }
    }

    /**
     * Позволяет сразу же, без запуска Activity, вернуть результат, например,
     * если получены невалидные входные данные.
     * Если подобное поведение не требуется, метод по умолчанию возвращает null.
     */
    override fun getSynchronousResult(context: Context, input: String?): SynchronousResult<String?>? {
        return super.getSynchronousResult(context, input)
    }
}