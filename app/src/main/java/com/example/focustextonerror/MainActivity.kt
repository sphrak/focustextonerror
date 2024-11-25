package com.example.focustextonerror

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.focustextonerror.ui.theme.FocustextonerrorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FocustextonerrorTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    contentWindowInsets = WindowInsets(0,0,0,0)
                ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .consumeWindowInsets(innerPadding)
                            .padding(innerPadding)
                            .statusBarsPadding()
                            .navigationBarsPadding()
                            .imePadding()
                            .verticalScroll(rememberScrollState())
                    ) {
                        repeat(16) { index ->
                            Greeting(
                                modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
                                initialText = "placeholder $index"
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Greeting(modifier: Modifier = Modifier, initialText: String) {
    val state = rememberTextFieldState(initialText)

    val illegalChars = remember { mutableListOf('!','#','€','%','&','/','=') }
    val isError by remember(state.text) {
        mutableStateOf(state.text.any { char -> char in illegalChars })
    }

    val interactionSource = remember { MutableInteractionSource() }

    BasicTextField(
        modifier = modifier
            .fillMaxWidth(),
        state = state,
        interactionSource = interactionSource,
        decorator = {
            TextFieldDefaults.DecorationBox(
                value = state.text.toString(),
                innerTextField = it,
                enabled = true,
                singleLine = true,
                visualTransformation = VisualTransformation.None,
                interactionSource = interactionSource,
                isError = isError,
                label = {
                    Text("label")
                },
                supportingText = if (isError) {
                    @Composable {
                        Text(
                            modifier = Modifier.padding(8.dp),
                            text = "illegal character!",
                            fontWeight = FontWeight.Bold,
                            color = Color.Red
                        )
                    }
                } else null
            )
        }
    )
}