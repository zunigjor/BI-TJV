package cz.cvut.fit.tjv.zunigjor.semestralka.service;

import cz.cvut.fit.tjv.zunigjor.semestralka.model.ParkingSpot;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IParkingSpotService {
    ParkingSpot create(ParkingSpot parkingSpot);
    Page<ParkingSpot> readAll(Pageable pageable);
    ParkingSpot readById(int id);
    ParkingSpot update(ParkingSpot parkingSpot);
    void delete(int id);
}
