package ru.isu.shitts.diplom;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends Activity {

    private static final int CM_DELETE_ID = 1;
    private static final int CM_UPDATE_ID = 2;
    ListView lvLecture;
    DB db;
    SimpleCursorAdapter scAdapterTLecture;
    Cursor cursorTLecture;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Button btnCreateLecture = (Button)findViewById(R.id.btnCreateLecture);

        btnCreateLecture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(),CreateClass.class);
                startActivity(intent);
            }
        });

        // открываем подключение к БД
        db = new DB(this);
        db.open();

        // получаем курсор
        cursorTLecture = db.getAllDataTLecture();
        startManagingCursor(cursorTLecture);

        // формируем столбцы сопоставления
        String[] from = new String[] {DB.LECTURE_TITLE};
        int[] to = new int[] {R.id.tvText};

        // создааем адаптер и настраиваем список
        scAdapterTLecture = new SimpleCursorAdapter(this, R.layout.item, cursorTLecture, from, to);
        lvLecture = (ListView) findViewById(R.id.lvLecture);
        lvLecture.setAdapter(scAdapterTLecture);

        // добавляем контекстное меню к списку
        registerForContextMenu(lvLecture);
    }
       public void onCreateContextMenu(ContextMenu menu, View v,
                ContextMenu.ContextMenuInfo menuInfo) {
            switch (v.getId()){
                case R.id.lvLecture:
                    menu.add(0,CM_UPDATE_ID,0,R.string.update_lecture);
                    menu.add(0,CM_DELETE_ID,0,R.string.delete_lecture);
                    break;
            }
            super.onCreateContextMenu(menu,v,menuInfo);
        }

        public boolean onContextItemSelected(MenuItem item) {
            switch (item.getItemId()){
                case CM_DELETE_ID:
                    AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                    db.delRecTLecture(acmi.id);
                    cursorTLecture.requery();
                    break;
                case CM_UPDATE_ID:
                   /* AdapterView.AdapterContextMenuInfo aci = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
                    long id_rec = aci.id;
                    Intent intentId = new Intent(getApplicationContext(),MainActivity3.class);
                    intentId.putExtra("id_rec",id_rec);
                    startActivity(intentId);*/
                    break;
            }
            return super.onContextItemSelected(item);
        }
    protected void onDestroy() {
        super.onDestroy();
// закрываем подключение при выходе
        db.close();
    }
}