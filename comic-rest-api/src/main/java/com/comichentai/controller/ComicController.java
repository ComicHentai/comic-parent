package com.comichentai.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.comichentai.bo.CategoryBusiness;
import com.comichentai.dto.ComicDto;
import com.comichentai.entity.Response;
import com.comichentai.entity.ResultSupport;
import com.comichentai.rest.utils.PageMapUtil;
import com.comichentai.rest.utils.TokenCheckUtil;
import com.comichentai.security.AESLocker;
import com.comichentai.service.ComicService;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

@Controller
@RequestMapping("/comic/")
@ImportResource("classpath*:/META-INF/spring/spring-dubbo-service-cli.xml")
@EnableAutoConfiguration
public class ComicController {

    private static final String IILEGAL_REQUEST = "非法请求";

    @Resource(name = "comicService")
    public ComicService comicService;

    @Resource(name = "categoryBusiness")
    public CategoryBusiness categoryBusiness;

    @RequestMapping(value = "index", method = RequestMethod.GET)
    @ResponseBody
    public Response getComicInfoByComicId(HttpServletRequest request, @RequestParam("comicId")int comicId){
        //获取参数
        String data = request.getParameter("data");
        JSONObject paramMap;
        String mode = request.getParameter("_mode");
        String auth = request.getParameter("_auth");
        try{
            checkNotNull(data, IILEGAL_REQUEST);
            checkArgument(!data.isEmpty(), IILEGAL_REQUEST);
            if(!"debug".equals(mode)){
                data = AESLocker.decrypt(data);
            }
            paramMap = JSON.parseObject(data);
            String token = paramMap.getString("token");
            String deviceId = paramMap.getString("deviceId");
            if(!"debug".equals(auth)){
                TokenCheckUtil.checkLoginToken(token, mode, deviceId, request);
            }

        }
    }

    @RequestMapping(value = "/getComic", method = RequestMethod.GET)
    @ResponseBody
    public Response getComicByQuery(HttpServletRequest request) {
        //获取参数
        String data = request.getParameter("data");
        JSONObject paramMap;
        //获取设备信息
        String mode = request.getParameter("_mode");
        String auth = request.getParameter("_auth");
        boolean isEnd = false;
        try {
            checkNotNull(data, IILEGAL_REQUEST);
            checkArgument(!data.isEmpty(), IILEGAL_REQUEST);
            if (!"debug".equals(mode)) {
                data = AESLocker.decrypt(data);
            }
            paramMap = JSON.parseObject(data);
            String token = paramMap.getString("token");
            String deviceId = paramMap.getString("deviceId");
            if (!"debug".equals(auth)) {
                TokenCheckUtil.checkLoginToken(token, mode, deviceId, request);
            }
            ComicDto query = PageMapUtil.getQuery(paramMap.getString("pageMap"), ComicDto.class);
            ResultSupport<List<ComicDto>> comicListByQuery = comicService.getComicListByQuery(query);
            return Response.getInstance(comicListByQuery.isSuccess())
                    .addAttribute("data", comicListByQuery.getModule())
                    .addAttribute("isEnd", comicListByQuery.getTotalCount() < query.getCurrentPage() * query.getPageSize())
                    .addAttribute("pageMap", PageMapUtil.sendNextPage(query));
        } catch (JSONException jsonException) {
            return Response.getInstance(false).setReturnMsg("参数非法");
        } catch (Exception e) {
            e.printStackTrace();
            return Response.getInstance(false).setReturnMsg(e.getMessage());
        }
    }






}