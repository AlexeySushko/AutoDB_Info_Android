package com.example.alexeysushko.carandpeopleproject;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.alexeysushko.carandpeopleproject.activities.MainActivity;
import com.example.alexeysushko.carandpeopleproject.entities.Car;
import com.example.alexeysushko.carandpeopleproject.entities.People;

import java.io.PipedOutputStream;
import java.util.List;

/**
 * Created by Alexey Sushko
 */

public class MainActivityAdapter extends BaseAdapter {
    private List<People> peopleList = null;
    private List<Car> carList = null;
    private MainActivity mainActivity;

    public MainActivityAdapter(MainActivity mainActivity, List<People> entityPeoples, List<Car> entityCarList) {
        peopleList = entityPeoples;
        carList = entityCarList;
        this.mainActivity = mainActivity;
    }

    //возвращает кол-во списока всех элементов которые вложены в адаптер
    @Override
    public int getCount() {
        return peopleList.size();
    }

    //позиция элемента который в данный момент выбран
    @Override
    public Object getItem(int position) {
        return peopleList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        //Это одна вьюха что приходит
        View row = view;

        ViewHolder viewHolder;

        //Если мы создали лист но ничего там нет то нужно подтянуть с разметки
        //если == null то...
        if(row==null){
            //ниже мы создаем разметку, если этого не сделать то при прокрутке это
            //привело бы к подтормаживанию
            //вызываем LayoutInflanter - обькт который аозволит настроить разметку
            LayoutInflater inflater = LayoutInflater.from(mainActivity);
            //с помощью метода инфлейт будем обращаться к той разметке которая есть
            row = inflater.inflate(R.layout.item_list_view_main_activity, parent, false);//фальсе когда берем с отдельного файла, тру когда указали кусок
            viewHolder = new ViewHolder();

            viewHolder.name = (TextView) row.findViewById(R.id.item_textview_fio);
            viewHolder.birth = (TextView) row.findViewById(R.id.item_textview_year_birthd);
            viewHolder.adress = (TextView) row.findViewById(R.id.item_textview_adress);
            viewHolder.marka = (TextView) row.findViewById(R.id.item_textview_marka);
            viewHolder.model = (TextView) row.findViewById(R.id.item_textview_model);
            viewHolder.year = (TextView) row.findViewById(R.id.item_textview_year_car);
            viewHolder.serialNumb = (TextView) row.findViewById(R.id.item_textview_serial_number);
            viewHolder.stateNumber = (TextView) row.findViewById(R.id.item_textview_state_namber);
            viewHolder.comment = (TextView) row.findViewById(R.id.item_textview_comment);
            row.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        Log.v("tag", "--- В элсе ---");
        viewHolder.name.setText(peopleList.get(position).getName());
        viewHolder.birth.setText(String.valueOf(peopleList.get(position).getBirth()));
        viewHolder.adress.setText(peopleList.get(position).getAdress());
        viewHolder.marka.setText(carList.get(position).getCarMarka());
        viewHolder.model.setText(carList.get(position).getCarModel());
        viewHolder.year.setText(String.valueOf(carList.get(position).getCarYear()));
        viewHolder.serialNumb.setText(carList.get(position).getCarSerialNumber());
        viewHolder.stateNumber.setText(carList.get(position).getCarStateNumber());
        viewHolder.comment.setText(carList.get(position).getCarComment());

        return row;
    }

    //Пишем внутренний класс который будет менять значения в айтеме
    private class ViewHolder{
        TextView name;
        TextView birth;
        TextView adress;
        TextView marka;
        TextView model;
        TextView year;
        TextView serialNumb;
        TextView stateNumber;
        TextView comment;
    }
}
