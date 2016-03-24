package com.comichentai.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.comichentai.bo.FavoriteBusiness;
import com.comichentai.dto.CategoryDto;
import com.comichentai.dto.FavoriteDto;
import com.comichentai.entity.Response;
import com.comichentai.entity.ResultSupport;
import com.comichentai.rest.utils.PageMapUtil;
import com.comichentai.rest.utils.TokenCheckUtil;
import com.comichentai.security.AESLocker;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by dintama on 16/3/24.
 */
@Controller
@RequestMapping("/mine/")
@ImportResource("classpath*:/META-INF/spring/spring-dubbo-service-cli.xml")
@EnableAutoConfiguration
public class MineController {

    private static final String IILEGAL_REQUEST = "非法请求";

    @Resource(name = "favoriteBusiness")
    private FavoriteBusiness favoriteBusiness;

    @RequestMapping(value = "collection", method = RequestMethod.GET)
    @ResponseBody
    public Response getMineCollection(HttpServletRequest request, @RequestParam("userId")Integer userId){
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
            FavoriteDto query = PageMapUtil.getQuery(paramMap.getString("pageMap"), FavoriteDto.class);
            query.setUserId(userId);
            ResultSupport<FavoriteDto> userFavoriteComics = favoriteBusiness.getUserFavoriteComics(query);
            return Response.getInstance(userFavoriteComics.isSuccess())
                    .addAttribute("data", userFavoriteComics.getModule())
                    .addAttribute("isEnd", userFavoriteComics.getTotalCount() < query.getPageSize() * query.getCurrentPage())
                    .addAttribute("pageMap", PageMapUtil.sendNextPage(query));

        } catch (JSONException jsonException) {
            return Response.getInstance(false).setReturnMsg("参数非法");
        } catch (Exception e) {
            e.printStackTrace();
            return Response.getInstance(false).setReturnMsg(e.getMessage());
        }
    }

    @RequestMapping(value = "special", method = RequestMethod.GET)
    @ResponseBody
    public Response getMineSpecial(HttpServletRequest request, @RequestParam("userId")Integer userId){
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
            FavoriteDto query = PageMapUtil.getQuery(paramMap.getString("pageMap"), FavoriteDto.class);
            query.setUserId(userId);
            ResultSupport<FavoriteDto> userFavoriteSpecials = favoriteBusiness.getUserFavoriteSpecials(query);
            return Response.getInstance(userFavoriteSpecials.isSuccess())
                    .addAttribute("data", userFavoriteSpecials.getModule())
                    .addAttribute("isEnd", userFavoriteSpecials.getTotalCount() < query.getPageSize() * query.getCurrentPage())
                    .addAttribute("pageMap", PageMapUtil.sendNextPage(query));

        } catch (JSONException jsonException) {
            return Response.getInstance(false).setReturnMsg("参数非法");
        } catch (Exception e) {
            e.printStackTrace();
            return Response.getInstance(false).setReturnMsg(e.getMessage());
        }
    }

}
