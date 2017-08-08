package com.example.alexeysushko.carandpeopleproject.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.alexeysushko.carandpeopleproject.R;

//*Активность на которой будет кнопка вызова второй активности длядобавления новой информации о владельцах и авто.
// Уже имеющаяся информация будет выведена в ListView/

public class MainActivity extends AppCompatActivity {
    private Button buttonAddInfo;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Найдем по ИД элементы
        listView = (ListView) findViewById(R.id.activity_main_list_view);
        registerForContextMenu(listView);
        buttonAddInfo = (Button) findViewById(R.id.activity_main_button_add_info);

        //Присвоим слушателя и откроем вторую активность для добавления новой информации
        buttonAddInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ActivityAdd.class));
            }
        });
    }
}
