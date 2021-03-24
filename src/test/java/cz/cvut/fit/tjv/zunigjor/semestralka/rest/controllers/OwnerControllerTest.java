package cz.cvut.fit.tjv.zunigjor.semestralka.rest.controllers;

import cz.cvut.fit.tjv.zunigjor.semestralka.model.Owner;
import cz.cvut.fit.tjv.zunigjor.semestralka.service.implementation.OwnerService;
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
class OwnerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OwnerService ownerService;

    @Test
    void create() throws Exception {
        // test data
        Owner testOwner = new Owner();
        testOwner.setName("Jorge");
        testOwner.setSurname("Zuniga");
        testOwner.setEmail("email@sluzba.cz");
        testOwner.setPhoneNumber("123456789");
        // mock definition
        BDDMockito.when(ownerService.create(BDDMockito.any(Owner.class))).thenReturn(testOwner);
        // test
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/owner")
                        .contentType("application/json")
                        .content("{ \"name\" : \"Jorge\", \"surname\" : \"zuniga\", \"email\" : \"email@sluzba.cz\", \"phoneNumber\" : \"123456789\" }")
        )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(testOwner.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.surname", CoreMatchers.is(testOwner.getSurname())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(testOwner.getEmail())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber", CoreMatchers.is(testOwner.getPhoneNumber())));
        //
        Mockito.verify(ownerService, Mockito.atLeastOnce()).create(Mockito.any(Owner.class));
    }

    @Test
    void readOne() throws Exception {
        // test data
        Owner testOwner = new Owner();
        testOwner.setName("Jorge");
        testOwner.setSurname("Zuniga");
        testOwner.setEmail("email@sluzba.cz");
        testOwner.setPhoneNumber("123456789");
        // mock definition
        BDDMockito.given(ownerService.readById(testOwner.getId())).willReturn(testOwner);
        // test
        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/owner/{id}", testOwner.getId())
                        .contentType("application/json")
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(testOwner.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.surname", CoreMatchers.is(testOwner.getSurname())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(testOwner.getEmail())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber", CoreMatchers.is(testOwner.getPhoneNumber())))
                .andExpect(MockMvcResultMatchers.jsonPath("$._links.self.href", CoreMatchers.endsWith("/owner/" + testOwner.getId())));
        //
        Mockito.verify(ownerService, Mockito.atLeastOnce()).readById(testOwner.getId());
    }

    @Test
    void readAll() throws Exception {
        // test data
        final int page = 0;
        final int size = 2;
        final Pageable pageable = PageRequest.of(page, size);
        Owner testOwner1 = new Owner();
        testOwner1.setName("Jorge");
        testOwner1.setSurname("Zuniga");
        testOwner1.setEmail("email@sluzba.cz");
        testOwner1.setPhoneNumber("123456789");
        Owner testOwner2 = new Owner();
        testOwner2.setName("Pavel");
        testOwner2.setSurname("Novak");
        testOwner2.setEmail("pavel@sluzba.cz");
        testOwner2.setPhoneNumber("987654321");
        Owner testOwner3 = new Owner();
        testOwner3.setName("Franta");
        testOwner3.setSurname("Vomacka");
        testOwner3.setEmail("franta@sluzba.cz");
        testOwner3.setPhoneNumber("456123789");

        final List<Owner> data = new LinkedList<Owner>();
        data.add(testOwner1);
        data.add(testOwner2);
        data.add(testOwner3);
        final Page<Owner> pageExpected = new PageImpl<>(data, pageable, data.size() + 1);
        // mock definition
        BDDMockito.given(ownerService.readAll(pageable)).willReturn(pageExpected);
        // test
        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/owner?page={page}&size={size}", page, size)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$._links.next.href", CoreMatchers.endsWith("/owner?page=1&size=2")));

    }

    @Test
    void update() throws Exception {
        // test data
        Owner testOwner = new Owner();
        testOwner.setName("Jorge");
        testOwner.setSurname("Zuniga");
        testOwner.setEmail("email@sluzba.cz");
        testOwner.setPhoneNumber("123456789");
        // mock definition
        BDDMockito.when(ownerService.update(BDDMockito.any(Owner.class))).thenReturn(testOwner);
        // test
        mockMvc.perform(
                MockMvcRequestBuilders
                        .put("/owner/{id}", testOwner.getId())
                        .contentType("application/json")
                        .content("{ \"name\" : \"Jorge\", \"surname\" : \"Zuniga\", \"email\" : \"email@sluzba.cz\", \"phoneNumber\" : \"123456789\" }")

        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(testOwner.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.surname", CoreMatchers.is(testOwner.getSurname())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(testOwner.getEmail())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber", CoreMatchers.is(testOwner.getPhoneNumber())))
                .andExpect(MockMvcResultMatchers.jsonPath("$._links.self.href", CoreMatchers.endsWith("/owner/" + testOwner.getId())));
        //
        Mockito.verify(ownerService, Mockito.atLeastOnce()).update(Mockito.any(Owner.class));
    }

    @Test
    void delete() throws Exception {
        // test data
        Owner testOwner = new Owner();
        testOwner.setName("Jorge");
        testOwner.setSurname("Zuniga");
        testOwner.setEmail("email@sluzba.cz");
        testOwner.setPhoneNumber("123456789");
        // test
        mockMvc.perform(
                MockMvcRequestBuilders
                        .delete("/owner/{id}", testOwner.getId())
        )
                .andExpect(MockMvcResultMatchers.status().isOk());
        //
        Mockito.verify(ownerService, Mockito.atLeastOnce()).delete(testOwner.getId());
    }
}