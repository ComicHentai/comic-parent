# encoding:UTF-8
# coding=utf8
import os
import comic_hentai_data_source


def initDir():
    if not os.path.exists("./dao"):
        os.mkdir("./dao")
    if not os.path.exists("./dataobject"):
        os.mkdir("./dataobject")
    if not os.path.exists("./datamapper"):
        os.mkdir("./datamapper")
    if not os.path.exists("./dto"):
        os.mkdir("./dto")
    if not os.path.exists("./impl"):
        os.mkdir("./impl")
    if not os.path.exists("./service"):
        os.mkdir("./service")
    if not os.path.exists("./impl"):
        os.mkdir("./impl")
    if not os.path.exists("./test"):
        os.mkdir("./test")


# 代码自动生成 do-dao-mapper-dto-service-serviceImpl-test一站式服务 我好强啊
# TODO:增加链接数据库读取阶段
# 查询表中所有字段
# select column_name,column_comment,is_nullable from information_schema.columns where table_name = 'TestUser' order by table_schema,table_name
# select distinct table_name from information_schema.columns where table_schema = 'ComicHentai' order by table_schema,table_name
def initDao(objectName):
    text = """
    package com.comichentai.dao;

    import com.comichentai.annotation.MybatisRepository;
    import com.comichentai.dataobject.{ObjectName}Do;
    import org.apache.ibatis.annotations.Param;

    import java.util.List;

    /**
     * 实体数据访问接口
     * Created by hope6537 by Code Generator
     */
    @MybatisRepository
    public interface {ObjectName}Dao {

        int insert{ObjectName}({ObjectName}Do {objectName}Do);

        int update{ObjectName}({ObjectName}Do {objectName}Do);

        int batchUpdate{ObjectName}(@Param("data") {ObjectName}Do {objectName}Do, @Param("idList") List<Integer> idList);

        int delete{ObjectName}(@Param("id") Integer id);

        int batchDelete{ObjectName}(@Param("idList") List<Integer> idList);

        {ObjectName}Do select{ObjectName}ById(@Param("id") Integer id);

        List<{ObjectName}Do> select{ObjectName}ListByIds(@Param("idList") List<Integer> idList);

        List<{ObjectName}Do> select{ObjectName}ListByQuery({ObjectName}Do query);

        int select{ObjectName}CountByQuery({ObjectName}Do query);

    }

    """
    text = text.replace("{ObjectName}", objectName)
    lower = objectName[0].lower() + objectName[1:]
    text = text.replace("{objectName}", lower)
    fileName = "./dao/" + objectName + "Dao.java"
    with open(fileName, 'w') as f:
        f.write(text)

    return os.getcwd() + '/Dao/' + fileName


def initDo(objectName, columns):
    text = """
    package com.comichentai.dataobject;

    /**
     * 实体DO
     * Created by hope6537 by Code Generator
     */
    public class {ObjectName}Do extends BasicDo {
    """
    for c in columns:
        if c[1] == 'varchar' or c[1] == 'text':
            text += """
            /**""" + c[3] + """ */
            private String """ + c[0] + """;
            """
        if c[1] == 'int':
            text += """
            /**""" + c[3] + """ */
            private Integer """ + c[0] + """;
            """
    text += """
    public {ObjectName}Do() {

    }
    """
    params = ""
    body = ""
    for c in columns:
        if c[1] == 'varchar' or c[1] == 'text':
            params += "String " + c[0] + ","
            body += "this." + c[0] + " = " + c[0] + ";\n"
        if c[1] == 'int':
            params += "Integer " + c[0] + ","
            body += "this." + c[0] + " = " + c[0] + ";\n"

    text += """
        public {ObjectName}Do(""" + params[0:-1] + """) {

           """ + body + """

        }
        """
    for c in columns:
        firstCharUpper = c[0][0].upper() + c[0][1:]
        if c[1] == 'varchar' or c[1] == 'text':
            text += """
                public String get""" + firstCharUpper + """() {
                    return """ + c[0] + """;
                }
                public void set""" + firstCharUpper + """(String """ + c[0] + """) {
                    this.""" + c[0] + """ = """ + c[0] + """;
                }
            """
        if c[1] == 'int':
            text += """
                public Integer get""" + firstCharUpper + """() {
                    return """ + c[0] + """;
                }
                public void set""" + firstCharUpper + """(Integer """ + c[0] + """) {
                    this.""" + c[0] + """ = """ + c[0] + """;
                }
            """
    text += """
    }
    """
    text = text.replace("{ObjectName}", objectName)
    lower = objectName[0].lower() + objectName[1:]
    text = text.replace("{objectName}", lower)
    fileName = "./dataobject/" + objectName + "Do.java"
    with open(fileName, 'w') as f:
        f.write(text)

    return os.getcwd() + '/' + fileName


