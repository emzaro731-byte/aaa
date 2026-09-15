package com.emzaro.aaa

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.io.File

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { AAAStudio() }
    }
}

@Composable fun AAAStudio() {
    var selected by remember { mutableStateOf("Dashboard") }
    val items = listOf("Dashboard", "Projects", "Code Editor", "AI Builder", "Import", "Build APK", "Build AAB", "GitHub", "Settings")
    MaterialTheme(colorScheme = darkColorScheme()) {
        Row(Modifier.fillMaxSize().background(Color(0xFF0B0D12))) {
            NavigationRail(containerColor = Color(0xFF11141B), modifier = Modifier.width(92.dp)) {
                Text("AAA", fontSize = 24.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(20.dp))
                items.forEach { label -> NavigationRailItem(selected = selected == label, onClick = { selected = label }, icon = { Text(label.take(1)) }, label = { Text(label, fontSize = 9.sp) }) }
            }
            Column(Modifier.fillMaxSize().padding(24.dp)) {
                Text("AAA Mobile Studio", fontSize = 28.sp, fontWeight = FontWeight.Bold)
                Text("AI-powered Android development workspace", color = Color.LightGray)
                Spacer(Modifier.height(24.dp))
                when (selected) {
                    "Dashboard" -> Dashboard()
                    "Projects" -> Projects()
                    "Code Editor" -> Editor()
                    "AI Builder" -> AIBuilder()
                    "Import" -> ImportScreen()
                    "Build APK", "Build AAB" -> BuildScreen(selected)
                    "GitHub" -> GitHubScreen()
                    "Settings" -> SettingsScreen()
                }
            }
        }
    }
}

@Composable fun Dashboard() {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text("Build apps from your phone", fontSize = 22.sp, fontWeight = FontWeight.SemiBold)
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            FeatureCard("AI Builder", "Generate a starter project from a description", Modifier.weight(1f))
            FeatureCard("Cloud Build", "Build APK/AAB with GitHub Actions", Modifier.weight(1f))
        }
        FeatureCard("Android Studio Workspace", "Project tree, Kotlin/Java/XML/Gradle editor, file operations, search and local storage")
    }
}

@Composable fun FeatureCard(title: String, body: String, modifier: Modifier = Modifier) { Card(modifier) { Column(Modifier.padding(18.dp)) { Text(title, fontWeight = FontWeight.Bold); Spacer(Modifier.height(6.dp)); Text(body) } } }

@Composable fun Projects() {
    val context = LocalContext.current
    var selectedProject by remember { mutableStateOf<File?>(null) }
    var refreshKey by remember { mutableIntStateOf(0) }
    val projects = remember(refreshKey) { StudioEngine(context).listProjects() }
    if (selectedProject == null) {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) { Text("Local Projects", fontSize = 22.sp, fontWeight = FontWeight.Bold); Spacer(Modifier.weight(1f)); OutlinedButton(onClick = { refreshKey++ }) { Text("↻ Refresh") } }
            Text("Android Studio-style projects stored on this phone.", color = Color.LightGray)
            if (projects.isEmpty()) Text("No local projects yet. Use AI Builder or Import to create one.")
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) { items(projects) { project -> Card { Row(Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) { Text("📁  ${project.name}", fontWeight = FontWeight.SemiBold); Spacer(Modifier.weight(1f)); Button(onClick = { selectedProject = project }) { Text("Open") } } } } }
        }
    } else ProjectExplorer(selectedProject!!, onBack = { selectedProject = null })
}

