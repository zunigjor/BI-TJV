package cz.cvut.fit.tjv.zunigjor.semestralka.dao;

import cz.cvut.fit.tjv.zunigjor.semestralka.model.Tires;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TiresRepository extends JpaRepository<Tires, Integer> {
}
