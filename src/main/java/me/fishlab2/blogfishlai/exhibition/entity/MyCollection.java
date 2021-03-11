package me.fishlab2.blogfishlai.exhibition.entity;

import lombok.*;
import me.fishlab2.blogfishlai.exhibition.entity.constraint.MyDateConstraint;
import me.fishlab2.blogfishlai.exhibition.entity.constraint.Unique;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.ConstraintTarget;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.lang.annotation.Repeatable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="my_collection")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@MyDateConstraint
public class MyCollection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="coll_no")
    private long id;

    @NotBlank(message="請填入作品名稱")
    @Unique
    @Column(name="coll_name", nullable=false, columnDefinition="varchar(40) unique")
    private String name;


    @Temporal(TemporalType.DATE)
    @Column(name="start_date", nullable=false)
    private Date startDate;
    //@OneToMay(cascade = CascadeType.ALL)

    @Temporal(TemporalType.DATE)
    @Column(name="stop_date", nullable=true)
    private Date stopDate;

    @NotBlank(message="拜託跟我說說此作品")
    @NotEmpty(message="拜託跟我說說此作品")
    @NotNull(message="拜託跟我說說此作品")
    @Column(name="coll_abstract", nullable=false, columnDefinition="varchar2(4000)")
    private String collAbs;

    @Column(name="cover_path", columnDefinition="varchar(150)")
    private String coverPath;

    @OneToMany(cascade=CascadeType.ALL, mappedBy="coll")
    @ToString.Exclude
    private List<Tech> techList;

    @Transient
    @NotEmpty(message="是什麼時候開始的呢？")
    @MyDateConstraint
    private String strStartDate;

    @Transient
    @MyDateConstraint
    private String strStopDate;

}