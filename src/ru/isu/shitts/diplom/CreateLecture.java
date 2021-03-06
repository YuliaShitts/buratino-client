package ru.isu.shitts.diplom;

/**
 * Created with IntelliJ IDEA.
 * User: User
 * Date: 06.10.13
 * Time: 19:18
 * To change this template use File | Settings | File Templates.
 */
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

public class CreateLecture extends Activity{

    String fulldate, fulltime;
    int DIALOG_TIME = 1;
    int myHour = 14;
    int myMinute = 20;
    TextView tvTime;

    int DIALOG_DATE = 2;
    int myYear = 2014;
    int myMonth = 01;
    int myDay = 13;
    TextView tvDate;

    DB db;
    SimpleCursorAdapter scAdapterTLecture;
    Cursor cursorTLecture;
    String title, date, student_group, update;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_lecture);
        tvTime = (TextView) findViewById(R.id.tvTime);
        tvDate = (TextView) findViewById(R.id.tvDate);

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
                date = fulldate;
                student_group = "2231";
                update = "24/03";
                title = student_group + ", "+ date + ", " + fulltime;
                db.addRecTLecture(title,date,student_group,update);
                cursorTLecture.requery();
                Intent intent = new Intent(getApplicationContext(), StudentList.class);
                startActivity(intent);
            }
        });
    }

    public void onclick(View view) {
        showDialog(DIALOG_TIME);
    }

    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_TIME) {
            TimePickerDialog tpd = new TimePickerDialog(this, myCallBackTime, myHour, myMinute, true);
            return tpd;
        }
        if (id == DIALOG_DATE) {
            DatePickerDialog tpd = new DatePickerDialog(this, myCallBack, myYear, myMonth, myDay);
            return tpd;
        }
        return super.onCreateDialog(id);
    }

    TimePickerDialog.OnTimeSetListener myCallBackTime = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            myHour = hourOfDay;
            myMinute = minute;
            fulltime = myHour + ":" + myMinute;
            tvTime.setText("   Время: " + fulltime);
        }
    };

    public void onclickData(View view) {
        showDialog(DIALOG_DATE);
    }

    DatePickerDialog.OnDateSetListener myCallBack = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myYear = year;
            myMonth = monthOfYear;
            myDay = dayOfMonth;
            fulldate = myDay + "/" + myMonth + "/" + myYear;
            tvDate.setText("   Дата: " + fulldate);
        }
    };

    protected void onDestroy() {
        super.onDestroy();
// закрываем подключение при выходе
        db.close();
    }
}