package johnross.delacruz.com.kahitano;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    final static String DBName = "stud.db";
    final static int VER = 1;

    final static String TABLE = "Grade";

    public DBHelper(Context context) {
        super(context, DBName, null, VER);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE Grade (ID INTEGER PRIMARY KEY AUTOINCREMENT, FName TEXT, LName TEXT, LGrade INTEGER)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String deleteTable = "DROP TABLE IF EXISTS Grade";
        db.execSQL(deleteTable);
        onCreate(db);
    }

    public boolean insert(String fname, String lname, int lgrade) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("Fname",fname);
        cv.put("Lname",lname);
        cv.put("Lgrade",lgrade);
        long inserted = db.insert(TABLE, null, cv);

        if (inserted == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor selectAllRecords() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM grade", null);
    }

    public boolean update(String id, String fname, String lname, int lgrade) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("Fname",fname);
        cv.put("Lname",lname);
        cv.put("Lgrade",lgrade);
        db.update(TABLE, cv, "ID=?", new String[]{id});
        return true;
    }

    public boolean delete(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE, "ID=?", new String[]{id});
        return true;
    }
}
