package ch.uzh.ifi.seal.soprafs17.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

/**
 * Created by ilecipi on 10.03.17.
 */
public class Stone implements Serializable {


    private String color;

    @JsonIgnore
    private boolean counted;

    public Stone(String color) {
        this.color = color;
    }

    public Stone() {
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isCounted() {
        return counted;
    }

    public void setCounted() {
        this.counted = true;
    }

}
