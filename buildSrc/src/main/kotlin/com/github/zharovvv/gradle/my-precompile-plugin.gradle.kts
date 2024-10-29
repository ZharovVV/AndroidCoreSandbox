package com.github.zharovvv.gradle

import java.nio.file.Files
import java.nio.file.Paths

// Create extension object
interface CreateFileExtension {
    val message: Property<String>
}

// Add the 'greeting' extension object to project
val extension = project.extensions.create<CreateFileExtension>("createFile")

// Set a default value for 'message'
extension.message.convention("Default message")

abstract class CreateFileTask : DefaultTask() {

    private val pluginDir = "${project.buildDir}/myPrecompilePlugin"

    @Input
    val input = project.objects.property<String>()

    @TaskAction
    fun action() {
        Files.createDirectories(Paths.get(pluginDir))
        val file = File("${pluginDir}/myfile.txt")
        file.createNewFile()
        file.writeText(input.get())
        println("The file was created. Location: file://${pluginDir.replace(" ", "%20")}/myfile.txt")
    }
}

tasks.register<CreateFileTask>("createFileTask") {
    group = "custom"
    description = "Create myfile.txt in the current directory"
    input.convention(extension.message)
}