package com.emzaro.aaa

import java.io.File

/** Safe local file operations for Android Studio-style projects. */
class LocalProjectFiles(private val root: File) {
    private val protectedNames = setOf("local.properties")

    fun listAll(): List<String> = root.walkTopDown()
        .filter { it.isFile }
        .map { it.relativeTo(root).path }
        .filterNot { it.substringAfterLast(File.separator) in protectedNames }
        .toList()

    fun read(relativePath: String): String = safeFile(relativePath).readText()

    fun write(relativePath: String, content: String) {
        val file = safeFile(relativePath)
        file.parentFile?.mkdirs()
        file.writeText(content)
    }

    fun createFile(relativePath: String, content: String = "") {
        val file = safeFile(relativePath)
        require(!file.exists()) { "File already exists" }
        file.parentFile?.mkdirs()
        file.writeText(content)
    }

    fun createFolder(relativePath: String) {
        val folder = safeFile(relativePath)
        require(!folder.exists()) { "Folder already exists" }
        require(folder.mkdirs()) { "Could not create folder" }
    }

    fun delete(relativePath: String) {
        val file = safeFile(relativePath)
        require(file.exists()) { "File or folder does not exist" }
        require(file.deleteRecursively()) { "Could not delete file or folder" }
    }

    private fun safeFile(relativePath: String): File {
        require(relativePath.isNotBlank()) { "Path is required" }
        val target = File(root, relativePath).canonicalFile
        val base = root.canonicalFile
        require(target.path == base.path || target.path.startsWith(base.path + File.separator)) { "Invalid project path" }
        require(target.name !in protectedNames) { "Protected project file" }
        return target
    }
}
