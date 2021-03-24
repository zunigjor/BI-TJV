package cz.cvut.fit.tjv.zunigjor.semestralka.rest.dto;

import org.springframework.hateoas.RepresentationModel;

public class ParkingSpotDTO extends RepresentationModel<ParkingSpotDTO> {
    private int id;
    private int parkingSpotNumber;
    private Integer carId;

    public ParkingSpotDTO() {
    }

    public ParkingSpotDTO(int id, int parkingSpotNumber, Integer carId) {
        this.id = id;
        this.parkingSpotNumber = parkingSpotNumber;
        this.carId = carId;
    }

    public ParkingSpotDTO(int parkingSpotNumber, Integer carId) {
        this.parkingSpotNumber = parkingSpotNumber;
        this.carId = carId;
    }

    public int getId() {
        return id;
    }

    public int getParkingSpotNumber() {
        return parkingSpotNumber;
    }

    public Integer getCarId() {
        return carId;
    }
}
