package me.fishlab2.blogfishlai.exhibition.entity;

import lombok.*;
import me.fishlab2.blogfishlai.exhibition.entity.constraint.MyDateConstraint;
import me.fishlab2.blogfishlai.exhibition.entity.constraint.Unique;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.ConstraintTarget;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
@Table(name="my_collection")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class MyCollection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="coll_no")
    private long id;

    @NotBlank(message="請填入作品名稱")
    @Column(name="coll_name", nullable=false, columnDefinition="varchar(40) unique")
    @Unique
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
/*
    //
    // Todo 透過重寫lombok 設定日期函數
    //  並且取代原有的 validator 使用參數執行較驗
    @Transient
    @NotEmpty(message="是什麼時候開始的呢？")
    @MyDateConstraint
    private String strStartDate;

    @Transient
    @MyDateConstraint
    private String strStopDate;
 */

    //@MyDateConstraint(validationAppliesTo=ConstraintTarget.PARAMETERS)
    //Todo remove method 通過較驗後可以直接轉為Date格式，直接set就好了
    public void setStartAndStopDates(String strStartDate, String strStopDate) {
        SimpleDateFormat sDF = new SimpleDateFormat("yyyy-MM");
        Pattern pat = Pattern.compile("\\d{4}-\\d{2}");
        Matcher matStart = pat.matcher(strStartDate);
        Matcher matStop = pat.matcher(strStopDate);
        try {
            if(matStart.matches()){
                this.startDate = sDF.parse(strStartDate);
            } else this.startDate = null;
            if (matStop.matches()) {
                this.stopDate = strStopDate == ""? null : sDF.parse(strStopDate);
            } else this.stopDate = null;
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}