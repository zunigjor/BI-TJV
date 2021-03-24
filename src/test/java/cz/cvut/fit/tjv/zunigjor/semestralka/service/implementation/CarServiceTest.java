package cz.cvut.fit.tjv.zunigjor.semestralka.service.implementation;

import cz.cvut.fit.tjv.zunigjor.semestralka.dao.CarRepository;
import cz.cvut.fit.tjv.zunigjor.semestralka.dao.OwnerRepository;
import cz.cvut.fit.tjv.zunigjor.semestralka.model.Car;
import cz.cvut.fit.tjv.zunigjor.semestralka.model.Owner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityExistsException;
import java.util.*;

@SpringBootTest(classes = CarService.class)
public class CarServiceTest {
    @Autowired
    private CarService carService;

    @MockBean
    private CarRepository carRepository;
    @MockBean
    private OwnerRepository ownerRepository;

    @Test
    void testCreate(){
        Car testCar = new Car();
        testCar.setRegistrationNumber("5A0 1234");
        testCar.setBrand("Skoda");
        testCar.setModel("Octavia");

        BDDMockito.given(carRepository.save(testCar)).willReturn(testCar);

        Assertions.assertDoesNotThrow(() -> carService.create(testCar));
        Mockito.verify(carRepository, Mockito.atLeastOnce()).save(testCar);
    }

    @Test
    void testCreateDuplicate(){
        Car testCar = new Car();
        testCar.setRegistrationNumber("5A0 1234");
        testCar.setBrand("Skoda");
        testCar.setModel("Octavia");

        BDDMockito.given(carRepository.existsById(testCar.getId())).willReturn(true);
        BDDMockito.given(carRepository.save(testCar)).willReturn(testCar);
        Assertions.assertThrows(EntityExistsException.class, () -> carService.create(testCar));
        Mockito.verify(carRepository, Mockito.atLeastOnce()).existsById(testCar.getId());
    }

    @Test
    void testReadAll(){
        Car testCar1 = new Car();
        testCar1.setRegistrationNumber("5A0 1234");
        testCar1.setBrand("Skoda");
        testCar1.setModel("Octavia");

        Car testCar2 = new Car();
        testCar2.setRegistrationNumber("5A1 2345");
        testCar2.setBrand("Volkswagen");
        testCar2.setModel("Golf");

        List<Car> carsList = new ArrayList<>();
        carsList.add(testCar1);
        carsList.add(testCar2);

        Pageable pageable = PageRequest.of(0, 10);
        Page<Car> carPage = new PageImpl<>(carsList, pageable, carsList.size());

        BDDMockito.given(carRepository.findAll(pageable)).willReturn(carPage);

        Assertions.assertEquals(carPage, carService.readAll(pageable));
        Assertions.assertEquals(2, carService.readAll(pageable).getTotalElements());
        Mockito.verify(carRepository, Mockito.atLeastOnce()).findAll(pageable);
    }

    @Test
    void testReadById(){
        Car testCar = new Car();
        testCar.setRegistrationNumber("5A0 1234");
        testCar.setBrand("Skoda");
        testCar.setModel("Octavia");

        BDDMockito.given(carRepository.findById(testCar.getId())).willReturn(Optional.of(testCar));

        Assertions.assertEquals(testCar, carService.readById(testCar.getId()));
        Mockito.verify(carRepository, Mockito.atLeastOnce()).findById(testCar.getId());
    }

    @Test
    void testUpdate(){
        Car testCar = new Car();
        testCar.setRegistrationNumber("5A0 1234");
        testCar.setBrand("Skoda");
        testCar.setModel("Octavia");

        BDDMockito.given(carRepository.save(testCar)).willReturn(testCar);

        Assertions.assertDoesNotThrow(() -> carService.create(testCar));
        Mockito.verify(carRepository, Mockito.atLeastOnce()).save(testCar);

    }

    @Test
    void testDelete(){
        int id = 1;
        BDDMockito.given(carRepository.existsById(id)).willReturn(true);
        carService.delete(id);
        Mockito.verify(carRepository, Mockito.atLeastOnce()).deleteById(id);
    }

    @Test
    void testAddOwner(){
        // input
        Car testCar = new Car();
        testCar.setRegistrationNumber("5A0 1234");
        testCar.setBrand("Skoda");
        testCar.setModel("Octavia");

        Owner testOwner = new Owner();
        testOwner.setName("Jorge");
        testOwner.setSurname("Zuniga");
        testOwner.setEmail("email@sluzba.cz");
        testOwner.setPhoneNumber("123456789");

        List<Car> carList = new LinkedList<Car>(Collections.singletonList(testCar));
        List<Owner> ownerList = new LinkedList<Owner>(Collections.singletonList(testOwner));
        // Mock repos
        BDDMockito.given(carRepository.findById(testCar.getId())).willReturn(Optional.of(testCar));
        BDDMockito.given(ownerRepository.findById(testOwner.getId())).willReturn(Optional.of(testOwner));

        BDDMockito.given(carRepository.save(testCar)).willReturn(testCar);
        BDDMockito.given(ownerRepository.save(testOwner)).willReturn(testOwner);
        // Test method
        Car result = carService.addOwner(testCar.getId(), testOwner.getId());
        // Link Owner and Car
        testCar.setOwners(ownerList);
        testOwner.setCars(carList);
        // Assert
        Assertions.assertEquals(result, testCar);

    }

    @Test
    void testRemoveOwner(){
        // input
        Car testCar = new Car();
        testCar.setRegistrationNumber("5A0 1234");
        testCar.setBrand("Skoda");
        testCar.setModel("Octavia");

        Owner testOwner = new Owner();
        testOwner.setName("Jorge");
        testOwner.setSurname("Zuniga");
        testOwner.setEmail("email@sluzba.cz");
        testOwner.setPhoneNumber("123456789");

        List<Car> carList = new LinkedList<Car>(Collections.singletonList(testCar));
        List<Owner> ownerList = new LinkedList<Owner>(Collections.singletonList(testOwner));
        // Link Owner and Car
        testCar.setOwners(ownerList);
        testOwner.setCars(carList);
        // Mock repos
        BDDMockito.given(carRepository.findById(testCar.getId())).willReturn(Optional.of(testCar));
        BDDMockito.given(ownerRepository.findById(testOwner.getId())).willReturn(Optional.of(testOwner));

        BDDMockito.given(carRepository.save(testCar)).willReturn(testCar);
        BDDMockito.given(ownerRepository.save(testOwner)).willReturn(testOwner);
        // Test method
        Car result = carService.removeOwner(testCar.getId(), testOwner.getId());
        // Unlink Owner and Car
        testCar.setOwners(null);
        testOwner.setCars(null);
        // Assert
        Assertions.assertEquals(result, testCar);
    }

}
