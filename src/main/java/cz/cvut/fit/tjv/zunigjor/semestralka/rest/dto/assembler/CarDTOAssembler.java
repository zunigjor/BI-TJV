package cz.cvut.fit.tjv.zunigjor.semestralka.rest.dto.assembler;

import cz.cvut.fit.tjv.zunigjor.semestralka.model.Car;
import cz.cvut.fit.tjv.zunigjor.semestralka.model.Owner;
import cz.cvut.fit.tjv.zunigjor.semestralka.model.Tires;
import cz.cvut.fit.tjv.zunigjor.semestralka.rest.controllers.CarController;
import cz.cvut.fit.tjv.zunigjor.semestralka.rest.dto.CarDTO;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CarDTOAssembler extends RepresentationModelAssemblerSupport<Car, CarDTO> {
    public CarDTOAssembler(){
        super(CarController.class, CarDTO.class);
    }

    @Override
    public CarDTO toModel(Car entity) {
        List<Integer> ownerIds = (entity.getOwners() == null) ? (null) : (entity.getOwners().stream().map(Owner::getId).collect(Collectors.toList()));
        Integer parkingSpotId = (entity.getParkingSpot() == null) ? (null) : (entity.getParkingSpot().getId());
        List<Integer> tireSetIds = (entity.getTireSets() == null) ? (null) : (entity.getTireSets().stream().map(Tires::getId).collect(Collectors.toList()));
        CarDTO result =  new CarDTO(entity.getId(),
                                    entity.getRegistrationNumber(),
                                    entity.getBrand(),
                                    entity.getModel(),
                                    ownerIds,
                                    parkingSpotId,
                                    tireSetIds);
        result.add(
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(CarController.class).readOne(entity.getId())
                ).withSelfRel()
        );
        return result;
    }
}
