package cz.cvut.fit.tjv.zunigjor.semestralka.dao;

import cz.cvut.fit.tjv.zunigjor.semestralka.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {
}