def initDto(objectName, columns):
    text = """
    package com.comichentai.dto;

    /**
     * 实体DTO
     * Created by hope6537 by Code Generator
     */
    public class {ObjectName}Dto extends BasicDto {
    """
    for c in columns:
        if c[1] == 'varchar' or c[1] == 'text':
            text += """
            /**""" + c[3] + """ */
            private String """ + c[0] + """;
            """
        if c[1] == 'int':
            text += """
            /**""" + c[3] + """ */
            private Integer """ + c[0] + """;
            """
    text += """
    public {ObjectName}Dto() {

    }
    """
    params = ""
    body = ""
    for c in columns:
        if c[1] == 'varchar' or c[1] == 'text':
            params += "String " + c[0] + ","
            body += "this." + c[0] + " = " + c[0] + ";\n"
        if c[1] == 'int':
            params += "Integer " + c[0] + ","
            body += "this." + c[0] + " = " + c[0] + ";\n"

    text += """
        public {ObjectName}Dto(""" + params[0:-1] + """) {

           """ + body + """

        }
        """
    for c in columns:
        firstCharUpper = c[0][0].upper() + c[0][1:]
        if c[1] == 'varchar' or c[1] == 'text':
            text += """
                public String get""" + firstCharUpper + """() {
                    return """ + c[0] + """;
                }
                public void set""" + firstCharUpper + """(String """ + c[0] + """) {
                    this.""" + c[0] + """ = """ + c[0] + """;
                }
            """
        if c[1] == 'int':
            text += """
                public Integer get""" + firstCharUpper + """() {
                    return """ + c[0] + """;
                }
                public void set""" + firstCharUpper + """(Integer """ + c[0] + """) {
                    this.""" + c[0] + """ = """ + c[0] + """;
                }
            """
    text += """
    }
    """
    text = text.replace("{ObjectName}", objectName)
    lower = objectName[0].lower() + objectName[1:]
    text = text.replace("{objectName}", lower)
    fileName = "./dto/" + objectName + "Dto.java"
    with open(fileName, 'w') as f:
        f.write(text)

    return os.getcwd() + '/' + fileName


