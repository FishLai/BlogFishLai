package me.fishlab2.blogfishlai.exhibition.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="my_collection")
@Data
public class MyCollection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="coll_no")
    private long id;

    @Column(name="coll_name", nullable=false, columnDefinition="varchar(20) unique")
    private String name;

    @Temporal(TemporalType.DATE)
    @Column(name="start_date", nullable=false)
    private Date start_date;
    //@OneToMay(cascade = CascadeType.ALL)

    @Temporal(TemporalType.DATE)
    @Column(name="stop_date", nullable=true)
    private Date stop_date;

    @Column(name="coll_abstract", nullable=true, columnDefinition="varchar(200)")
    private String coll_abs;

    @Column(name="cover_path", columnDefinition="varchar(50)")
    private String cover_path;


    @OneToMany(cascade=CascadeType.ALL, mappedBy="coll")
    @ToString.Exclude
    private List<Tech> techList;
}