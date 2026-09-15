package com.emzaro.aaa

object ProjectTemplate {
    fun androidStarter(packageName: String, appName: String): String = """
        package $packageName

        import android.os.Bundle
        import androidx.activity.ComponentActivity
        import androidx.activity.compose.setContent
        import androidx.compose.material3.Text

        class MainActivity : ComponentActivity() {
            override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                setContent { Text(\"$appName\") }
            }
        }
    """.trimIndent()
}
