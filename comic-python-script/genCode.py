# encoding:UTF-8
import os

def initDao(objectName):
    text = 'package com.comichentai.dao;\n' \
           'import com.comichentai.annotation.MybatisRepository;\n' \
           'import com.comichentai.dataobject.{L}Do;\n' \
           'import org.apache.ibatis.annotations.Param;\n' \
           'import java.util.List;\n' \
           '@MybatisRepository \n' \
           'public ' \
           'interface ' \
           '{L}Dao \n' \
           '{\n ' \
           '{L}Do select{L}ById(@Param("id") Integer id);\n' \
           'List<{L}Do> select{L}ListByIds(@Param("ids") Integer... idList);\n' \
           'List<{L}Do> select{L}ByTitle(@Param("title") String title);\n' \
           'void insert{L}({L}Do '+ objectName.lower() +'Do);\n' \
           'void update{L}({L}Do '+ objectName.lower() +'Do);\n' \
           'void delete{L}ByIds(@Param("ids") Integer... ids);' \
           '}'
    text = text.replace("{L}", objectName)
    fileName = objectName + "Dao.java"
    with open(fileName, 'w') as f:
        f.write(text)

    return os.getcwd() + '/' + fileName


print(initDao("User"))
