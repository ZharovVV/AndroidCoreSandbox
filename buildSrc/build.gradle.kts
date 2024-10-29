plugins {
    `kotlin-dsl`
    //Под капотом добавляется плагин `java-gradle-plugin`?
}

repositories {
    mavenCentral()
}

//Script Plugins (Плагины скриптов) обычно представляют собой небольшие локальные плагины,
// написанные в файлах скриптов для задач, специфичных для одной сборки или проекта.
// Их не нужно повторно использовать в нескольких проектах.
// Плагины скриптов не рекомендуются,
// но многие другие формы плагинов развиваются из плагинов скриптов.
class GreetingPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.task("hello") {
            doLast {
                println("Hello from the GreetingPlugin")
            }
        }
    }
}

// Apply the plugin
apply<GreetingPlugin>()

//Pre-compiled Script Plugins (в т.ч. convention plugin)
// Предварительно скомпилированные плагины скриптов компилируются в файлы классов
// и упаковываются в JAR перед выполнением.
// Эти плагины используют Groovy DSL или Kotlin DSL вместо чистой Java, Kotlin или Groovy.
// Их лучше всего использовать в качестве плагинов соглашений,
// которые разделяют логику сборки между проектами
// или как способ аккуратно организовать логику сборки.
//см. my-precompile-plugin.gradle.kts

//Binary Plugins (Бинарные плагины) - это плагины, которые создаются как отдельные JAR-файлы
// и применяются к проекту с помощью plugins{} блока в скрипте сборки.
//Плагины, готовые к публикации не рекомендуется располагать в buildSrc (сейчас это так)).
//Вместо этого следует размещать плагины в отдельной сборке (included build).
gradlePlugin {
    plugins {
        create("myPlugins") {
            id = "my-plugin"
            implementationClass = "com.github.zharovvv.gradle.MyPlugin"
        }
    }
}