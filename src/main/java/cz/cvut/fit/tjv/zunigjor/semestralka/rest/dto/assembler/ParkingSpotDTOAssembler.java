package cz.cvut.fit.tjv.zunigjor.semestralka.rest.dto.assembler;

import cz.cvut.fit.tjv.zunigjor.semestralka.rest.controllers.ParkingSpotController;
import cz.cvut.fit.tjv.zunigjor.semestralka.rest.dto.ParkingSpotDTO;
import cz.cvut.fit.tjv.zunigjor.semestralka.model.ParkingSpot;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class ParkingSpotDTOAssembler extends RepresentationModelAssemblerSupport<ParkingSpot, ParkingSpotDTO> {
    public ParkingSpotDTOAssembler(){
        super(ParkingSpotController.class, ParkingSpotDTO.class);
    }
    @Override
    public ParkingSpotDTO toModel(ParkingSpot entity) {
        Integer carId = (entity.getCar() == null) ? (null) : (entity.getCar().getId());
        ParkingSpotDTO result = new ParkingSpotDTO( entity.getId(),
                                                    entity.getParkingSpotNumber(),
                                                    carId);
        result.add(
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(ParkingSpotController.class).readOne(entity.getId())
                ).withSelfRel()
        );
        return result;
    }
}
