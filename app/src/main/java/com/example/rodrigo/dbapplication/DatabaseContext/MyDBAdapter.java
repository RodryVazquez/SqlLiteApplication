package com.example.rodrigo.dbapplication.DatabaseContext;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Define la base de datos asi como los metodos para el CRUD
 */
public class MyDBAdapter {

    Context context;
    private MyDBHelper dbHelper;
    private SQLiteDatabase db;

    //Nombre y version de la base de datos
    private String DATABASE_NAME = "Students";
    private int DATABASE_VERSION = 1;

    /**
     * Inicializa la base de datos
     *
     * @param context
     */
    public MyDBAdapter(Context context) {
        this.context = context;
        dbHelper = new MyDBHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Abre la db para la lectura e escritura
     */
    public void onOpen() {
        try {
            db = dbHelper.getWritableDatabase();
        } catch (SQLException ex) {

        }
    }

    //Core de la base de datos
    private static class MyDBHelper extends SQLiteOpenHelper {

        /**
         * @param context contexto actual de la aplicacion
         * @param name    nombre de la base de datos
         * @param factory cursor factory
         * @param version version
         */
        public MyDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        /**
         * @param db
         */
        @Override
        public void onCreate(SQLiteDatabase db) {
            String query = "CREATE TABLE students (id integer primary key autoincrement, name text, faculty integer);";
            db.execSQL(query);
        }

        /**
         * @param db
         * @param oldVersion
         * @param newVersion
         */
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            String query = "DROP TABLE IF EXISTS students;";
            db.execSQL(query);
            onCreate(db);
        }
    }

    /**
     * Registra un nuevo estudiante
     * @param name
     * @param faculty
     */
    public void insertStudent(String name, int faculty){
        ContentValues cv = new ContentValues();
        cv.put("name",name);
        cv.put("faculty",faculty);
        db.insert("students",null,cv);
    }

    /**
     * Consulta todos los estudiantes
     * @return
     */
    public ArrayList<String>getAllStudents(){

        ArrayList<String> studentsList = new ArrayList<>();
        Cursor cursor = db.query("students", null, null, null, null, null, null);
        //Si el cursor contiene por lo menos un elemento
        if(cursor != null && cursor.moveToFirst()){
            do{
                studentsList.add(cursor.getString(1));
            }while (cursor.moveToNext());
        }
        return studentsList;
    }

    /**
     * Borra todos los estudiantes de ingenieria
     */
    public void deleteAllEngineers(){
        db.delete("students","faculty=1", null);
    }
}
