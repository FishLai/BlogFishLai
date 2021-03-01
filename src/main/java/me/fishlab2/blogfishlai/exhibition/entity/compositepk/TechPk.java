package me.fishlab2.blogfishlai.exhibition.entity.compositepk;

import me.fishlab2.blogfishlai.exhibition.entity.Tech;

import java.io.Serializable;
import java.util.Objects;

public class TechPk implements Serializable {
    private long coll;
    private String name;

    public TechPk() {};

    public TechPk(long coll, String name) {
        this.coll = coll;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Tech)) return false;
        if(this==o) return true;
        Tech that = (Tech) o;
        return (coll == that.getColl().getId() && name == that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(coll, name);
    }
}
