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
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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
        ModelAndView mav = new ModelAndView("collections/add");
        mav.addObject("myCollection", new MyCollection());

        return mav;
    }

    @PostMapping("/add")
    public String saveCollection(@RequestParam("strStartDate") String startDate,
                                 @RequestParam("strStopDate") String stopDate,
                                 @Valid MyCollection mC,
                                 BindingResult bindingResult) {
        if(storageServiceImpl.getDistinationFile() != null) {
            mC.setCoverPath(storageServiceImpl.persistFile(mC.getName()));
        }
        @Valid
        HashMap<String, Date> dates = mCServ.doTransformDate(startDate, stopDate);
        mC.setStartDate(dates.get("startDate"));
        mC.setStopDate(dates.get("stopDate"));

        if(bindingResult.hasErrors()) return "collections/add";
        myCollRepository.save(mC);
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
