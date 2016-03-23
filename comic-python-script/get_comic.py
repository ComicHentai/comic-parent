# encoding:UTF-8
import json
import os
import sys
import time

import requests
# 首先获取漫画的基本信息
from pyquery import PyQuery as pq
from requests.exceptions import ConnectionError, ReadTimeout

import init_proxy

proxy_list = []
url = "http://lofi.e-hentai.org"
headers = {
    'cache-control': "no-cache",
    'postman-token': "5bd3dab9-6df8-e0b8-a2e7-a714c104c74b",
    'cookie': 'xres=3'
}
index = 0


def init(proxy, page=1, max_page=20, use_proxy=True, dir_name=None):
    global index, proxy_list
    if dir_name is None:
        # 首先创建一个基于当前时间的文件夹
        dir_name = time.strftime("[%Y-%m-%d %H:%M:%S]", time.localtime(int(time.time())))
        dir_name = "ComicHentai" + dir_name
        os.mkdir(dir_name)
    proxies = {
        "http": proxy,
    }
    while page < max_page:
        querystring = {
            "page": page,
            "f_apply": "Search",
            "f_search": "Chinese"
        }
        response = None
        while response is None:
            try:
                if use_proxy:
                    response = requests.request("GET", url, headers=headers, params=querystring, proxies=proxies,
                                                timeout=10)
                else:
                    response = requests.request("GET", url, headers=headers, params=querystring, timeout=10)
                if response.text.count(
                        'Your IP address has been temporarily banned for excessive pageloads which indicates that you are using automated mirroring/harvesting software.') > 0:
                    print("当前代理已被Ban,切换代理")
                    raise ConnectionError("has been blocked")
                print("入侵成功!当前为第" + str(page) + "页")
            except ConnectionError:
                # 如果代理连不上,换一个
                index += 1
                if index == len(proxy_list):
                    index = 0
                    init_proxy.create_proxy()
                    print("重新获取代理成功")
                    with open('proxy.json', 'r') as f:
                        proxy_list = json.loads(f.read(-1))
                print("代理[" + proxies.get('http') + "]不可用,切换至[" + proxy_list[index] + "],当前为第" + str(page) + "页")
                proxies = {
                    "http": proxy_list[index]
                }
                continue
            except ReadTimeout:
                # 如果代理连不上,换一个
                index += 1
                if index == len(proxy_list):
                    index = 0
                    init_proxy.create_proxy()
                    print("重新获取代理成功")
                    with open('proxy.json', 'r') as f:
                        proxy_list = json.loads(f.read(-1))
                print("代理[" + proxies.get('http') + "]超时,切换至[" + proxy_list[index] + "],当前为第" + str(page) + "页")
                proxies = {
                    "http": proxy_list[index]
                }
                continue
        # 获得漫画栏,对漫画栏的每个元素进行迭代 div id = ig
        comic_list = pq(response.text).find('.ig')
        comic_json_list = []
        list_len = 0
        for elements in comic_list:
            comic = read_basic_comic_info(elements)
            # 获取完成基本信息之后,就要遍历漫画的每张图片了
            # imgList = read_basic_comic_info()
            # comic["imgList"] = json.dumps(imgList)
            comic_json_list.append(comic)
            list_len += 1
            print("读取漫画信息中[" + json.dumps(comic) + "]")

        comic_json_list = json.dumps(comic_json_list)
        print("读取了 " + str(list_len) + " 个漫画 , 准备写入")
        if list_len == 0:
            print("读取漫画失败,重新读取")
            continue
        # 最后将当前页的数据写回json
        with open(dir_name + "/" + str(page) + ".json", 'w') as f:
            f.write(comic_json_list)
        page += 1
        print("数据写入成功,等待睡眠2秒后进行下一页查询")
        time.sleep(2)
    return dir_name


