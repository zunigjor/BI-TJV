package cz.cvut.fit.tjv.zunigjor.semestralka.service.implementation;

import cz.cvut.fit.tjv.zunigjor.semestralka.service.IParkingSpotService;
import cz.cvut.fit.tjv.zunigjor.semestralka.model.ParkingSpot;
import cz.cvut.fit.tjv.zunigjor.semestralka.dao.ParkingSpotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class ParkingSpotService implements IParkingSpotService {
    private final ParkingSpotRepository parkingSpotRepository;

    @Autowired
    public ParkingSpotService(ParkingSpotRepository parkingSpotRepository) {
        this.parkingSpotRepository = parkingSpotRepository;
    }

    @Override
    @Transactional
    public ParkingSpot create(ParkingSpot parkingSpot){
        if (parkingSpotRepository.existsById(parkingSpot.getId()))
            throw new EntityExistsException();
        return parkingSpotRepository.save(parkingSpot);
    }

    @Override
    public Page<ParkingSpot> readAll(Pageable pageable) {
        return parkingSpotRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public ParkingSpot readById(int id){
        Optional<ParkingSpot> optionalParkingSpot = parkingSpotRepository.findById(id);
        if (! optionalParkingSpot.isPresent())
            throw new EntityNotFoundException();
        return optionalParkingSpot.get();
    }

    @Override
    @Transactional
    public ParkingSpot update(ParkingSpot parkingSpot){
        if (! parkingSpotRepository.existsById(parkingSpot.getId()))
            throw new EntityNotFoundException();
        return parkingSpotRepository.save(parkingSpot);
    }

    @Override
    @Transactional
    public void delete(int id){
        if (! parkingSpotRepository.existsById(id))
            throw new EntityNotFoundException();
        parkingSpotRepository.deleteById(id);
    }
}
