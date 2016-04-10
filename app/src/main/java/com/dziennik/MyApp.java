package com.dziennik;

import com.dziennik.R.id;
import com.dziennik.R.layout;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;

public class MyApp extends Activity implements OnClickListener
{
    EditText editRollno,editName,editMarks;
    Button btnAdd,btnDelete,btnModify,btnViewAll, btnAverage, btnAccelerometer, btnJson;
    SQLiteDatabase db;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_my_app);
        editRollno=(EditText)findViewById(R.id.editRollno);
        editName=(EditText)findViewById(R.id.editName);
        editMarks=(EditText)findViewById(R.id.editMarks);
        btnAdd=(Button)findViewById(R.id.btnAdd);
        btnDelete=(Button)findViewById(R.id.btnDelete);
        btnModify=(Button)findViewById(R.id.btnModify);
        btnViewAll=(Button)findViewById(R.id.btnViewAll);
        btnAverage=(Button)findViewById(R.id.btnAverage);
        btnAccelerometer=(Button)findViewById(id.btnAccelerometer);
        btnJson=(Button)findViewById(id.btnJson);
        btnAdd.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnModify.setOnClickListener(this);
        btnViewAll.setOnClickListener(this);
        btnAverage.setOnClickListener(this);
        btnAccelerometer.setOnClickListener(this);
        btnJson.setOnClickListener(this);
        db=openOrCreateDatabase("student", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS student(rollno INTEGER PRIMARY KEY AUTOINCREMENT,name VARCHAR,marks VARCHAR);");
    }
    public void onClick(View view)
    {
        if(view==btnAdd)
        {
            if(editName.getText().toString().trim().length()==0||
                    editMarks.getText().toString().trim().length()==0)
            {
                showMessage("Błąd", "Wypełnij wszystkie pola");
                return;
            }
            if (Float.parseFloat(editMarks.getText().toString()) < 1 ||
                    Float.parseFloat(editMarks.getText().toString()) > 6)
            {
                showMessage("Błąd", "Ocena musi być z zakresu 1-6");
                return;
            }
            db.execSQL("INSERT INTO student (name, marks) VALUES('"+editName.getText()+
                    "','"+editMarks.getText()+"');");
            clearText();
        }
        if(view==btnDelete)
        {
            if(editRollno.getText().toString().trim().length()==0)
            {
                showMessage("Błąd", "Podaj ID");
                return;
            }
            Cursor c=db.rawQuery("SELECT * FROM student WHERE rollno='"+editRollno.getText()+"'", null);
            if(c.moveToFirst())
            {
                db.execSQL("DELETE FROM student WHERE rollno='"+editRollno.getText()+"'");
            }
            else
            {
                showMessage("Błąd", "Niepoprawne ID");
            }
            clearText();
        }
        if(view==btnModify)
        {
            if(editRollno.getText().toString().trim().length()==0)
            {
                showMessage("Błąd", "Podaj ID");
                return;
            }
            Cursor c=db.rawQuery("SELECT * FROM student WHERE rollno='"+editRollno.getText()+"'", null);
            if(c.moveToFirst())
            {
                db.execSQL("UPDATE student SET name='"+editName.getText()+"',marks='"+editMarks.getText()+
                        "' WHERE rollno='"+editRollno.getText()+"'");
            }
            else
            {
                showMessage("Błąd", "Niepoprawne ID");
            }
            clearText();
        }
        if(view==btnViewAll)
        {
            Cursor c=db.rawQuery("SELECT * FROM student", null);
            if(c.getCount()==0)
            {
                showMessage("Błąd", "Baza pusta");
                return;
            }
            StringBuffer buffer=new StringBuffer();
            while(c.moveToNext())
            {
                buffer.append("ID: "+c.getString(0)+"\n");
                buffer.append("Przedmiot: "+c.getString(1)+"\n");
                buffer.append("Ocena: "+c.getString(2)+"\n\n");
            }
            showMessage("Oceny", buffer.toString());
        }
        if(view==btnAverage)
        {
            if(editName.getText().toString().trim().length()==0)
            {
                showMessage("Błąd", "Podaj nazwe przemdiotu");
                return;
            }
            Cursor c=db.rawQuery("SELECT CAST(AVG(marks) as float(10,1)) FROM student WHERE name='"+editName.getText()+"'", null);
            c.moveToFirst();
            StringBuffer buffer=new StringBuffer();
            buffer.append("Przedmiot: "+editName.getText()+"\n");
            buffer.append("Srednia: "+c.getString(0)+"\n");
            showMessage("Srednia ocen", buffer.toString());
        }
        if(view==btnAccelerometer)
        {
            Intent intent = new Intent(MyApp.this, Accelerometer.class);
            startActivity(intent);
        }
        if(view==btnJson)
        {
            Intent intent = new Intent(MyApp.this, JsonList.class);
            startActivity(intent);
        }
    }

    public void showMessage(String title,String message)
    {
        Builder builder=new Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
    public void clearText()
    {
        editRollno.setText("");
        editName.setText("");
        editMarks.setText("");
        editRollno.requestFocus();
    }
}