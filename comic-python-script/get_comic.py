# encoding:UTF-8
import json
import os
import sys
import time

import requests
# 首先获取漫画的基本信息
from pyquery import PyQuery as pq
from requests.exceptions import ConnectionError, ReadTimeout, TooManyRedirects

import init_proxy

proxy_list = []
url = "http://lofi.e-hentai.org"
headers = {
    'cache-control': "no-cache",
    'postman-token': "5bd3dab9-6df8-e0b8-a2e7-a714c104c74b",
    'cookie': 'xres=3'
}
index = 0


def now():
    return time.strftime("[%Y-%m-%d %H:%M:%S]", time.localtime(int(time.time())))


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
                if response.text.count('squid') > 0 or response.text.count("http://warning.or.kr") > 0:
                    print(now() + "当前代理出现异常,切换代理")
                    raise ConnectionError("has been blocked")
                if response.status_code != 200:
                    print(now() + "非正常返回结果,代码[" + str(response.status_code) + "],切换代理")
                    raise ConnectionError("has been blocked")
                if response.text.count(
                        'Your IP address has been temporarily banned for excessive pageloads which indicates that you are using automated mirroring/harvesting software.') > 0:
                    print(now() + "当前代理已被Ban,切换代理")
                    raise ConnectionError("has been blocked")
                print(now() + "入侵成功!当前为第" + str(page) + "页")
            except ConnectionError:
                # 如果代理连不上,换一个
                index += 1
                if index == len(proxy_list):
                    index = 0
                    init_proxy.create_proxy()
                    print(now() + "重新获取代理成功")
                    with open('proxy.json', 'r') as f:
                        proxy_list = json.loads(f.read(-1))
                print(
                    now() + "代理[" + proxies.get('http') + "]不可用,切换至[" + proxy_list[index] + "],当前为第" + str(page) + "页")
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
                    print(now() + "重新获取代理成功")
                    with open('proxy.json', 'r') as f:
                        proxy_list = json.loads(f.read(-1))
                print(now() + "代理[" + proxies.get('http') + "]超时,切换至[" + proxy_list[index] + "],当前为第" + str(page) + "页")
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
            print(now() + "读取漫画信息中[" + json.dumps(comic) + "]")

        comic_json_list = json.dumps(comic_json_list)
        print(now() + "读取了 " + str(list_len) + " 个漫画 , 准备写入")
        if list_len == 0:
            print(now() + "读取漫画失败,重新读取")
            continue
        # 最后将当前页的数据写回json
        with open(dir_name + "/" + str(page) + ".json", 'w') as f:
            f.write(comic_json_list)
        page += 1
        print(now() + "数据写入成功,等待睡眠2秒后进行下一页查询")
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
    if not os.path.exists('ComicData') or not os.path.isdir('ComicData'):
        os.mkdir('ComicData')
    global index, proxy_list
    comic_id = comic_link.split('/')[5]
    if os.path.exists('ComicData/' + comic_id + ".json"):
        print(now() + "漫画[" + comic_id + "]的数据已存在")
        return
    print(now() + "正在读取漫画[" + comic_id + "]的数据")
    this_page_link = comic_link
    prev_page_link = ''
    proxy = {
        "http": proxy
    }
    page = 0
    img_list = []
    total_page = 999
    # 当上一页和当前页相同时,说明完结了,退出
    while this_page_link != prev_page_link:
        if this_page_link is None:
            break
        try:
            if use_proxy:
                response = requests.request("GET", this_page_link, headers=headers, proxies=proxy, timeout=10)
            else:
                response = requests.request("GET", this_page_link, headers=headers, timeout=10)
            # 代理出了问题
            if response.text.count('squid') or response.text.count("http://warning.or.kr") > 0:
                print(now() + "当前代理出现异常,切换代理")
                raise ConnectionError("has been blocked")
            if response.status_code != 200:
                print(now() + "非正常返回结果,代码[" + str(response.status_code) + "],切换代理")
                raise ConnectionError("has been blocked")
            # print(now()+response.text)
            if page == 0:
                # 目录页 提取第一页的链接
                tmp = pq(response.text).find('.gi').eq(0).find('a').attr('href')
                if tmp is None:
                    print(now() + "读取数据错误,重新读取,切换代理")
                    raise ConnectionError("has been blocked")
                if tmp.count("590.gif") > 0:
                    print(now() + "当前代理已被Ban,重新读取,切换代理")
                    raise ConnectionError("has been blocked")
                prev_page_link = this_page_link
                this_page_link = tmp
                page += 1
                print(now() + "目录页读取完成,开始进入第一页")
            else:
                # 图片页 提取下一页的链接
                img_link = pq(response.text)("#sm").attr('src')
                if img_link is None:
                    print(now() + "读取数据错误,重新读取,切换代理")
                    raise ConnectionError("has been blocked")
                if img_link.count("590.gif") > 0:
                    print(now() + "当前代理已被Ban,重新读取,切换代理")
                    raise ConnectionError("has been blocked")
                print(now() + "第" + str(page) + "页数据为[" + img_link + "]")
                img_list.append(img_link)
                td = pq(response.text)('#ia').children().eq(0).children().children()
                total_page = int(td.eq(1).text().split('/')[1])
                tmp = td.eq(2).children().attr('href')
                prev_page_link = this_page_link
                this_page_link = tmp
                print(now() + "第" + str(page) + "页读取完成,总共有" + str(total_page) + "页")
                if total_page == page:
                    print(now() + "当前漫画已读取完毕")
                    break
                if this_page_link is None:
                    print(now() + "读取下一页出现错误")
                    raise ConnectionError("has been blocked")
                print(now() + "下一页为[" + this_page_link + "]")
                page += 1
        except ConnectionError:
            # 如果代理连不上,换一个
            index += 1
            if index == len(proxy_list):
                index = 0
                init_proxy.create_proxy()
                print(now() + "重新获取代理成功")
                with open('proxy.json', 'r') as f:
                    proxy_list = json.loads(f.read(-1))
            print(now() + "代理[" + proxy.get('http') + "]不可用,切换至[" + proxy_list[index] +
                  "],当前为" + ("目录" if page == 0 else "第" + str(page)) + "页")
            proxy = {
                "http": proxy_list[index]
            }
            continue
        except TooManyRedirects:
            # 如果代理连不上,换一个
            index += 1
            if index == len(proxy_list):
                index = 0
                init_proxy.create_proxy()
                print(now() + "重新获取代理成功")
                with open('proxy.json', 'r') as f:
                    proxy_list = json.loads(f.read(-1))
            print(now() + "代理[" + proxy.get('http') + "]链接不可用,切换至[" + proxy_list[index] +
                  "],当前为" + ("目录" if page == 0 else "第" + str(page)) + "页")
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
                print(now() + "重新获取代理成功")
                with open('proxy.json', 'r') as f:
                    proxy_list = json.loads(f.read(-1))
            print(now() + "代理[" + proxy.get('http') + "]超时,切换至[" + proxy_list[index] +
                  "],当前为" + ("目录" if page == 0 else "第" + str(page)) + "页")
            proxy = {
                "http": proxy_list[index]
            }
            continue
        print(now() + "数据查询成功,等待睡眠2秒后进行下一页查询")
        time.sleep(2)
        if this_page_link == prev_page_link and total_page != page:
            print(now() + "漫画[" + comic_id + "]写入出现问题,页数不符")
    print(now() + "当前漫画所有页读取完成,进行写入")
    json_list = json.dumps(img_list)
    with open("ComicData/" + comic_id + ".json", 'w') as f:
        f.write(json_list)
    print(now() + "漫画[" + comic_id + "]写入完成")


