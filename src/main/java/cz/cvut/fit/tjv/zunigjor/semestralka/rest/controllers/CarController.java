package cz.cvut.fit.tjv.zunigjor.semestralka.rest.controllers;

import cz.cvut.fit.tjv.zunigjor.semestralka.model.Car;
import cz.cvut.fit.tjv.zunigjor.semestralka.rest.dto.CarDTO;
import cz.cvut.fit.tjv.zunigjor.semestralka.rest.dto.assembler.CarDTOAssembler;
import cz.cvut.fit.tjv.zunigjor.semestralka.service.ICarService;
import cz.cvut.fit.tjv.zunigjor.semestralka.service.IOwnerService;
import cz.cvut.fit.tjv.zunigjor.semestralka.service.IParkingSpotService;
import cz.cvut.fit.tjv.zunigjor.semestralka.service.ITiresService;
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
@RequestMapping("/car")
public class CarController {
    private final ICarService carService;
    private final IParkingSpotService parkingSpotService;
    private final ITiresService tiresService;
    private final IOwnerService ownerService;
    private final CarDTOAssembler carDTOAssembler;
    private final PagedResourcesAssembler<Car> carPagedResourcesAssembler;

    @Autowired
    public CarController(ICarService carService, IParkingSpotService parkingSpotService, ITiresService tiresService, IOwnerService ownerService, CarDTOAssembler carDTOAssembler, PagedResourcesAssembler<Car> carPagedResourcesAssembler) {
        this.carService = carService;
        this.parkingSpotService = parkingSpotService;
        this.tiresService = tiresService;
        this.ownerService = ownerService;
        this.carDTOAssembler = carDTOAssembler;
        this.carPagedResourcesAssembler = carPagedResourcesAssembler;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CarDTO create(@RequestBody CarDTO car){
        try {
            Car dataEntity = new Car(
                    car.getRegistrationNumber(), car.getBrand(), car.getModel(),
                    car.getParkingSpot() == null ? null : parkingSpotService.readById(car.getParkingSpot()),
                    car.getTireSetIds() == null ? null : car.getTireSetIds().stream().map(tiresService::readById).collect(Collectors.toList()),
                    car.getOwnerIds() == null ? null : car.getOwnerIds().stream().map(ownerService::readById).collect(Collectors.toList())
            );
            Car result = carService.create(dataEntity);
            CarDTO dto =  carDTOAssembler.toModel(result);
            dto.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).readAll(0, 10)).withRel(IanaLinkRelations.COLLECTION));
            return dto;
        } catch (EntityExistsException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        } catch (NoSuchElementException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public CarDTO readOne(@PathVariable int id){
        try {
            return carDTOAssembler.toModel(carService.readById(id));
        } catch (EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public PagedModel<CarDTO> readAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        Page<Car> carPage = carService.readAll(PageRequest.of(page, size));
        return carPagedResourcesAssembler.toModel(carPage, carDTOAssembler);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CarDTO update(@PathVariable int id, @RequestBody CarDTO car){
        try {
            Car newData = new Car(
                    car.getRegistrationNumber(), car.getBrand(), car.getModel(),
                    car.getParkingSpot() == null ? null : parkingSpotService.readById(car.getParkingSpot()),
                    car.getTireSetIds() == null ? null : car.getTireSetIds().stream().map(tiresService::readById).collect(Collectors.toList()),
                    car.getOwnerIds() == null ? null : car.getOwnerIds().stream().map(ownerService::readById).collect(Collectors.toList())
            );
            newData.setId(id);
            Car result = carService.update(newData);
            CarDTO dto = carDTOAssembler.toModel(result);
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
            carService.delete(id);
        } catch (EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{id}/add")
    @ResponseStatus(HttpStatus.OK)
    public CarDTO addOwner(@PathVariable int id, @RequestParam(defaultValue = "null") Integer ownerId){
        if (ownerId == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        try {
            Car result = carService.addOwner(id, ownerId);
            CarDTO dto =  carDTOAssembler.toModel(result);
            dto.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).readAll(0, 10)).withRel(IanaLinkRelations.COLLECTION));
            return dto;
        } catch (EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (InputMismatchException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{id}/remove")
    @ResponseStatus(HttpStatus.OK)
    public CarDTO removeOwner(@PathVariable int id, @RequestParam(defaultValue = "null") Integer ownerId){
        if (ownerId == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        try {
            Car result = carService.removeOwner(id, ownerId);
            CarDTO dto =  carDTOAssembler.toModel(result);
            dto.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).readAll(0, 10)).withRel(IanaLinkRelations.COLLECTION));
            return dto;
        } catch (EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (InputMismatchException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
