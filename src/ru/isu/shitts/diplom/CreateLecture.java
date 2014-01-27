package ru.isu.shitts.diplom;

/**
 * Created with IntelliJ IDEA.
 * User: User
 * Date: 06.10.13
 * Time: 19:18
 * To change this template use File | Settings | File Templates.
 */
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class CreateLecture extends Activity{

    DB db;
    SimpleCursorAdapter scAdapterTLecture;
    Cursor cursorTLecture;
    String title, date, student_group, update;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_lecture);

        Button btnSaveLecture = (Button)findViewById(R.id.btnSaveLecture);

        Button btnBack = (Button)findViewById(R.id.btnBack);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });

        // открываем подключение к БД
        db = new DB(this);
        db.open();

        // получаем курсор
        cursorTLecture = db.getAllDataTLecture();
        startManagingCursor(cursorTLecture);

        btnSaveLecture.setOnClickListener(new OnClickListener(){
            @Override
            public  void onClick(View v) {
                title = "лекция 1";
                date = "23/03";
                student_group = "2231";
                update = "24/03";
                db.addRecTLecture(title,date,student_group,update);
                cursorTLecture.requery();
                Intent intent = new Intent(getApplicationContext(), StudentList.class);
                startActivity(intent);
            }
        });
    }
    protected void onDestroy() {
        super.onDestroy();
// закрываем подключение при выходе
        db.close();
    }
}