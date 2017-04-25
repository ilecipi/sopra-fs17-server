package ch.uzh.ifi.seal.soprafs17.model.repository;

import ch.uzh.ifi.seal.soprafs17.model.entity.ships.AShip;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by ilecipi on 22.03.17.
 */

@Repository("shipRepository")
public interface ShipRepository extends CrudRepository<AShip,Long>{
        AShip findById(Long id);
}
