package com.comichentai.monitor;

import com.alibaba.fastjson.JSON;
import com.comichentai.annotation.WatchedMethod;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.util.UUID;

/**
 * 方法监控切面
 * Created by hope6537 on 16/4/21.
 */
@Aspect
public class MethodMonitor {

    private final Logger logger = LoggerFactory.getLogger("monitor");

    private static final String businessExecution = "execution(* com.comichentai.business.*.*(..))";
    private static final String serviceExecution = "execution(* com.comichentai.service.*.*(..))";

    public MethodMonitor() {
        this.scanAnnotations();
    }

    /**
     * 搜索注解
     */
    public void scanAnnotations() {
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(true);
        scanner.addIncludeFilter(new AnnotationTypeFilter(WatchedMethod.class));
        for (BeanDefinition bd : scanner.findCandidateComponents("com.comichentai")) {
            //System.out.println(bd.getBeanClassName());
        }
    }

    @Pointcut(MethodMonitor.serviceExecution)
    private void basicServices() {
    }

    @Pointcut(MethodMonitor.businessExecution)
    private void basicBusiness() {
    }

    @Pointcut("@annotation(com.comichentai.annotation.WatchedMethod)")
    private void annotationMethods() {
    }

    @Around("basicServices() || basicBusiness() || annotationMethods()")
    public Object intercept(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long localStart = System.nanoTime();
        String executionId = UUID.randomUUID().toString();
        Signature signature = proceedingJoinPoint.getSignature();
        Object[] methodArgs;
        if (signature instanceof MethodSignature) {
            methodArgs = proceedingJoinPoint.getArgs();
            String parameterString = JSON.toJSONString(methodArgs);
            this.logMethodDetail(executionId, signature.toLongString());
            this.logMethodInParameters(executionId, parameterString);
        } else {
            throw new Exception("[切入点不是方法切入点]");
        }
        long nanoTimeStart = System.nanoTime();
        long nanoEndTime = 0;
        try {
            Object returnValue = proceedingJoinPoint.proceed(methodArgs);
            nanoEndTime = System.nanoTime();
            this.logMethodExecutionMillisecond(executionId, nanoTimeToMillisecond(nanoEndTime - nanoTimeStart));
            String returnValueString = JSON.toJSONString(returnValue);
            this.logMethodReturnValues(executionId, returnValueString);
            return returnValue;
        } catch (Exception e) {
            nanoEndTime = System.nanoTime();
            this.logMethodExecutionMillisecond(executionId, nanoTimeToMillisecond(nanoEndTime - nanoTimeStart));
            this.logMethodExcetionMessage(executionId, JSON.toJSONString(e));
            throw e;
        } finally {
            long localEnd = System.nanoTime();
            this.logMethodMonitorDumpMillisecond(executionId, nanoTimeToMillisecond(localEnd - localStart - (nanoEndTime - nanoTimeStart)));
            logger.debug("完成监控");
        }
    }

    public double nanoTimeToMillisecond(long expr) {
        return expr / 1000000.0;
    }

    public void logMethodDetail(String executionId, String detail) {
        logger.info("[监控][方法执行ID:" + executionId + "][方法概览]" + detail);
    }

    public void logMethodInParameters(String executionId, String params) {
        logger.info("[监控][方法执行ID:" + executionId + "][传入参数]" + params);
    }

    public void logMethodReturnValues(String executionId, String returnValues) {
        logger.info("[监控][方法执行ID:" + executionId + "][返回数据]" + returnValues);
    }

    public void logMethodExcetionMessage(String executionId, String exceptionMsg) {
        logger.info("[监控][方法执行ID:" + executionId + "][异常信息]" + exceptionMsg);
    }

    public void logMethodExecutionMillisecond(String executionId, double millisecond) {
        logger.info("[监控][方法执行ID:" + executionId + "][执行毫秒]" + millisecond + "ms");
    }

    public void logMethodMonitorDumpMillisecond(String executionId, double millisecond) {
        logger.info("[监控][方法执行ID:" + executionId + "][监控开销]" + millisecond + "ms");
    }
}
