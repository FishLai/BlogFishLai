package me.fishlab2.blogfishlai.exhibition.entity;

import lombok.Data;
import me.fishlab2.blogfishlai.exhibition.entity.compositepk.TechPk;

import javax.persistence.*;

@Entity
@Data
@Table(name = "coll_tech")
@IdClass(TechPk.class)
public class Tech {
    @ManyToOne
    @Id
    @JoinColumn(name="coll_no")
    private MyCollection coll;

    @Id
    @Column(name="tech_name", nullable=false, columnDefinition="varchar(15)")
    private String name;

    //@Id
    //@JoinColumn(name="coll_no", referencedColumnName="coll_no")
    //private long collNo;

}
