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
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.websocket.server.PathParam;
import java.util.Date;
import java.util.HashMap;

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
        mav.addObject("mCServ", mCServ);

        return mav;
    }

    @RequestMapping(value= {"/add", "/{name}/edit"}, method=RequestMethod.GET)
    public ModelAndView addColl(@PathVariable(required=false, name="name") String collName) {
        boolean isEdit = collName == null ? false: true;


        ModelAndView mav = new ModelAndView("collections/edit");
        if(!isEdit) {
            mav.addObject("myCollection", new MyCollection());
            mav.addObject("imgTmp", "");
            mav.addObject("isEdit", isEdit);
        } else if (isEdit) {
            mav.addObject("myCollection", myCollRepository.findByName(collName));
            mav.addObject("isEdit", isEdit);
        }
        return mav;
    }

    @PostMapping(value = {"/add", "/{name}/edit"})
    public String saveCollection(@PathVariable(required=false, name="name") String collName,
                                 @RequestParam(value="strStartDate") String startDate,
                                 @RequestParam(value="strStopDate") String stopDate,
                                 @RequestParam(value="imgTmp") String imageTemp,
                                 @RequestParam(value="ipt-labels") String strTeches,
                                 @Validated MyCollection myCollection,
                                 BindingResult bindingResult,
                                 RedirectAttributes attr,
                                 Model model) throws NoSuchMethodException {
        /*
         * judge 是新增還是修改
         */
        boolean isEdit = collName == null? false: true;
        //Todo 重寫日期較驗方式，
        // 以 globalexceptionhandler @initbinder 實現較驗
        // controller 用parameter @Pattern、@NotEmpty 較驗日期輸入格式

        HashMap<String, String> dateError = mCServ.validateDates(startDate, stopDate);
        if(dateError != null)
            bindingResult.rejectValue(dateError.get("property"), "error.myCollection", dateError.get("msg"));

        /*
         * strTech 如是空值""，表示使用者沒有提示使用技術
         */
        if(!strTeches.isEmpty()) myCollection.setTechList(mCServ.str2List(strTeches, myCollection));

        if(bindingResult.hasErrors()) {
            /*
             * Todo find the way resolve XHR 回傳的response 沒有執行內部js 的問題
             */
            model.addAttribute("startDate", startDate);
            model.addAttribute("stopDate", stopDate);
            model.addAttribute("imgTmp", imageTemp == ""? null: imageTemp);
            model.addAttribute("isEdit", isEdit);
            return "collections/edit";
        }

        /*
         * 通過較驗後設定日期
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
        logger.info("edit");

        myCollRepository.save(myCollection);
        return "redirect:/mycollection";
    }

    /*
     * @ResponseBody 存入伺服器後，回傳Json
     */
    @PostMapping("/saveImg")
    @ResponseBody
    public JSONObject handleFileUpload(@RequestParam("file") MultipartFile file) {
        /*
         * Todo 永久儲存檔名改成cover以便換照片直接覆蓋
         */
        HashMap<String, String> result = storageServiceImpl.store(file, "temp");

        return JSONObject.fromObject(result);
    }

    /*
     * 刪除展品
     */
    @DeleteMapping("/delete/{id}")
    public String deleteCollection(@PathVariable long id) {

        myCollRepository.deleteById(id);

        return "redirect:/mycollection";
    }
    /*
     * Todo del the tech at the same time remove the label
     */
}