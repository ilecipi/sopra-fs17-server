package ch.uzh.ifi.seal.soprafs17.model.repository;

import ch.uzh.ifi.seal.soprafs17.model.entity.Round;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by ilecipi on 27.03.17.
 */
@Repository("roundRepository")
public interface RoundRepository extends CrudRepository<Round, Long> {
    Round findById(Long id);
}
