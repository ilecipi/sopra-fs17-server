package ch.uzh.ifi.seal.soprafs17.model.entity.marketCards;

import ch.uzh.ifi.seal.soprafs17.model.entity.User;

import javax.persistence.*;

/**
 * Created by tonio99tv on 10/04/17.
 */
@Entity
@Inheritance
@DiscriminatorColumn(name ="market_card_type")
public abstract class AMarketCard {
    public AMarketCard(){

    }
    @Id
    @GeneratedValue
    private Long id;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @OneToOne
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String cardType;

    public abstract String getCardType();
}
