package cz.cvut.fit.tjv.zunigjor.semestralka.service.implementation;

import cz.cvut.fit.tjv.zunigjor.semestralka.dao.ParkingSpotRepository;
import cz.cvut.fit.tjv.zunigjor.semestralka.model.ParkingSpot;
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

@SpringBootTest(classes = ParkingSpotService.class)
public class ParkingSpotServiceTest {
    @Autowired
    private ParkingSpotService parkingSpotService;

    @MockBean
    private ParkingSpotRepository parkingSpotRepository;

    @Test
    void testCreate(){
        ParkingSpot testParkingSpot = new ParkingSpot();
        testParkingSpot.setParkingSpotNumber(123);

        BDDMockito.given(parkingSpotRepository.save(testParkingSpot)).willReturn(testParkingSpot);

        Assertions.assertDoesNotThrow(() -> parkingSpotService.create(testParkingSpot));
        Mockito.verify(parkingSpotRepository, Mockito.atLeastOnce()).save(testParkingSpot);
    }

    @Test
    void testCreateDuplicate(){
        ParkingSpot testParkingSpot = new ParkingSpot();
        testParkingSpot.setParkingSpotNumber(123);

        BDDMockito.given(parkingSpotRepository.existsById(testParkingSpot.getId())).willReturn(true);
        BDDMockito.given(parkingSpotRepository.save(testParkingSpot)).willReturn(testParkingSpot);
        Assertions.assertThrows(EntityExistsException.class, () -> parkingSpotService.create(testParkingSpot));
        Mockito.verify(parkingSpotRepository, Mockito.atLeastOnce()).existsById(testParkingSpot.getId());
    }

    @Test
    void testReadAll(){
        ParkingSpot testParkingSpot1 = new ParkingSpot();
        testParkingSpot1.setParkingSpotNumber(1);

        ParkingSpot testParkingSpot2 = new ParkingSpot();
        testParkingSpot2.setParkingSpotNumber(2);

        List<ParkingSpot> parkingSpotsList = new ArrayList<>();
        parkingSpotsList.add(testParkingSpot1);
        parkingSpotsList.add(testParkingSpot2);

        Pageable pageable = PageRequest.of(0, 10);
        Page<ParkingSpot> parkingSpotPage = new PageImpl<>(parkingSpotsList, pageable, parkingSpotsList.size());

        BDDMockito.given(parkingSpotRepository.findAll(pageable)).willReturn(parkingSpotPage);

        Assertions.assertEquals(parkingSpotPage, parkingSpotService.readAll(pageable));
        Assertions.assertEquals(2, parkingSpotService.readAll(pageable).getTotalElements());
        Mockito.verify(parkingSpotRepository, Mockito.atLeastOnce()).findAll(pageable);
    }

    @Test
    void testReadById(){
        ParkingSpot testParkingSpot = new ParkingSpot();
        testParkingSpot.setParkingSpotNumber(123);

        BDDMockito.given(parkingSpotRepository.findById(testParkingSpot.getId())).willReturn(Optional.of(testParkingSpot));

        Assertions.assertEquals(testParkingSpot, parkingSpotService.readById(testParkingSpot.getId()));
        Mockito.verify(parkingSpotRepository, Mockito.atLeastOnce()).findById(testParkingSpot.getId());
    }

    @Test
    void testUpdate(){
        ParkingSpot testParkingSpot = new ParkingSpot();
        testParkingSpot.setParkingSpotNumber(123);

        BDDMockito.given(parkingSpotRepository.save(testParkingSpot)).willReturn(testParkingSpot);

        Assertions.assertDoesNotThrow(() -> parkingSpotService.create(testParkingSpot));
        Mockito.verify(parkingSpotRepository, Mockito.atLeastOnce()).save(testParkingSpot);
    }

    @Test
    void testDelete(){
        int id = 1;
        BDDMockito.given(parkingSpotRepository.existsById(id)).willReturn(true);
        parkingSpotService.delete(id);
        Mockito.verify(parkingSpotRepository, Mockito.atLeastOnce()).deleteById(id);
    }
}