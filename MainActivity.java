package com.example.itwill.myapplication190820_02;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    DatePicker dp;
    EditText edit;
    Button btn;
    String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("간단 일기장");

        dp = (DatePicker) findViewById(R.id.datePicker);
        edit = (EditText) findViewById(R.id.edit);
        btn = (Button) findViewById(R.id.btnWrite);

        Calendar cal = Calendar.getInstance();
        int cYear = cal.get(Calendar.YEAR);
        int cMonth = cal.get(Calendar.MONTH);
        int cDay = cal.get(Calendar.DAY_OF_MONTH);

        dp.init(cYear, cMonth, cDay, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // 해당 일기의 내용을 해당 날짜를 제목으로 저장
                // y_m_d.txt
                // fileName = year+"_"+(monthOfYear+1)+"_"+dayOfMonth+".txt";
                fileName = Integer.toString(year)+"_"+Integer.toString(monthOfYear+1)
                         +"_"+Integer.toString(dayOfMonth)+".txt";

                // 변경된 날짜의 일기를 읽어오는 작업
                String str = readDiary(fileName);

                edit.setText(str);
                btn.setEnabled(true);

            }
        }); // init()

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    FileOutputStream fos = openFileOutput(fileName, Context.MODE_PRIVATE);
                    String inputData = edit.getText().toString();
                    fos.write(inputData.getBytes());
                    fos.close();
                    Toast.makeText(getApplicationContext(), fileName + " 저장완료", Toast.LENGTH_SHORT).show();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    } // onCreate()

    // readDiary(Name)
    public String readDiary(String Name){
        String diary = "";
        FileInputStream fis;
        try {
            fis = openFileInput(Name);
            byte[] txt = new byte[500];

            fis.read(txt);
            fis.close();

            diary = (new String(txt)).trim();
            btn.setText("수정하기");

            if(diary.length() > 0){
                Toast.makeText(getApplicationContext(),fileName+" 불러옴",Toast.LENGTH_SHORT).show();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            edit.setHint("일기가 없습니다. 새로운 일기를 작성하세요 ^^");
            btn.setText("새로 저장");
        } catch (IOException e) {
            e.printStackTrace();
        }


        return diary;
    }
    // readDiary(Name)

}
