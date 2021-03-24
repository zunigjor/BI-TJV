package cz.cvut.fit.tjv.zunigjor.semestralka.rest.dto.assembler;

import cz.cvut.fit.tjv.zunigjor.semestralka.rest.controllers.TiresController;
import cz.cvut.fit.tjv.zunigjor.semestralka.rest.dto.TiresDTO;
import cz.cvut.fit.tjv.zunigjor.semestralka.model.Tires;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class TiresDTOAssembler extends RepresentationModelAssemblerSupport<Tires, TiresDTO> {
    TiresDTOAssembler(){
        super(TiresController.class, TiresDTO.class);
    }

    @Override
    public TiresDTO toModel(Tires entity) {
        Integer carId = (entity.getCar() == null) ? (null) : (entity.getCar().getId());
        TiresDTO result =  new TiresDTO(entity.getId(),
                                        entity.getBrand(),
                                        entity.getUse(),
                                        entity.getSerialNumber(),
                                        carId);
        result.add(
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(TiresController.class).readOne(entity.getId())
                ).withSelfRel()
        );
        return result;
    }
}
