package ch.uzh.ifi.seal.soprafs17.model.repository;

import ch.uzh.ifi.seal.soprafs17.model.entity.User;
import ch.uzh.ifi.seal.soprafs17.model.entity.siteboards.Temple;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by erion on 20.03.17.
 */
@Repository("templeRepository")
public interface TempleRepository extends CrudRepository<Temple,Long> {
    Temple findById(Long id);
}
