package ch.uzh.ifi.seal.soprafs17.model.repository;

import ch.uzh.ifi.seal.soprafs17.model.entity.siteboards.SiteBoard;
import ch.uzh.ifi.seal.soprafs17.model.entity.siteboards.StoneBoard;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by erion on 03.04.17.
 */
@Repository("siteBoardRepository")
public interface SiteBoardRepository extends CrudRepository<SiteBoard, Long> {
    StoneBoard findById(Long id);
//    SiteBoard findMarket(Long id);
}
