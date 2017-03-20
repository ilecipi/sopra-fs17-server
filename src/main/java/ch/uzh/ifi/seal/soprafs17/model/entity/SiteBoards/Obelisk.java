package ch.uzh.ifi.seal.soprafs17.model.entity.SiteBoards;

import java.util.Map;
import ch.uzh.ifi.seal.soprafs17.model.entity.Stone;

/**
 * Created by liwitz on 20.03.17.
 */


public class Obelisk extends StoneBoard implements Countable{

    private Map<Long, Stone[]> obelisks;

}