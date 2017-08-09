package com.example.alexeysushko.carandpeopleproject.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.alexeysushko.carandpeopleproject.DBHelper;
import com.example.alexeysushko.carandpeopleproject.MainActivityAdapter;
import com.example.alexeysushko.carandpeopleproject.R;
import com.example.alexeysushko.carandpeopleproject.entities.Car;
import com.example.alexeysushko.carandpeopleproject.entities.People;

import java.util.ArrayList;
import java.util.List;

// Активность на которой будет кнопка вызова второй активности для добавления новой информации о владельцах и авто.
// Так же кнопка очистки БД полностью
// Уже имеющаяся информация будет выведена в ListView, при длятельном нажатии на item будет выыедено
// контекстное меню Удилить или Редактировать item/

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemLongClickListener {
    private Button buttonAddInfo, buttonClear;
    private ListView listView;
    private List<People> peopleEntityList = new ArrayList<>();//Сида запишем людей
    private List<Car> carEntityList = new ArrayList<>();//Сюда запишем авто
    private DBHelper dbHelper;
    private int idDelete;//ИД по которому будет удаляться запись с БД
    //Публичные Люди и Авто для вставки в форму при редактировании
    public static People personUpgrade;
    public static Car carUpgrade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Найдем по ИД элементы
        listView = (ListView) findViewById(R.id.activity_main_list_view);
        listView.setOnItemLongClickListener(this);
        registerForContextMenu(listView);

        // создаем объект для создания и управления версиями БД
        dbHelper = new DBHelper(this, "MyDB");

        buttonAddInfo = (Button) findViewById(R.id.activity_main_button_add_info);
        buttonClear = (Button) findViewById(R.id.activity_main_button_clear_all);

        //Присвоим слушателя и откроем вторую активность для добавления новой информации
        buttonAddInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ActivityAdd.class);
                intent.putExtra("operation", "add");//передадим интент что мы добавляем
                startActivity(intent);
            }
        });

        //Аналогично обработаем нажатие на кнопку очистки БД
        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                //удаляем все записи
                db.delete("mytable", null, null);
                Toast.makeText(getApplicationContext(), "БД очищена", Toast.LENGTH_LONG).show();
                db.close();
                onResume();
            }
        });
    }


    //Метод чтения информации с БД
    private void readSQLite(){
        //Подключаемся к БД
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Log.v("tag", "--- Читаем с БД ---");
        //Делаем запрос всех данных с таблицы получаем курсор
        Cursor c = db.query("mytable", null, null, null, null, null, null);

        //ставим позицию курсора на первую строку выборки
        //если в выборке нет строк вернется false
        if(c.moveToFirst()) {

            //определяем номера столбцов по имени в выборке
            int idColIndex = c.getColumnIndex("id");
            int nameColIndex = c.getColumnIndex("name");
            int birthColIndex = c.getColumnIndex("birth");
            int adressColIndex = c.getColumnIndex("adress");
            int markaColIndex = c.getColumnIndex("marka");
            int modelColIndex = c.getColumnIndex("model");
            int year_carColIndex = c.getColumnIndex("year_car");
            int number_serialColIndex = c.getColumnIndex("number_serial");
            int number_stateColIndex = c.getColumnIndex("number_state");
            int commentColIndex = c.getColumnIndex("comment");

            do {
                //Заполним наши ArrayList
                peopleEntityList.add(new People(
                        c.getInt(idColIndex),
                        c.getString(nameColIndex),
                        c.getInt(birthColIndex),
                        c.getString(adressColIndex)));

                carEntityList.add(new Car(
                        c.getString(markaColIndex),
                        c.getString(modelColIndex),
                        c.getInt(year_carColIndex),
                        c.getString(number_serialColIndex),
                        c.getString(number_stateColIndex),
                        c.getString(commentColIndex)));
            } while (c.moveToNext());

        }
        c.close();
        db.close();
        Log.v("tag", "----------ЗАКРЫЛИ БД-------");
        Log.v("tag", "Вытфнутое с БД peopleEntityList---->>>> " + peopleEntityList);
        Log.v("tag", "Вытфнутое с БД carEntityList---->>>> " + carEntityList);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Обнулим значения перед чтением БД что бы не дублировалось
        peopleEntityList = new ArrayList<>();
        carEntityList = new ArrayList<>();
        readSQLite();
        listView.setAdapter(new MainActivityAdapter(this, peopleEntityList, carEntityList));
    }


    //Метод длительного нажатия на айтем
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Log.v("tag", "--->>>> ITEM Нажатие <<<<<---" + id);//это равносильно info.position в onCreateItemSelected
        idDelete = peopleEntityList.get((int) id).getId();
        personUpgrade = peopleEntityList.get((int) id);//Получаем элементы что б было что вставить при редактировании
        carUpgrade = carEntityList.get((int) id);
        Log.v("tag", "--->>>> ЛЮДИ <<<<<---" + personUpgrade.toString());
        Log.v("tag", "--->>>> АВТО <<<<<---" + carUpgrade.toString());

        return false;
    }

    //Отобразим контекстное меню по уже созданной разметке в папке menu в рессурсах
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
    }


    //Действия при нажатии на удаление или редактирование на контеустном меню
    @Override
    public boolean onContextItemSelected(MenuItem item) {

//        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();//для получения позиции айтема
        switch (item.getItemId()){

            case R.id.context_menu_edit:
//                Log.v("tag", "--->>>> НАЖАТО РЕДАКТИРОВАТЬ <<<<<---"+"ИД--->"+info.position);
                Intent intent = new Intent(this, ActivityAdd.class);
                intent.putExtra("operation", "upgrade");
                intent.putExtra("ID", idDelete);
                startActivity(intent);
                return true;

            case R.id.context_menu_delete:
                Log.v("tag", "--->>>> НАЖАТО УДАЛИТЬ <<<<<---"+"ИД--->"+idDelete);
                //подключаемся к БД
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.delete("mytable", "id = " + idDelete, null );
                Log.v("tag", "--->>>> УДАЛЕНО <<<<<---");
                dbHelper.close();
                onResume();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}
