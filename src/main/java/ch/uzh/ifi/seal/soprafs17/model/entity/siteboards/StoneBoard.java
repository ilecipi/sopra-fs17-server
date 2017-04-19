package ch.uzh.ifi.seal.soprafs17.model.entity.siteboards;

import ch.uzh.ifi.seal.soprafs17.model.entity.Stone;

import javax.persistence.Entity;

/**
 * Created by erion on 20.03.17.
 */
@Entity
public abstract class StoneBoard extends SiteBoard implements Countable {

    public void addStone(Stone stone){}

    public boolean isCounted() {
        return counted;
    }

    public void setCounted(boolean counted) {
        this.counted = counted;
    }

    private boolean counted = false;
}
