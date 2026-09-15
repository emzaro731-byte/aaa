package com.emzaro.aaa

import java.io.File
import java.net.URL

/** Imports simple text projects from a public GitHub repository into the AAA workspace. */
class ProjectImporter(private val engine: StudioEngine) {
    fun importPublicGithub(repoUrl: String): File {
        val match = Regex("github\\.com/([^/]+)/([^/#?]+)").find(repoUrl.trim())
            ?: throw IllegalArgumentException("Enter a public GitHub URL like https://github.com/owner/repository")
        val owner = match.groupValues[1]
        val repo = match.groupValues[2].removeSuffix(".git")
        val project = engine.createProject("$owner-$repo")
        val readmeUrl = "https://raw.githubusercontent.com/$owner/$repo/HEAD/README.md"
        runCatching { URL(readmeUrl).readText() }.onSuccess {
            engine.saveFile(project, "README.md", it)
        }
        engine.saveFile(project, "SOURCE.txt", "Imported from https://github.com/$owner/$repo\n\nUse GitHub ZIP export for a complete offline project import.\n")
        return project
    }
}
