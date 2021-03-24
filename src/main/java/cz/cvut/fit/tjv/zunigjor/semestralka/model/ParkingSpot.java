package cz.cvut.fit.tjv.zunigjor.semestralka.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class ParkingSpot {
    @Id
    @GeneratedValue
    private int id;
    private int parkingSpotNumber;

    @OneToOne(mappedBy = "parkingSpot")
    private Car car;

    public ParkingSpot() {
    }

    public ParkingSpot(int parkingSpotNumber) {
        this.parkingSpotNumber = parkingSpotNumber;
    }

    public ParkingSpot(int parkingSpotNumber, Car car) {
        this.parkingSpotNumber = parkingSpotNumber;
        this.car = car;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParkingSpotNumber() {
        return parkingSpotNumber;
    }

    public void setParkingSpotNumber(int parkingSpotNumber) {
        this.parkingSpotNumber = parkingSpotNumber;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
}
