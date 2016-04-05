package com.comichentai.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.comichentai.bo.CategoryBusiness;
import com.comichentai.dto.CategoryDto;
import com.comichentai.dto.ClassifiedDto;
import com.comichentai.entity.Response;
import com.comichentai.entity.ResultSupport;
import com.comichentai.rest.utils.JSONUtil;
import com.comichentai.rest.utils.PageMapUtil;
import com.comichentai.rest.utils.TokenCheckUtil;
import com.comichentai.security.AESLocker;
import com.comichentai.service.CategoryService;
import com.comichentai.service.ClassifiedService;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.hazelcast.HazelcastJpaDependencyAutoConfiguration;
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

    @Resource(name = "categoryService")
    private CategoryService categoryService;

    @RequestMapping(value = "category/add", method = RequestMethod.GET)
    @ResponseBody
    public Response addCategory(HttpServletRequest request){
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
            CategoryDto categoryDto = paramMap.getObject("categoryDto", CategoryDto.class);
            checkNotNull(categoryDto, IILEGAL_REQUEST);
            ResultSupport<Integer> integerResultSupport = categoryService.addCategory(categoryDto);
            return Response.getInstance(integerResultSupport.isSuccess())
                    .addAttribute("data", integerResultSupport.getModule());
        } catch (JSONException jsonException) {
            return Response.getInstance(false).setReturnMsg("参数非法");
        } catch (Exception e) {
            e.printStackTrace();
            return Response.getInstance(false).setReturnMsg(e.getMessage());
        }
    }

    @RequestMapping(value = "category/deleted", method = RequestMethod.GET)
    @ResponseBody
    public Response deletedCategory(HttpServletRequest request){
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
            CategoryDto categoryDto = paramMap.getObject("categoryDto", CategoryDto.class);
            checkNotNull(categoryDto, IILEGAL_REQUEST);
            ResultSupport<List<CategoryDto>> categoryListByQuery = categoryService.getCategoryListByQuery(categoryDto);
            List<CategoryDto> module = categoryListByQuery.getModule();
            checkNotNull(module, IILEGAL_REQUEST);
            checkArgument(!module.isEmpty(), IILEGAL_REQUEST);
            List<Integer> idList = new LinkedList<>();
            for(CategoryDto o : module){
                idList.add(o.getId());
            }
            ResultSupport<Integer> integerResultSupport = categoryService.batchRemoveCategory(idList);
            return Response.getInstance(integerResultSupport.isSuccess())
                    .addAttribute("data", integerResultSupport.getModule());
        } catch (JSONException jsonException) {
            return Response.getInstance(false).setReturnMsg("参数非法");
        } catch (Exception e) {
            e.printStackTrace();
            return Response.getInstance(false).setReturnMsg(e.getMessage());
        }
    }


    @RequestMapping(value = "add", method = RequestMethod.GET)
    @ResponseBody
    public Response addClassified(HttpServletRequest request){
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
            ClassifiedDto classifiedDto = paramMap.getObject("classified", ClassifiedDto.class);
            checkNotNull(classifiedDto, IILEGAL_REQUEST);
            checkArgument(classifiedDto.getCoverTitle() != null && classifiedDto.getTitle() != null, IILEGAL_REQUEST);
            ResultSupport<Integer> integerResultSupport = classifiedService.addClassified(classifiedDto);
            return Response.getInstance(integerResultSupport.isSuccess())
                    .addAttribute("data", integerResultSupport.getModule());
        } catch (JSONException jsonException) {
            return Response.getInstance(false).setReturnMsg("参数非法");
        } catch (Exception e) {
            e.printStackTrace();
            return Response.getInstance(false).setReturnMsg(e.getMessage());
        }
    }

    @RequestMapping(value = "deleted", method = RequestMethod.GET)
    @ResponseBody
    public Response deletedClassified(HttpServletRequest request){
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
            Integer classifiedId = paramMap.getInteger("classifiedId");
            checkNotNull(classifiedId, IILEGAL_REQUEST);
            ResultSupport<Integer> integerResultSupport = classifiedService.removeClassified(classifiedId);
            return Response.getInstance(integerResultSupport.isSuccess())
                    .addAttribute("data", integerResultSupport.getModule());
        } catch (JSONException jsonException) {
            return Response.getInstance(false).setReturnMsg("参数非法");
        } catch (Exception e) {
            e.printStackTrace();
            return Response.getInstance(false).setReturnMsg(e.getMessage());
        }
    }

    @RequestMapping(value = "deleted/batch", method = RequestMethod.GET)
    @ResponseBody
    public Response batchDeletedClassified(HttpServletRequest request){
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
            List<Integer> idList = JSONUtil.parseJsonArrayToList(paramMap.getString("idList"), Integer.class);
            checkNotNull(idList, IILEGAL_REQUEST);
            checkArgument(!idList.isEmpty(), IILEGAL_REQUEST);
            ResultSupport<Integer> integerResultSupport = classifiedService.batchRemoveClassified(idList);
            return Response.getInstance(integerResultSupport.isSuccess())
                    .addAttribute("data", integerResultSupport.getModule());
        } catch (JSONException jsonException) {
            return Response.getInstance(false).setReturnMsg("参数非法");
        } catch (Exception e) {
            e.printStackTrace();
            return Response.getInstance(false).setReturnMsg(e.getMessage());
        }
    }

    @RequestMapping(value = "updated", method = RequestMethod.GET)
    @ResponseBody
    public Response updatedClassified(HttpServletRequest request){
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
            ClassifiedDto classifiedDto = paramMap.getObject("classified", ClassifiedDto.class);
            checkNotNull(classifiedDto, IILEGAL_REQUEST);
            ResultSupport<Integer> integerResultSupport = classifiedService.modifyClassified(classifiedDto);
            return Response.getInstance(integerResultSupport.isSuccess())
                    .addAttribute("data", integerResultSupport.getModule());
        } catch (JSONException jsonException) {
            return Response.getInstance(false).setReturnMsg("参数非法");
        } catch (Exception e) {
            e.printStackTrace();
            return Response.getInstance(false).setReturnMsg(e.getMessage());
        }
    }


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


    @RequestMapping(value = "ComicDetail", method = RequestMethod.GET)
    @ResponseBody
    public Response getClassifiedComicDetail(HttpServletRequest request){
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
            Integer classifiedId = paramMap.getInteger("classifiedId");
            checkNotNull(classifiedId, IILEGAL_REQUEST);
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

    @RequestMapping(value = "SpecialDetail", method = RequestMethod.GET)
    @ResponseBody
    public Response getClassifiedSpecialDetail(HttpServletRequest request){
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
            Integer classifiedId = paramMap.getInteger("classifiedId");
            checkNotNull(classifiedId, IILEGAL_REQUEST);
            query.setTargetId(classifiedId);
            ResultSupport<CategoryDto> specialByClassified = categoryBusiness.getSpecialByClassified(query);
            return Response.getInstance(specialByClassified.isSuccess())
                    .addAttribute("date", specialByClassified.getModule())
                    .addAttribute("isEnd", specialByClassified.getTotalCount() < query.getPageSize() * query.getCurrentPage())
                    .addAttribute("pageMap", PageMapUtil.sendNextPage(query));
        } catch (JSONException jsonException) {
            return Response.getInstance(false).setReturnMsg("参数非法");
        } catch (Exception e) {
            e.printStackTrace();
            return Response.getInstance(false).setReturnMsg(e.getMessage());
        }
    }

    @RequestMapping(value = "volatileClassified", method = RequestMethod.GET)
    @ResponseBody
    public Response volatileClassified(HttpServletRequest request){
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
            ClassifiedDto classified = paramMap.getObject("classified", ClassifiedDto.class);
            checkNotNull(classified.getTitle(), IILEGAL_REQUEST);
            ResultSupport<List<ClassifiedDto>> classifiedListByQuery = classifiedService.getClassifiedListByQuery(classified);
            if(classifiedListByQuery.getModule().size() != 0){
                return Response.getInstance(false).setReturnMsg("该分类名称已被使用");
            }
            return Response.getInstance(classifiedListByQuery.isSuccess());
        }catch (JSONException jsonException) {
            return Response.getInstance(false).setReturnMsg("参数非法");
        } catch (Exception e) {
            e.printStackTrace();
            return Response.getInstance(false).setReturnMsg(e.getMessage());
        }
    }
    



}