def initMapper(objectName, columns):
    insertColumns = ""
    insertDynamic = ""
    updateColumns = ""
    batchUpdateColumns = ""
    whereColumns = ""
    for c in columns:
        insertColumns += "`" + c[0] + "`,"
        insertDynamic += "#{" + c[0] + "},"
        updateColumns += '<if test="' + c[0] + '!=null and ' + c[0] + '!=\'\'"> `' + c[0] + '` = #{' + c[
            0] + '}, </if>\n\t\t\t'
        batchUpdateColumns += '<if test="data.' + c[0] + '!=null and data.' + c[0] + '!=\'\'"> `' + c[
            0] + '` = #{data.' + c[0] + '}, </if>\n\t\t\t'
        whereColumns += '<if test="' + c[0] + '!=null and ' + c[0] + '!=\'\'"> AND `' + c[0] + '` = #{' + c[
            0] + '} </if>\n\t\t\t'
    text = """<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD iBatis Mapper 3.0 //EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.comichentai.dao.{ObjectName}Dao">
    <!-- 默认模板生成 添加单行记录 -->
    <insert id="insert{ObjectName}"> INSERT INTO `{ObjectName}`
        (""" + insertColumns + """
        <!--<if test="可以为空字段!=null and 可以为空字段!=''"> `可以为空字段`, </if>-->
        `status`, `isDeleted`, `created`, `updated`)
        VALUES
        (""" + insertDynamic + """
        <!--<if test="可以为空字段!=null and 可以为空字段!=''"> #{可以为空字段}, </if>-->
        #{status}, 0, UNIX_TIMESTAMP(), UNIX_TIMESTAMP());
        <selectKey resultType="integer" keyProperty="id">SELECT LAST_INSERT_ID()</selectKey>
    </insert>
    <!-- 默认模板生成 更新单行记录 -->
    <update id="update{ObjectName}"> UPDATE `{ObjectName}`
        <set>
            """ + updateColumns + """
            <if test="status!=null and status!=''"> `status` = #{status}, </if>
            updated = UNIX_TIMESTAMP()
        </set>
        <where> id = #{id} </where>
        LIMIT 1
    </update>
    <!-- 默认模板生成 更新多行记录 -->
    <update id="batchUpdate{ObjectName}"> UPDATE `{ObjectName}`
        <set>
            """ + batchUpdateColumns + """
            <if test="data.status!=null and data.status!=''"> `status` = #{data.status}, </if>
        </set>
        <where>
            <foreach collection="idList" item="id" separator=" or ">id = #{id}</foreach>
        </where>
        LIMIT ${idList.size}
    </update>
    <!-- 默认模板生成 删除单行记录-->
    <update id="delete{ObjectName}"> UPDATE `{ObjectName}` SET isDeleted = 1, updated = UNIX_TIMESTAMP() WHERE id = ${id} LIMIT 1 </update>
    <!-- 默认模板生成 删除多行记录-->
    <update id="batchDelete{ObjectName}"> UPDATE `{ObjectName}` SET isDeleted = 1,updated = UNIX_TIMESTAMP()
        <where>
            <foreach collection="idList" item="id" separator=" or ">id = #{id}</foreach>
        </where>
        LIMIT ${idList.size}
    </update>
    <!-- 默认模板生成 根据ID选取单行记录 -->
    <select id="select{ObjectName}ById" resultType="com.comichentai.dataobject.{ObjectName}Do"> SELECT * FROM `{ObjectName}` WHERE id = ${id} LIMIT 1 </select>
    <!-- 默认模板生成 根据ID集合选取多行记录-->
    <select id="select{ObjectName}ListByIds" resultType="com.comichentai.dataobject.{ObjectName}Do"> SELECT * FROM `{ObjectName}`
        <where>
            id in (
            <foreach collection="idList" item="id" separator=" , ">#{id}</foreach>
            )
        </where>
        LIMIT ${idList.size}
    </select>
    <!-- 默认模板生成 动态SQL语句 通常字段判断是否为空 并增加日期范围 -->
    <sql id="where">
        <where> 1 = 1
            """ + whereColumns + """
            <!--<if test="模糊查询字段!=null and 模糊查询字段!=''"> AND`模糊查询字段` LIKE concat('%',#{模糊查询字段},'%') </if>-->
            <if test="createdAfter!=null and createdAfter!=''"> AND `created` &lt; #{createdAfter} </if>
            <if test="createdBefore!=null and createdBefore!=''">AND `created` &gt; #{createdBefore} </if>
            <if test="updatedAfter!=null and updatedAfter!=''"> AND `updated` &lt; #{updatedAfter} </if>
            <if test="updatedBefore!=null and updatedBefore!=''">AND `updated` &gt; #{updatedBefore} </if>
            <if test="status!=null and status!=''"> AND `status` = #{status} </if>
            <if test="isDeleted!=null and isDeleted!=''"> AND `isDeleted` = #{isDeleted} </if>
        </where>
    </sql>
    <!-- 默认模板生成 根据Query对象查询记录 -->
    <select id="select{ObjectName}ListByQuery" resultType="com.comichentai.dataobject.{ObjectName}Do" parameterType="com.comichentai.dto.{ObjectName}Dto"> SELECT * FROM `{ObjectName}`
        <include refid="where"/>
        ORDER BY `created` DESC
        <if test="limit!=null and limit!=''"> LIMIT
            <if test="offset!=null and offset!=''">#{offset},</if> #{limit} </if>
    </select>
    <!-- 默认模板生成 根据Query对象查询符合条件的个数 -->
    <select id="select{ObjectName}CountByQuery" resultType="Integer" parameterType="com.comichentai.dataobject.{ObjectName}Do"> SELECT count(*) FROM `{ObjectName}`
        <include refid="where"/>
    </select>
</mapper>
    """
    text = text.replace("{ObjectName}", objectName)
    lower = objectName[0].lower() + objectName[1:]
    text = text.replace("{objectName}", lower)
    fileName = "./datamapper/" + objectName.lower() + "-mapper.xml"
    with open(fileName, 'w') as f:
        f.write(text)

    return os.getcwd() + '/' + fileName