def start(page=1, max_page=20):
    global proxy_list
    init_proxy.create_proxy()
    print(now() + "获取代理成功")
    with open('proxy.json', 'r') as f:
        proxy_list = json.loads(f.read(-1))

    dir_name = init(proxy_list[index], page, max_page)
    print(now() + "梳理获取的数据")
    new_list = []
    for i in range(1, max_page - 1):
        with open(dir_name + '/' + str(i) + ".json", 'r') as f:
            new_list += json.loads(f.read(-1))

    with open(dir_name + '/total.json', 'w') as f:
        f.write(json.dumps(new_list))
    print(now() + "梳理完成")

    # 梳理完成后开始读取图片
    data = ''
    with open(dir_name + '/total.json', 'r') as f:
        for line in f.readlines():
            data += line.strip()
        data = json.loads(data)
        for comic in data:
            read_comic_img_info(comic['comicLink'], headers, proxy_list[index])


def after(total_json_file):
    global proxy_list
    init_proxy.create_proxy()
    print(now() + "获取代理成功")
    with open('proxy.json', 'r') as f:
        proxy_list = json.loads(f.read(-1))
    data = ''
    with open(total_json_file, 'r') as f:
        for line in f.readlines():
            data += line.strip()
        data = json.loads(data)
        for comic in data:
            read_comic_img_info(comic['comicLink'], headers, proxy_list[index])


reload(sys)
sys.setdefaultencoding('utf8')
