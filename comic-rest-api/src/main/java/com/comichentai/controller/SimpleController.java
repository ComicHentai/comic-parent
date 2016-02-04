package com.comichentai.controller;

import com.comichentai.dto.TestComicDto;
import com.comichentai.entity.Response;
import com.google.common.collect.Lists;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Random;

@Controller
@EnableAutoConfiguration
public class SimpleController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public String hello() {
        return "ComicHentai";
    }

    @RequestMapping(value = "/getComic/{query}", method = RequestMethod.GET)
    @ResponseBody
    public Response getComicByQuery(@PathVariable String query) {
        Random random = new Random(System.currentTimeMillis());
        List<TestComicDto> comicList = Lists.newArrayList();
        for (int i = 0; i < random.nextInt(); i++) {
            TestComicDto testComicDto = new TestComicDto();
            testComicDto.setId(random.nextInt());
            testComicDto.setTitle(query + "[漫画名]");
            testComicDto.setImgTitle("/p1/v1/xxxx" + random.nextInt(10000) + ".img");
            comicList.add(testComicDto);
        }
        Response data = Response.getInstance(true).addAttribute("data", comicList);
        data.setTotalCount(comicList.size());
        return data;
    }

    @RequestMapping(value = "/getComic", method = RequestMethod.GET)
    @ResponseBody
    public Response getComic() {
        Random random = new Random(System.currentTimeMillis());
        List<TestComicDto> comicList = Lists.newArrayList();
        for (int i = 0; i < random.nextInt(); i++) {
            TestComicDto testComicDto = new TestComicDto();
            testComicDto.setId(random.nextInt());
            testComicDto.setTitle("[漫画名" + random.nextInt() + "]");
            testComicDto.setImgTitle("/p1/v1/xxxx" + random.nextInt(10000) + ".img");
            comicList.add(testComicDto);
        }
        Response data = Response.getInstance(true).addAttribute("data", comicList);
        data.setTotalCount(comicList.size());
        return data;
    }


    public static void main(String[] args) {
        SpringApplication.run(SimpleController.class, args);
    }
}