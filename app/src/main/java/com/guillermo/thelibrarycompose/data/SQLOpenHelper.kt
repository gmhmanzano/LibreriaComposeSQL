package com.guillermo.thelibrarycompose.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLOpenHelper(context: Context?) : SQLiteOpenHelper(context, "BIBLIOTECA.DB", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val SQL =
            "CREATE TABLE books (idBook INTEGER PRIMARY KEY AUTOINCREMENT, titleBook TEXT, authorBook TEXT, priceBook DOUBLE, pagesBook DOUBLE )"
        db!!.execSQL(SQL)
    }

    fun addBookDB(context: Context?, book: book): Boolean {
        val contentValues = ContentValues()
        val sqlOpenHelper = SQLOpenHelper(context)
        val db = sqlOpenHelper.writableDatabase
        var response = false
        contentValues.put("titleBook", book.title)
        contentValues.put("authorBook", book.author)
        contentValues.put("priceBook", book.price)
        contentValues.put("pagesBook", book.pages)
        try {
            db.insert("books", null, contentValues)
        } catch (e: Exception) {
            e.printStackTrace()
            response = true
        } finally {
            db.close()
        }

        return response
    }

    fun ListBook(context: Context?): ArrayList<book> {
        val arrayList = ArrayList<book>()
        val sqlOpenHelper = SQLOpenHelper(context)
        val db = sqlOpenHelper.readableDatabase
        val SQL = "SELECT * FROM books ORDER BY titleBook"
        val row = db.rawQuery(SQL, null)
        while (row.moveToNext()) {
            arrayList.add(
                book(
                    row.getInt(0),
                    row.getString(1),
                    row.getString(2),
                    row.getDouble(3),
                    row.getDouble(4)
                )
            )
        }
        db.close()
        return arrayList
    }

    fun SeekBook(context: Context?, id: Int): book? {
        var book: book? = null
        val lista = arrayOf(id.toString())
        val sqlOpenHelper = SQLOpenHelper(context)
        val db = sqlOpenHelper.readableDatabase
        val row = db.rawQuery("SELECT * FROM books WHERE idBook = ?", lista)
        while (row.moveToNext()) {
            book = book(
                id = row.getInt(0),
                title = row.getString(1),
                author = row.getString(2),
                price = row.getDouble(3),
                pages = row.getDouble(4)
            )
        }
        db.close()
        return book
    }

    fun updateBook(context: Context?, book: book): Boolean {
        val sqlOpenHelper = SQLOpenHelper(context)
        val db = sqlOpenHelper.writableDatabase
        val contentValues = ContentValues()
        var response = true
        val argumentos = arrayOf(book.id.toString())
        contentValues.put("titleBook", book.title)
        contentValues.put("authorBook", book.author)
        contentValues.put("priceBook", book.price)
        contentValues.put("pagesBook", book.pages)
        try {
            db.update("books", contentValues, "idBook = ?", argumentos)
        } catch (e: Exception) {
            e.printStackTrace()
            response = false
        } finally {
            db.close()
        }
        return response
    }

    fun deleteBook(context: Context?, id: Int): Boolean {
        val sqlOpenHelper = SQLOpenHelper(context)
        val db = sqlOpenHelper.writableDatabase
        val lista = arrayOf(id.toString())
        var response = true
        try {
            db.delete("books", "idBook = ?", lista)
        } catch (e: Exception) {
            e.printStackTrace()
            response = false
        } finally {
            db.close()
        }
        return response
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}