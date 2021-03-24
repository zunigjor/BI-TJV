package cz.cvut.fit.tjv.zunigjor.semestralka.service;

import cz.cvut.fit.tjv.zunigjor.semestralka.model.Tires;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ITiresService {
    Tires create(Tires tires);
    Page<Tires> readAll(Pageable pageable);
    Tires readById(int id);
    Tires update(Tires tires);
    void delete(int id);
}
