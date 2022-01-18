package com.github.zharovvv.android.core.sandbox.fragment

import android.os.Bundle
import android.widget.Button
import android.widget.Spinner
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.github.zharovvv.android.core.sandbox.LogLifecycleAppCompatActivity
import com.github.zharovvv.android.core.sandbox.R
import com.github.zharovvv.android.core.sandbox.databinding.ActivityFragmentExampleBinding
import com.github.zharovvv.android.core.sandbox.fragment.ExampleFragment.Companion.FRAGMENT_COLOR_KEY
import com.github.zharovvv.android.core.sandbox.fragment.ExampleFragment.Companion.FRAGMENT_DATA_KEY

class FragmentExampleActivity : LogLifecycleAppCompatActivity(), FragmentOnResumeListener {

    private lateinit var binding: ActivityFragmentExampleBinding
    private val tagStack = TagStack()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFragmentExampleBinding.inflate(layoutInflater)
        initSpinner(spinner = binding.fragmentBackgroundSpinner)
        setContentView(binding.root)
        initAddButton(button = binding.addButton)
        initReplaceButton(button = binding.replaceButton)
        initRemoveButton(button = binding.removeButton)
    }

    private fun initSpinner(spinner: Spinner) {
        spinner.apply {
            adapter = ColorArraySpinnerAdapter(
                context = context,
                objects = listOf(
                    R.color.blue,
                    R.color.purple_200,
                    R.color.purple_500,
                    R.color.purple_700
                )
            )
            prompt = "Fragment background"
            setSelection(0)
        }
    }

    private fun bundleFromBinding(): Bundle {
        val data = binding.initialDataEditText.text.toString()
        val colorId: Int = binding.fragmentBackgroundSpinner.selectedItem as Int
        return bundleOf(
            FRAGMENT_DATA_KEY to data,
            FRAGMENT_COLOR_KEY to colorId
        )
    }

    private fun initAddButton(button: Button) {
        button.setOnClickListener {
            val bundle = bundleFromBinding()
            supportFragmentManager.beginTransaction()
                .setReorderingAllowed(true) //Настоятельно рекомендуется вызывать данный метод всегда.
                // Для совместимости поведения флаг переупорядочивания по умолчанию не включен.
                // Однако это необходимо для того, чтобы разрешить FragmentManager правильное выполнение
                // вашего FragmentTransaction, особенно когда он работает с бэкстеком и запускает анимацию и переходы.
                // Включение этого флага гарантирует, что при одновременном выполнении нескольких транзакций
                // любые промежуточные фрагменты (т. е. те, которые добавляются, а затем сразу же заменяются)
                // не претерпевают изменения жизненного цикла или их анимации или переходы не выполняются.
                // Обратите внимание, что этот флаг влияет как на начальное выполнение транзакции,
                // так и на отмену транзакции с помощью popBackStack().
                .add(//добавляет фрагмент на активити или другой фрагмент.
                    R.id.fragment_host,
                    ExampleFragment::class.java,
                    bundle,
                    generateFragmentTag()
                )
                .apply {
                    if (binding.addToBackStackCheckBox.isChecked) {
                        addToBackStack(null)
                    }
                }
                .commit()   //Выполняется асинхронно
            //Транзакция не выполняется во время вызова метода.
            // commit() добавляет транзакцию в очередь главного потока и транзакция выполняется при первой возможности.
            //Чтобы выполнить транзакцию синхронно, можно воспользоваться методом commitNow() вместо commit()
            // или вызвать executePendingTransactions() после метода commit().
        }
    }

    private fun generateFragmentTag(): String {
        val colorId: Int = binding.fragmentBackgroundSpinner.selectedItem as Int
        return "ExampleFragment-$colorId"
    }

    private fun initReplaceButton(button: Button) {
        button.setOnClickListener {
            val bundle = bundleFromBinding()
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace<ExampleFragment>( //удаляет все фрагменты, добавленные методом add() в заданный контейнер,
                    // и добавляет переданный аргументом фрагмент в контейнер. Параметр tag может быть null.
                    containerViewId = R.id.fragment_host,
                    tag = generateFragmentTag(),
                    args = bundle
                )
                if (binding.addToBackStackCheckBox.isChecked) {
                    addToBackStack(null)    //Метод addToBackStack() добавляет транзакцию в Back Stack.
                    // Это значит, что когда пользователь нажмет Back транзакция откатится.
                    // addToBackStack() применяется ко всем операциям в транзакции.

                    // В контексте данного примера:

                    // Если сначала вызывать add (без addToBackStack), а затем вызвать replace без addToBackStack (в следующей транзакции),
                    // то у добавленных ранее фрагментов будут вызваны onDestroy и onDetach. Добавленные фрагменты уничтожатся.

                    // Если сначала вызывать add с addToBackStack, а затем вызвать replace (с или без addToBackStack) (в следующей транзакции),
                    // то у добавленных ранее фрагментов не будут вызваны onDestroy и onDetach.
                    // - Если был вызван replace + addToBackStack, то при popBackStack у добавленных ранее фрагментов
                    // вызовется сразу onCreateView (и далее до onResume) (у тех же самых объектов Fragment).
                    // - Если был вызван просто replace, то при popBackStack у последнего добавленного ранее фрагмента
                    // вызовется onDestroy и onDetach (у того же объекта Fragment). Так как транзакция с replace не была добавлена в BackStack.
                    // Фрагмент, добаленный через replace остается видимым для пользователя.

                    // Метод popBackStack() удаляет транзакцию с верхушки бэкстэка, возвращает true,
                    // если бэкстэк хранил хотя бы одну транзакцию.
                }
            }
        }
    }

    private fun initRemoveButton(button: Button) {
        button.setOnClickListener {
            tagStack.pop()?.let {
                val fragment = supportFragmentManager.findFragmentByTag(it)
                if (fragment != null) {
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        remove(fragment)    //операция, обратная add(). Удаляет фрагмент.
                    }
                }
            }
        }
    }

    override fun fragmentOnResume(fragmentTag: String) {
        tagStack.push(tag = fragmentTag)
    }
}

interface FragmentOnResumeListener {
    fun fragmentOnResume(fragmentTag: String)
}

class TagStack {
    private val deque = ArrayDeque<String>()

    fun push(tag: String): String {
        deque.add(tag)
        return tag
    }

    fun pop(): String? {
        return deque.removeLastOrNull()
    }

    fun peek(): String? {
        return deque.lastOrNull()
    }

}