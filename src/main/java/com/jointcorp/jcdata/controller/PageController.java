package com.jointcorp.jcdata.controller;

import com.jointcorp.jcdata.entity.UserInfo;
import com.jointcorp.jcdata.service.DataService;
import com.jointcorp.jcdata.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class PageController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private DataService dataService;

    @GetMapping("/index")
    public String showIndex(Model model) throws Exception {
        List<UserInfo> users = userInfoService.selectByUids();
        model.addAttribute("users",users);
        return "index";
    }
    @GetMapping("/ecg")
    public String showEcg(Model model) throws Exception {
        List<UserInfo> users = userInfoService.selectByUids();
        model.addAttribute("users",users);
        return "ecg";
    }

    @GetMapping("/gps")
    public String showGps(Model model) throws Exception {
        List<UserInfo> users = userInfoService.selectByUids();
        model.addAttribute("users",users);
        return "gps";
    }

    @GetMapping("/ecgdata")
    public String showEcgdata() throws Exception {
        return "ecgdata";
    }


    @GetMapping("/queryAll")
    @ResponseBody
    public List<UserInfo> selectAll() throws Exception {
        return userInfoService.selectByUids();
    }

    @GetMapping("/gpsIndex")
    public String showGpsIndex(long uid, String mac, String t) throws Exception {
        int v = dataService.showGpsIndex(uid,mac,t);
        if(v == 0) {
            return "track";
        } else {
            return "baidu_track";
        }
    }


}