# 生成一个Comic对象
def read_basic_comic_info(element):
    # 链接
    comic_link = pq(element).find("a").eq(0).attr("href")
    # 漫画ID
    comic_id = comic_link.split('/')[5]
    # 漫画标题
    comic_title = pq(element).find("a").eq(1).text()
    # 漫画时间
    comic_date = pq(element).find('td .ik').eq(0).next().text().split("by")[0]
    # 漫画分类
    comic_category = pq(element).find('td .ik').eq(1).next().text()
    # 漫画标签
    comic_tag = pq(element).find('td .ik').eq(2).next().text()
    # 漫画评分
    comic_rank = str(pq(element).find('td .ik').eq(3).next().text().count("*"))
    # 漫画封面
    comic_cover = pq(element).find("a").eq(0).children().attr("src")

    dict = {"comicLink": comic_link, "comicId": comic_id, "comicTitle": comic_title, "comicDate": comic_date,
            "comicCategory": comic_category, "comicTag": comic_tag, "comicRank": comic_rank, "comicCover": comic_cover,
            "imgList": []}

    return dict


def read_comic_img_info(comic_link, headers, proxy, use_proxy=True):
    global index, proxy_list
    this_page_link = comic_link
    prev_page_link = ''
    proxy = {
        "http": proxy
    }
    page = 0
    # 当上一页和当前页相同时,说明完结了,退出
    while this_page_link != prev_page_link:
        try:
            if use_proxy:
                response = requests.request("GET", this_page_link, headers=headers, proxies=proxy, timeout=10)
            else:
                response = requests.request("GET", this_page_link, headers=headers, timeout=10)
            print(response.text)
            if page == 0:
                # 目录页 提取第一页的链接
                prev_page_link = this_page_link
                this_page_link = pq(response.text).find('.gi').eq(0).find('a').attr('href')
                page += 1
                print("目录页读取完成,开始进入第一页")
            else:
                # 图片页 提取下一页的链接
                print(response.text)

        except ConnectionError:
            # 如果代理连不上,换一个
            index += 1
            if index == len(proxy_list):
                index = 0
                init_proxy.create_proxy()
                print("重新获取代理成功")
                with open('proxy.json', 'r') as f:
                    proxy_list = json.loads(f.read(-1))
            print("代理[" + proxy.get('http') + "]不可用,切换至[" + proxy_list[index] +"],当前为" + ("目录" if page == 0 else "第") + str(page) + "页")
            proxy = {
                "http": proxy_list[index]
            }
            continue
        except ReadTimeout:
            # 如果代理连不上,换一个
            index += 1
            if index == len(proxy_list):
                index = 0
                init_proxy.create_proxy()
                print("重新获取代理成功")
                with open('proxy.json', 'r') as f:
                    proxy_list = json.loads(f.read(-1))
            print("代理[" + proxy.get('http') + "]超时,切换至[" + proxy_list[index] +"],当前为" + ("目录" if page == 0 else "第") + str(page) + "页")
            proxy = {
                "http": proxy_list[index]
            }
            continue
        print("数据写入成功,等待睡眠2秒后进行下一页查询")
        time.sleep(2)


def start(page=1, max_page=20):
    global proxy_list
    init_proxy.create_proxy()
    print("获取代理成功")
    with open('proxy.json', 'r') as f:
        proxy_list = json.loads(f.read(-1))
    dir_name = init(proxy_list[index], page, max_page)
    print("梳理获取的数据")
    new_list = []
    for i in range(1, 10):
        with open(dir_name + '/' + str(i) + ".json", 'r') as f:
            new_list += json.loads(f.read(-1))

    with open(dir_name + '/total.json', 'w') as f:
        f.write(json.dumps(new_list))
    print ("梳理完成")


# start()
def test():
    global proxy_list

    with open('proxy.json', 'r') as f:
        proxy_list = json.loads(f.read(-1))
    read_comic_img_info('http://lofi.e-hentai.org/g/916326/90c9376790/', headers, proxy_list[index])


reload(sys)
sys.setdefaultencoding('utf8')
test()
