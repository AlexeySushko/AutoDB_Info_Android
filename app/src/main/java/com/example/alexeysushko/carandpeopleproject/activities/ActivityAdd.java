package com.example.alexeysushko.carandpeopleproject.activities;


import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.alexeysushko.carandpeopleproject.DBHelper;
import com.example.alexeysushko.carandpeopleproject.R;

//Активность для добавления в БД информации о владельцах и авто
public class ActivityAdd extends AppCompatActivity {

    private EditText etName, etBirth, etAdress, etMarka, etModel, etYearCar, etSerialNumber, etStateNumber, etComment;
    private Button addInDB;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        //Найдем все елементы по ИД
        etName = (EditText) findViewById(R.id.activity_add_edittext_fio);
        etBirth = (EditText) findViewById(R.id.activity_add_edittext_year_birth);
        etAdress = (EditText) findViewById(R.id.activity_add_edittext_adress);
        etMarka = (EditText) findViewById(R.id.activity_add_edittext_marka_auto);
        etModel = (EditText) findViewById(R.id.activity_add_edittext_model_auto);
        etYearCar = (EditText) findViewById(R.id.activity_add_edittext_year_auto);
        etSerialNumber = (EditText) findViewById(R.id.activity_add_edittext_serial_number);
        etStateNumber = (EditText) findViewById(R.id.activity_add_edittext_state_number);
        etComment= (EditText) findViewById(R.id.activity_add_edittext_comment);

        //Создаем обьект для создания и управления БД
        dbHelper = new DBHelper(this, "MyDB");

        addInDB = (Button) findViewById(R.id.activity_add_button_add);
        //добавим сразу анонимного слушателя
        addInDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                String operation = intent.getStringExtra("operation").toString();
                int idUpgrade = intent.getIntExtra("ID", 0);

                if(Proverka()){

                    if(operation.equals("upgrade")){
                        upgradeSQLite(idUpgrade);//передаается ИД по которому будет производиться обновление
                        Toast.makeText(getApplicationContext(), "Инормация ОБНОВЛЕНА" , Toast.LENGTH_SHORT).show();
                        finish();


                    }if(operation.equals("add")){
                        writeSQLite();
                        Toast.makeText(getApplicationContext(), "Инормация добавлена" , Toast.LENGTH_SHORT).show();
                        finish();
                    }

                }else{
                    Toast.makeText(getApplicationContext(), "Не все поля заполнены" , Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    /**
     * Метод для записи информации в БД
     */
    private void writeSQLite(){
        Log.v("tag", "--- Записываем в БД ---");

        //Подключаемся в БД и подготавливаем данные для вставки в виде пар
        // наименование столбца-значение с помощью метода CreateValuesForSQLite() который возвращает ContentValues cv
        SQLiteDatabase  db = dbHelper.getWritableDatabase();

        //вставляем запись и получаем ее ID
        long rowID = db.insert("mytable", null, CreateValuesForSQLite());
        Log.v("tag", "row inserted, ID = " + rowID);
        db.close();
    }


    //Метод для обновления инфы в БД
    private void upgradeSQLite(int id){

        //Подключаемся в БД и подготавливаем данные для вставки в виде пар
        // наименование столбца-значение с помощью метода CreateValuesForSQLite() который возвращает ContentValues cv
        SQLiteDatabase  db = dbHelper.getWritableDatabase();
        db.update("mytable", CreateValuesForSQLite(), "id = "+id, null);
        db.close();

    }

    //Метод создания данных для записи, будет немного закручено по методам но зато не дублируется(Без копипаста)
    private ContentValues CreateValuesForSQLite(){
        //Создаем обьект данных
        ContentValues cv = new ContentValues();

        cv.put("name", String.valueOf(etName.getText()));
        cv.put("birth", Integer.valueOf(etBirth.getText().toString()));
        cv.put("adress", String.valueOf(etAdress.getText()));
        cv.put("marka", String.valueOf(etMarka.getText()));
        cv.put("model", String.valueOf(etModel.getText()));
        cv.put("year_car", Integer.valueOf(etYearCar.getText().toString()));
        cv.put("number_serial", String.valueOf(etSerialNumber.getText()));
        cv.put("number_state", String.valueOf(etStateNumber.getText()));
        cv.put("comment", String.valueOf(etComment.getText()));
        return cv;
    }

    //Метод проверки чтобы все поля на форме были заполнены
    private boolean Proverka(){
        if(etName.getText().toString().equals("")||
                etBirth.getText().toString().equals("")||
                etAdress.getText().toString().equals("")||
                etMarka.getText().toString().equals("")||
                etModel.getText().toString().equals("")||
                etYearCar.getText().toString().equals("")||
                etSerialNumber.getText().toString().equals("")||
                etStateNumber.getText().toString().equals("")||
                etComment.getText().toString().equals("")){
            return false;
        }
        return true;
    }


    //Метод установки значений в поля перед редактированием
    private void setValues(){
        etName.setText(MainActivity.personUpgrade.getName());
        etBirth.setText(String.valueOf(MainActivity.personUpgrade.getBirth()));
        etAdress.setText(MainActivity.personUpgrade.getAdress());
        etMarka.setText(MainActivity.carUpgrade.getCarMarka());
        etModel.setText(MainActivity.carUpgrade.getCarModel());
        etYearCar.setText(String.valueOf(MainActivity.carUpgrade.getCarYear()));
        etSerialNumber.setText(MainActivity.carUpgrade.getCarSerialNumber());
        etStateNumber.setText(MainActivity.carUpgrade.getCarStateNumber());
        etComment.setText(MainActivity.carUpgrade.getCarComment());
    }


    @Override
    protected void onResume() {
        super.onResume();

        //Проверка если редактирование то что бы засетило значения в поля
        Intent intent = getIntent();
        String operation = intent.getStringExtra("operation").toString();
            if (operation.equals("upgrade")) {
                setValues();
            }
    }
}
