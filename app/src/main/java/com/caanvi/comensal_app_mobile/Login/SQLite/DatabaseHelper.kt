package com.caanvi.comensal_app_mobile.Login.SQLite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.caanvi.comensal_app_mobile.Login.Storage.usuarioData


class DatabaseHelper (context: Context):SQLiteOpenHelper(context, dbname, factory, version){

    companion object{
        internal val dbname="sqlDB"
        internal val factory = null
        internal val version = 1
    }


    override fun onCreate(db: SQLiteDatabase) {
        db?.execSQL("CREATE TABLE user (id VARCHAR PRIMARY KEY," +
                "email VARCHAR," +
                "nombre VARCHAR,"+
                "apellido VARCHAR,"+
                "contacto VARCHAR)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        //db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        //onCreate(db)
    }


    fun selectDB (): Boolean {

        val db : SQLiteDatabase = writableDatabase
        val query = "SELECT * FROM user"
        val cursor : Cursor = db.rawQuery(query, null)


        if (cursor.count > 0)
        {

            while(cursor.moveToNext()){
                usuarioData.idGeneral = cursor.getString(0)
                usuarioData.emailGeneral = cursor.getString(1)
                //usuarioData.nombreGeneral = cursor.getString(2)
                //usuarioData.apellidoGeneral = cursor.getString(3)
                //usuarioData.contactoGeneral = cursor.getString(4)
            }

            cursor.close()
            return true;
        }
        else{

            cursor.close()
            return false;
        }
    }

    fun insertDB (id:String,
                  email:String,
                  nombre:String,
                  apellido:String,
                  contacto:String){
        val db : SQLiteDatabase = writableDatabase
        val values: ContentValues = ContentValues()

        values.put("id", id)
        values.put("email", email)
        //values.put("nombre", nombre)
        //values.put("apellido", apellido)
        //values.put("contacto", contacto)

        db.insert("user", null, values)
        db.close()
    }


    fun deleteDB (id: String){
        val db : SQLiteDatabase = writableDatabase
        db.delete("user", "id =?", arrayOf(id))

        db.close()
    }

}