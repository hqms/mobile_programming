package com.hqm.mp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MPTheme {
                    Greeting(
                        name = "Android",
                        modifier = Modifier
                    )

            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    var message by rememberSaveable { mutableStateOf("Android") }
    Scaffold(
        bottomBar = {
            Box(modifier.fillMaxWidth(), contentAlignment = Alignment.Center ) {
                Button(onClick = { message = "again" }) {
                    Text("Click Me")
                }
            }
        }
    ) { innerPadding ->
        Text(
            text = "Hello $message!",
            modifier = modifier
                .fillMaxWidth()
                .padding(innerPadding),
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center,
        )

    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MPTheme {
        Greeting("Android")
    }
}