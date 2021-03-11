package me.fishlab2.blogfishlai.exhibition.entity.constraint;

import me.fishlab2.blogfishlai.exhibition.entity.MyCollection;

import javax.validation.*;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy= {
        MyDateConstraint.MyDateRangeConstraintValidator.class,
        MyDateConstraint.MyDateFormatValidator.class
})
public @interface MyDateConstraint {
    String message() default "{me.fishlab2.blogfishlai.exhibition.entity.constraint.MyDateConstraint.message}";
    Class<?>[] groups() default {};

    /*
     * 宣告為宣告為
     * 型態是繼承Payload 介面的 class list
     */
    Class<? extends Payload>[] payload() default {};

    //ConstraintTarget validationAppliesTo() default ConstraintTarget.IMPLICIT;
    /*
     * 將validator 寫為inner class
     * 較驗 Field 的日期格式
     */
    public class MyDateFormatValidator implements ConstraintValidator<MyDateConstraint, String> {

        @Override
        public void initialize(MyDateConstraint constraintAnnotation) {}

        @Override
        public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
            /*
             * check field is null
             */
            if(s == "") return true;

            String target = new String(s);
            Pattern pat = Pattern.compile("(?<year>\\d{4})-(?<month>\\d{2})");
            Matcher mat = pat.matcher(target);
            if(mat.matches()){
                Boolean isYear = false, isMonth = false; //先不相信日期正確
                int maxMonth = 12;
                int year = Integer.parseInt(mat.group("year")); //轉換符合的年分成整數數字
                int month = Integer.parseInt(mat.group("month")); //轉換月份字串到整數

                LocalDate today = LocalDate.now(); //使用今天作為日期最大值
                if(year <= today.getYear() && year >= 1900) isYear=true; //年份邏輯正確
                if(year == today.getYear()) maxMonth = today.getMonthValue(); //作品年份為今年，則最大月份為當月
                if(month <= maxMonth && month > 0) isMonth=true; //月份邏輯正確

                if(isMonth && isYear) return true;
                else {
                    overrideErrorMsg("範圍錯誤", constraintValidatorContext);
                    return false;
                }
            }else {
                overrideErrorMsg("日期格式錯誤，不符 yyyy-MM", constraintValidatorContext);
                return false;
            }
        }

        private void overrideErrorMsg(String msg, ConstraintValidatorContext cVC) {
            cVC.disableDefaultConstraintViolation();
            cVC.buildConstraintViolationWithTemplate(
                    "{me.fishlab2.blogfishlai.exhibition.entity.constraint.MyDateConstraint.message}"
                    + msg
            ).addConstraintViolation();

        }
    }

    /*
     * 較驗日期邏輯的 inner class
     */
    public class MyDateRangeConstraintValidator implements ConstraintValidator<MyDateConstraint, MyCollection> {

        @Override
        public void initialize(MyDateConstraint constraintAnnotation) {

        }

        @Override
        public boolean isValid(MyCollection myCollection, ConstraintValidatorContext constraintValidatorContext) {
            String strStart = myCollection.getStrStartDate(), strStop = myCollection.getStrStopDate();
            if( strStart == "" || strStop == "") return true; //其中一個日期沒有就不較驗
            SimpleDateFormat sDF = new SimpleDateFormat("yyyy-MM");
            try {
                Date startDate = sDF.parse(strStart);
                Date stopDate = sDF.parse(strStop);
                if(startDate.compareTo(stopDate) != 1) return true; //開始日期在停止日期之前
            } catch (ParseException e) {
                e.printStackTrace();
            }

            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(
                    "{me.fishlab2.blogfishlai.exhibition.entity.constraint.MyDateConstraint.message}" +
                            "停止日期不能在開始日期之前"
            ).addPropertyNode("strStartDate").addConstraintViolation();
            return false;
        }
    }
}
