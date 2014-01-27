package ru.isu.shitts.diplom;

/**
 * Created with IntelliJ IDEA.
 * User: User
 * Date: 06.10.13
 * Time: 18:01
 * To change this template use File | Settings | File Templates.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DB {

    private static final String DB_NAME = "dbjournal";
    private static final int DB_VERSION = 1;

    //Таблица групп студентов
    private static final String ST_GROUP ="student_group";
    private static final String ST_GROUP_ID = "_id";
    private static final String ST_TITLE = "title";

    //Таблица студентов
    private static final String STUDENT = "student";
    public static final String STUDENT_ID = "_id";
    public static final String STUDENT_FIRST_NAME = "first_name";
    public static final String STUDENT_lAST_NAME = "last_name";
    public static final String STUDENT_PHONE = "phone";
    public static final String STUDENT_GROUP = "student_group_id";

    //Таблица лекций
    private static final String LECTURE = "lecture";
    public static final String LECTURE_ID = "_id";
    public static final String LECTURE_TITLE = "title_lecture";
    public static final String LECTURE_DATE = "date_lecture";
    public static final String LECTURE_GROUP = "group_lecture";
    public static final String LECTURE_UPDATE = "updat";

    private static final String DB_CREATE_STUDENT_GROUP =
            "create table " + ST_GROUP + "(" +
                    ST_GROUP_ID + " integer primary key autoincrement, " +
                    ST_TITLE + " text" +
                    ");";

    private static final String DB_CREATE_STUDENT =
            "create table " + STUDENT + "(" +
                    STUDENT_ID + " integer primary key autoincrement, " +
                    STUDENT_FIRST_NAME + " text," +
                    STUDENT_lAST_NAME + " text," +
                    STUDENT_PHONE + " text," +
                    STUDENT_GROUP + " text," +
                    "FOREIGN KEY (" + STUDENT_GROUP + ")" + " REFERENCES " + ST_GROUP + "(" + ST_GROUP_ID + ")" +
                    ");";

    private static final String DB_CREATE_LECTURE =
            "create table " + LECTURE + "(" +
                    LECTURE_ID + " integer primary key autoincrement, " +
                    LECTURE_TITLE + " text," +
                    LECTURE_DATE + " text," +
                    LECTURE_UPDATE + " text," +
                    LECTURE_GROUP + " text, " +
                   "FOREIGN KEY (" + LECTURE_GROUP + ")" + " REFERENCES " + ST_GROUP + "(" + ST_GROUP_ID + ")" +
                    ");";


    private final Context mCtx;


    private DBHelper mDBHelper;
    private SQLiteDatabase mDB;

    public DB(Context ctx) {
        mCtx = ctx;
    }

    // открыть подключение
    public void open() {
        mDBHelper = new DBHelper(mCtx, DB_NAME, null, DB_VERSION);
        mDB = mDBHelper.getWritableDatabase();
    }

    // закрыть подключение
    public void close() {
        if (mDBHelper!=null) mDBHelper.close();
    }

    // получить все данные из таблицы STUDENT
    public Cursor getAllDataTStudent() {
        return mDB.query(STUDENT, null, null, null, null, null, null);
    }

    // добавить запись в STUDENT
    public void addRecTStudent(String last_name, String first_name, String phone, String student_group) {
        ContentValues cv = new ContentValues();
        cv.put(STUDENT_lAST_NAME, last_name);
        cv.put(STUDENT_FIRST_NAME, first_name);
        cv.put(STUDENT_PHONE, phone);
        cv.put(STUDENT_GROUP, student_group);
        mDB.insert(STUDENT, null, cv);
    }

    // удалить запись из STUDENT
    public void delRecTStudent(long id) {
        mDB.delete(STUDENT, STUDENT_ID + " = " + id, null);
    }

    // получить все данные из таблицы LECTURE
    public Cursor getAllDataTLecture() {
        return mDB.query(LECTURE, null, null, null, null, null, null);
    }

    // добавить запись в LECTURE
    public void addRecTLecture(String title, String date, String student_group, String update) {
        ContentValues cv = new ContentValues();
        cv.put(LECTURE_TITLE, title);
        cv.put(LECTURE_DATE, date);
        cv.put(LECTURE_GROUP, student_group);
        cv.put(LECTURE_UPDATE, update);
        mDB.insert(LECTURE, null, cv);
    }

    // удалить запись из STUDENT
    public void delRecTLecture(long id) {
        mDB.delete(LECTURE, LECTURE_ID + " = " + id, null);
    }

    // получить все данные из таблицы ST_GROUP
    public Cursor getAllDataTStudentGroup() {
        return mDB.query(ST_GROUP, null, null, null, null, null, null);
    }

    // добавить запись в ST_GROUP
    public void addRecTStudentGroup(String title_sg) {
        ContentValues cv = new ContentValues();
        cv.put(ST_TITLE, title_sg);
        mDB.insert(ST_GROUP, null, cv);
    }

    // удалить запись из ST_GROUP
    public void delRecTStudentGroup(long id) {
        mDB.delete(ST_GROUP, ST_GROUP_ID + " = " + id, null);
    }

    // класс по созданию и управлению БД
    private class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                        int version) {
            super(context, name, factory, version);
        }

        // создаем и заполняем БД
        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(DB_CREATE_STUDENT_GROUP);
            db.execSQL(DB_CREATE_STUDENT);
            db.execSQL(DB_CREATE_LECTURE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }
}