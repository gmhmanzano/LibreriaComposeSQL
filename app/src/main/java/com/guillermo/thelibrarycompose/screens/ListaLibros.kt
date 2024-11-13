package com.guillermo.thelibrarycompose.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.guillermo.thelibrarycompose.data.SQLOpenHelper
import com.guillermo.thelibrarycompose.data.book
import com.guillermo.thelibrarycompose.navigation.AppScreen


@Composable
fun ListaLibros(navController: NavController) {

    val sqlOpenHelper = SQLOpenHelper(LocalContext.current)
    val arrayList = sqlOpenHelper.ListBook(LocalContext.current)
    LazyColumn(verticalArrangement = Arrangement.SpaceAround) {
        items(arrayList) { MyBook ->
            ItemBook(navController,MyBook) {
                book(MyBook.id, MyBook.title, MyBook.author, MyBook.price, MyBook.pages)
            }
        }
    }
}

@Composable
fun ItemBook(navController: NavController,book: book, onItemSelected: (book) -> Unit) {
    val context = LocalContext.current
    Row(modifier = Modifier
        .fillMaxWidth()
        .drawBehind {
            val borderSize = 1.dp.toPx()
            drawLine(
                color = Color.Black,
                start = Offset(0f, size.height),
                end = Offset(size.width, size.height),
                strokeWidth = borderSize
            )
        }
        .padding(vertical = 10.dp)
        .clickable {
             onItemSelected(book)
            val numero = book.id
            navController.navigate(AppScreen.DetailBook.route + "/$numero")
            //Toast.makeText(context,numero.toString(),Toast.LENGTH_SHORT).show()

        }) {

        Text(book.id.toString(), modifier = Modifier.padding(horizontal = 5.dp))
        Text(book.title, modifier = Modifier.padding(horizontal = 5.dp))
        Text(book.author, modifier = Modifier.padding(horizontal = 5.dp))
    }
}
