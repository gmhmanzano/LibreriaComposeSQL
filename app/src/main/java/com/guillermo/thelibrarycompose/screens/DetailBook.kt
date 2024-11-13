package com.guillermo.thelibrarycompose.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Card
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.guillermo.thelibrarycompose.R
import com.guillermo.thelibrarycompose.data.SQLOpenHelper
import com.guillermo.thelibrarycompose.data.book
import com.guillermo.thelibrarycompose.ui.theme.TopBar


@Composable
fun DetailBook(navController: NavHostController, id: String) {
    val context: Context = LocalContext.current

    Scaffold(topBar = { TopBar() },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(Icons.Filled.Home, contentDescription = "Casa")
            }
        }) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val data = BuscaData(context, id = id.toInt())
            var titulo by rememberSaveable { mutableStateOf(data!!.title) }
            var autor by rememberSaveable { mutableStateOf(data!!.author) }
            var precio by rememberSaveable { mutableStateOf(data!!.price.toString()) }
            var pages by rememberSaveable { mutableStateOf(data!!.pages.toString()) }
            Card(
                border = BorderStroke(2.dp, Color.Blue),
                modifier = Modifier
                    .width(400.dp)
                    .padding(vertical = 30.dp)
            ) {
                Image(
                    painterResource(R.drawable.book),
                    contentDescription = "Libro",
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
                Text(
                    "DATOS DEL LIBRO",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    fontSize = 36.sp,
                    textAlign = TextAlign.Center,
                    color = Color.Red
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    "Número $id",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center,
                    color = Color.Red,
                    fontFamily = FontFamily.Cursive
                )
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    OutlinedTextField(value = titulo, onValueChange = { titulo = it }, label = {
                        Text("Título del libro", color = Color.DarkGray)
                    }, modifier = Modifier
                        .padding(vertical = 10.dp)
                        .fillMaxWidth(0.85f)
                    )
                    OutlinedTextField(value = autor, onValueChange = { autor = it }, label = {
                        Text("Autor del libro", color = Color.DarkGray)
                    }, modifier = Modifier
                        .padding(vertical = 10.dp)
                        .fillMaxWidth(0.85f)
                    )

                    OutlinedTextField(
                        value = precio,
                        onValueChange = { precio = it },
                        label = {
                            Text("Precio del libro", color = Color.DarkGray)
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        modifier = Modifier
                            .padding(vertical = 10.dp)
                            .fillMaxWidth(0.85f)
                    )
                    OutlinedTextField(
                        value = pages,
                        onValueChange = { pages = it },
                        label = {
                            Text("Páginas del libro", color = Color.DarkGray)
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        modifier = Modifier
                            .padding(vertical = 10.dp)
                            .fillMaxWidth(0.85f)
                    )
                    Row(modifier = Modifier.padding(10.dp)) {
                        OutlinedButton(onClick = {
                            val NewBook = book(
                                id = id.toInt(),
                                title = titulo,
                                author = autor,
                                price = precio.toDouble(),
                                pages = pages.toDouble()
                            )
                            UpdateData(context = context, book = NewBook, navController)
                        }, modifier = Modifier.padding(horizontal = 5.dp)) { Text("Actualizar") }
                        OutlinedButton(onClick = {
                            deleteData(context, id.toInt(), navController)
                        }) { Text("Eliminar") }
                    }
                }

            }


        }
    }
}

fun BuscaData(context: Context, id: Int): book? {
    val sqlOpenHelper = SQLOpenHelper(context = context)
    val book = sqlOpenHelper.SeekBook(context = context, id = id)
    return book
}

fun UpdateData(context: Context, book: book, navController: NavHostController) {
    val sqlOpenHelper = SQLOpenHelper(context)
    val db = sqlOpenHelper.updateBook(context, book)
    if (db) {
        Toast.makeText(context, "Proceso Conluído con éxito", Toast.LENGTH_SHORT).show()
        navController.popBackStack()
    } else {
        Toast.makeText(context, "Hubo un problema al actualizar", Toast.LENGTH_SHORT).show()
        navController.popBackStack()
    }
}

fun deleteData(context: Context, id: Int, navController: NavHostController) {
    val sqlOpenHelper = SQLOpenHelper(context)
    val response = sqlOpenHelper.deleteBook(context, id)
    if (response) {
        Toast.makeText(context, "Proceso Conluído con éxito", Toast.LENGTH_SHORT).show()
        navController.popBackStack()
    } else {
        Toast.makeText(context, "Hubo un problema al eliminar el libro", Toast.LENGTH_SHORT).show()
        navController.popBackStack()
    }
}

