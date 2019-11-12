package com.jointcorp.jcdata.controller;

import com.jointcorp.common.util.MessageResult;
import com.jointcorp.jcdata.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/userInfo")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping("/queryMacByUid")
    @ResponseBody
    public MessageResult queryMacByUid(Long uid) throws Exception {

        return userInfoService.queryMacByUid(uid);
    }

}
