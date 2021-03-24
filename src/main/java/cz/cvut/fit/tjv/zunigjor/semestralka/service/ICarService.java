package cz.cvut.fit.tjv.zunigjor.semestralka.service;

import cz.cvut.fit.tjv.zunigjor.semestralka.model.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICarService {
    Car create(Car car);
    Page<Car> readAll(Pageable pageable);
    Car readById(int id);
    Car update(Car car);
    void delete(int id);

    Car addOwner(int carId, int ownerId);
    Car removeOwner(int carId, int ownerId);
}
