package com.example.appdatabase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.appdatabase.ui.theme.AppDatabaseTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.appdatabase.roomDB.Pessoa
import com.example.appdatabase.roomDB.PessoaDataBase
import com.example.appdatabase.viewModel.PessoaViewModel
import com.example.appdatabase.viewModel.Repository

class MainActivity : ComponentActivity() {

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            PessoaDataBase::class.java,
            "pessoa.db"
        ).build()
    }

    private val viewModel by viewModels<PessoaViewModel> (
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T{
                    return PessoaViewModel(Repository(db)) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppDatabaseTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    App(Modifier, viewModel, this)
                }
            }
        }
    }
}

@Composable
fun App(
    modifier: Modifier = Modifier,
    viewModel: PessoaViewModel,
    mainActivity: MainActivity
) {
    var nome by remember {
        mutableStateOf("")
    }
    var telefone by remember {
        mutableStateOf("")
    }
    val pessoa = Pessoa(
        nome,
        telefone
    )

    var pessoaList by remember {
        mutableStateOf(listOf<Pessoa>())
    }

    viewModel.getPessoa().observe(mainActivity) {
        pessoaList = it
    }

    // Podem existir diferenças de estilização no código a seguir
    Column(
        Modifier
            .background(Color.White)
            .fillMaxHeight()
    ) {
        Row(
            Modifier
                .padding(20.dp)
        ) {

        }
        Row(
            Modifier
                .fillMaxWidth(),
            Arrangement.Center
        ) {
            Text(
                text = "App DataBase",
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                fontSize = 30.sp,
                color = Color(28, 100, 255)
            )
        }
        Row(
            Modifier
                .padding(20.dp)
        ) {

        }
        Row(
            Modifier
                .fillMaxWidth(),
            Arrangement.Center
        ) {
            TextField(
                value = nome,
                onValueChange = { nome = it },
                label = { Text("Nome:") },
                colors = TextFieldDefaults.colors(
                    unfocusedIndicatorColor = Color(28, 100, 255),
                    unfocusedLabelColor = Color(28, 100, 255),
                    unfocusedContainerColor = Color.LightGray,
                    unfocusedTextColor = Color.Black,

                    focusedIndicatorColor = Color(28, 100, 255),
                    focusedLabelColor = Color(28, 100, 255),
                    focusedContainerColor = Color.LightGray,
                    focusedTextColor = Color.Black,

                    cursorColor = Color(28, 100, 255)
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                )
            )
        }
        Row(
            Modifier
                .padding(20.dp)
        ) {

        }
        Row(
            Modifier
                .fillMaxWidth(),
            Arrangement.Center
        ) {
            TextField(
                value = telefone,
                onValueChange = { telefone = it },
                label = { Text("Telefone:") },
                colors = TextFieldDefaults.colors(
                    unfocusedIndicatorColor = Color(28, 100, 255),
                    unfocusedLabelColor = Color(28, 100, 255),
                    unfocusedContainerColor = Color.LightGray,
                    unfocusedTextColor = Color.Black,

                    focusedIndicatorColor = Color(28, 100, 255),
                    focusedLabelColor = Color(28, 100, 255),
                    focusedContainerColor = Color.LightGray,
                    focusedTextColor = Color.Black,

                    cursorColor = Color(28, 100, 255)
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                )
            )
        }
        Row(
            Modifier
                .padding(20.dp)
        ) {

        }
        Row(
            Modifier
                .fillMaxWidth(),
            Arrangement.Center
        ) {
            Button(
                onClick = {
                    viewModel.upsertPessoa(pessoa)
                    nome = ""
                    telefone = ""
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(28, 100, 255)),
            ) {
                Text(
                    text = "Cadastrar",
                    color = Color.White
                )
            }
        }

        Row(
            Modifier
                .padding(20.dp)
        ) {

        }

        HorizontalDivider()
        LazyColumn {
            items(pessoaList) { pessoa ->
                Row(
                    Modifier
                        .fillMaxWidth(),
                    Arrangement.Center
                ) {
                    Column (
                        Modifier
                            .fillMaxWidth(0.5f),
                        Arrangement.Center
                    ) {
                        Text(
                            text = "${pessoa.nome}",
                            color = Color.Black
                        )
                    }

                    Column (
                        Modifier
                            .fillMaxWidth(0.5f),
                        Arrangement.Center
                    ) {
                        Text(
                            text = "${pessoa.telefone}",
                            color = Color.Black
                        )
                    }
                }
                HorizontalDivider()
            }
        }

    }
}

/*
@Preview(showBackground = true)
@Composable
fun AppPreview() {
    AppDatabaseTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            App(Modifier, viewModel, this)
        }
    }
}
*/