package ch.uzh.ifi.seal.soprafs17.model.entity.siteboards;

import java.util.Map;

/**
 * Created by erion on 20.03.17.
 */
public interface Countable {

    Map<Long,Integer> countAfterMove();
    Map<Long,Integer> countEndOfRound();
    Map<Long,Integer> countEndOfGame();




}
