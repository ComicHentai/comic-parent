package com.comichentai.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.comichentai.dto.ComicDto;
import com.comichentai.entity.Response;
import com.comichentai.entity.ResultSupport;
import com.comichentai.rest.utils.ElasticSearchUtil;
import com.comichentai.rest.utils.PageMapUtil;
import com.comichentai.security.AESLocker;
import com.comichentai.service.ComicService;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
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


    @Resource(name = "comicService")
    private ComicService comicService;

    @Resource(name = "elasticSearchUtil")
    private ElasticSearchUtil elasticSearchUtil;

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
            //在这里,我们获取到了我们需要进行查询的部分.
            String keyWord = paramMap.getString("keyWord");
            List<Integer> idList = elasticSearchUtil.getIdList("comichentai", "comic", keyWord, query.getCurrentPage(), query.getPageSize());
            ResultSupport<List<ComicDto>> comicListByIdList = comicService.getComicListByIdList(idList);
            return Response.getInstance(comicListByIdList.isSuccess())
                    .addAttribute("data", comicListByIdList.getModule())
                    .addAttribute("isEnd", comicListByIdList.getTotalCount() < query.getPageSize() * query.getCurrentPage())
                    .addAttribute("pageMap", PageMapUtil.sendNextPage(query));
        } catch (JSONException jsonException) {
            return Response.getInstance(false).setReturnMsg("参数非法");
        } catch (Exception e) {
            e.printStackTrace();
            return Response.getInstance(false).setReturnMsg(e.getMessage());
        }
    }

}
