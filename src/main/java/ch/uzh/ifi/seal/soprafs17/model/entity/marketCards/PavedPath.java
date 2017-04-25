package ch.uzh.ifi.seal.soprafs17.model.entity.marketCards;

import org.springframework.data.annotation.Id;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

/**
 * Created by tonio99tv on 10/04/17.
 */
@Entity
@DiscriminatorValue("paved_path")
public class PavedPath extends AMarketCard implements MCImmediate {

    public String cardType = "PAVED_PATH";
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

    @Override
    public String getCardType() {
        return this.cardType;
    }
}
