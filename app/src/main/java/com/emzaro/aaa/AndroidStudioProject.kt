package com.emzaro.aaa

import java.io.File

/** Recognises common Android Studio project files and directories. */
object AndroidStudioProject {
    private val editableExtensions = setOf(
        "kt", "kts", "java", "xml", "gradle", "properties", "toml",
        "json", "pro", "cpp", "c", "h", "hpp", "md", "txt"
    )

    private val importantNames = setOf(
        "AndroidManifest.xml", "gradlew", "gradlew.bat",
        "proguard-rules.pro", "gradle-wrapper.properties"
    )

    fun isProject(root: File): Boolean =
        File(root, "settings.gradle").exists() ||
        File(root, "settings.gradle.kts").exists() ||
        File(root, "gradlew").exists() ||
        File(root, "build.gradle").exists() ||
        File(root, "build.gradle.kts").exists()

    fun editableFiles(root: File): List<File> = root.walkTopDown()
        .filter { it.isFile && (it.extension.lowercase() in editableExtensions || it.name in importantNames) }
        .filterNot { it.name == "local.properties" }
        .toList()

    fun relativePath(root: File, file: File): String = file.relativeTo(root).path
}
