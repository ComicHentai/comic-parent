package com.comichentai.controller;

import com.comichentai.security.AESLocker;
import com.comichentai.service.ComicService;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@EnableAutoConfiguration
public class LockerController {

    private static final String IILEGAL_REQUEST = "非法请求";

    @Resource(name = "comicService")
    public ComicService comicService;

    @RequestMapping(value = "/encrypt", method = RequestMethod.GET)
    @ResponseBody
    public String getEncryptMessage(HttpServletRequest request) {
        String message = request.getParameter("message");
        return AESLocker.encrypt(message);
    }

    @RequestMapping(value = "/decrypt", method = RequestMethod.GET)
    @ResponseBody
    public String getDecryptMessage(HttpServletRequest request) {
        String message = request.getParameter("message");
        return AESLocker.decrypt(message);
    }

}