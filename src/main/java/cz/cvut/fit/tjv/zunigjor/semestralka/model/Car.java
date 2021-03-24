package cz.cvut.fit.tjv.zunigjor.semestralka.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Car {
    @Id
    @GeneratedValue
    private int id;
    private String registrationNumber;
    private String brand;
    private String model;

    @OneToOne
    private ParkingSpot parkingSpot;

    @OneToMany(mappedBy = "car")
    private List<Tires> tireSets;

    @ManyToMany(mappedBy = "cars")
    private List<Owner> owners;

    public Car() {
    }

    public Car(String registrationNumber, String brand, String model) {
        this.registrationNumber = registrationNumber;
        this.brand = brand;
        this.model = model;
    }

    public Car(String registrationNumber, String brand, String model, ParkingSpot parkingSpot, List<Tires> tireSets, List<Owner> owners) {
        this.registrationNumber = registrationNumber;
        this.brand = brand;
        this.model = model;
        this.parkingSpot = parkingSpot;
        this.tireSets = tireSets;
        this.owners = owners;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public ParkingSpot getParkingSpot() {
        return parkingSpot;
    }

    public void setParkingSpot(ParkingSpot parkingSpot) {
        this.parkingSpot = parkingSpot;
    }

    public List<Tires> getTireSets() {
        return tireSets;
    }

    public void setTireSets(List<Tires> tireSets) {
        this.tireSets = tireSets;
    }

    public List<Owner> getOwners() {
        return owners;
    }

    public void setOwners(List<Owner> owners) {
        this.owners = owners;
    }
}
