package cz.cvut.fit.tjv.zunigjor.semestralka.rest.controllers;

import cz.cvut.fit.tjv.zunigjor.semestralka.rest.dto.OwnerDTO;
import cz.cvut.fit.tjv.zunigjor.semestralka.rest.dto.assembler.OwnerDTOAssembler;
import cz.cvut.fit.tjv.zunigjor.semestralka.service.ICarService;
import cz.cvut.fit.tjv.zunigjor.semestralka.service.IOwnerService;
import cz.cvut.fit.tjv.zunigjor.semestralka.model.Owner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/owner")
public class OwnerController {
    private final IOwnerService ownerService;
    private final ICarService carService;
    private final OwnerDTOAssembler ownerDTOAssembler;
    private final PagedResourcesAssembler<Owner> ownerPagedResourcesAssembler;

    @Autowired
    public OwnerController(IOwnerService ownerService, ICarService carService, OwnerDTOAssembler ownerDTOAssembler, PagedResourcesAssembler<Owner> ownerPagedResourcesAssembler) {
        this.ownerService = ownerService;
        this.carService = carService;
        this.ownerDTOAssembler = ownerDTOAssembler;
        this.ownerPagedResourcesAssembler = ownerPagedResourcesAssembler;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OwnerDTO create(@RequestBody OwnerDTO owner){
        try {
            Owner dataEntity = new Owner(
                    owner.getName(), owner.getSurname(), owner.getEmail(), owner.getPhoneNumber(),
                    owner.getCarIds() == null ? null : owner.getCarIds().stream().map(carService::readById).collect(Collectors.toList())
            );
            Owner result = ownerService.create(dataEntity);
            OwnerDTO dto = ownerDTOAssembler.toModel(result);
            dto.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).readAll(0, 10)).withRel(IanaLinkRelations.COLLECTION));
            return dto;
        } catch (EntityExistsException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        } catch (NoSuchElementException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public OwnerDTO readOne(@PathVariable int id){
        try {
            return ownerDTOAssembler.toModel(ownerService.readById(id));
        } catch (EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public PagedModel<OwnerDTO> readAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        Page<Owner> ownerPage = ownerService.readAll(PageRequest.of(page, size));
        return ownerPagedResourcesAssembler.toModel(ownerPage, ownerDTOAssembler);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OwnerDTO update(@PathVariable int id, @RequestBody OwnerDTO owner){
        try {
            Owner newData = new Owner(
                    owner.getName(), owner.getSurname(), owner.getEmail(), owner.getPhoneNumber(),
                    owner.getCarIds() == null ? null : owner.getCarIds().stream().map(carService::readById).collect(Collectors.toList())
            );
            newData.setId(id);
            Owner result = ownerService.update(newData);
            OwnerDTO dto = ownerDTOAssembler.toModel(result);
            dto.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).readAll(0, 10)).withRel(IanaLinkRelations.COLLECTION));
            return dto;
        } catch (EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (InputMismatchException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable int id){
        try {
            ownerService.delete(id);
        } catch (EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