def initService(objectName, columns):
    params = ""
    for c in columns:
        if c[1] == 'varchar' or c[1] == 'text':
            params += "String " + c[0] + ","
        if c[1] == 'int':
            params += "Integer " + c[0] + ","
    params = params[0:-1]
    text = """
package com.comichentai.service;

import com.comichentai.dto.{ObjectName}Dto;
import com.comichentai.entity.ResultSupport;

import java.util.List;

/**
 * 实体服务接口
 * Created by hope6537 on 16/1/30.
 */
public interface {ObjectName}Service {

    /**
     * 标准模板生成-向数据库添加单行记录
     *
     * @param {objectName}Dto 数据转换对象
     * @return ResultSupport.getData = 新添加的数据的ID
     */
    ResultSupport<Integer> add{ObjectName}({ObjectName}Dto {objectName}Dto);

    /**
     * 标准模板生成-向数据库添加单行记录 参数集合
     * @param 数据字段集合
     * @return ResultSupport.getData = 更新行数
     */
    ResultSupport<Integer> add{ObjectName}(""" + params + """);

    /**
     * 标准模板生成-向数据库更新单行记录
     *
     * @param {objectName}Dto 数据转换对象
     * @return ResultSupport.getData = 更新行数
     */
    ResultSupport<Integer> modify{ObjectName}({ObjectName}Dto {objectName}Dto);

    /**
     * 标准模板生成-向数据库更新多行记录
     *
     * @param {objectName}Dto 数据转换对象
     * @param idList       要更新的ID集合
     * @return ResultSupport.getData = 更新行数
     */
    ResultSupport<Integer> batchModify{ObjectName}({ObjectName}Dto {objectName}Dto, List<Integer> idList);

    /**
     * 标准模板生成-向数据库删除单行记录
     *
     * @param id 要删除的id
     * @return ResultSupport.getData = 更新行数
     */
    ResultSupport<Integer> remove{ObjectName}(Integer id);

    /**
     * 标准模板生成-向数据库删除多行记录
     *
     * @param idList 要删除的ID集合
     * @return ResultSupport.getData = 更新行数
     */
    ResultSupport<Integer> batchRemove{ObjectName}(List<Integer> idList);

    /**
     * 标准模板生成-根据ID查询单个数据
     *
     * @param id 要查询的id
     * @return ResultSupport.getData = 单条数据
     */
    ResultSupport<{ObjectName}Dto> get{ObjectName}ById(Integer id);

    /**
     * 标准模板生成-根据IDList查询多个数据
     *
     * @param idList 要查询的ID集合
     * @return ResultSupport.getData = 多条符合条件的数据
     */
    ResultSupport<List<{ObjectName}Dto>> get{ObjectName}ListByIdList(List<Integer> idList);

    /**
     * 标准模板生成-根据Query对象查询符合条件的数据
     *
     * @param query 数据查询对象
     * @return ResultSupport.getData = 多条符合条件的数据
     */
    ResultSupport<List<{ObjectName}Dto>> get{ObjectName}ListByQuery({ObjectName}Dto query);
}
    """
    text = text.replace("{ObjectName}", objectName)
    lower = objectName[0].lower() + objectName[1:]
    text = text.replace("{objectName}", lower)
    fileName = "./service/" + objectName + "Service.java"
    with open(fileName, 'w') as f:
        f.write(text)

    return os.getcwd() + '/' + fileName


