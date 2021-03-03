package me.fishlab2.blogfishlai.exhibition.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
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

    @NotBlank(message="請填入作品名稱")
    @Column(name="coll_name", nullable=false, columnDefinition="varchar(20) unique")
    private String name;

    @Temporal(TemporalType.DATE)
    @Column(name="start_date", nullable=false)
    private Date startDate;
    //@OneToMay(cascade = CascadeType.ALL)

    @Temporal(TemporalType.DATE)
    @Column(name="stop_date", nullable=true)
    private Date stopDate;

    @NotBlank(message="請稍作說明")
    @Column(name="coll_abstract", nullable=true, columnDefinition="varchar(200)")
    private String collAbs;

    @Column(name="cover_path", columnDefinition="varchar(150)")
    private String coverPath;

    @OneToMany(cascade=CascadeType.ALL, mappedBy="coll")
    @ToString.Exclude
    private List<Tech> techList;
}