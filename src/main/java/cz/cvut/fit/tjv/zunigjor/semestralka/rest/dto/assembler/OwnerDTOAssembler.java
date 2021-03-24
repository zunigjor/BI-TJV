package cz.cvut.fit.tjv.zunigjor.semestralka.rest.dto.assembler;

import cz.cvut.fit.tjv.zunigjor.semestralka.model.Car;
import cz.cvut.fit.tjv.zunigjor.semestralka.model.Owner;
import cz.cvut.fit.tjv.zunigjor.semestralka.rest.controllers.OwnerController;
import cz.cvut.fit.tjv.zunigjor.semestralka.rest.dto.OwnerDTO;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OwnerDTOAssembler extends RepresentationModelAssemblerSupport<Owner, OwnerDTO> {
    public OwnerDTOAssembler(){
        super(OwnerController.class, OwnerDTO.class);
    }

    @Override
    public OwnerDTO toModel(Owner entity) {
        List<Integer> carIds = (entity.getCars() == null) ? (null) : (entity.getCars().stream().map(Car::getId).collect(Collectors.toList()));
        OwnerDTO result = new OwnerDTO( entity.getId(),
                                        entity.getName(),
                                        entity.getSurname(),
                                        entity.getEmail(),
                                        entity.getPhoneNumber(),
                                        carIds);
        result.add(
                WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(OwnerController.class).readOne(entity.getId())
                ).withSelfRel()
        );
        return result;
    }
}