def initServiceImpl(objectName, columns):
    params = ""
    validation = ''
    nextStep = ""
    for c in columns:
        if c[1] == 'varchar' or c[1] == 'text':
            params += "String " + c[0] + ","
        if c[1] == 'int':
            params += "Integer " + c[0] + ","
        validation += 'checkNotNull(' + c[0] + ', "[添加失败][当前插入数据字段(' + c[0] + ')为空]");\n'
        nextStep += c[0] + ','

    params = params[0:-1]
    nextStep = nextStep[0:-1]

    text = """
package com.comichentai.service.impl;

import com.comichentai.convert.impl.DozerMappingConverter;
import com.comichentai.dao.{ObjectName}Dao;
import com.comichentai.dataobject.BasicDo;
import com.comichentai.dataobject.{ObjectName}Do;
import com.comichentai.dto.{ObjectName}Dto;
import com.comichentai.entity.ResultSupport;
import com.comichentai.enums.IsDeleted;
import com.comichentai.page.PageDto;
import com.comichentai.service.{ObjectName}Service;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * 实体服务实现类
 * Created by hope6537 by Code Generator
 */
@Service(value = "{objectName}Service")
public class {ObjectName}ServiceImpl implements {ObjectName}Service {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource(name = "{objectName}Dao")
    private {ObjectName}Dao {objectName}Dao;

    @Resource(name = "mappingConverter")
    private DozerMappingConverter mappingConverter;

    @Override
    public ResultSupport<Integer> add{ObjectName}({ObjectName}Dto {objectName}Dto) {
        Integer result;
        Integer id;
        try {
            checkNotNull({objectName}Dto, "[添加失败][当前插入数据实体为空]");
            if ({objectName}Dto.getStatus() == null) {
                {objectName}Dto.setStatus(0);
            }
            {ObjectName}Do {objectName}Do = mappingConverter.doMap({objectName}Dto, {ObjectName}Do.class);
            result = {objectName}Dao.insert{ObjectName}({objectName}Do);
            checkNotNull({objectName}Do.getId(), "[添加失败][数据库没有返回实体ID]");
            id = {objectName}Do.getId();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResultSupport.getInstance(e);
        }
        boolean expr = (result == 1);
        return ResultSupport.getInstance(expr, expr ? "[添加成功]" : "[添加失败][与预期结果不符][result==1]", id);
    }

    @Override
    public ResultSupport<Integer> add{ObjectName}(""" + params + """) {
        try {
            """ + validation + """
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResultSupport.getInstance(e);
        }
        return this.add{ObjectName}(new {ObjectName}Dto(""" + nextStep + """));
    }

    @Override
    public ResultSupport<Integer> modify{ObjectName}({ObjectName}Dto {objectName}Dto) {
        Integer result;
        try {
            checkNotNull({objectName}Dto, "[单体更新失败][当前入参实体为空]");
            checkNotNull({objectName}Dto.getId(), "[单体更新失败][当前入参实体ID为空,无法更新]");
            result = {objectName}Dao.update{ObjectName}(mappingConverter.doMap({objectName}Dto, {ObjectName}Do.class));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResultSupport.getInstance(e);
        }
        boolean expr = (result == 1);
        return ResultSupport.getInstance(expr, expr ? "[单体更新成功]" : "[单体更新失败][与预期结果不符][result==1]", result);
    }

    @Override
    public ResultSupport<Integer> batchModify{ObjectName}({ObjectName}Dto {objectName}Dto, List<Integer> idList) {
        Integer result;
        try {
            checkNotNull({objectName}Dto, "[批量更新失败][当前入参实体为空]");
            checkNotNull(idList, "[批量更新失败][当前入参实体ID为空,无法更新]");
            checkArgument(idList.size() > 0, "[批量更新失败][当前入参实体更新的ID集合大小为0]");
            result = {objectName}Dao.batchUpdate{ObjectName}(mappingConverter.doMap({objectName}Dto, {ObjectName}Do.class), idList);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResultSupport.getInstance(e);
        }
        boolean expr = (result == idList.size());
        return ResultSupport.getInstance(expr, expr ? "[批量更新成功]" : "[批量更新失败][与预期结果不符][result==" + idList.size() + "]", result);
    }

    @Override
    public ResultSupport<Integer> remove{ObjectName}(Integer id) {
        Integer result;
        try {
            checkNotNull(id, "[单体删除失败][当前入参实体为空]");
            {ObjectName}Do readyToDelete = {objectName}Dao.select{ObjectName}ById(id);
            checkNotNull(readyToDelete, "[单体删除失败][ID=" + id + "记录从未存在]");
            checkArgument(readyToDelete.getIsDeleted() != 1, "[单体删除失败][ID=" + id + "记录已经被删除]");
            result = {objectName}Dao.delete{ObjectName}(id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResultSupport.getInstance(e);
        }
        boolean expr = (result == 1);
        return ResultSupport.getInstance(expr, expr ? "[单体删除成功]" : "[单体删除失败][与预期结果不符][result==1]", result);
    }

    @Override
    public ResultSupport<Integer> batchRemove{ObjectName}(List<Integer> idList) {
        Integer result; //返回结果
        boolean flag = true;  //是否存在无效数据
        String alreadyBeenDeletedIdListString; //打印的ID
        try {
            checkNotNull(idList, "[批量删除失败][当前入参实体为空]");
            checkArgument(idList.size() > 0, "[批量删除失败][当前入参实体更新的ID集合大小为0]");

            List<{ObjectName}Do> readyToDelete = {objectName}Dao.select{ObjectName}ListByIds(idList);

            checkNotNull(readyToDelete, "[批量删除失败][所有记录从未存在]");
            checkArgument(readyToDelete.size() > 0, "[[批量删除失败][所有记录从未存在]");

            //已经处于删除状态的ID
            List<Integer> alreadyBeenDeleted = readyToDelete.stream().filter(r -> r.getIsDeleted() == 1).map(BasicDo::getId).collect(Collectors.toList());
            if (alreadyBeenDeleted == null) {
                alreadyBeenDeleted = Lists.newArrayList();
            }
            checkArgument(alreadyBeenDeleted.size() != readyToDelete.size(), "[批量删除失败][所有记录均已被删除]");
            if (alreadyBeenDeleted.size() > 0) {
                flag = false;
            }
            alreadyBeenDeletedIdListString = alreadyBeenDeleted.toString();
            //求出还未被删除的
            final List<Integer> finalAlreadyBeenDeleted = alreadyBeenDeleted;
            idList = idList.stream().filter(id -> !finalAlreadyBeenDeleted.contains(id)).collect(Collectors.toList());
            result = {objectName}Dao.batchDelete{ObjectName}(idList);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResultSupport.getInstance(e);
        }
        boolean expr = (result == idList.size());
        return ResultSupport.getInstance(expr, expr ? flag ? "[批量删除成功]" : "[批量删除成功][存在不符合条件的数据][idList=" + alreadyBeenDeletedIdListString + "]" : "[批量删除失败][与预期结果不符][result==1]", result);
    }

    @Override
    public ResultSupport<{ObjectName}Dto> get{ObjectName}ById(Integer id) {
        {ObjectName}Dto result;
        try {
            checkNotNull(id, "[单体查询失败][当前入参实体为空]");
            {ObjectName}Do comicDo = {objectName}Dao.select{ObjectName}ById(id);
            result = mappingConverter.doMap(comicDo, {ObjectName}Dto.class);
            //判断单条数据是否合法
            checkNotNull(result, "[单体查询失败][查询无结果]");
            checkNotNull(result.getId(), "[单体查询失败][查询结果无主键]");
            checkNotNull(result.getStatus(), "[单体查询失败][查询结果无状态]");
            checkNotNull(result.getIsDeleted(), "[单体查询失败][查询结果无是否删除标记]");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResultSupport.getInstance(e);
        }
        return ResultSupport.getInstance(true, "[单体查询成功]", result);
    }

    @Override
    public ResultSupport<List<{ObjectName}Dto>> get{ObjectName}ListByIdList(List<Integer> idList) {
        boolean flag;
        List<{ObjectName}Dto> result;
        List<{ObjectName}Do> disableResultList;
        try {
            checkNotNull(idList, "[批量查询失败][当前入参实体为空]");
            List<{ObjectName}Do> list = {objectName}Dao.select{ObjectName}ListByIds(idList);
            checkNotNull(list, "[批量查询失败][查询为空]");
            disableResultList = list.parallelStream().filter(o -> o.getId() == null || o.getStatus() == null || o.getIsDeleted() == null).collect(Collectors.toList());
            if (disableResultList == null) {
                disableResultList = Lists.newArrayList();
            }
            flag = disableResultList.size() == 0;
            result = list.parallelStream().filter(o -> o.getId() != null).map(o -> mappingConverter.doMap(o, {ObjectName}Dto.class)).collect(Collectors.toList());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResultSupport.getInstance(e);
        }
        return ResultSupport.getInstance(flag, flag ? "[批量查询成功]" : "[批量查询成功][存在不符合条件的数据][idList=" + disableResultList.toString() + "]", result);
    }

    @Override
    public ResultSupport<List<{ObjectName}Dto>> get{ObjectName}ListByQuery({ObjectName}Dto query) {
        List<{ObjectName}Dto> result;
        Integer countByQuery;
        try {
            checkNotNull(query, "[条件查询失败][查询对象为空]");
            if (query.getCurrentPage() == null) {
                query.setCurrentPage(1);
            }
            if (query.getPageSize() == null || query.getPageSize() > 500) {
                query.setPageSize(PageDto.DEFAULT_PAGESIZE);
            }
            if (query.getIsDeleted() == null) {
                query.setIsDeleted(IsDeleted.NO);
            }
            {ObjectName}Do doQuery = mappingConverter.doMap(query, {ObjectName}Do.class);
            countByQuery = {objectName}Dao.select{ObjectName}CountByQuery(doQuery);
            result = {objectName}Dao.select{ObjectName}ListByQuery(doQuery).stream().map(o -> mappingConverter.doMap(o, {ObjectName}Dto.class)).collect(Collectors.toList());

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResultSupport.getInstance(e);
        }
        boolean expr = countByQuery >= 0 && result != null;
        ResultSupport<List<{ObjectName}Dto>> instance = ResultSupport.getInstance(expr, expr ? "[条件查询成功]" : "[条件查询失败][查询无数据,请变更查询条件]", result);
        instance.setTotalCount(countByQuery);
        return instance;
    }
}

    """
    text = text.replace("{ObjectName}", objectName)
    lower = objectName[0].lower() + objectName[1:]
    text = text.replace("{objectName}", lower)
    fileName = "./impl/" + objectName + "ServiceImpl.java"
    with open(fileName, 'w') as f:
        f.write(text)

    return os.getcwd() + '/' + fileName


