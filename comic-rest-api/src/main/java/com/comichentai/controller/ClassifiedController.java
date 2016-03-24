package com.comichentai.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.comichentai.bo.CategoryBusiness;
import com.comichentai.dto.CategoryDto;
import com.comichentai.dto.ClassifiedDto;
import com.comichentai.entity.Response;
import com.comichentai.entity.ResultSupport;
import com.comichentai.rest.utils.PageMapUtil;
import com.comichentai.rest.utils.TokenCheckUtil;
import com.comichentai.security.AESLocker;
import com.comichentai.service.ClassifiedService;
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

/**
 * Created by dintama on 16/3/23.
 */
@Controller
@RequestMapping("/classification/")
@ImportResource("classpath*:/META-INF/spring/spring-dubbo-service-cli.xml")
@EnableAutoConfiguration
public class ClassifiedController {

    private static final String IILEGAL_REQUEST = "非法请求";

    @Resource(name = "classifiedService")
    private ClassifiedService classifiedService;

    @Resource(name = "categoryBusiness")
    private CategoryBusiness categoryBusiness;


    @RequestMapping(value = "index", method = RequestMethod.GET)
    @ResponseBody
    public Response getClassifiedIndex(HttpServletRequest request){
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
            ClassifiedDto query = PageMapUtil.getQuery(paramMap.getString("pageMap"), ClassifiedDto.class);
            ResultSupport<List<ClassifiedDto>> classifiedListByQuery = classifiedService.getClassifiedListByQuery(query);
            return Response.getInstance(classifiedListByQuery.isSuccess())
                    .addAttribute("data", classifiedListByQuery.getModule())
                    .addAttribute("isEnd", classifiedListByQuery.getTotalCount() < query.getCurrentPage() * query.getPageSize())
                    .addAttribute("pageMap", PageMapUtil.sendNextPage(query));

        } catch (JSONException jsonException) {
            return Response.getInstance(false).setReturnMsg("参数非法");
        } catch (Exception e) {
            e.printStackTrace();
            return Response.getInstance(false).setReturnMsg(e.getMessage());
        }

    }


    @RequestMapping(value = "detail", method = RequestMethod.GET)
    @ResponseBody
    public Response getClassifiedDetail(HttpServletRequest request, @RequestParam("classifiedId")Integer classifiedId){
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
            CategoryDto query = PageMapUtil.getQuery(paramMap.getString("pageMap"), CategoryDto.class);
            query.setTargetId(classifiedId);
            ResultSupport<CategoryDto> comicByClassified = categoryBusiness.getComicByClassified(query);
            return Response.getInstance(comicByClassified.isSuccess())
                    .addAttribute("date", comicByClassified.getModule())
                    .addAttribute("isEnd", comicByClassified.getTotalCount() < query.getPageSize() * query.getCurrentPage())
                    .addAttribute("pageMap", PageMapUtil.sendNextPage(query));

        } catch (JSONException jsonException) {
            return Response.getInstance(false).setReturnMsg("参数非法");
        } catch (Exception e) {
            e.printStackTrace();
            return Response.getInstance(false).setReturnMsg(e.getMessage());
        }
    }

}
