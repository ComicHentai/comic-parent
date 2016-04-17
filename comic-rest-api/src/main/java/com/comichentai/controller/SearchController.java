package com.comichentai.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.comichentai.bo.ComicBusiness;
import com.comichentai.dto.CategoryDto;
import com.comichentai.dto.ComicDto;
import com.comichentai.entity.Response;
import com.comichentai.entity.ResultSupport;
import com.comichentai.rest.utils.PageMapUtil;
import com.comichentai.rest.utils.TokenCheckUtil;
import com.comichentai.security.AESLocker;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by dintama on 16/3/23.
 */
@Controller
@RequestMapping("/search/")
@EnableAutoConfiguration
public class SearchController {

    private static final String IILEGAL_REQUEST = "非法请求";

    @Resource(name = "comicBusiness")
    private ComicBusiness comicBusiness;

    @RequestMapping(value = "result", method = RequestMethod.GET)
    @ResponseBody
    public Response getSearchResult(HttpServletRequest request){
        //获取参数
        String data = request.getParameter("data");
        JSONObject paramMap;
        String mode = request.getParameter("_mode");
        try{
            checkNotNull(data, IILEGAL_REQUEST);
            checkArgument(!data.isEmpty(), IILEGAL_REQUEST);
            if(!"debug".equals(mode)){
                data = AESLocker.decryptBase64(data);
            }
            paramMap = JSON.parseObject(data);
            ComicDto query = PageMapUtil.getQuery(paramMap.getString("pageMap"), ComicDto.class);
            String name = paramMap.getString("keyWord");
            query.setTitle(name);
            query.setAuthor(name);
            ResultSupport<List<ComicDto>> comicListByName = comicBusiness.getComicListByName(query);
            return Response.getInstance(comicListByName.isSuccess())
                    .addAttribute("data", comicListByName.getModule())
                    .addAttribute("isEnd", comicListByName.getTotalCount() < query.getPageSize() * query.getCurrentPage())
                    .addAttribute("pageMap", PageMapUtil.sendNextPage(query));
        } catch (JSONException jsonException) {
            return Response.getInstance(false).setReturnMsg("参数非法");
        } catch (Exception e) {
            e.printStackTrace();
            return Response.getInstance(false).setReturnMsg(e.getMessage());
        }
    }

}
