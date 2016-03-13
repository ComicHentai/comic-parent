package com.comichentai.controller;

import com.comichentai.dto.ComicDto;
import com.comichentai.dto.TestComicDto;
import com.comichentai.entity.Response;
import com.comichentai.entity.ResultSupport;
import com.comichentai.service.ComicService;
import com.comichentai.service.TestComicService;
import com.google.common.collect.Lists;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Random;

@Controller
@ImportResource("classpath*:/META-INF/spring/spring-dubbo-service-cli.xml")
@EnableAutoConfiguration
public class SimpleController {

    @Resource(name = "testComicService")
    public TestComicService testComicService;

    @Resource(name = "comicService")
    public ComicService comicService;

    public static void main(String[] args) {
        SpringApplication.run(SimpleController.class, args);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public String hello() {
        return "ComicHentai";
    }

    @RequestMapping(value = "/testComic", method = RequestMethod.GET)
    @ResponseBody
    public List<TestComicDto> getTestComic() {
        return testComicService.getTestComicListByIdList(Lists.newArrayList(1, 2, 3, 4)).getModule();
    }

    @RequestMapping(value = "/resultSupport", method = RequestMethod.GET)
    @ResponseBody
    public ResultSupport<List<TestComicDto>> getTestComicResult() {
        return testComicService.getTestComicListByIdList(Lists.newArrayList(1, 2, 3, 4));
    }

    @RequestMapping(value = "/getComic" , method = RequestMethod.GET)
    @ResponseBody
    public ResultSupport<List<ComicDto>> getComic(){
        return comicService.getComicListByQuery(new ComicDto());
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

    public void setTestComicService(TestComicService testComicService) {
        this.testComicService = testComicService;
    }
}