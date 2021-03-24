package cz.cvut.fit.tjv.zunigjor.semestralka.rest.dto;

import org.springframework.hateoas.RepresentationModel;

import java.util.List;

public class OwnerDTO extends RepresentationModel<OwnerDTO> {
    private int id;
    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private List<Integer> carIds;

    public OwnerDTO() {
    }

    public OwnerDTO(int id, String name, String surname, String email, String phoneNumber, List<Integer> carIds) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.carIds = carIds;
    }

    public OwnerDTO(String name, String surname, String email, String phoneNumber, List<Integer> carIds) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.carIds = carIds;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public List<Integer> getCarIds() {
        return carIds;
    }
}
