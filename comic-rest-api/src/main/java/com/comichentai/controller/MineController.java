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
import com.comichentai.service.FavoriteService;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.LinkedList;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by dintama on 16/3/24.
 */
@Controller
@RequestMapping("/mine/")
@EnableAutoConfiguration
public class MineController {

    private static final int TYPE_COMIC = 0;
    private static final int TYPE_SPECIAL = 1;
    private static final String IILEGAL_REQUEST = "非法请求";

    @Resource(name = "favoriteBusiness")
    private FavoriteBusiness favoriteBusiness;

    @Resource(name = "favoriteService")
    private FavoriteService favoriteService;

    private List<Integer> getFavoriteIdList(Integer userInfoId, Integer targetId, Integer targetType){
        FavoriteDto favoriteDto = new FavoriteDto(userInfoId, targetId, targetType);
        ResultSupport<List<FavoriteDto>> favoriteListByQuery = favoriteService.getFavoriteListByQuery(favoriteDto);
        checkNotNull(favoriteListByQuery.getModule(), IILEGAL_REQUEST);
        checkArgument(!favoriteListByQuery.getModule().isEmpty(), IILEGAL_REQUEST);
        List<FavoriteDto> favoriteDtoList = favoriteListByQuery.getModule();
        List<Integer> idList = new LinkedList<>();
        for(FavoriteDto o : favoriteDtoList){
            idList.add(o.getTargetId());
        }
        return idList;
    }


    @RequestMapping(value = "collection/add", method = RequestMethod.GET)
    @ResponseBody
    public Response addMineCollection(HttpServletRequest request){
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
            checkNotNull(token, IILEGAL_REQUEST);
            checkArgument(!token.isEmpty(), IILEGAL_REQUEST);
            JSONObject tokenMap = JSON.parseObject(token);
            Integer userInfoId = tokenMap.getInteger("userInfoId");
            checkNotNull(userInfoId, IILEGAL_REQUEST);
            Integer comicId = paramMap.getInteger("comicId");
            ResultSupport<Integer> integerResultSupport = favoriteService.addFavorite(userInfoId, comicId, TYPE_COMIC);
            return Response.getInstance(integerResultSupport.isSuccess())
                    .addAttribute("data", integerResultSupport.getModule());

        } catch (JSONException jsonException) {
            return Response.getInstance(false).setReturnMsg("参数非法");
        } catch (Exception e) {
            e.printStackTrace();
            return Response.getInstance(false).setReturnMsg(e.getMessage());
        }
    }

    @RequestMapping(value = "collection/deleted", method = RequestMethod.GET)
    @ResponseBody
    public Response deletedMineCollection(HttpServletRequest request){
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
            checkNotNull(token, IILEGAL_REQUEST);
            checkArgument(!token.isEmpty(), IILEGAL_REQUEST);
            JSONObject tokenMap = JSON.parseObject(token);
            Integer userInfoId = tokenMap.getInteger("userInfoId");
            checkNotNull(userInfoId, IILEGAL_REQUEST);
            Integer comicId = paramMap.getInteger("comicId");
            List<Integer> favoriteIdList = getFavoriteIdList(userInfoId, comicId, TYPE_COMIC);
            ResultSupport<Integer> integerResultSupport = favoriteService.batchRemoveFavorite(favoriteIdList);
            return Response.getInstance(integerResultSupport.isSuccess())
                    .addAttribute("data", integerResultSupport.getModule());

        } catch (JSONException jsonException) {
            return Response.getInstance(false).setReturnMsg("参数非法");
        } catch (Exception e) {
            e.printStackTrace();
            return Response.getInstance(false).setReturnMsg(e.getMessage());
        }
    }

    @RequestMapping(value = "collection/index", method = RequestMethod.GET)
    @ResponseBody
    public Response getMineCollection(HttpServletRequest request){
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
            JSONObject tokenObject = JSON.parseObject(token);
            Integer userInfoId = tokenObject.getInteger("userInfoId");
            checkNotNull(userInfoId, IILEGAL_REQUEST);
            query.setUserId(userInfoId);
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

    @RequestMapping(value = "special/add", method = RequestMethod.GET)
    @ResponseBody
    public Response addMineSpecial(HttpServletRequest request){
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
            checkNotNull(token, IILEGAL_REQUEST);
            checkArgument(!token.isEmpty(), IILEGAL_REQUEST);
            JSONObject tokenMap = JSON.parseObject(token);
            Integer userInfoId = tokenMap.getInteger("userInfoId");
            checkNotNull(userInfoId, IILEGAL_REQUEST);
            Integer specialId = paramMap.getInteger("specialId");
            ResultSupport<Integer> integerResultSupport = favoriteService.addFavorite(userInfoId, specialId, TYPE_SPECIAL);
            return Response.getInstance(integerResultSupport.isSuccess())
                    .addAttribute("data", integerResultSupport.getModule());

        } catch (JSONException jsonException) {
            return Response.getInstance(false).setReturnMsg("参数非法");
        } catch (Exception e) {
            e.printStackTrace();
            return Response.getInstance(false).setReturnMsg(e.getMessage());
        }
    }

    @RequestMapping(value = "special/deleted", method = RequestMethod.GET)
    @ResponseBody
    public Response deletedMineSpecial(HttpServletRequest request){
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
            checkNotNull(token, IILEGAL_REQUEST);
            checkArgument(!token.isEmpty(), IILEGAL_REQUEST);
            JSONObject tokenMap = JSON.parseObject(token);
            Integer userInfoId = tokenMap.getInteger("userInfoId");
            checkNotNull(userInfoId, IILEGAL_REQUEST);
            Integer specialId = paramMap.getInteger("specialId");
            List<Integer> favoriteIdList = getFavoriteIdList(userInfoId, specialId, TYPE_SPECIAL);
            ResultSupport<Integer> integerResultSupport = favoriteService.batchRemoveFavorite(favoriteIdList);
            return Response.getInstance(integerResultSupport.isSuccess())
                    .addAttribute("data", integerResultSupport.getModule());

        } catch (JSONException jsonException) {
            return Response.getInstance(false).setReturnMsg("参数非法");
        } catch (Exception e) {
            e.printStackTrace();
            return Response.getInstance(false).setReturnMsg(e.getMessage());
        }
    }

    @RequestMapping(value = "special/index", method = RequestMethod.GET)
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
            JSONObject tokenObject = JSON.parseObject(token);
            Integer userInfoId = tokenObject.getInteger("userInfoId");
            checkNotNull(userInfoId, IILEGAL_REQUEST);
            query.setUserId(userInfoId);
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
