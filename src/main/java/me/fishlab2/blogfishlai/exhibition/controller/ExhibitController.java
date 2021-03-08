package me.fishlab2.blogfishlai.exhibition.controller;

import me.fishlab2.blogfishlai.exhibition.entity.MyCollection;
import me.fishlab2.blogfishlai.exhibition.repository.MyCollectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("mycollection")
public class ExhibitController {
    @Autowired
    private MyCollectionRepository myCollRepository;

    @RequestMapping("")
    public ModelAndView exhibitCollection() {
        Sort sortColl = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(0, 10, sortColl);
        Page<MyCollection> collList = myCollRepository.findAll(pageable);

        System.out.println(collList);
        ModelAndView mav = new ModelAndView("collections/list");
        mav.addObject("collList", collList);
        return mav;
    }

    @RequestMapping("/add")
    public ModelAndView addColl() {
        ModelAndView mav = new ModelAndView("collections/addColl");
        mav.addObject("myCollection", new MyCollection());
        return mav;
    }

    @PostMapping("")
    public String saveCollection(@Valid MyCollection mC, RedirectAttributes attr) {

        attr.addFlashAttribute("myCollection", mC);
        return "redirect:/mycollection/";
    }

}
