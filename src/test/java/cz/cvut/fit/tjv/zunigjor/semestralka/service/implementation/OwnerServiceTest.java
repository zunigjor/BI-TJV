package cz.cvut.fit.tjv.zunigjor.semestralka.service.implementation;

import cz.cvut.fit.tjv.zunigjor.semestralka.dao.CarRepository;
import cz.cvut.fit.tjv.zunigjor.semestralka.dao.OwnerRepository;
import cz.cvut.fit.tjv.zunigjor.semestralka.dao.TiresRepository;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest(classes = OwnerService.class)
public class OwnerServiceTest {
    @Autowired
    private OwnerService ownerService;

    @MockBean
    private OwnerRepository ownerRepository;

    @Test
    void testCreate(){
        Owner testOwner = new Owner();
        testOwner.setName("Jorge");
        testOwner.setSurname("Zuniga");
        testOwner.setEmail("email@sluzba.cz");
        testOwner.setPhoneNumber("123456789");

        BDDMockito.given(ownerRepository.save(testOwner)).willReturn(testOwner);

        Assertions.assertDoesNotThrow(() -> ownerService.create(testOwner));
        Mockito.verify(ownerRepository, Mockito.atLeastOnce()).save(testOwner);
    }

    @Test
    void testCreateDuplicate(){
        Owner testOwner = new Owner();
        testOwner.setName("Jorge");
        testOwner.setSurname("Zuniga");
        testOwner.setEmail("email@sluzba.cz");
        testOwner.setPhoneNumber("123456789");

        BDDMockito.given(ownerRepository.existsById(testOwner.getId())).willReturn(true);
        BDDMockito.given(ownerRepository.save(testOwner)).willReturn(testOwner);
        Assertions.assertThrows(EntityExistsException.class, () -> ownerService.create(testOwner));
        Mockito.verify(ownerRepository, Mockito.atLeastOnce()).existsById(testOwner.getId());
    }

    @Test
    void testReadAll(){
        Owner testOwner1 = new Owner();
        testOwner1.setName("Jorge");
        testOwner1.setSurname("Zuniga");
        testOwner1.setEmail("email@sluzba.cz");
        testOwner1.setPhoneNumber("123456789");

        Owner testOwner2 = new Owner();
        testOwner2.setName("Petr");
        testOwner2.setSurname("Novak");
        testOwner2.setEmail("novakuvmail@sluzba.cz");
        testOwner2.setPhoneNumber("987654321");

        List<Owner> ownersList = new ArrayList<>();
        ownersList.add(testOwner1);
        ownersList.add(testOwner2);

        Pageable pageable = PageRequest.of(0, 10);
        Page<Owner> ownerPage = new PageImpl<>(ownersList, pageable, ownersList.size());

        BDDMockito.given(ownerRepository.findAll(pageable)).willReturn(ownerPage);

        Assertions.assertEquals(ownerPage, ownerService.readAll(pageable));
        Assertions.assertEquals(2, ownerService.readAll(pageable).getTotalElements());
        Mockito.verify(ownerRepository, Mockito.atLeastOnce()).findAll(pageable);
    }

    @Test
    void testReadById(){
        Owner testOwner = new Owner();
        testOwner.setName("Jorge");
        testOwner.setSurname("Zuniga");
        testOwner.setEmail("email@sluzba.cz");
        testOwner.setPhoneNumber("123456789");

        BDDMockito.given(ownerRepository.findById(testOwner.getId())).willReturn(Optional.of(testOwner));

        Assertions.assertEquals(testOwner, ownerService.readById(testOwner.getId()));
        Mockito.verify(ownerRepository, Mockito.atLeastOnce()).findById(testOwner.getId());
    }

    @Test
    void testUpdate(){
        Owner testOwner = new Owner();
        testOwner.setName("Jorge");
        testOwner.setSurname("Zuniga");
        testOwner.setEmail("email@sluzba.cz");
        testOwner.setPhoneNumber("123456789");

        BDDMockito.given(ownerRepository.save(testOwner)).willReturn(testOwner);

        Assertions.assertDoesNotThrow(() -> ownerService.create(testOwner));
        Mockito.verify(ownerRepository, Mockito.atLeastOnce()).save(testOwner);
    }

    @Test
    void testDelete(){
        int id = 1;
        BDDMockito.given(ownerRepository.existsById(id)).willReturn(true);
        ownerService.delete(id);
        Mockito.verify(ownerRepository, Mockito.atLeastOnce()).deleteById(id);
    }
}
