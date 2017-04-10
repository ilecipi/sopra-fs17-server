package ch.uzh.ifi.seal.soprafs17.model.entity.marketCards;

import javax.persistence.*;

/**
 * Created by tonio99tv on 10/04/17.
 */
@Entity
@Inheritance
@DiscriminatorColumn(name ="market_card_type")
public abstract class AMarketCard {

    @Id
    @GeneratedValue
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
