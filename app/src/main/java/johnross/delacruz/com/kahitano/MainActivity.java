package johnross.delacruz.com.kahitano;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText eFname, eLname, eScore;
    DBHelper helper;
    Cursor table;
    int pos;

    private void displayData() {
        eFname.setText(table.getString(1));
        eLname.setText(table.getString(2));
        eScore.setText(table.getString(3));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helper = new DBHelper(this);

        table = helper.selectAllRecords();

        eFname = findViewById(R.id.etFname);
        eLname = findViewById(R.id.etLname);
        eScore = findViewById(R.id.etScore);
    }

    @Override
    protected void onStart() {
        super.onStart();
        table.moveToFirst();
        pos = 0;
        displayData();
    }

    public void addRecord(View v) {
        String fname = eFname.getText().toString().trim();
        String lname = eLname.getText().toString().trim();
        int score = Integer.parseInt(eScore.getText().toString().trim());

        boolean isInserted = helper.insert(fname,lname,score);

        if (isInserted == true) {
            Toast.makeText(this, "Record inserted...", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failes inserting record...", Toast.LENGTH_SHORT).show();
        }

    }

    public void moveFirst(View v) {
        table.moveToFirst();
        pos = 0;
        displayData();
    }

    public void movePrevious(View v) {
        if(table.getPosition() == 0) {
            table.moveToFirst();
            Toast.makeText(this, "Reached first record...", Toast.LENGTH_SHORT).show();
        } else {
            table.moveToPrevious();
            pos--;
        }
        displayData();
    }

    public void moveNext(View v) {
        if(table.getPosition() == table.getCount() - 1) {
            table.moveToLast();
            Toast.makeText(this, "Reached end of record...", Toast.LENGTH_SHORT).show();
        } else {
            table.moveToNext();
            pos++;
        }
        displayData();
    }

    public void moveLast(View v) {
        table.moveToLast();
        pos = table.getCount();
        displayData();
    }

    public void editRecord(View v) {
        String id = ""+table.getInt(0);
        String fname = eFname.getText().toString().trim();
        String lname = eLname.getText().toString().trim();
        int score = Integer.parseInt(eScore.getText().toString().trim());

        boolean isUpdated = helper.update(id, fname, lname, score);

        if(isUpdated == true) {
            Toast.makeText(this, "Record updated...", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Update failed...", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteRecord(View v) {
        String id = table.getString(0);
        boolean isDeleted = helper.delete(id);

        if(isDeleted == true) {
            Toast.makeText(this, "Record deleted...", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed deleting record...", Toast.LENGTH_SHORT).show();
        }
    }
}
