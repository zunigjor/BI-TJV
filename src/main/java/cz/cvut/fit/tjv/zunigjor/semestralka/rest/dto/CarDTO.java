package cz.cvut.fit.tjv.zunigjor.semestralka.rest.dto;

import org.springframework.hateoas.RepresentationModel;

import java.util.List;

public class CarDTO extends RepresentationModel<CarDTO>{
    private int id;
    private String registrationNumber;
    private String brand;
    private String model;
    private List<Integer> ownerIds;
    private Integer parkingSpot;
    private List<Integer> tireSetIds;

    public CarDTO() {
    }

    public CarDTO(int id, String registrationNumber, String brand, String model, List<Integer> ownerIds, Integer parkingSpot, List<Integer> tireSetIds) {
        this.id = id;
        this.registrationNumber = registrationNumber;
        this.brand = brand;
        this.model = model;
        this.ownerIds = ownerIds;
        this.parkingSpot = parkingSpot;
        this.tireSetIds = tireSetIds;
    }

    public CarDTO(String registrationNumber, String brand, String model, List<Integer> ownerIds, Integer parkingSpot, List<Integer> tireSetIds) {
        this.registrationNumber = registrationNumber;
        this.brand = brand;
        this.model = model;
        this.ownerIds = ownerIds;
        this.parkingSpot = parkingSpot;
        this.tireSetIds = tireSetIds;
    }

    public int getId() {
        return id;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public List<Integer> getOwnerIds() {
        return ownerIds;
    }

    public Integer getParkingSpot() {
        return parkingSpot;
    }

    public List<Integer> getTireSetIds() {
        return tireSetIds;
    }
}
