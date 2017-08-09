package com.example.alexeysushko.carandpeopleproject.entities;

/**
 * Created by Alexey Sushko
 */

public class Car {
    public String carMarka;
    public String carModel;
    public int carYear;

    public void setCarMarka(String carMarka) {
        this.carMarka = carMarka;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public void setCarYear(int carYear) {
        this.carYear = carYear;
    }

    public void setCarSerialNumber(String carSerialNumber) {
        this.carSerialNumber = carSerialNumber;
    }

    public void setCarStateNumber(String carStateNumber) {
        this.carStateNumber = carStateNumber;
    }

    public void setCarComment(String carComment) {
        this.carComment = carComment;
    }

    public String carSerialNumber;
    public String carStateNumber;
    public String carComment;

    public Car(String carMarka, String carModel, int carYear, String carSerialNumber, String carStateNumber, String carComment){
        this.carMarka = carMarka;
        this.carModel = carModel;
        this.carYear = carYear;
        this.carSerialNumber = carSerialNumber;
        this.carStateNumber = carStateNumber;
        this.carComment = carComment;
    }

    @Override
    public String toString() {
        return "Car{" +
                "carMarka='" + carMarka + '\'' +
                ", carModel='" + carModel + '\'' +
                ", carYear=" + carYear +
                ", carSerialNumber='" + carSerialNumber + '\'' +
                ", carStateNumber='" + carStateNumber + '\'' +
                ", carComment='" + carComment + '\'' +
                '}';
    }

    public String getCarMarka() {
        return carMarka;
    }

    public String getCarModel() {
        return carModel;
    }

    public int getCarYear() {
        return carYear;
    }

    public String getCarSerialNumber() {
        return carSerialNumber;
    }

    public String getCarStateNumber() {
        return carStateNumber;
    }

    public String getCarComment() {
        return carComment;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Car car = (Car) o;

        if (carYear != car.carYear) return false;
        if (carMarka != null ? !carMarka.equals(car.carMarka) : car.carMarka != null) return false;
        if (carModel != null ? !carModel.equals(car.carModel) : car.carModel != null) return false;
        if (carSerialNumber != null ? !carSerialNumber.equals(car.carSerialNumber) : car.carSerialNumber != null)
            return false;
        if (carStateNumber != null ? !carStateNumber.equals(car.carStateNumber) : car.carStateNumber != null)
            return false;
        return carComment != null ? carComment.equals(car.carComment) : car.carComment == null;

    }

    @Override
    public int hashCode() {
        int result = carMarka != null ? carMarka.hashCode() : 0;
        result = 31 * result + (carModel != null ? carModel.hashCode() : 0);
        result = 31 * result + carYear;
        result = 31 * result + (carSerialNumber != null ? carSerialNumber.hashCode() : 0);
        result = 31 * result + (carStateNumber != null ? carStateNumber.hashCode() : 0);
        result = 31 * result + (carComment != null ? carComment.hashCode() : 0);
        return result;
    }
}
