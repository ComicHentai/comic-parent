
package com.comichentai.service.impl;

import com.comichentai.convert.impl.DozerMappingConverter;
import com.comichentai.dao.ClassifiedDao;
import com.comichentai.dataobject.BasicDo;
import com.comichentai.dataobject.ClassifiedDo;
import com.comichentai.dto.ClassifiedDto;
import com.comichentai.entity.ResultSupport;
import com.comichentai.enums.IsDeleted;
import com.comichentai.page.PageDto;
import com.comichentai.service.ClassifiedService;
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
@Service(value = "classifiedService")
public class ClassifiedServiceImpl implements ClassifiedService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource(name = "classifiedDao")
    private ClassifiedDao classifiedDao;

    @Resource(name = "mappingConverter")
    private DozerMappingConverter mappingConverter;

    @Override
    public ResultSupport<Integer> addClassified(ClassifiedDto classifiedDto) {
        Integer result;
        Integer id;
        try {
            checkNotNull(classifiedDto, "[添加失败][当前插入数据实体为空]");
            if (classifiedDto.getStatus() == null) {
                classifiedDto.setStatus(0);
            }
            ClassifiedDo classifiedDo = mappingConverter.doMap(classifiedDto, ClassifiedDo.class);
            result = classifiedDao.insertClassified(classifiedDo);
            checkNotNull(classifiedDo.getId(), "[添加失败][数据库没有返回实体ID]");
            id = classifiedDo.getId();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResultSupport.getInstance(e);
        }
        boolean expr = (result == 1);
        return ResultSupport.getInstance(expr, expr ? "[添加成功]" : "[添加失败][与预期结果不符][result==1]", id);
    }

    @Override
    public ResultSupport<Integer> addClassified(String title,String coverTitle) {
        try {
            checkNotNull(title, "[添加失败][当前插入数据字段(title)为空]");
checkNotNull(coverTitle, "[添加失败][当前插入数据字段(coverTitle)为空]");

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResultSupport.getInstance(e);
        }
        return this.addClassified(new ClassifiedDto(title,coverTitle));
    }

    @Override
    public ResultSupport<Integer> modifyClassified(ClassifiedDto classifiedDto) {
        Integer result;
        try {
            checkNotNull(classifiedDto, "[单体更新失败][当前入参实体为空]");
            checkNotNull(classifiedDto.getId(), "[单体更新失败][当前入参实体ID为空,无法更新]");
            result = classifiedDao.updateClassified(mappingConverter.doMap(classifiedDto, ClassifiedDo.class));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResultSupport.getInstance(e);
        }
        boolean expr = (result == 1);
        return ResultSupport.getInstance(expr, expr ? "[单体更新成功]" : "[单体更新失败][与预期结果不符][result==1]", result);
    }

    @Override
    public ResultSupport<Integer> batchModifyClassified(ClassifiedDto classifiedDto, List<Integer> idList) {
        Integer result;
        try {
            checkNotNull(classifiedDto, "[批量更新失败][当前入参实体为空]");
            checkNotNull(idList, "[批量更新失败][当前入参实体ID为空,无法更新]");
            checkArgument(idList.size() > 0, "[批量更新失败][当前入参实体更新的ID集合大小为0]");
            result = classifiedDao.batchUpdateClassified(mappingConverter.doMap(classifiedDto, ClassifiedDo.class), idList);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResultSupport.getInstance(e);
        }
        boolean expr = (result == idList.size());
        return ResultSupport.getInstance(expr, expr ? "[批量更新成功]" : "[批量更新失败][与预期结果不符][result==" + idList.size() + "]", result);
    }

    @Override
    public ResultSupport<Integer> removeClassified(Integer id) {
        Integer result;
        try {
            checkNotNull(id, "[单体删除失败][当前入参实体为空]");
            ClassifiedDo readyToDelete = classifiedDao.selectClassifiedById(id);
            checkNotNull(readyToDelete, "[单体删除失败][ID=" + id + "记录从未存在]");
            checkArgument(readyToDelete.getIsDeleted() != 1, "[单体删除失败][ID=" + id + "记录已经被删除]");
            result = classifiedDao.deleteClassified(id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResultSupport.getInstance(e);
        }
        boolean expr = (result == 1);
        return ResultSupport.getInstance(expr, expr ? "[单体删除成功]" : "[单体删除失败][与预期结果不符][result==1]", result);
    }

    @Override
    public ResultSupport<Integer> batchRemoveClassified(List<Integer> idList) {
        Integer result; //返回结果
        boolean flag = true;  //是否存在无效数据
        String alreadyBeenDeletedIdListString; //打印的ID
        try {
            checkNotNull(idList, "[批量删除失败][当前入参实体为空]");
            checkArgument(idList.size() > 0, "[批量删除失败][当前入参实体更新的ID集合大小为0]");

            List<ClassifiedDo> readyToDelete = classifiedDao.selectClassifiedListByIds(idList);

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
            result = classifiedDao.batchDeleteClassified(idList);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResultSupport.getInstance(e);
        }
        boolean expr = (result == idList.size());
        return ResultSupport.getInstance(expr, expr ? flag ? "[批量删除成功]" : "[批量删除成功][存在不符合条件的数据][idList=" + alreadyBeenDeletedIdListString + "]" : "[批量删除失败][与预期结果不符][result==1]", result);
    }

    @Override
    public ResultSupport<ClassifiedDto> getClassifiedById(Integer id) {
        ClassifiedDto result;
        try {
            checkNotNull(id, "[单体查询失败][当前入参实体为空]");
            ClassifiedDo comicDo = classifiedDao.selectClassifiedById(id);
            result = mappingConverter.doMap(comicDo, ClassifiedDto.class);
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
    public ResultSupport<List<ClassifiedDto>> getClassifiedListByIdList(List<Integer> idList) {
        boolean flag;
        List<ClassifiedDto> result;
        List<ClassifiedDo> disableResultList;
        try {
            checkNotNull(idList, "[批量查询失败][当前入参实体为空]");
            List<ClassifiedDo> list = classifiedDao.selectClassifiedListByIds(idList);
            checkNotNull(list, "[批量查询失败][查询为空]");
            disableResultList = list.parallelStream().filter(o -> o.getId() == null || o.getStatus() == null || o.getIsDeleted() == null).collect(Collectors.toList());
            if (disableResultList == null) {
                disableResultList = Lists.newArrayList();
            }
            flag = disableResultList.size() == 0;
            result = list.parallelStream().filter(o -> o.getId() != null).map(o -> mappingConverter.doMap(o, ClassifiedDto.class)).collect(Collectors.toList());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResultSupport.getInstance(e);
        }
        return ResultSupport.getInstance(flag, flag ? "[批量查询成功]" : "[批量查询成功][存在不符合条件的数据][idList=" + disableResultList.toString() + "]", result);
    }

    @Override
    public ResultSupport<List<ClassifiedDto>> getClassifiedListByQuery(ClassifiedDto query) {
        List<ClassifiedDto> result;
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
            ClassifiedDo doQuery = mappingConverter.doMap(query, ClassifiedDo.class);
            countByQuery = classifiedDao.selectClassifiedCountByQuery(doQuery);
            result = classifiedDao.selectClassifiedListByQuery(doQuery).stream().map(o -> mappingConverter.doMap(o, ClassifiedDto.class)).collect(Collectors.toList());

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResultSupport.getInstance(e);
        }
        boolean expr = countByQuery >= 0 && result != null;
        ResultSupport<List<ClassifiedDto>> instance = ResultSupport.getInstance(expr, expr ? "[条件查询成功]" : "[条件查询失败][查询无数据,请变更查询条件]", result);
        instance.setTotalCount(countByQuery);
        return instance;
    }
}

    