package com.comichentai.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.comichentai.bo.JumpBusiness;
import com.comichentai.dto.ClassifiedDto;
import com.comichentai.dto.JumpDto;
import com.comichentai.dto.SpecialDto;
import com.comichentai.entity.Response;
import com.comichentai.entity.ResultSupport;
import com.comichentai.rest.utils.JSONUtil;
import com.comichentai.rest.utils.PageMapUtil;
import com.comichentai.rest.utils.TokenCheckUtil;
import com.comichentai.security.AESLocker;
import com.comichentai.service.SpecialService;
import com.sun.org.apache.regexp.internal.RE;
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
 * Created by dintama on 16/3/24.
 */
@Controller
@RequestMapping("/special/")
@ImportResource("classpath*:/META-INF/spring/spring-dubbo-service-cli.xml")
public class SpecialController {


    private static final String IILEGAL_REQUEST = "非法请求";

    @Resource(name = "jumpBusiness")
    private JumpBusiness jumpBusiness;

    @Resource(name = "specialService")
    private SpecialService specialService;

    @RequestMapping(value = "add", method = RequestMethod.GET)
    @ResponseBody
    public Response addSpecial(HttpServletRequest request){
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
            JSONObject tokenObject = JSON.parseObject(token);
            Integer userInfoId = tokenObject.getInteger("userInfoId");
            String title = paramMap.getString("specialTitle");
            ResultSupport<Integer> integerResultSupport = specialService.addSpecial(title, userInfoId);
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
    public Response deletedSpecial(HttpServletRequest request){
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
            checkArgument(!idList.isEmpty(), IILEGAL_REQUEST);
            ResultSupport<Integer> integerResultSupport = specialService.batchRemoveSpecial(idList);
            return Response.getInstance(integerResultSupport.isSuccess())
                    .addAttribute("data", integerResultSupport.getModule());
        } catch (JSONException jsonException) {
            return Response.getInstance(false).setReturnMsg("参数非法");
        } catch (Exception e) {
            e.printStackTrace();
            return Response.getInstance(false).setReturnMsg(e.getMessage());
        }
    }

    @RequestMapping(value = "content", method = RequestMethod.GET)
    @ResponseBody
    public Response getSpecialContent(HttpServletRequest request, @RequestParam("specialId")Integer specialId){
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
            JumpDto query = PageMapUtil.getQuery(paramMap.getString("pageMap"), JumpDto.class);
            query.setSpecialId(specialId);
            ResultSupport<JumpDto> jumpBySpecial = jumpBusiness.getJumpBySpecial(query);
            return Response.getInstance(jumpBySpecial.isSuccess())
                    .addAttribute("data", jumpBySpecial.getModule())
                    .addAttribute("isEnd", jumpBySpecial.getTotalCount() < query.getCurrentPage() * query.getPageSize())
                    .addAttribute("pageMap", PageMapUtil.sendNextPage(query));

        } catch (JSONException jsonException) {
            return Response.getInstance(false).setReturnMsg("参数非法");
        } catch (Exception e) {
            e.printStackTrace();
            return Response.getInstance(false).setReturnMsg(e.getMessage());
        }
    }

}
