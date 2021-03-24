package cz.cvut.fit.tjv.zunigjor.semestralka.service.implementation;

import cz.cvut.fit.tjv.zunigjor.semestralka.dao.TiresRepository;
import cz.cvut.fit.tjv.zunigjor.semestralka.model.Tires;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest(classes = TiresService.class)
public class TiresServiceTest {
    @Autowired
    private TiresService tiresService;

    @MockBean
    private TiresRepository tiresRepository;

    @Test
    void testCreate(){
        Tires testTires = new Tires();
        testTires.setBrand("Mega Fast Tires");
        testTires.setSerialNumber("123ABC567QWE");
        testTires.setUse("racing");

        BDDMockito.given(tiresRepository.save(testTires)).willReturn(testTires);

        Assertions.assertDoesNotThrow(() -> tiresService.create(testTires));
        Mockito.verify(tiresRepository, Mockito.atLeastOnce()).save(testTires);
    }

    @Test
    void testCreateDuplicate(){
        Tires testTires = new Tires();
        testTires.setBrand("Mega Fast Tires");
        testTires.setSerialNumber("123ABC567QWE");
        testTires.setUse("racing");

        BDDMockito.given(tiresRepository.existsById(testTires.getId())).willReturn(true);
        Assertions.assertThrows(EntityExistsException.class, () -> tiresService.create(testTires));
        Mockito.verify(tiresRepository, Mockito.atLeastOnce()).existsById(testTires.getId());
    }

    @Test
    void testReadAll(){
        Tires testTires1 = new Tires();
        testTires1.setBrand("Mega Fast Tires");
        testTires1.setSerialNumber("123ABC567QWE");
        testTires1.setUse("racing");

        Tires testTires2 = new Tires();
        testTires2.setBrand("Very Slow Tires");
        testTires2.setSerialNumber("987ZXY65POI");
        testTires2.setUse("slow driving");

        List<Tires> tiresList = new ArrayList<>();
        tiresList.add(testTires1);
        tiresList.add(testTires2);

        Pageable pageable = PageRequest.of(0, 10);
        Page<Tires> tiresPage = new PageImpl<>(tiresList, pageable, tiresList.size());

        BDDMockito.given(tiresRepository.findAll(pageable)).willReturn(tiresPage);

        Assertions.assertEquals(tiresPage, tiresService.readAll(pageable));
        Assertions.assertEquals(2, tiresService.readAll(pageable).getTotalElements());
        Mockito.verify(tiresRepository, Mockito.atLeastOnce()).findAll(pageable);
    }

    @Test
    void testReadById(){
        Tires testTires = new Tires();
        testTires.setBrand("Mega Fast Tires");
        testTires.setSerialNumber("123ABC567QWE");
        testTires.setUse("racing");

        BDDMockito.given(tiresRepository.findById(testTires.getId())).willReturn(Optional.of(testTires));

        Assertions.assertEquals(testTires, tiresService.readById(testTires.getId()));
        Mockito.verify(tiresRepository, Mockito.atLeastOnce()).findById(testTires.getId());
    }

    @Test
    void testUpdate(){
        Tires testTires = new Tires();
        testTires.setBrand("Mega Fast Tires");
        testTires.setSerialNumber("123ABC567QWE");
        testTires.setUse("racing");

        BDDMockito.given(tiresRepository.save(testTires)).willReturn(testTires);

        Assertions.assertDoesNotThrow(() -> tiresService.create(testTires));
        Mockito.verify(tiresRepository, Mockito.atLeastOnce()).save(testTires);

    }

    @Test
    void testDelete(){
        int id = 1;
        BDDMockito.given(tiresRepository.existsById(id)).willReturn(true);
        tiresService.delete(id);
        Mockito.verify(tiresRepository, Mockito.atLeastOnce()).deleteById(id);
    }
}