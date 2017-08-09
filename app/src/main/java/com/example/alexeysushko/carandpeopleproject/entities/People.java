package com.example.alexeysushko.carandpeopleproject.entities;

/**
 * Created by Alexey Sushko
 */

public class People {

    private int id;
    private String name;
    private int birth;
    private String adress;

    public People(int id, String name, int birth, String adress){
        this.id = id;
        this.name = name;
        this.birth = birth;
        this.adress = adress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        People people = (People) o;

        if (id != people.id) return false;
        if (birth != people.birth) return false;
        if (name != null ? !name.equals(people.name) : people.name != null) return false;
        return adress != null ? adress.equals(people.adress) : people.adress == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + birth;
        result = 31 * result + (adress != null ? adress.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "People{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birth=" + birth +
                ", adress='" + adress + '\'' +
                '}';
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public void setBirth(int birth) {
        this.birth = birth;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getBirth() {
        return birth;
    }

    public String getAdress() {
        return adress;
    }

    public int getId() {
        return id;
    }
}
