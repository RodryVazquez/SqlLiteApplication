package com.example.rodrigo.dbapplication.Service;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.example.rodrigo.dbapplication.DatabaseContext.MyDBAdapter;
import java.util.List;

/**
 * Servicio para el CRUD de estudiantes
 */
public class StudentService {

    private Context context;
    private MyDBAdapter dbAdapter;
    private ListView lstStudents;

    /**
     *
     * @param context
     * @param dbAdapter
     * @param lstStudents
     */
    public StudentService(Context context, MyDBAdapter dbAdapter, ListView lstStudents){
        this.context = context;
        this.dbAdapter = dbAdapter;
        this.lstStudents = lstStudents;
    }

    //
    public void refreshStudents(){
        List<String> students = dbAdapter.getAllStudents();
        lstStudents.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, students));
    }

    //
    public void createStudent(String name, int faculty){
        dbAdapter.insertStudent(name,faculty);
        refreshStudents();
    }

    //
    public void deleteEngineers(){
        dbAdapter.deleteAllEngineers();
        refreshStudents();
    }
}
