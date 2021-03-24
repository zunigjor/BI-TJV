package cz.cvut.fit.tjv.zunigjor.semestralka.rest.controllers;

import cz.cvut.fit.tjv.zunigjor.semestralka.model.Car;
import cz.cvut.fit.tjv.zunigjor.semestralka.model.Owner;
import cz.cvut.fit.tjv.zunigjor.semestralka.service.implementation.CarService;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


@SpringBootTest
@AutoConfigureMockMvc
class CarControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarService carService;

    @Test
    void create() throws Exception {
        // test data
        Car testCar = new Car();
        testCar.setRegistrationNumber("5A0 1234");
        testCar.setBrand("Skoda");
        testCar.setModel("Octavia");
        // mock definition
        BDDMockito.when(carService.create(BDDMockito.any(Car.class))).thenReturn(testCar);
        // test
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/car")
                        .contentType("application/json")
                        .content("{ \"registrationNumber\" : \"5A0 1234\", \"brand\" : \"Skoda\", \"model\" : \"Octavia\" }")
        )
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(MockMvcResultMatchers.jsonPath("$.registrationNumber", CoreMatchers.is(testCar.getRegistrationNumber())))
        .andExpect(MockMvcResultMatchers.jsonPath("$.brand", CoreMatchers.is(testCar.getBrand())))
        .andExpect(MockMvcResultMatchers.jsonPath("$.model", CoreMatchers.is(testCar.getModel())));
        //
        Mockito.verify(carService, Mockito.atLeastOnce()).create(Mockito.any(Car.class));
    }

    @Test
    void readOne() throws Exception {
        // test data
        Car testCar = new Car();
        testCar.setRegistrationNumber("5A0 1234");
        testCar.setBrand("Skoda");
        testCar.setModel("Octavia");
        // mock definition
        BDDMockito.given(carService.readById(testCar.getId())).willReturn(testCar);
        // test
        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/car/{id}", testCar.getId())
                        .contentType("application/json")
        ).andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(testCar.getId())))
        .andExpect(MockMvcResultMatchers.jsonPath("$.registrationNumber", CoreMatchers.is(testCar.getRegistrationNumber())))
        .andExpect(MockMvcResultMatchers.jsonPath("$.brand", CoreMatchers.is(testCar.getBrand())))
        .andExpect(MockMvcResultMatchers.jsonPath("$.model", CoreMatchers.is(testCar.getModel())))
        .andExpect(MockMvcResultMatchers.jsonPath("$._links.self.href", CoreMatchers.endsWith("/car/" + testCar.getId())));
        //
        Mockito.verify(carService, Mockito.atLeastOnce()).readById(testCar.getId());
    }

    @Test
    void readAll() throws Exception {
        // test data
        final int page = 0;
        final int size = 2;
        final Pageable pageable = PageRequest.of(page, size);

        Car testCar1 = new Car();
        testCar1.setRegistrationNumber("1A0 1234");
        testCar1.setBrand("Skoda");
        testCar1.setModel("Octavia");

        Car testCar2 = new Car();
        testCar2.setRegistrationNumber("2B9 5678");
        testCar2.setBrand("BMW");
        testCar2.setModel("i7");

        Car testCar3 = new Car();
        testCar3.setRegistrationNumber("3B9 5678");
        testCar3.setBrand("Skoda");
        testCar3.setModel("Fabia");

        final List<Car> data = new LinkedList<Car>();
        data.add(testCar1);
        data.add(testCar2);
        data.add(testCar3);
        final Page<Car> pageExpected = new PageImpl<>(data, pageable, data.size() + 1);
        // mock definition
        BDDMockito.given(carService.readAll(pageable)).willReturn(pageExpected);
        // test
        mockMvc.perform(
                MockMvcRequestBuilders
                .get("/car?page={page}&size={size}", page, size)
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$._links.next.href", CoreMatchers.endsWith("/car?page=1&size=2")));
    }

    @Test
    void update() throws Exception {
        // test data
        Car testCar = new Car();
        testCar.setRegistrationNumber("5A0 1234");
        testCar.setBrand("Skoda");
        testCar.setModel("Octavia");
        // mock definition
        BDDMockito.when(carService.update(BDDMockito.any(Car.class))).thenReturn(testCar);
        // test
        mockMvc.perform(
                MockMvcRequestBuilders
                        .put("/car/{id}", testCar.getId())
                        .contentType("application/json")
                        .content("{ \"registrationNumber\" : \"5A0 1234\", \"brand\" : \"Skoda\", \"model\" : \"Octavia\" }")
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.registrationNumber", CoreMatchers.is(testCar.getRegistrationNumber())))
        .andExpect(MockMvcResultMatchers.jsonPath("$.brand", CoreMatchers.is(testCar.getBrand())))
        .andExpect(MockMvcResultMatchers.jsonPath("$.model", CoreMatchers.is(testCar.getModel())));
        //
        Mockito.verify(carService, Mockito.atLeastOnce()).update(Mockito.any(Car.class));
    }

    @Test
    void delete() throws Exception {
        // test data
        Car testCar = new Car();
        testCar.setRegistrationNumber("5A0 1234");
        testCar.setBrand("Skoda");
        testCar.setModel("Octavia");
        // test
        mockMvc.perform(
                MockMvcRequestBuilders
                        .delete("/car/{id}", testCar.getId())
        )
                .andExpect(MockMvcResultMatchers.status().isOk());
        //
        Mockito.verify(carService, Mockito.atLeastOnce()).delete(testCar.getId());
    }

    @Test
    void addOwner() throws Exception {
        // test data
        Car testCar = new Car();
        testCar.setRegistrationNumber("5A0 1234");
        testCar.setBrand("Skoda");
        testCar.setModel("Octavia");

        Owner testOwner = new Owner();
        testOwner.setName("Jorge");
        testOwner.setSurname("Zuniga");
        testOwner.setEmail("email@sluzba.cz");
        testOwner.setPhoneNumber("123456789");
        // Link Owner and Car
        List<Car> carList = new LinkedList<Car>(Collections.singletonList(testCar));
        List<Owner> ownerList = new LinkedList<Owner>(Collections.singletonList(testOwner));
        testCar.setOwners(ownerList);
        testOwner.setCars(carList);
        // mock definition
        BDDMockito.given(carService.addOwner(testCar.getId(), testOwner.getId())).willReturn(testCar);
        mockMvc.perform(
                MockMvcRequestBuilders
                .post("/car/{id}/add?ownerId={ownerId}", testCar.getId(), testOwner.getId())
                .contentType("application/json")
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(testCar.getId())))
        .andExpect(MockMvcResultMatchers.jsonPath("$.registrationNumber", CoreMatchers.is(testCar.getRegistrationNumber())))
        .andExpect(MockMvcResultMatchers.jsonPath("$.brand", CoreMatchers.is(testCar.getBrand())))
        .andExpect(MockMvcResultMatchers.jsonPath("$.model", CoreMatchers.is(testCar.getModel())))
        .andExpect(MockMvcResultMatchers.jsonPath("$.ownerIds", Matchers.containsInAnyOrder(testOwner.getId())))
        .andExpect(MockMvcResultMatchers.jsonPath("$._links.self.href", CoreMatchers.endsWith("/car/" + testCar.getId())));
        //
        Mockito.verify(carService, Mockito.atLeastOnce()).addOwner(testCar.getId(), testOwner.getId());
    }

    @Test
    void removeOwner() throws Exception {
        // test data
        Car testCar = new Car();
        testCar.setRegistrationNumber("5A0 1234");
        testCar.setBrand("Skoda");
        testCar.setModel("Octavia");

        Owner testOwner = new Owner();
        testOwner.setName("Jorge");
        testOwner.setSurname("Zuniga");
        testOwner.setEmail("email@sluzba.cz");
        testOwner.setPhoneNumber("123456789");
        // mock definition
        BDDMockito.given(carService.removeOwner(testCar.getId(), testOwner.getId())).willReturn(testCar);
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/car/{id}/remove?ownerId={ownerId}", testCar.getId(), testOwner.getId())
                        .contentType("application/json")
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(testCar.getId())))
        .andExpect(MockMvcResultMatchers.jsonPath("$.registrationNumber", CoreMatchers.is(testCar.getRegistrationNumber())))
        .andExpect(MockMvcResultMatchers.jsonPath("$.brand", CoreMatchers.is(testCar.getBrand())))
        .andExpect(MockMvcResultMatchers.jsonPath("$.model", CoreMatchers.is(testCar.getModel())))
        .andExpect(MockMvcResultMatchers.jsonPath("$.ownerIds", Matchers.nullValue()))
        .andExpect(MockMvcResultMatchers.jsonPath("$._links.self.href", CoreMatchers.endsWith("/car/" + testCar.getId())));
        //
        Mockito.verify(carService, Mockito.atLeastOnce()).removeOwner(testCar.getId(), testOwner.getId());
    }
}