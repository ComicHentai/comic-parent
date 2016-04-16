package com.comichentai.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.comichentai.dto.ComicDto;
import com.comichentai.entity.Response;
import com.comichentai.entity.ResultSupport;
import com.comichentai.rest.utils.PageMapUtil;
import com.comichentai.rest.utils.TokenCheckUtil;
import com.comichentai.security.AESLocker;
import com.comichentai.service.ComicService;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by dintama on 16/3/22.
 */
@Controller
@RequestMapping("/welcome/")
@EnableAutoConfiguration
public class WelcomeController {

    private static final String IILEGAL_REQUEST = "非法请求";

    @Resource(name = "comicService")
    public ComicService comicService;

    @RequestMapping(value = "index", method = RequestMethod.GET)
    @ResponseBody
    public Response getWelcomeComicByQuery(HttpServletRequest request, @RequestBody String r_request){
        JSONObject JSONRequest = JSON.parseObject(r_request);
        //获取参数
        String data = JSONRequest.getString("data");
        JSONObject paramMap;
        //获取设备信息
        String mode = JSONRequest.getString("_mode");
        String auth = JSONRequest.getString("_auth");
        try{
            //判断data是否合法
            checkNotNull(data, IILEGAL_REQUEST);
            checkArgument(!data.isEmpty(), IILEGAL_REQUEST);
            //判断是否需要对data进行加密
            if(!"debug".equals(mode)){
                data = AESLocker.decrypt(data);
            }
            //获取验证登录的必要信息
            paramMap = JSON.parseObject(data);
            String token = paramMap.getString("token");
            String deviceId = paramMap.getString("deviceId");
            if(!"debug".equals(auth)){
                TokenCheckUtil.checkLoginToken(token, mode, deviceId, request);
            }
            //验证完成,开始查询
            ComicDto query = PageMapUtil.getQuery(paramMap.getString("pageMap"), ComicDto.class);
            ResultSupport<List<ComicDto>> comicListByQuery = comicService.getComicListByQuery(query);
            return Response.getInstance(comicListByQuery.isSuccess())
                    .addAttribute("data", comicListByQuery.getModule())
                    .addAttribute("isEnd", comicListByQuery.getTotalCount() < query.getPageSize() * query.getCurrentPage())
                    .addAttribute("pageMap", PageMapUtil.sendNextPage(query));
        }catch (JSONException e){
            return Response.getInstance(false).setReturnMsg("非法参数");
        }catch (Exception e){
            e.printStackTrace();
            return Response.getInstance(false).setReturnMsg(e.getMessage());
        }

    }

}
