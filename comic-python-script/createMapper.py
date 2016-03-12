# encoding:UTF-8
import os

def initDao(objectName):
    text = '<?xml version="1.0" encoding="UTF-8" standalone="no"?>\n' \
           '<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD iBatis Mapper 3.0 //EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">\n' \
           '<mapper namespace="com.comichentai.dao.{L}Dao">\n' \
           '<select id="select{L}ByIds" resultType="com.comichentai.dao.{L}Dao">\n' \
           '</select> \n' \
           '<select id="select{L}ById" resultType="com.comichentai.dao.{L}Dao">\n' \
           '</select>\n' \
           '<select id="select{L}ByTitle" resultType="com.comichentai.dao.{L}Dao">\n' \
           '</select>\n' \
           '<insert id="insert{L}" parameterType="com.comichentai.dataobject.{L}Do" useGeneratedKeys="true" keyProperty="id"> \n' \
           '</insert>\n' \
           '<update id="update{L}" parameterType="com.comichentai.dataobject.{L}Do">\n' \
           '</update> \n' \
           '<update id="delete{L}ByIds" parameterType="com.comichentai.dataobject.{L}Do">\n' \
           '</update> \n' \
           '</mapper>'
    text = text.replace("{L}", objectName)
    fileName = objectName.lower() + "-mapper.xml"
    with open(fileName, 'w') as f:
        f.write(text)

    return os.getcwd() + '/' + fileName


print(initDao("User"))