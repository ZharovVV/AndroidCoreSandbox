package com.github.zharovvv.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

class MyPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        println("===================== MyPlugin Work!!! ==========================")
        with(project.tasks) {
            create("pupaTask") {
                group = "custom"
                description = "prints Pupa in console"
                doLast {
                    println("===================== PupaTask Work!!! ==========================")
                }
                dependsOn("assemble")
            }
        }
    }
}