@Composable fun ProjectExplorer(project: File, onBack: () -> Unit) {
    var openedPath by remember { mutableStateOf<String?>(null) }
    var content by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var search by remember { mutableStateOf("") }
    var refreshKey by remember { mutableIntStateOf(0) }
    var dialog by remember { mutableStateOf<String?>(null) }
    val manager = remember(project) { LocalProjectFiles(project) }
    val allFiles = remember(refreshKey) { manager.listAll().sorted() }
    val files = allFiles.filter { search.isBlank() || it.contains(search, ignoreCase = true) }
    Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) { Button(onClick = onBack) { Text("← Projects") }; Spacer(Modifier.width(12.dp)); Text("📂 ${project.name}", fontSize = 20.sp, fontWeight = FontWeight.Bold); Spacer(Modifier.weight(1f)); OutlinedButton(onClick = { refreshKey++ }) { Text("↻ Refresh") } }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) { Button(onClick = { dialog = "file" }) { Text("+ File") }; OutlinedButton(onClick = { dialog = "folder" }) { Text("+ Folder") }; OutlinedButton(enabled = openedPath != null, onClick = { dialog = "delete" }) { Text("🗑 Delete") } }
        OutlinedTextField(value = search, onValueChange = { search = it }, modifier = Modifier.fillMaxWidth(), singleLine = true, placeholder = { Text("🔍 Search project files...") })
        Row(Modifier.fillMaxSize(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Card(Modifier.width(300.dp).fillMaxHeight()) { Column(Modifier.padding(12.dp)) { Text("Project Files", fontWeight = FontWeight.Bold); Text("app / src / main / java / res / assets", color = Color.LightGray, fontSize = 11.sp); Spacer(Modifier.height(8.dp)); LazyColumn(verticalArrangement = Arrangement.spacedBy(2.dp)) { items(files) { path -> TextButton(onClick = { runCatching { content = manager.read(path); openedPath = path; message = "" }.onFailure { message = "Cannot open file: ${it.message}" } }, modifier = Modifier.fillMaxWidth()) { Text(fileIcon(path) + "  " + path, fontSize = 12.sp) } } } } }
            Card(Modifier.weight(1f).fillMaxHeight()) { Column(Modifier.fillMaxSize().padding(12.dp)) { Text(openedPath ?: "Select a file", fontWeight = FontWeight.Bold); Spacer(Modifier.height(8.dp)); if (openedPath != null) { OutlinedTextField(value = content, onValueChange = { content = it; message = "" }, modifier = Modifier.fillMaxWidth().weight(1f), textStyle = LocalTextStyle.current.copy(fontFamily = FontFamily.Monospace, fontSize = 13.sp)); Spacer(Modifier.height(8.dp)); Row(verticalAlignment = Alignment.CenterVertically) { Button(onClick = { runCatching { manager.write(openedPath!!, content); message = "Saved $openedPath" }.onFailure { message = "Save failed: ${it.message}" } }) { Text("💾 Save") }; Spacer(Modifier.width(10.dp)); Text(message, color = if (message.startsWith("Saved")) Color(0xFF8BE28B) else Color.LightGray, fontSize = 12.sp) } } else Text("Open a Kotlin, Java, XML, Gradle, JSON, properties, Markdown or other project file.") } }
        }
    }
    if (dialog != null) ProjectActionDialog(dialog!!, onDismiss = { dialog = null }, onDone = { path, isFolder -> runCatching { if (isFolder) manager.createFolder(path) else manager.createFile(path); message = "Created $path"; refreshKey++ }.onFailure { message = "Create failed: ${it.message}" }; dialog = null }, onDelete = { runCatching { manager.delete(openedPath!!); message = "Deleted $openedPath"; openedPath = null; content = ""; refreshKey++ }.onFailure { message = "Delete failed: ${it.message}" }; dialog = null }, selectedPath = openedPath)
}

@Composable fun ProjectActionDialog(kind: String, onDismiss: () -> Unit, onDone: (String, Boolean) -> Unit, onDelete: () -> Unit, selectedPath: String?) {
    var path by remember { mutableStateOf("") }
    AlertDialog(onDismissRequest = onDismiss, title = { Text(if (kind == "delete") "Delete file/folder" else if (kind == "folder") "Create folder" else "Create file") }, text = { if (kind == "delete") Text("Delete ${selectedPath ?: "the selected item"}? This cannot be undone.") else OutlinedTextField(value = path, onValueChange = { path = it }, singleLine = true, placeholder = { Text(if (kind == "folder") "app/src/main/assets" else "app/src/main/res/values/new.xml") }) }, confirmButton = { Button(onClick = { if (kind == "delete") onDelete() else if (path.isNotBlank()) onDone(path, kind == "folder") }, enabled = kind == "delete" || path.isNotBlank()) { Text(if (kind == "delete") "Delete" else "Create") } }, dismissButton = { TextButton(onClick = onDismiss) { Text("Cancel") } })
}

fun fileIcon(path: String): String = when { path.endsWith(".kt") || path.endsWith(".kts") -> "K"; path.endsWith(".java") -> "J"; path.endsWith(".xml") -> "X"; path.endsWith(".gradle") || path.endsWith(".gradle.kts") -> "G"; path.endsWith(".json") -> "{}"; path.endsWith(".properties") -> "P"; path.endsWith(".md") -> "M"; path.endsWith(".png") || path.endsWith(".jpg") || path.endsWith(".webp") -> "IMG"; else -> "•" }

