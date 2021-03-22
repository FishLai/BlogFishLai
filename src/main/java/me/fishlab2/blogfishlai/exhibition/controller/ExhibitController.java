package me.fishlab2.blogfishlai.exhibition.controller;

import me.fishlab2.blogfishlai.exhibition.entity.MyCollection;
import me.fishlab2.blogfishlai.exhibition.repository.MyCollectionRepository;
import me.fishlab2.blogfishlai.exhibition.service.impl.FileSystemStorageServiceImpl;
import me.fishlab2.blogfishlai.exhibition.service.impl.MyCollectionServiceImpl;
import net.sf.json.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.Pattern;
import javax.validation.executable.ExecutableValidator;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

@Controller
@RequestMapping("mycollection")
public class ExhibitController {
    Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private MyCollectionRepository myCollRepository;

    @Autowired
    private MyCollectionServiceImpl mCServ;

    @Autowired
    private FileSystemStorageServiceImpl storageServiceImpl;

    @RequestMapping("")
    public ModelAndView exhibitCollection() {
        Sort sortColl = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(0, 10, sortColl);
        Page<MyCollection> collList = myCollRepository.findAll(pageable);

        ModelAndView mav = new ModelAndView("collections/list");
        mav.addObject("collList", collList);
        return mav;
    }

    @RequestMapping("/add")
    public ModelAndView addColl() {
        ModelAndView mav = new ModelAndView("collections/edit");
        mav.addObject("myCollection", new MyCollection());
        mav.addObject("imgTmp", "");
        return mav;
    }

    @PostMapping("/add")
    public String saveCollection(@RequestParam(value="strStartDate") String startDate,
                                 @RequestParam(value="strStopDate") String stopDate,
                                 @RequestParam(value="imgTmp") String imageTemp,
                                 @Validated MyCollection myCollection,
                                 BindingResult bindingResult,
                                 RedirectAttributes attr,
                                 Model model) throws NoSuchMethodException {
        //Todo 重寫日期較驗方式，
        // 以 globalexceptionhandler @initbinder 實現較驗
        // controller 用parameter @Pattern、@NotEmpty 較驗日期輸入格式

        HashMap<String, String> dateError = mCServ.validateDates(startDate, stopDate);
        if(dateError != null)
            bindingResult.rejectValue(dateError.get("property"), "error.myCollection", dateError.get("msg"));


        if(bindingResult.hasErrors()) {
            /*
             * Todo find the way resolve XHR 回傳的response 沒有執行內部js 的問題
             */
            logger.info(imageTemp);
            model.addAttribute("startDate", startDate);
            model.addAttribute("stopDate", stopDate);
            model.addAttribute("imgTmp", imageTemp);
            return "collections/edit";
        }

        /*
         * 通過較驗後社設定日期
         */
        HashMap<String, Date> dates = mCServ.doTransformDate(startDate, stopDate);
        myCollection.setStartDate(dates.get("startDate"));
        myCollection.setStopDate(dates.get("stopDate"));

        /*
         * get path record and save to persist directory
         */
        if(storageServiceImpl.getDistinationFile() != null) {
            myCollection.setCoverPath(storageServiceImpl.persistFile(myCollection.getName()));
        }

        myCollRepository.save(myCollection);
        return "redirect:/mycollection";
    }

    /*
     * @ResponseBody 存入伺服器後，回傳Json
     */
    @PostMapping("/saveImg")
    @ResponseBody
    public JSONObject handleFileUpload(@RequestParam("file") MultipartFile file) {
        HashMap<String, String> result = storageServiceImpl.store(file, "temp");

        return JSONObject.fromObject(result);
    }
}
