package cz.cvut.fit.tjv.zunigjor.semestralka.service.implementation;

import cz.cvut.fit.tjv.zunigjor.semestralka.model.Car;
import cz.cvut.fit.tjv.zunigjor.semestralka.service.IOwnerService;
import cz.cvut.fit.tjv.zunigjor.semestralka.dao.OwnerRepository;
import cz.cvut.fit.tjv.zunigjor.semestralka.model.Owner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;

@Service
public class OwnerService implements IOwnerService {
    private final OwnerRepository ownerRepository;

    @Autowired
    public OwnerService(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    @Override
    @Transactional
    public Owner create(Owner owner) {
        if (ownerRepository.existsById(owner.getId()))
            throw new EntityExistsException();
        return ownerRepository.save(owner);
    }

    @Override
    @Transactional
    public Page<Owner> readAll(Pageable pageable){
        return ownerRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public Owner readById(int id){
        Optional<Owner> optionalOwner = ownerRepository.findById(id);
        if (!optionalOwner.isPresent())
            throw new EntityNotFoundException();
        return optionalOwner.get();
    }

    @Override
    @Transactional
    public Owner update(Owner owner) {
        Optional<Owner> optionalOwnerOld = ownerRepository.findById(owner.getId());
        if (!optionalOwnerOld.isPresent())
            throw new EntityNotFoundException();
        Owner oldOwner = optionalOwnerOld.get();
        // Block car list change
        List<Car> oldList = oldOwner.getCars();
        List<Car> newList = owner.getCars();
        if ( oldList.isEmpty() && newList == null )
            return ownerRepository.save(owner);
        if ( !oldList.isEmpty() && newList == null )
            throw new InputMismatchException();
        if ( !oldList.containsAll(newList) )
            throw new InputMismatchException();
        return ownerRepository.save(owner);
    }

    @Override
    @Transactional
    public void delete(int id) {
        if (! ownerRepository.existsById(id))
            throw new EntityNotFoundException();
        ownerRepository.deleteById(id);
    }


}
