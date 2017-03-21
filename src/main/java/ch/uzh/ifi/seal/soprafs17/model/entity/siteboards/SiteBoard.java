package ch.uzh.ifi.seal.soprafs17.model.entity.siteboards;

/**
 * Created by erion on 20.03.17.
 */
public abstract class SiteBoard {


    private boolean isOccupied;

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

}
