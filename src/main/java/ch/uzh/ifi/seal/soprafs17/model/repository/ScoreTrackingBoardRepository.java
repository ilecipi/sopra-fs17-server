package ch.uzh.ifi.seal.soprafs17.model.repository;

import ch.uzh.ifi.seal.soprafs17.model.entity.ScoreTrackingBoard;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by tonio99tv on 04/04/17.
 */
@Repository("scoreTrackingBoardRepository")
public interface ScoreTrackingBoardRepository extends CrudRepository<ScoreTrackingBoard,Long> {
    ScoreTrackingBoard findById(Long id);
}
