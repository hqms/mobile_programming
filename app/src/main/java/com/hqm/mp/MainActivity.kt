package com.hqm.mp

import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.hqm.mp.ui.theme.MPTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Button
import androidx.compose.runtime.remember
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.Image
import androidx.compose.material3.TextField
import android.content.Context

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MPTheme {
                    Greeting(
                        name = "Android",
                        modifier = Modifier,
                        this
                    )
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier, context: Context ) {
    var name by rememberSaveable { mutableStateOf("Name") }
    var major by rememberSaveable { mutableStateOf("Major") }
    Scaffold(
        bottomBar = {
            Box(
                modifier.fillMaxWidth().padding(20.dp),
                contentAlignment = Alignment.Center ) {
                Button(onClick = { Toast.makeText(context , "Hello $name !", Toast.LENGTH_LONG).show() }) {
                    Text("Show Profile")
                }
            }
        }
    ) { innerPadding ->
        Image(
            painter = painterResource(R.drawable.metaguna),
            "Metaguna University"
        )
        TextField(
            value = name,
            label = { Text("Name")},
            placeholder = { Text("Enter your name")},
            onValueChange = { name = it },
            modifier = Modifier.padding(innerPadding)
        )
        TextField(
            value = major,
            label = { Text("Major")},
            placeholder = { Text("Enter your major")},
            onValueChange = { major = it },
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MPTheme {
        Greeting("", Modifier, MainActivity())
    }
}