package domain;

import domain.item.Color;
import domain.item.Family;
import domain.item.Gift;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;


public class Match {
    private final Family family;
    private final Gift gift;
    private final Color color;

    public Match(Family family, Gift gift, Color color) {
        this.family = family;
        this.gift = gift;
        this.color = color;
    }

    @Override
    public boolean equals(Object that) {
        return reflectionEquals(this, that);
    }

    @Override
    public int hashCode() {
        return reflectionHashCode(this);
    }

    public Family getFamily() {
        return family;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "Match{" +
                "family=" + family +
                ", gift=" + gift +
                ", color=" + color +
                '}';
    }

    public Gift getGift() {
        return gift;
    }
}
