package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText etname;
    private EditText etemail;
    private EditText etphone;
    private Button btn_write;
    private Button btn_remove;
    private Button btn_update;
    private Button btn_read;
    private TextView tv_display;
    MyHelper myHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void click(View view){
        String name,email,phone;
        SQLiteDatabase db;
        ContentValues values;
        etname=findViewById(R.id.et_name);
        etemail=findViewById(R.id.et_email);
        etphone=findViewById(R.id.et_phone);
        btn_write=findViewById(R.id.btn_write);
        btn_remove=findViewById(R.id.btn_remove);
        btn_update=findViewById(R.id.btn_update);
        btn_read=findViewById(R.id.btn_read);
        tv_display=findViewById(R.id.tv_display);
        myHelper=new MyHelper(this);
        switch(view.getId()){
            case R.id.btn_write:
                name=etname.getText().toString();
                email=etemail.getText().toString();
                phone=etphone.getText().toString();
                db=myHelper.getWritableDatabase();
                values=new ContentValues();
                values.put("name",name);
                values.put("email",email);
                values.put("phone",phone);
                db.insert("info",null,values);
                Toast.makeText(this,"Write Successfully",Toast.LENGTH_SHORT).show();
                db.close();
                break;
            case R.id.btn_remove:
                db=myHelper.getWritableDatabase();
                db.delete("info",null,null);
                Toast.makeText(this,"Remove Successfully",Toast.LENGTH_SHORT).show();
                tv_display.setText("\"                              ----------------\\n                           --no records--\\n                              ----------------\"");
                db.close();
            case R.id.btn_update:
                db=myHelper.getWritableDatabase();
                values=new ContentValues();
                values.put("name",etphone.getText().toString());
                values.put("email",etemail.getText().toString());
                db.update("info",values,"phone=?",new String[]{etphone.getText().toString()});
                Toast.makeText(this,"Update Successfully",Toast.LENGTH_SHORT).show();
                db.close();
            case R.id.btn_read:
                db=myHelper.getWritableDatabase();
                Cursor cursor=db.query("info",null,null,null,null,null,null,null);
                if(cursor.getCount()==0){
                    tv_display.setText("                              ----------------\n                           --no records--\n                              ----------------");
                    Toast.makeText(this,"None",Toast.LENGTH_SHORT).show();
                }
                else{
                    cursor.moveToFirst();
                    tv_display.setText("name:"+cursor.getString(1)+"\nemail："+cursor.getString(2)+"\nphoneNumber："+cursor.getString(3));
                }
                while(cursor.moveToNext()){
                    tv_display.append("\n"+"name:"+cursor.getString(1)+"\nemail："+cursor.getString(2)+"\nphoneNumber："+cursor.getString(3));
                }
                cursor.close();
                db.close();
                break;
        }

    }
}