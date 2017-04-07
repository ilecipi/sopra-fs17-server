package ch.uzh.ifi.seal.soprafs17.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by ilecipi on 10.03.17.
 */
//@Entity
public class Stone implements Serializable{
//
//    @Id
//    @GeneratedValue
//    private Long id;

    private String color;

    private boolean counted;

    public Stone(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Stone(){}

    public boolean isCounted() {
        return counted;
    }

    public void setCounted() {
        this.counted = true;
    }

}
