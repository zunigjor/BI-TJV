package cz.cvut.fit.tjv.zunigjor.semestralka.rest.controllers;

import cz.cvut.fit.tjv.zunigjor.semestralka.model.Car;
import cz.cvut.fit.tjv.zunigjor.semestralka.model.Tires;
import cz.cvut.fit.tjv.zunigjor.semestralka.service.implementation.TiresService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
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

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class TiresControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TiresService tiresService;

    @Test
    void create() throws Exception {
        // test data
        Tires testTires = new Tires();
        testTires.setBrand("Mega Fast Tires");
        testTires.setSerialNumber("123ABC567QWE");
        testTires.setUse("racing");
        // mock definition
        BDDMockito.when(tiresService.create(BDDMockito.any(Tires.class))).thenReturn(testTires);
        // test
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/tires")
                        .contentType("application/json")
                        .content("{ \"brand\" : \"Mega Fast Tires\", \"serialNumber\" : \"123ABC567QWE\", \"use\" : \"racing\" }")
        ).andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(MockMvcResultMatchers.jsonPath("$.brand", CoreMatchers.is(testTires.getBrand())))
        .andExpect(MockMvcResultMatchers.jsonPath("$.serialNumber", CoreMatchers.is(testTires.getSerialNumber())))
        .andExpect(MockMvcResultMatchers.jsonPath("$.use", CoreMatchers.is(testTires.getUse())))
        .andExpect(MockMvcResultMatchers.jsonPath("$._links.self.href", CoreMatchers.endsWith("/tires/" + testTires.getId())));;
        //
        Mockito.verify(tiresService, Mockito.atLeastOnce()).create(Mockito.any(Tires.class));
    }

    @Test
    void readOne() throws Exception {
        // test data
        Tires testTires = new Tires();
        testTires.setBrand("Mega Fast Tires");
        testTires.setSerialNumber("123ABC567QWE");
        testTires.setUse("racing");
        // mock definition
        BDDMockito.given(tiresService.readById(testTires.getId())).willReturn(testTires);
        // test
        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/tires/{id}", testTires.getId())
                        .contentType("application/json")
        ).andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(testTires.getId())))
        .andExpect(MockMvcResultMatchers.jsonPath("$.brand", CoreMatchers.is(testTires.getBrand())))
        .andExpect(MockMvcResultMatchers.jsonPath("$.serialNumber", CoreMatchers.is(testTires.getSerialNumber())))
        .andExpect(MockMvcResultMatchers.jsonPath("$.use", CoreMatchers.is(testTires.getUse())))
        .andExpect(MockMvcResultMatchers.jsonPath("$._links.self.href", CoreMatchers.endsWith("/tires/" + testTires.getId())));
        //
        Mockito.verify(tiresService, Mockito.atLeastOnce()).readById(testTires.getId());
    }

    @Test
    void readAll() throws Exception {
        // test data
        final int page = 0;
        final int size = 2;
        final Pageable pageable = PageRequest.of(page, size);

        Tires testTires1 = new Tires();
        testTires1.setBrand("Mega Fast Tires");
        testTires1.setSerialNumber("123ABC567QWE");
        testTires1.setUse("racing");

        Tires testTires2 = new Tires();
        testTires2.setBrand("Very Normal Tires");
        testTires2.setSerialNumber("23BC67QWE");
        testTires2.setUse("normal driving");

        Tires testTires3 = new Tires();
        testTires3.setBrand("Cheap Tires");
        testTires3.setSerialNumber("3C7E");
        testTires3.setUse("driving");

        final List<Tires> data = new LinkedList<Tires>();
        data.add(testTires1);
        data.add(testTires2);
        data.add(testTires3);
        final Page<Tires> pageExpected = new PageImpl<>(data, pageable, data.size() + 1);
        // mock definition
        BDDMockito.given(tiresService.readAll(pageable)).willReturn(pageExpected);
        // test
        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/tires?page={page}&size={size}", page, size)
        ).andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$._links.next.href", CoreMatchers.endsWith("/tires?page=1&size=2")));

    }

    @Test
    void update() throws Exception {
        // test data
        Tires testTires = new Tires();
        testTires.setBrand("Mega Fast Tires");
        testTires.setSerialNumber("123ABC567QWE");
        testTires.setUse("racing");
        // mock definition
        BDDMockito.when(tiresService.update(BDDMockito.any(Tires.class))).thenReturn(testTires);
        // test
        mockMvc.perform(
                MockMvcRequestBuilders
                        .put("/tires/{id}", testTires.getId())
                        .contentType("application/json")
                        .content("{ \"brand\" : \"Mega Fast Tires\", \"serialNumber\" : \"123ABC567QWE\", \"use\" : \"racing\" }")
        ).andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(testTires.getId())))
        .andExpect(MockMvcResultMatchers.jsonPath("$.brand", CoreMatchers.is(testTires.getBrand())))
        .andExpect(MockMvcResultMatchers.jsonPath("$.serialNumber", CoreMatchers.is(testTires.getSerialNumber())))
        .andExpect(MockMvcResultMatchers.jsonPath("$.use", CoreMatchers.is(testTires.getUse())))
        .andExpect(MockMvcResultMatchers.jsonPath("$._links.self.href", CoreMatchers.endsWith("/tires/" + testTires.getId())));
        //
        Mockito.verify(tiresService, Mockito.atLeastOnce()).update(Mockito.any(Tires.class));

    }

    @Test
    void delete() throws Exception {
        // test data
        Tires testTires = new Tires();
        testTires.setBrand("Mega Fast Tires");
        testTires.setSerialNumber("123ABC567QWE");
        testTires.setUse("racing");
        // test
        mockMvc.perform(
                MockMvcRequestBuilders
                        .delete("/tires/{id}", testTires.getId())
        )
                .andExpect(MockMvcResultMatchers.status().isOk());
        //
        Mockito.verify(tiresService, Mockito.atLeastOnce()).delete(testTires.getId());

    }
}