@Composable fun GitHubScreen() { SimpleList("GitHub", listOf("Import repository", "Push project", "View build runs")) }
@Composable fun SettingsScreen() { SimpleList("Settings", listOf("AI provider", "GitHub connection", "Supabase connection", "Build preferences")) }
@Composable fun SimpleList(title: String, values: List<String>) { Text(title, fontSize = 22.sp, fontWeight = FontWeight.Bold); Spacer(Modifier.height(12.dp)); LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) { items(values) { value -> Card { Row(Modifier.fillMaxWidth().padding(18.dp), verticalAlignment = Alignment.CenterVertically) { Text(value, fontSize = 16.sp); Spacer(Modifier.weight(1f)); Text("›", fontSize = 24.sp) } } } } }

@Composable fun Editor() {
    val context = LocalContext.current
    var code by remember { mutableStateOf("fun main() {\n    println(\"Hello from AAA\")\n}") }
    var saved by remember { mutableStateOf(false) }
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) { Text("Quick Editor", fontWeight = FontWeight.Bold); OutlinedTextField(value = code, onValueChange = { code = it; saved = false }, modifier = Modifier.fillMaxWidth().height(420.dp), textStyle = LocalTextStyle.current.copy(fontFamily = FontFamily.Monospace)); Button(onClick = { StudioEngine(context).createProject("Quick-Project").also { StudioEngine(context).saveFile(it, "Main.kt", code) }; saved = true }) { Text("Save as Local Project") }; if (saved) Text("Saved locally", color = Color(0xFF8BE28B)) }
}

@Composable fun AIBuilder() {
    val context = LocalContext.current
    var prompt by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) { Text("AI App Builder", fontSize = 22.sp, fontWeight = FontWeight.Bold); Text("Describe an app and AAA will create a starter project on the phone."); OutlinedTextField(value = prompt, onValueChange = { prompt = it }, modifier = Modifier.fillMaxWidth(), minLines = 4, placeholder = { Text("Build me a marketplace app with phone login...") }); Button(onClick = { result = "Created: ${AIProjectGenerator(StudioEngine(context)).generate(prompt).name}" }, enabled = prompt.isNotBlank()) { Text("Generate Project") }; if (result.isNotBlank()) Text(result, color = Color(0xFF8BE28B)); Text("Connect a backend AI provider for production code generation. Never embed private API keys in the APK.", color = Color.LightGray, fontSize = 12.sp) }
}

@Composable fun ImportScreen() {
    val context = LocalContext.current
    var url by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) { Text("Import Project", fontSize = 22.sp, fontWeight = FontWeight.Bold); Text("Import a public GitHub repository into the AAA workspace."); OutlinedTextField(value = url, onValueChange = { url = it }, modifier = Modifier.fillMaxWidth(), placeholder = { Text("https://github.com/owner/repository") }); Button(onClick = { runCatching { ProjectImporter(StudioEngine(context)).importPublicGithub(url) }.onSuccess { result = "Imported: ${it.name}" }.onFailure { result = "Import error: ${it.message}" } }, enabled = url.isNotBlank()) { Text("Import GitHub Project") }; if (result.isNotBlank()) Text(result) }
}

@Composable fun BuildScreen(type: String) {
    val context = LocalContext.current
    val actionsUrl = "https://github.com/emzaro731-byte/aaa/actions/workflows/android.yml"
    var reviewOpen by remember { mutableStateOf(false) }
    var reviewed by remember { mutableStateOf(false) }
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(type, fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Text("Cloud build integration")
        Card { Column(Modifier.padding(16.dp)) {
            Text(if (type == "Build APK") "Debug APK" else "Release AAB", fontWeight = FontWeight.Bold)
            Text("Review the build information before opening the download page.", color = Color.LightGray)
            Spacer(Modifier.height(10.dp))
            Button(onClick = { reviewOpen = true }) { Text("Review Before Download") }
            if (reviewed) Text("✓ Review completed", color = Color(0xFF8BE28B), fontSize = 12.sp)
        } }
        Text("Builds are produced by GitHub Actions. Signing keys and tokens must stay in GitHub Secrets.", color = Color.LightGray, fontSize = 12.sp)
    }
    if (reviewOpen) AlertDialog(onDismissRequest = { reviewOpen = false }, title = { Text("Review build before download") }, text = { Column(verticalArrangement = Arrangement.spacedBy(8.dp)) { Text("Build target: $type"); Text("Source: AAA Mobile Studio repository"); Text("Build system: GitHub Actions"); Text(if (type == "Build APK") "Artifact: aaa-debug-apk" else "Artifact: aaa-release-aab"); Text("Check the workflow result and artifact before downloading.", color = Color.LightGray) } }, confirmButton = { Button(onClick = { reviewed = true; reviewOpen = false; context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(actionsUrl))) }) { Text("Review & Open Download") } }, dismissButton = { TextButton(onClick = { reviewOpen = false }) { Text("Cancel") } })
}
