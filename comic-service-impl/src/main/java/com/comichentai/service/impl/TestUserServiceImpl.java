
package com.comichentai.service.impl;

import com.comichentai.convert.impl.DozerMappingConverter;
import com.comichentai.dao.TestUserDao;
import com.comichentai.dataobject.BasicDo;
import com.comichentai.dataobject.TestUserDo;
import com.comichentai.dto.TestUserDto;
import com.comichentai.entity.ResultSupport;
import com.comichentai.enums.IsDeleted;
import com.comichentai.page.PageDto;
import com.comichentai.service.TestUserService;
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
@Service(value = "testUserService")
public class TestUserServiceImpl implements TestUserService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource(name = "testUserDao")
    private TestUserDao testUserDao;

    @Resource(name = "mappingConverter")
    private DozerMappingConverter mappingConverter;

    @Override
    public ResultSupport<Integer> addTestUser(TestUserDto testUserDto) {
        Integer result;
        Integer id;
        try {
            checkNotNull(testUserDto, "[添加失败][当前插入数据实体为空]");
            if (testUserDto.getStatus() == null) {
                testUserDto.setStatus(0);
            }
            TestUserDo testUserDo = mappingConverter.doMap(testUserDto, TestUserDo.class);
            result = testUserDao.insertTestUser(testUserDo);
            checkNotNull(testUserDo.getId(), "[添加失败][数据库没有返回实体ID]");
            id = testUserDo.getId();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResultSupport.getInstance(e);
        }
        boolean expr = (result == 1);
        return ResultSupport.getInstance(expr, expr ? "[添加成功]" : "[添加失败][与预期结果不符][result==1]", id);
    }

    @Override
    public ResultSupport<Integer> addTestUser(String username, String password) {
        try {
            checkNotNull(username, "[添加失败][当前插入数据字段(username)为空]");
            checkNotNull(password, "[添加失败][当前插入数据字段(password)为空]");

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResultSupport.getInstance(e);
        }
        return this.addTestUser(new TestUserDto(username, password));
    }

    @Override
    public ResultSupport<Integer> modifyTestUser(TestUserDto testUserDto) {
        Integer result;
        try {
            checkNotNull(testUserDto, "[单体更新失败][当前入参实体为空]");
            checkNotNull(testUserDto.getId(), "[单体更新失败][当前入参实体ID为空,无法更新]");
            result = testUserDao.updateTestUser(mappingConverter.doMap(testUserDto, TestUserDo.class));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResultSupport.getInstance(e);
        }
        boolean expr = (result == 1);
        return ResultSupport.getInstance(expr, expr ? "[单体更新成功]" : "[单体更新失败][与预期结果不符][result==1]", result);
    }

    @Override
    public ResultSupport<Integer> batchModifyTestUser(TestUserDto testUserDto, List<Integer> idList) {
        Integer result;
        try {
            checkNotNull(testUserDto, "[批量更新失败][当前入参实体为空]");
            checkNotNull(idList, "[批量更新失败][当前入参实体ID为空,无法更新]");
            checkArgument(idList.size() > 0, "[批量更新失败][当前入参实体更新的ID集合大小为0]");
            result = testUserDao.batchUpdateTestUser(mappingConverter.doMap(testUserDto, TestUserDo.class), idList);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResultSupport.getInstance(e);
        }
        boolean expr = (result == idList.size());
        return ResultSupport.getInstance(expr, expr ? "[批量更新成功]" : "[批量更新失败][与预期结果不符][result==" + idList.size() + "]", result);
    }

    @Override
    public ResultSupport<Integer> removeTestUser(Integer id) {
        Integer result;
        try {
            checkNotNull(id, "[单体删除失败][当前入参实体为空]");
            TestUserDo readyToDelete = testUserDao.selectTestUserById(id);
            checkNotNull(readyToDelete, "[单体删除失败][ID=" + id + "记录从未存在]");
            checkArgument(readyToDelete.getIsDeleted() != 1, "[单体删除失败][ID=" + id + "记录已经被删除]");
            result = testUserDao.deleteTestUser(id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResultSupport.getInstance(e);
        }
        boolean expr = (result == 1);
        return ResultSupport.getInstance(expr, expr ? "[单体删除成功]" : "[单体删除失败][与预期结果不符][result==1]", result);
    }

    @Override
    public ResultSupport<Integer> batchRemoveTestUser(List<Integer> idList) {
        Integer result; //返回结果
        boolean flag = true;  //是否存在无效数据
        String alreadyBeenDeletedIdListString; //打印的ID
        try {
            checkNotNull(idList, "[批量删除失败][当前入参实体为空]");
            checkArgument(idList.size() > 0, "[批量删除失败][当前入参实体更新的ID集合大小为0]");

            List<TestUserDo> readyToDelete = testUserDao.selectTestUserListByIds(idList);

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
            result = testUserDao.batchDeleteTestUser(idList);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResultSupport.getInstance(e);
        }
        boolean expr = (result == idList.size());
        return ResultSupport.getInstance(expr, expr ? flag ? "[批量删除成功]" : "[批量删除成功][存在不符合条件的数据][idList=" + alreadyBeenDeletedIdListString + "]" : "[批量删除失败][与预期结果不符][result==1]", result);
    }

    @Override
    public ResultSupport<TestUserDto> getTestUserById(Integer id) {
        TestUserDto result;
        try {
            checkNotNull(id, "[单体查询失败][当前入参实体为空]");
            TestUserDo comicDo = testUserDao.selectTestUserById(id);
            result = mappingConverter.doMap(comicDo, TestUserDto.class);
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
    public ResultSupport<List<TestUserDto>> getTestUserListByIdList(List<Integer> idList) {
        boolean flag;
        List<TestUserDto> result;
        List<TestUserDo> disableResultList;
        try {
            checkNotNull(idList, "[批量查询失败][当前入参实体为空]");
            List<TestUserDo> list = testUserDao.selectTestUserListByIds(idList);
            checkNotNull(list, "[批量查询失败][查询为空]");
            disableResultList = list.parallelStream().filter(o -> o.getId() == null || o.getStatus() == null || o.getIsDeleted() == null).collect(Collectors.toList());
            if (disableResultList == null) {
                disableResultList = Lists.newArrayList();
            }
            flag = disableResultList.size() == 0;
            result = list.parallelStream().filter(o -> o.getId() != null).map(o -> mappingConverter.doMap(o, TestUserDto.class)).collect(Collectors.toList());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResultSupport.getInstance(e);
        }
        return ResultSupport.getInstance(flag, flag ? "[批量查询成功]" : "[批量查询成功][存在不符合条件的数据][idList=" + disableResultList.toString() + "]", result);
    }

    @Override
    public ResultSupport<List<TestUserDto>> getTestUserListByQuery(TestUserDto query) {
        List<TestUserDto> result;
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
            TestUserDo doQuery = mappingConverter.doMap(query, TestUserDo.class);
            countByQuery = testUserDao.selectTestUserCountByQuery(doQuery);
            result = testUserDao.selectTestUserListByQuery(doQuery).stream().map(o -> mappingConverter.doMap(o, TestUserDto.class)).collect(Collectors.toList());

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResultSupport.getInstance(e);
        }
        boolean expr = countByQuery >= 0 && result != null;
        ResultSupport<List<TestUserDto>> instance = ResultSupport.getInstance(expr, expr ? "[条件查询成功]" : "[条件查询失败][查询无数据,请变更查询条件]", result);
        instance.setTotalCount(countByQuery);
        return instance;
    }
}

    