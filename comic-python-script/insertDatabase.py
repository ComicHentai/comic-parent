# encoding:utf-8
import comic_hentai_data_source
import random

def get_comic_id_with_status():
    find_comic_id_list = "select id from Comic where status = 0"
    conn = comic_hentai_data_source.get_conn()
    cursor = conn.cursor()
    cursor.execute(find_comic_id_list)
    comic_id_list = cursor.fetchall()
    return comic_id_list

def get_classified_id_list():
    find_classified_id_list = "select id from Classified"
    conn = comic_hentai_data_source.get_conn()
    cursor = conn.cursor()
    cursor.execute(find_classified_id_list)
    classified_id_list = cursor.fetchall()
    return classified_id_list

def insert_into_category():
    print "开始获取漫画和用户ID"
    conn = comic_hentai_data_source.get_conn()

    comic_id_list = get_comic_id_with_status()
    classified_id_list = get_classified_id_list()
    for classified_id in classified_id_list:
        print "开始添加分类ID为" + str(classified_id[0]) + "的漫画"
        comic_id_list_child = random.sample(comic_id_list, 20)
        print "分类漫画获取完成,开始添加"
        for comic_id in comic_id_list_child:
            insert_sql = "insert into Category(classifiedId, targetId, targetType, created, updated, isDeleted, status)values ("+ str(classified_id[0]) +", "+ str(comic_id[0]) +", 0, UNIX_TIMESTAMP(), UNIX_TIMESTAMP(), 0, 0)"
            cursor = conn.cursor()
            cursor.execute(insert_sql)
            cursor.close()
    conn.commit()
    conn.close()

insert_into_category()