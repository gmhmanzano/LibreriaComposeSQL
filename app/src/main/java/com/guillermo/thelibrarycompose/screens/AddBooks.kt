package com.guillermo.thelibrarycompose.screens

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.guillermo.thelibrarycompose.data.SQLOpenHelper
import com.guillermo.thelibrarycompose.data.book
import com.guillermo.thelibrarycompose.ui.theme.TopBar

@Composable
fun AddBooks(NavigationBack: () -> Unit) {
    val context = LocalContext.current
    Scaffold(
        topBar = { TopBar() },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            FloatingActionButton(onClick = {
                NavigationBack()
            }) {
                Icon(Icons.Filled.Home, contentDescription = "Casa")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize().verticalScroll(rememberScrollState()), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var titulo by rememberSaveable { mutableStateOf("") }
            var autor by rememberSaveable { mutableStateOf("") }
            var precio by rememberSaveable { mutableStateOf("") }
            var paginas by rememberSaveable { mutableStateOf("")

            }
            Text("Datos del Libro", modifier = Modifier.padding(10.dp), fontSize = 25.sp)
            OutlinedTextField(value = titulo, onValueChange = {titulo = it}, label = {
                Text("Título del Libro", color = Color.Blue)
            }, modifier = Modifier.fillMaxWidth(0.85f).padding(vertical = 10.dp))
            OutlinedTextField(value = autor, onValueChange = {autor = it}, label = {
                Text("Autor del Libro", color = Color.Blue)
            }, modifier = Modifier.fillMaxWidth(0.85f).padding(vertical = 10.dp))
            OutlinedTextField(value = precio, onValueChange = {precio = it}, label = {
                Text("Precio", color = Color.Blue)
            }, modifier = Modifier.fillMaxWidth(0.85f).padding(vertical = 10.dp))
            OutlinedTextField(value = paginas, onValueChange = {paginas = it}, label = {
                Text("Páginas", color = Color.Blue)
            }, modifier = Modifier.fillMaxWidth(0.85f).padding(vertical = 10.dp))
            OutlinedButton(onClick = {

                SaveData(context, book(id = null, title = titulo, author = autor, pages = paginas.toDouble(), price = precio.toDouble()))
                titulo = ""
                autor = ""
                precio = ""
                paginas = ""
            }, colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary) ) {
                Text("Grabar")
            }
        }


    }
}
fun SaveData(context: Context, book:book){
    val sqlOpenHelper = SQLOpenHelper(context)
    Toast.makeText(context,"Operación realizada con éxito",Toast.LENGTH_SHORT).show()
    if (sqlOpenHelper.addBookDB(context = context, book = book) ){
        Toast.makeText(context,"Operación realizada con éxito",Toast.LENGTH_SHORT).show()
    }
    else {
        Toast.makeText(context,"No se pudo grabar",Toast.LENGTH_SHORT).show()
    }

}
