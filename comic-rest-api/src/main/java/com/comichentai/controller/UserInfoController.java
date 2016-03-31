package com.comichentai.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.comichentai.dto.UserInfoDto;
import com.comichentai.entity.Response;
import com.comichentai.entity.ResultSupport;
import com.comichentai.rest.utils.PageMapUtil;
import com.comichentai.rest.utils.TokenCheckUtil;
import com.comichentai.security.AESLocker;
import com.comichentai.service.UserInfoService;
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
 * Created by dintama on 16/3/31.
 */
@Controller
@ImportResource("classpath*:/META-INF/spring/spring-dubbo-service-cli.xml")
@RequestMapping("/user/")
@EnableAutoConfiguration
public class UserInfoController {

    private static final String IILEGAL_REQUEST = "非法请求";
    private static final Integer DEFALUT_AVAILABLE_DAYS = 7;

    @Resource(name = "userInfoService")
    private UserInfoService userInfoService;


    @RequestMapping(value = "signUp", method = RequestMethod.POST)
    @ResponseBody
    public Response signUpUserInfo(UserInfoDto userInfoDto){
        try{
            //判断userInfoDto是否合法
            checkNotNull(userInfoDto, IILEGAL_REQUEST);
            //验证完成 -- username和nickname应该在焦点离开时异步校验的吧= =
            ResultSupport<Integer> integerResultSupport = userInfoService.addUserInfo(userInfoDto);
            return Response.getInstance(integerResultSupport.isSuccess())       //这个分页什么的应该不用加到里面了吧..
                    .addAttribute("data", integerResultSupport.getModule());
        }catch (Exception e){
            e.printStackTrace();
            return Response.getInstance(false).setReturnMsg(e.getMessage());
        }
    }


    @RequestMapping(value = "updated", method = RequestMethod.POST)
    @ResponseBody
    public Response updatedUserInfo(UserInfoDto userInfoDto){
        try{
            //判断userInfoDto是否合法
            checkNotNull(userInfoDto, IILEGAL_REQUEST);
            //验证完成 -- nickname在焦点离开时异步校验
            ResultSupport<Integer> integerResultSupport = userInfoService.modifyUserInfo(userInfoDto);
            return Response.getInstance(integerResultSupport.isSuccess())
                    .addAttribute("data", integerResultSupport.getModule());
        }catch (Exception e){
            e.printStackTrace();
            return Response.getInstance(false).setReturnMsg(e.getMessage());
        }
    }


    @RequestMapping(value = "deleted", method = RequestMethod.GET)
    @ResponseBody
    public Response deletedUserInfo(@RequestParam("id")Integer id){
        try{
            checkNotNull(id, IILEGAL_REQUEST);
            ResultSupport<Integer> integerResultSupport = userInfoService.removeUserInfo(id);
            return Response.getInstance(integerResultSupport.isSuccess())
                    .addAttribute("data", integerResultSupport.getModule());
        }catch (Exception e){
            e.printStackTrace();
            return Response.getInstance(false).setReturnMsg(e.getMessage());
        }
    }

    @RequestMapping(value = "deleted/batch", method = RequestMethod.GET)
    @ResponseBody
    public Response batchDeletedUserInfo(@RequestParam("idList")List<Integer> idList){
        try{
            checkNotNull(idList, IILEGAL_REQUEST);
            checkArgument(!idList.isEmpty(), IILEGAL_REQUEST);
            ResultSupport<Integer> integerResultSupport = userInfoService.batchRemoveUserInfo(idList);
            return Response.getInstance(integerResultSupport.isSuccess())
                    .addAttribute("data", integerResultSupport.getModule());
        }catch (Exception e){
            e.printStackTrace();
            return Response.getInstance(false).setReturnMsg(e.getMessage());
        }
    }

    @RequestMapping(value = "signIn", method = RequestMethod.POST)
    @ResponseBody
    public Response signInUserInfo(HttpServletRequest request, UserInfoDto userInfoDto){
        //获取参数
        String data = request.getParameter("data");
        com.alibaba.fastjson.JSONObject paramMap;
        //获取设备信息
        String mode = request.getParameter("_mode");
        try{
            //判断userInfoDto是否合法
            checkNotNull(userInfoDto, IILEGAL_REQUEST);
            //判断data是否合法
            checkNotNull(data, IILEGAL_REQUEST);
            checkArgument(!data.isEmpty(), IILEGAL_REQUEST);
            //判断是否需要对data进行加密
            if(!"debug".equals(mode)){
                data = AESLocker.decrypt(data);
            }
            //获取登录后的必要信息
            paramMap = JSON.parseObject(data);
            String deviceId = paramMap.getString("deviceId");
            //验证完成,开始查询
            ResultSupport<List<UserInfoDto>> userInfoListByQuery = userInfoService.getUserInfoListByQuery(userInfoDto);
            if(userInfoListByQuery.getModule().size() != 1){        //没匹配到,登录失败~
                return Response.getInstance(false).setReturnMsg("登录失败,请检查用户名密码是否正确");
            }
            UserInfoDto user = userInfoListByQuery.getModule().get(0);
            //登录成功
            TokenCheckUtil.initToken(user.getId().toString(), DEFALUT_AVAILABLE_DAYS, deviceId, request);
            return Response.getInstance(userInfoListByQuery.isSuccess())
                    .addAttribute("data", user);
        }catch (JSONException e){
            return Response.getInstance(false).setReturnMsg("非法参数");
        }catch (Exception e){
            e.printStackTrace();
            return Response.getInstance(false).setReturnMsg(e.getMessage());
        }
    }


    /**
     * 用户注册前验证username
     * 用户修改或注册时nickname的验证
     * */
    @RequestMapping(value = "volatileUser", method = RequestMethod.POST)
    @ResponseBody
    public Response volatileUser(UserInfoDto userInfoDto){
        try{
            //判断userInfoDto是否合法
            checkNotNull(userInfoDto, IILEGAL_REQUEST);
            //验证完成,开始查询
            ResultSupport<List<UserInfoDto>> userInfoListByQuery = userInfoService.getUserInfoListByQuery(userInfoDto);
            Integer flag = userInfoListByQuery.getModule().size();
            if(flag != 0){   //该账号已经被注册
                return Response.getInstance(false)
                        .setReturnMsg("[该用户名或昵称已经被占用]");
            }
            return Response.getInstance(userInfoListByQuery.isSuccess())        //可能查询失败
                    .addAttribute("data", flag);
        }catch (Exception e){
            e.printStackTrace();
            return Response.getInstance(false).setReturnMsg(e.getMessage());
        }
    }

}
