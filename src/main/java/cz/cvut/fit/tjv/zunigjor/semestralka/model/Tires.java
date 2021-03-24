package cz.cvut.fit.tjv.zunigjor.semestralka.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Tires {
    @Id
    @GeneratedValue
    private int id;
    private String brand;
    private String use;
    private String serialNumber;

    @ManyToOne
    private Car car;

    public Tires() {
    }

    public Tires(String brand, String use, String serialNumber) {
        this.brand = brand;
        this.use = use;
        this.serialNumber = serialNumber;
    }

    public Tires(String brand, String use, String serialNumber, Car car) {
        this.brand = brand;
        this.use = use;
        this.serialNumber = serialNumber;
        this.car = car;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getUse() {
        return use;
    }

    public void setUse(String use) {
        this.use = use;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
}
