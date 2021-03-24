package cz.cvut.fit.tjv.zunigjor.semestralka.rest.controllers;

import cz.cvut.fit.tjv.zunigjor.semestralka.model.ParkingSpot;
import cz.cvut.fit.tjv.zunigjor.semestralka.service.implementation.ParkingSpotService;
import org.hamcrest.CoreMatchers;
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

import java.util.LinkedList;
import java.util.List;


@SpringBootTest
@AutoConfigureMockMvc
class ParkingSpotControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ParkingSpotService parkingSpotService;

    @Test
    void create() throws Exception {
        // test data
        ParkingSpot testParkingSpot = new ParkingSpot();
        testParkingSpot.setParkingSpotNumber(123);
        // mock definition
        BDDMockito.when(parkingSpotService.create(BDDMockito.any(ParkingSpot.class))).thenReturn(testParkingSpot);
        // test
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/parkingspot")
                        .contentType("application/json")
                        .content("{ \"parkingSpotNumber\" : \"123\" }")
        )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.parkingSpotNumber", CoreMatchers.is(testParkingSpot.getParkingSpotNumber())));
        //
        Mockito.verify(parkingSpotService, Mockito.atLeastOnce()).create(Mockito.any(ParkingSpot.class));

    }

    @Test
    void readOne() throws Exception {
        // test data
        ParkingSpot testParkingSpot = new ParkingSpot();
        testParkingSpot.setParkingSpotNumber(123);
        // mock definition
        BDDMockito.given(parkingSpotService.readById(testParkingSpot.getId())).willReturn(testParkingSpot);
        // test
        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/parkingspot/{id}", testParkingSpot.getId())
                        .contentType("application/json")
        ).andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(testParkingSpot.getId())))
        .andExpect(MockMvcResultMatchers.jsonPath("$.parkingSpotNumber", CoreMatchers.is(testParkingSpot.getParkingSpotNumber())))
        .andExpect(MockMvcResultMatchers.jsonPath("$._links.self.href", CoreMatchers.endsWith("/parkingspot/" + testParkingSpot.getId())));
        //
        Mockito.verify(parkingSpotService, Mockito.atLeastOnce()).readById(testParkingSpot.getId());
    }

    @Test
    void readAll() throws Exception {
        // test data
        final int page = 0;
        final int size = 2;
        final Pageable pageable = PageRequest.of(page, size);

        ParkingSpot testParkingSpot1 = new ParkingSpot();
        testParkingSpot1.setParkingSpotNumber(1);
        ParkingSpot testParkingSpot2 = new ParkingSpot();
        testParkingSpot2.setParkingSpotNumber(2);
        ParkingSpot testParkingSpot3 = new ParkingSpot();
        testParkingSpot3.setParkingSpotNumber(3);

        final List<ParkingSpot> data = new LinkedList<ParkingSpot>();
        data.add(testParkingSpot1);
        data.add(testParkingSpot2);
        data.add(testParkingSpot3);
        final Page<ParkingSpot> pageExpected = new PageImpl<>(data, pageable, data.size() + 1);
        // mock definition
        BDDMockito.given(parkingSpotService.readAll(pageable)).willReturn(pageExpected);
        // test
        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/parkingspot?page={page}&size={size}", page, size)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$._links.next.href", CoreMatchers.endsWith("/parkingspot?page=1&size=2")));

    }

    @Test
    void update() throws Exception {
        // test data
        ParkingSpot testParkingSpot = new ParkingSpot();
        testParkingSpot.setParkingSpotNumber(123);
        // mock definition
        BDDMockito.when(parkingSpotService.update(BDDMockito.any(ParkingSpot.class))).thenReturn(testParkingSpot);
        // test
        mockMvc.perform(
                MockMvcRequestBuilders
                        .put("/parkingspot/{id}", testParkingSpot.getId())
                        .contentType("application/json")
                        .content("{ \"parkingSpotNumber\" : \"123\" }")
        ).andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(testParkingSpot.getId())))
        .andExpect(MockMvcResultMatchers.jsonPath("$.parkingSpotNumber", CoreMatchers.is(testParkingSpot.getParkingSpotNumber())))
        .andExpect(MockMvcResultMatchers.jsonPath("$._links.self.href", CoreMatchers.endsWith("/parkingspot/" + testParkingSpot.getId())));
        //
        Mockito.verify(parkingSpotService, Mockito.atLeastOnce()).update(Mockito.any(ParkingSpot.class));

    }

    @Test
    void delete() throws Exception {
        // test data
        ParkingSpot testParkingSpot = new ParkingSpot();
        testParkingSpot.setParkingSpotNumber(123);
        // test
        mockMvc.perform(
                MockMvcRequestBuilders
                        .delete("/parkingspot/{id}", testParkingSpot.getId())
        )
                .andExpect(MockMvcResultMatchers.status().isOk());
        //
        Mockito.verify(parkingSpotService, Mockito.atLeastOnce()).delete(testParkingSpot.getId());
    }
}