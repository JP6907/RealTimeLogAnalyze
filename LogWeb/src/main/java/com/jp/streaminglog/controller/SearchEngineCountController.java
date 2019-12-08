package com.jp.streaminglog.controller;

import com.jp.streaminglog.domain.SearchEngineCount;
import com.jp.streaminglog.service.SearchEngineCountService;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//@RestController //=Controller + ResponseBody
@Controller //jsp页面不能使用RestController
public class SearchEngineCountController {

    @Autowired
    private SearchEngineCountService searchEngineCountService;

    //页面跳转
    @RequestMapping("/searchClick")
    public String toGetCourceClick(){
        return "searchclick";
    }

    @ResponseBody
    @RequestMapping(value = "/getSearchClickCount",method = RequestMethod.GET)
    public JSONArray searchClickCount(String date){
        //如果url没有传值，传入一个默认值
        if(date == null || date.equals("")){
            date = "20190330";
        }
        List<SearchEngineCount> list = null;
        list = searchEngineCountService.findSearchEngineCount("ns1:courses_search_clickcount",date);
        //封装JSON数据
        return JSONArray.fromObject(list);
    }
}
