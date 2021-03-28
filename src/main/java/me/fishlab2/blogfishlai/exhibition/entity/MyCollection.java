package me.fishlab2.blogfishlai.exhibition.entity;

import lombok.*;
import me.fishlab2.blogfishlai.exhibition.entity.constraint.MyDateConstraint;
import me.fishlab2.blogfishlai.exhibition.entity.constraint.Unique;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.ConstraintTarget;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
@Unique
public class MyCollection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="coll_no")
    private long id;

    @NotBlank(message="請填入作品名稱")
    @Column(name="coll_name", nullable=false, columnDefinition="varchar(40) unique")
    private String name;

    @DateTimeFormat(pattern = "yyyy-MM")
    @Temporal(TemporalType.DATE)
    @Column(name="start_date", nullable=false)
    private Date startDate;
    //@OneToMay(cascade = CascadeType.ALL)

    @DateTimeFormat(pattern = "yyyy-MM")
    @Temporal(TemporalType.DATE)
    @Column(name="stop_date", nullable=true)
    private Date stopDate;

    /*
     * Todo 確認欄位大小是否夠用
     */
    @NotBlank(message="拜託跟我說說此作品")
    @NotEmpty(message="拜託跟我說說此作品")
    @NotNull(message="拜託跟我說說此作品")
    @Column(name="coll_abstract", nullable=false, columnDefinition="varchar2(4000)")
    private String collAbs;

    @Column(name="cover_path", columnDefinition="varchar(150)")
    private String coverPath;

    @Valid
    @UniqueElements(message="出現重複技術名稱")
    @OneToMany(cascade=CascadeType.ALL, mappedBy="coll")
    @ToString.Exclude
    private List<Tech> techList;

    //Todo should modify 目前只拿來做參數較驗，日期參數是否正確
    // 執行儲存時不會驗證日期，利用後臺儲存會有人為錯誤
    @MyDateConstraint(validationAppliesTo=ConstraintTarget.PARAMETERS)
    public int setStartAndStopDates(String strStartDate, String strStopDate) {
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
        return 0;
    }
}