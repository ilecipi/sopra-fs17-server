package ch.uzh.ifi.seal.soprafs17.model.entity.siteboards;

import java.util.Map;

/**
 * Created by erion on 20.03.17.
 */
public interface Countable {

    Map<String, Integer> countAfterMove();
    Map<String,Integer> countEndOfRound();
    Map<String,Integer> countEndOfGame();




}
