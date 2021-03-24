package cz.cvut.fit.tjv.zunigjor.semestralka.service;

import cz.cvut.fit.tjv.zunigjor.semestralka.model.Owner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IOwnerService {
    Owner create(Owner owner);
    Page<Owner> readAll(Pageable pageable);
    Owner readById(int id);
    Owner update(Owner owner);
    void delete(int id);
}
