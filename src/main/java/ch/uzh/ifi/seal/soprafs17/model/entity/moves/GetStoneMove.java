package ch.uzh.ifi.seal.soprafs17.model.entity.moves;

import ch.uzh.ifi.seal.soprafs17.model.entity.Game;
import ch.uzh.ifi.seal.soprafs17.model.entity.Round;
import ch.uzh.ifi.seal.soprafs17.model.entity.User;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by erion on 05.04.17.
 */
@Entity
public class GetStoneMove extends AMove {

    @Id
    @GeneratedValue
    private Long id;

    public GetStoneMove() {
    }

    public GetStoneMove(User user, Round round, Game game) {
        super(user, game, round);
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Game getStones(Game game) {
        int toAddStone;
        if (super.getUser().getSupplySled() == 0) {
            if (super.getUser().getStoneQuarry() >= 3) {
                toAddStone = 3;
                super.getUser().setSupplySled(toAddStone);
                super.getUser().setStoneQuarry(super.getUser().getStoneQuarry() - toAddStone);
            } else if (super.getUser().getStoneQuarry() == 2) {
                toAddStone = 2;
                super.getUser().setSupplySled(toAddStone);
                super.getUser().setStoneQuarry(super.getUser().getStoneQuarry() - toAddStone);
            } else {
                toAddStone = 1;
                super.getUser().setSupplySled(toAddStone);
                super.getUser().setStoneQuarry(super.getUser().getStoneQuarry() - toAddStone);
            }
        } else if (super.getUser().getSupplySled() == 1) {
            if (super.getUser().getStoneQuarry() >= 3) {
                toAddStone = 4;
                super.getUser().setSupplySled(toAddStone);
                super.getUser().setStoneQuarry(super.getUser().getStoneQuarry() - 3);
            } else if (super.getUser().getStoneQuarry() == 2) {
                toAddStone = 3;
                super.getUser().setSupplySled(toAddStone);
                super.getUser().setStoneQuarry(super.getUser().getStoneQuarry() - 2);
            } else {
                toAddStone = 2;
                super.getUser().setSupplySled(toAddStone);
                super.getUser().setStoneQuarry(super.getUser().getStoneQuarry() - 1);
            }
        } else if (super.getUser().getSupplySled() == 2) {
            if (super.getUser().getStoneQuarry() >= 3) {
                toAddStone = 5;
                super.getUser().setSupplySled(toAddStone);
                super.getUser().setStoneQuarry(super.getUser().getStoneQuarry() - 3);
            } else if (super.getUser().getStoneQuarry() == 2) {
                toAddStone = 4;
                super.getUser().setSupplySled(toAddStone);
                super.getUser().setStoneQuarry(super.getUser().getStoneQuarry() - 2);
            } else {
                toAddStone = 3;
                super.getUser().setSupplySled(toAddStone);
                super.getUser().setStoneQuarry(super.getUser().getStoneQuarry() - 1);
            }
        } else if (super.getUser().getSupplySled() == 3) {
            if (super.getUser().getStoneQuarry() >= 2) {
                toAddStone = 5;
                super.getUser().setSupplySled(toAddStone);
                super.getUser().setStoneQuarry(super.getUser().getStoneQuarry() - 2);

            } else if (super.getUser().getStoneQuarry() == 1) {
                toAddStone = 4;
                super.getUser().setSupplySled(toAddStone);
                super.getUser().setStoneQuarry(super.getUser().getStoneQuarry() - 1);

            }
        } else if (super.getUser().getSupplySled() == 4) {
            toAddStone = 5;
            super.getUser().setSupplySled(toAddStone);
            super.getUser().setStoneQuarry(super.getUser().getStoneQuarry() - 1);
        }
        game.findNextPlayer();
        return game;
    }

    @Override
    public Game makeMove(Game game) {
        return getStones(game);
    }

}