def initTest(objectName, testColumn):
    param = ''
    modify_param = ''
    delete_param = ''
    i = 0
    for c in testColumn:
        if c[1] == 'varchar' or c[1] == 'text':
            param += ('"test' + str(i) + '"+System.currentTimeMillis(),')
            modify_param += ('"modify' + str(i) + '"+System.currentTimeMillis(),')
            delete_param += ('"wait_delete' + str(i) + '"+System.currentTimeMillis(),')
        if c[1] == 'int':
            param += ("9" + str(i) + ',')
            modify_param += ("8" + str(i) + ',')
            delete_param += ("7" + str(i) + ',')
        i += 1
    param = param[0:-1]
    modify_param = modify_param[0:-1]
    delete_param = delete_param[0:-1]
    text = """
    import com.alibaba.fastjson.JSON;
import com.comichentai.dto.{ObjectName}Dto;
import com.comichentai.entity.ResultSupport;
import com.comichentai.helper.SpringTestHelper;
import com.comichentai.service.{ObjectName}Service;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * 实体服务测试类
 * Created by hope6537 by Code Generator
 */
@ContextConfiguration("classpath:spring/spring-service-impl-context.xml")
public class {ObjectName}ServiceImplTest extends SpringTestHelper {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private {ObjectName}Service {objectName}Service;

    static void pro() throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/META-INF/spring/spring-dubbo-service-impl.xml");
        context.start();
        System.in.read();
    }

    @Before
    public void init() {
        logger.info({objectName}Service.toString());
    }

    @Test
    public void testAdd{ObjectName}() {
        ResultSupport<Integer> integerResultSupport = {objectName}Service.add{ObjectName}(""" + param + """);
        logger.info(JSON.toJSONString(integerResultSupport));
        assertTrue(integerResultSupport.getModule() > 0);
    }

    @Test
    public void testModify{ObjectName}() {
        ResultSupport<Integer> resultSupport = {objectName}Service.add{ObjectName}(""" + param + """);
        Integer id = resultSupport.getModule();
        {ObjectName}Dto dto = new {ObjectName}Dto(""" + modify_param + """);
        dto.setId(id);
        ResultSupport<Integer> modifyResultSupport = {objectName}Service.modify{ObjectName}(dto);
        logger.info(JSON.toJSONString(modifyResultSupport));
        assertTrue(modifyResultSupport.getModule() == 1);
        ResultSupport<Integer> batchModifyResultSupport = {objectName}Service.batchModify{ObjectName}(dto, Lists.newArrayList(1, 2, 3));
        logger.info(JSON.toJSONString(batchModifyResultSupport));
        assertTrue(batchModifyResultSupport.getModule() == 3);
        logger.info(JSON.toJSONString({objectName}Service.get{ObjectName}ListByIdList(Lists.newArrayList(1, 2, 3))));
    }

    @Test
    public void testRemove{ObjectName}() {
        ResultSupport<Integer> resultSupport = {objectName}Service.add{ObjectName}(""" + delete_param + """);
        Integer id = resultSupport.getModule();
        ResultSupport<Integer> modifyResultSupport = {objectName}Service.remove{ObjectName}(id);
        logger.info(JSON.toJSONString(modifyResultSupport));
        assertTrue(modifyResultSupport.getModule() == 1);
        ResultSupport<Integer> batchModifyResultSupport = {objectName}Service.batchRemove{ObjectName}(Lists.newArrayList(1, 2, 3));
        logger.info(JSON.toJSONString(batchModifyResultSupport));
        assertTrue(batchModifyResultSupport.getModule() == 3);
        logger.info(JSON.toJSONString({objectName}Service.get{ObjectName}ListByIdList(Lists.newArrayList(1, 2, 3))));
    }


    @Test
    public void testGet{ObjectName}ById() {
        String comic = JSON.toJSONString({objectName}Service.get{ObjectName}ById(1));
        logger.info(comic);
    }

    @Test
    public void testGet{ObjectName}ListByIdList() {
        String comicList = JSON.toJSONString({objectName}Service.get{ObjectName}ListByIdList(Lists.newArrayList(1, 2, 3, 4)));
        logger.info(comicList);
    }

    @Test
    public void testGet{ObjectName}ListByQuery() {
        {ObjectName}Dto dto = new {ObjectName}Dto();
        dto.setPageSize(2);
        dto.setCurrentPage(1);
        ResultSupport<List<{ObjectName}Dto>> {objectName}ListByQuery = {objectName}Service.get{ObjectName}ListByQuery(dto);
        logger.info(JSON.toJSONString({objectName}ListByQuery));
        dto.setCurrentPage(2);
        {objectName}ListByQuery = {objectName}Service.get{ObjectName}ListByQuery(dto);
        logger.info(JSON.toJSONString({objectName}ListByQuery));
        dto.setCurrentPage(3);
        {objectName}ListByQuery = {objectName}Service.get{ObjectName}ListByQuery(dto);
        logger.info(JSON.toJSONString({objectName}ListByQuery));
    }

    @Test
    public void testDubbo() throws IOException {
        pro();
    }

}

    """
    text = text.replace("{ObjectName}", objectName)
    lower = objectName[0].lower() + objectName[1:]
    text = text.replace("{objectName}", lower)
    fileName = "./test/" + objectName + "ServiceImplTest.java"
    with open(fileName, 'w') as f:
        f.write(text)

    return os.getcwd() + '/' + fileName


