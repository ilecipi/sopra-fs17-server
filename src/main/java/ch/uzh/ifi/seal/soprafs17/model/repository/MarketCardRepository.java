package ch.uzh.ifi.seal.soprafs17.model.repository;

import ch.uzh.ifi.seal.soprafs17.model.entity.marketCards.AMarketCard;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by tonio99tv on 10/04/17.
 */
public interface MarketCardRepository extends CrudRepository<AMarketCard, Long> {
    AMarketCard findById(Long id);
}
