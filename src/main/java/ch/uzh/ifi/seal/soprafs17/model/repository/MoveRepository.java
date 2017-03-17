package ch.uzh.ifi.seal.soprafs17.model.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ch.uzh.ifi.seal.soprafs17.model.entity.Move;

@Repository("moveRepository")
public interface MoveRepository extends CrudRepository<Move, Long> {
}
