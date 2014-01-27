package ru.isu.shitts.diplom;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created with IntelliJ IDEA.
 * User: User
 * Date: 13.10.13
 * Time: 17:20
 * To change this template use File | Settings | File Templates.
 */
public class StudentList extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_list);

        Button btnSaveStudent = (Button)findViewById(R.id.btnSaveStudent);

        Button btnBackL = (Button)findViewById(R.id.btnBackL);

        btnBackL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(),CreateLecture.class);
                startActivity(intent);
            }
        });

        Button btnGoToListLecture = (Button)findViewById(R.id.btnGoToListLecture);

        btnGoToListLecture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent1);
            }
        });

        /*btnSaveLecture.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StudentList.class);
                startActivity(intent);
            }
        });*/
    }
}
