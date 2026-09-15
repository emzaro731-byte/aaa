package com.emzaro.aaa

import android.content.Context
import java.io.File

/** Local project workspace used by AAA. Heavy Android compilation remains cloud based. */
class StudioEngine(private val context: Context) {
    private val root = File(context.filesDir, "projects").apply { mkdirs() }

    fun createProject(name: String): File {
        val safe = name.trim().replace(Regex("[^A-Za-z0-9._-]"), "-").ifBlank { "NewProject" }
        return File(root, safe).apply { mkdirs() }
    }

    fun listProjects(): List<File> = root.listFiles()?.filter { it.isDirectory }?.sortedBy { it.name } ?: emptyList()

    fun saveFile(project: File, path: String, content: String) {
        val target = File(project, path)
        target.parentFile?.mkdirs()
        target.writeText(content)
    }

    fun readFile(project: File, path: String): String = File(project, path).takeIf { it.isFile }?.readText() ?: ""
}
