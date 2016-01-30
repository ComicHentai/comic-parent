package com.comichentai.convert;

/**
 * Created by wuyang.zp on 2015/7/20.
 */
public interface MappingConverter {

    <T> T doMap(Object source, Class<T> destinationClass);

}
