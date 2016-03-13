package com.comichentai.service;

import com.comichentai.dto.UserInfoDto;
import com.comichentai.entity.ResultSupport;

import java.util.List;

/**
 * 实体服务接口
 * Created by hope6537 on 16/1/30.
 */
public interface UserInfoService {

    /**
     * 标准模板生成-向数据库添加单行记录
     *
     * @param userInfoDto 数据转换对象
     * @return ResultSupport.getData = 新添加的数据的ID
     */
    ResultSupport<Integer> addUserInfo(UserInfoDto userInfoDto);

    /**
     * 标准模板生成-向数据库添加单行记录 参数集合
     *
     * @param 数据字段集合
     * @return ResultSupport.getData = 更新行数
     */
    ResultSupport<Integer> addUserInfo(String username, String password, String nickname, String sexy, String email);

    /**
     * 标准模板生成-向数据库更新单行记录
     *
     * @param userInfoDto 数据转换对象
     * @return ResultSupport.getData = 更新行数
     */
    ResultSupport<Integer> modifyUserInfo(UserInfoDto userInfoDto);

    /**
     * 标准模板生成-向数据库更新多行记录
     *
     * @param userInfoDto 数据转换对象
     * @param idList      要更新的ID集合
     * @return ResultSupport.getData = 更新行数
     */
    ResultSupport<Integer> batchModifyUserInfo(UserInfoDto userInfoDto, List<Integer> idList);

    /**
     * 标准模板生成-向数据库删除单行记录
     *
     * @param id 要删除的id
     * @return ResultSupport.getData = 更新行数
     */
    ResultSupport<Integer> removeUserInfo(Integer id);

    /**
     * 标准模板生成-向数据库删除多行记录
     *
     * @param idList 要删除的ID集合
     * @return ResultSupport.getData = 更新行数
     */
    ResultSupport<Integer> batchRemoveUserInfo(List<Integer> idList);

    /**
     * 标准模板生成-根据ID查询单个数据
     *
     * @param id 要查询的id
     * @return ResultSupport.getData = 单条数据
     */
    ResultSupport<UserInfoDto> getUserInfoById(Integer id);

    /**
     * 标准模板生成-根据IDList查询多个数据
     *
     * @param idList 要查询的ID集合
     * @return ResultSupport.getData = 多条符合条件的数据
     */
    ResultSupport<List<UserInfoDto>> getUserInfoListByIdList(List<Integer> idList);

    /**
     * 标准模板生成-根据Query对象查询符合条件的数据
     *
     * @param query 数据查询对象
     * @return ResultSupport.getData = 多条符合条件的数据
     */
    ResultSupport<List<UserInfoDto>> getUserInfoListByQuery(UserInfoDto query);
}
    