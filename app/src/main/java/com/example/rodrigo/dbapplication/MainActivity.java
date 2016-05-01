package com.example.rodrigo.dbapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.rodrigo.dbapplication.DatabaseContext.MyDBAdapter;
import com.example.rodrigo.dbapplication.Service.StudentService;

import java.util.List;
import java.util.jar.Manifest;

public class MainActivity extends AppCompatActivity {

    //Variables globales
    private Button btnAddStudent,btnDeleteEngineering;
    private ListView lstStudents;
    private Spinner spnFaculties;
    private EditText edtInputStudent;
    private MyDBAdapter dbAdapter;
    private StudentService studentService;

    /**
     * Al cargar la activiad
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Referenciamos de la vista
        btnAddStudent = (Button)findViewById(R.id.btnAdd_Student);
        btnDeleteEngineering = (Button)findViewById(R.id.btnDelete_Student);
        lstStudents = (ListView)findViewById(R.id.lstStudents_List);
        spnFaculties = (Spinner)findViewById(R.id.spnFaculties);
        edtInputStudent = (EditText) findViewById(R.id.edtStudent_Name);

        //Inicializamos la base datos
        dbAdapter = new MyDBAdapter(MainActivity.this);
        dbAdapter.onOpen();
        //Inicializamos el servicio
        studentService = new StudentService(MainActivity.this, dbAdapter, lstStudents);
        studentService.refreshStudents();


        //Seteamos el array adapter al spinner
        spnFaculties.setAdapter(new ArrayAdapter<>(MainActivity.this,
                android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.faculties_datasource)));

        //Agregar
        btnAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtInputStudent.getText().length() > 0){
                    studentService.createStudent(edtInputStudent.getText().toString(),spnFaculties.getSelectedItemPosition() + 1);
                    edtInputStudent.setText("");
                }else{
                    Toast.makeText(MainActivity.this, "Ingresa un nombre valido", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Borrar
        btnDeleteEngineering.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                studentService.deleteEngineers();
            }
        });
    }
}
