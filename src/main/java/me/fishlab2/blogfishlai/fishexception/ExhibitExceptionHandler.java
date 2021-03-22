package me.fishlab2.blogfishlai.fishexception;

import lombok.Getter;
import lombok.Setter;
import me.fishlab2.blogfishlai.exhibition.controller.ExhibitController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

//@ControllerAdvice
public class ExhibitExceptionHandler {



    //@ResponseBody
    //@ResponseStatus(HttpStatus.OK)
    //@ExceptionHandler({ConstraintViolationException.class})
    public ModelAndView handleConstraintValidationExceptionHandler(ConstraintViolationException e, WebRequest req){
        Set<ConstraintViolation<?>> viols = e.getConstraintViolations();
        String msg = viols.iterator().next().getMessage();
        ModelAndView mav = new ModelAndView("collections/list");
        //mav.addObject("myCollection", );
        mav.addObject("Error", msg);
        return mav;
        //return msg;
    }
}

@Getter
@Setter
class Result {
    private Integer code = 0;
    private String message;
    private Object result;

    public static Result ok(Object result){
        Result r = new Result();
        r.setCode(200);
        r.setResult(result);
        return r;
    }

    public static Result ok() {
        Result r = new Result();
        r.setCode(200);
        r.setMessage("操作成功");
        return r;
    }

    public static Result fail() {
        Result r = new Result();
        r.setCode(500);
        r.setMessage("系統異常");
        return r;
    }

    public static Result fail(String message) {
        Result r = new Result();
        r.setMessage(message);
        return r;
    }

    public static Result fail(Integer code, String message) {
        Result r = new Result();
        if(code == null) code = 0;
        r.setCode(code);
        r.setMessage(message);
        return r;
    }

    public static Result illegalParam() {
        Result r = new Result();
        r.setCode(400);
        r.setMessage("引數錯誤");
        return r;
    }

}
