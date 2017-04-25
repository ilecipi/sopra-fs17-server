package ch.uzh.ifi.seal.soprafs17.model.entity.marketCards;

import javax.persistence.*;

/**
 * Created by tonio99tv on 10/04/17.
 */
@Entity
@DiscriminatorValue("lever")
public class Lever extends AMarketCard implements MCAction {
    @Id
    @GeneratedValue
    private Long id;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String cardType = "LEVER";

    @Override
    public String getCardType() {
        return this.cardType;
    }

}
