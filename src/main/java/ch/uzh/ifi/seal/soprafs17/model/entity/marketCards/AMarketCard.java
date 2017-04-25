package ch.uzh.ifi.seal.soprafs17.model.entity.marketCards;

import ch.uzh.ifi.seal.soprafs17.model.entity.User;

import javax.persistence.*;

/**
 * Created by tonio99tv on 10/04/17.
 */
@Entity
@Inheritance
@DiscriminatorColumn(name = "market_card_type")
public abstract class AMarketCard {
    @Column
    public boolean taken;
    @Column
    public boolean played;
    public String cardType;
    @Id
    @GeneratedValue
    private Long id;
    @OneToOne
    private User user;

    public AMarketCard() {

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isTaken() {
        return taken;
    }

    public void setTaken(boolean taken) {
        this.taken = taken;
    }

    public boolean isPlayed() {
        return played;
    }

    public void setPlayed(boolean played) {
        this.played = played;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public abstract String getCardType();
}
