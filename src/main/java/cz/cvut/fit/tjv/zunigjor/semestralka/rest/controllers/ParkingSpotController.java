package cz.cvut.fit.tjv.zunigjor.semestralka.rest.controllers;

import cz.cvut.fit.tjv.zunigjor.semestralka.rest.dto.ParkingSpotDTO;
import cz.cvut.fit.tjv.zunigjor.semestralka.service.ICarService;
import cz.cvut.fit.tjv.zunigjor.semestralka.service.IParkingSpotService;
import cz.cvut.fit.tjv.zunigjor.semestralka.model.ParkingSpot;
import cz.cvut.fit.tjv.zunigjor.semestralka.rest.dto.assembler.ParkingSpotDTOAssembler;
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
@RequestMapping("/parkingspot")
public class ParkingSpotController {
    private final IParkingSpotService parkingSpotService;
    private final ICarService carService;
    private final ParkingSpotDTOAssembler parkingSpotDTOAssembler;
    private final PagedResourcesAssembler<ParkingSpot> parkingSpotPagedResourcesAssembler;

    @Autowired
    public ParkingSpotController(IParkingSpotService parkingSpotService, ICarService carService, ParkingSpotDTOAssembler parkingSpotDTOAssembler, PagedResourcesAssembler<ParkingSpot> parkingSpotPagedResourcesAssembler) {
        this.parkingSpotService = parkingSpotService;
        this.carService = carService;
        this.parkingSpotDTOAssembler = parkingSpotDTOAssembler;
        this.parkingSpotPagedResourcesAssembler = parkingSpotPagedResourcesAssembler;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ParkingSpotDTO create(@RequestBody ParkingSpotDTO parkingSpot){
        try {
            ParkingSpot dataEntity = new ParkingSpot(
                    parkingSpot.getParkingSpotNumber(),
                    parkingSpot.getCarId() == null ? null : carService.readById(parkingSpot.getCarId())
            );
            ParkingSpot result = parkingSpotService.create(dataEntity);
            ParkingSpotDTO dto = parkingSpotDTOAssembler.toModel(result);
            dto.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).readAll(0, 10)).withRel(IanaLinkRelations.COLLECTION));
            return dto;
        } catch (EntityExistsException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        } catch (NoSuchElementException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ParkingSpotDTO readOne(@PathVariable int id){
        try {
            return parkingSpotDTOAssembler.toModel(parkingSpotService.readById(id));
        } catch (EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public PagedModel<ParkingSpotDTO> readAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        Page<ParkingSpot> parkingSpotPage = parkingSpotService.readAll(PageRequest.of(page, size));
        return parkingSpotPagedResourcesAssembler.toModel(parkingSpotPage, parkingSpotDTOAssembler);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ParkingSpotDTO update(@PathVariable int id, @RequestBody ParkingSpotDTO parkingSpot){
        try {
            ParkingSpot newData = new ParkingSpot(
                    parkingSpot.getParkingSpotNumber(),
                    parkingSpot.getCarId() == null ? null : carService.readById(parkingSpot.getCarId())
            );
            newData.setId(id);
            ParkingSpot result = parkingSpotService.update(newData);
            ParkingSpotDTO dto = parkingSpotDTOAssembler.toModel(result);
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
            parkingSpotService.delete(id);
        } catch (EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
