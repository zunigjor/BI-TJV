package cz.cvut.fit.tjv.zunigjor.semestralka.rest.dto;

import org.springframework.hateoas.RepresentationModel;

public class TiresDTO extends RepresentationModel<TiresDTO> {
    private int id;
    private String brand;
    private String use;
    private String serialNumber;
    private Integer carId;

    public TiresDTO() {
    }

    public TiresDTO(int id, String brand, String use, String serialNumber, Integer carId) {
        this.id = id;
        this.brand = brand;
        this.use = use;
        this.serialNumber = serialNumber;
        this.carId = carId;
    }

    public TiresDTO(String brand, String use, String serialNumber, Integer carId) {
        this.brand = brand;
        this.use = use;
        this.serialNumber = serialNumber;
        this.carId = carId;
    }

    public int getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public String getUse() {
        return use;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public Integer getCarId() {
        return carId;
    }
}