# print(initDao("User"))
def initAll(objectName, columns):
    initDir()
    print(initDo(objectName, columns))
    print(initDto(objectName, columns))
    print(initDao(objectName))
    print(initMapper(objectName, columns))
    print(initService(objectName, columns))
    print(initServiceImpl(objectName, columns))
    print(initTest(objectName, columns))


def mysql_connect():
    conn = comic_hentai_data_source.get_conn()
    cursor = conn.cursor()
    # 得到当前数据库中的所有表
    cursor.execute(
            "select distinct table_name from information_schema.columns where table_schema = 'ComicHentai' order by table_schema,table_name")
    tables = cursor.fetchall()
    print(tables)
    for table in tables:
        table = table[0]
        if table == 'TestComic' or table == "TestUser":
            continue
        cursor.execute(
                "select column_name,data_type,is_nullable,column_comment from information_schema.columns where table_name = '" + table + "' order by table_schema,table_name", )
        values = cursor.fetchall()
        columns = []
        for column in values:
            if not (column[0] == 'id' or column[0] == 'created' or column[0] == 'updated' or column[0] == 'isDeleted' or
                            column[0] == 'status'):
                columns.append(column)
        print(columns)
        initAll(table, columns)
    cursor.close()
    conn.close()


mysql_connect()
# initAll("TestUser")
