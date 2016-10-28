package com.otcyan.frameexample_master;

import com.otcyan.jlog.JLog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        JLog.v("snamon test logger verson");
        JLog.e("snamon test logger error");

        JLog.v("snamon","snamon test logger verson");

        JLog.json("{name:snamon;age:25}");

        Student student = new Student();
        student.age =25;
        student.name = "snamon";
        JLog.obj(student);

        HashMap<String , Student> map = new HashMap<>();
        map.put("test" , student);
        map.put("ttt" , student);
        JLog.obj(map);

        List<Student> students = new ArrayList<>();
        students.add(student);
        students.add(student);
        students.add(student);
        JLog.obj(students);

    }

    public class Student{
        public String name ;
        public int age;
    }
}
