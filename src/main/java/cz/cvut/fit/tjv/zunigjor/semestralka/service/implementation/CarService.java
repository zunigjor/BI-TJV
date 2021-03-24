package cz.cvut.fit.tjv.zunigjor.semestralka.service.implementation;

import cz.cvut.fit.tjv.zunigjor.semestralka.model.Car;
import cz.cvut.fit.tjv.zunigjor.semestralka.service.ICarService;
import cz.cvut.fit.tjv.zunigjor.semestralka.dao.OwnerRepository;
import cz.cvut.fit.tjv.zunigjor.semestralka.dao.CarRepository;
import cz.cvut.fit.tjv.zunigjor.semestralka.model.Owner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.*;

@Service
public class CarService implements ICarService {
    private final CarRepository carRepository;
    private final OwnerRepository ownerRepository;

    @Autowired
    public CarService(CarRepository carRepository, OwnerRepository ownerRepository) {
        this.carRepository = carRepository;
        this.ownerRepository = ownerRepository;
    }

    @Override
    @Transactional
    public Car create(Car car){
        if (carRepository.existsById(car.getId()))
            throw new EntityExistsException();
        return carRepository.save(car);
    }

    @Override
    @Transactional
    public Page<Car> readAll(Pageable pageable){
        return carRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public Car readById(int id){
        Optional<Car> optionalCar = carRepository.findById(id);
        if (!optionalCar.isPresent())
            throw new EntityNotFoundException();
        return optionalCar.get();
    }

    @Override
    @Transactional
    public Car update(Car car){
        Optional<Car> optionalCarOld = carRepository.findById(car.getId());
        if (!optionalCarOld.isPresent())
            throw new EntityNotFoundException();
        Car oldCar = optionalCarOld.get();
        // Block owner list change
        List<Owner> oldList = oldCar.getOwners();
        List<Owner> newList = car.getOwners();
        if ( oldList.isEmpty() && newList == null )
            return carRepository.save(car);
        if ( !oldList.isEmpty() && newList == null )
            throw new InputMismatchException();
        if ( !oldList.containsAll(newList) )
            throw new InputMismatchException();
        return carRepository.save(car);
    }

    @Override
    @Transactional
    public void delete(int id){
        if (! carRepository.existsById(id))
            throw new EntityNotFoundException();
        carRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Car addOwner(int carId, int ownerId) {
        Optional<Car> optionalCar = carRepository.findById(carId);
        Optional<Owner> optionalOwner = ownerRepository.findById(ownerId);

        if (!optionalCar.isPresent() || !optionalOwner.isPresent() )
            throw new EntityNotFoundException();

        Car car = optionalCar.get();
        Owner owner = optionalOwner.get();

        List<Owner> ownerList = car.getOwners();
        List<Car> carList = owner.getCars();

        if (ownerList == null)
            ownerList = new LinkedList<Owner>(Collections.singletonList(owner));
        else
            ownerList.add(owner);
        if (carList == null)
            carList = new LinkedList<Car>(Collections.singletonList(car));
        else
            carList.add(car);

        car.setOwners(ownerList);
        owner.setCars(carList);

        ownerRepository.save(owner);
        return carRepository.save(car);
    }

    @Override
    @Transactional
    public Car removeOwner(int carId, int ownerId) {
        Optional<Car> optionalCar = carRepository.findById(carId);
        Optional<Owner> optionalOwner = ownerRepository.findById(ownerId);

        if (!optionalCar.isPresent() || !optionalOwner.isPresent() )
            throw new EntityNotFoundException();

        Car car = optionalCar.get();
        Owner owner = optionalOwner.get();

        List<Owner> ownerList = car.getOwners();
        List<Car> carList = owner.getCars();

        if ( ownerList.stream().noneMatch(i -> i.getId() == ownerId) || carList.stream().noneMatch(i -> i.getId() == carId))
            throw new InputMismatchException();

        ownerList.remove(owner);
        carList.remove(car);

        if (ownerList.isEmpty())
            ownerList = null;
        if (carList.isEmpty())
            carList = null;

        car.setOwners(ownerList);
        owner.setCars(carList);

        ownerRepository.save(owner);
        return carRepository.save(car);
    }
}
