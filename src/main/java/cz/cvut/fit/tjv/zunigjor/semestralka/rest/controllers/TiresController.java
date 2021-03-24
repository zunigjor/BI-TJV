package cz.cvut.fit.tjv.zunigjor.semestralka.rest.controllers;

import cz.cvut.fit.tjv.zunigjor.semestralka.service.ICarService;
import cz.cvut.fit.tjv.zunigjor.semestralka.service.ITiresService;
import cz.cvut.fit.tjv.zunigjor.semestralka.model.Tires;
import cz.cvut.fit.tjv.zunigjor.semestralka.rest.dto.TiresDTO;
import cz.cvut.fit.tjv.zunigjor.semestralka.rest.dto.assembler.TiresDTOAssembler;
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
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/tires")
public class TiresController {
    private final ITiresService tiresService;
    private final ICarService carService;
    private final TiresDTOAssembler tiresDTOAssembler;
    private final PagedResourcesAssembler<Tires> tiresPagedResourcesAssembler;

    @Autowired
    public TiresController(ITiresService tiresService, ICarService carService, TiresDTOAssembler tiresDTOAssembler, PagedResourcesAssembler<Tires> tiresPagedResourcesAssembler) {
        this.tiresService = tiresService;
        this.carService = carService;
        this.tiresDTOAssembler = tiresDTOAssembler;
        this.tiresPagedResourcesAssembler = tiresPagedResourcesAssembler;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TiresDTO create(@RequestBody TiresDTO tires){
        try {
            Tires dataEntity = new Tires(
                    tires.getBrand(), tires.getUse(), tires.getSerialNumber(),
                    tires.getCarId() == null ? null : carService.readById(tires.getCarId())
            );
            Tires result = tiresService.create(dataEntity);
            TiresDTO dto = tiresDTOAssembler.toModel(result);
            dto.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).readAll(0, 10)).withRel(IanaLinkRelations.COLLECTION));
            return dto;
        } catch (EntityExistsException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        } catch (NoSuchElementException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public TiresDTO readOne(@PathVariable int id){
        try {
            return tiresDTOAssembler.toModel(tiresService.readById(id));
        } catch (EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public PagedModel<TiresDTO> readAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        Page<Tires> tiresPage = tiresService.readAll(PageRequest.of(page, size));
        return tiresPagedResourcesAssembler.toModel(tiresPage, tiresDTOAssembler);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TiresDTO update(@PathVariable int id, @RequestBody TiresDTO tires){
        try {
            Tires newData = new Tires(
                    tires.getBrand(), tires.getUse(), tires.getSerialNumber(),
                    tires.getCarId() == null ? null : carService.readById(tires.getCarId())
            );
            newData.setId(id);
            Tires result = tiresService.update(newData);
            TiresDTO dto = tiresDTOAssembler.toModel(result);
            dto.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).readAll(0, 10)).withRel(IanaLinkRelations.COLLECTION));
            return dto;
        } catch (EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable int id){
        try {
            tiresService.delete(id);
        } catch (EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
