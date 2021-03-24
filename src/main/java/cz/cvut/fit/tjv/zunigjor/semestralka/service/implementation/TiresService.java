package cz.cvut.fit.tjv.zunigjor.semestralka.service.implementation;

import cz.cvut.fit.tjv.zunigjor.semestralka.dao.TiresRepository;
import cz.cvut.fit.tjv.zunigjor.semestralka.service.ITiresService;
import cz.cvut.fit.tjv.zunigjor.semestralka.model.Tires;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class TiresService implements ITiresService {
    private final TiresRepository tiresRepository;

    @Autowired
    public TiresService(TiresRepository tiresRepository) {
        this.tiresRepository = tiresRepository;
    }

    @Override
    @Transactional
    public Tires create(Tires tires){
        if (tiresRepository.existsById(tires.getId()))
            throw new EntityExistsException();
        return tiresRepository.save(tires);
    }

    @Override
    public Page<Tires> readAll(Pageable pageable) {
        return tiresRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public Tires readById(int id){
        Optional<Tires> optionalTires = tiresRepository.findById(id);
        if (! optionalTires.isPresent())
            throw new EntityNotFoundException();
        return optionalTires.get();
    }

    @Override
    @Transactional
    public Tires update(Tires tires){
        if (! tiresRepository.existsById(tires.getId()))
            throw new EntityNotFoundException();
        return tiresRepository.save(tires);
    }

    @Override
    @Transactional
    public void delete(int id){
        if (! tiresRepository.existsById(id))
            throw new EntityNotFoundException();
        tiresRepository.deleteById(id);
    }
}
