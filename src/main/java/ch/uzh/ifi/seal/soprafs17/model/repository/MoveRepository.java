package ch.uzh.ifi.seal.soprafs17.model.repository;

import ch.uzh.ifi.seal.soprafs17.model.entity.moves.AMove;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("moveRepository")
public interface MoveRepository extends CrudRepository<AMove, Long> {
}
