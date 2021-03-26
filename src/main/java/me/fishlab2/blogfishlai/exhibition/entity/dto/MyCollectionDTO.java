package me.fishlab2.blogfishlai.exhibition.entity.dto;

import me.fishlab2.blogfishlai.exhibition.entity.MyCollection;
import me.fishlab2.blogfishlai.exhibition.entity.Tech;
import org.hibernate.validator.constraints.UniqueElements;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MyCollectionDTO extends MyCollection implements Serializable {
    private List<Tech> techList = new ArrayList<Tech>(super.getTechList());


}
