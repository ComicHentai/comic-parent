# encoding:UTF-8
import cookielib
import os
import urllib
import urllib2

from pyquery import PyQuery as pq

__author__ = 'hope6537'

wget = "wget "
https = "https://"
dir = os.getcwd() + '/hentai/'
urlFile = False
site = "http://lofi.e-hentai.org/"


def init_web(only_url=0, page=1):
    print("====ComicHentai Auto Comic Search Tools====")
    print("init env")
    try:
        os.mkdir(dir)
    except:
        print("env exists? result:" + str(os.path.exists(dir)))

    print("switch mode")
    print("0 -- only url")
    print("other input will download picture")
    only_url = int(raw_input(""))
    if only_url == 0:
        only_url = True
    else:
        only_url = False
    print("then input page , 1 is the start")
    page = int(raw_input(""))
    if page <= 1:
        page = 1
    # cookie
    cj = cookielib.CookieJar()
    opener = urllib2.build_opener(urllib2.HTTPCookieProcessor(cj))
    opener.addheaders = [('cookie', "xres=3")]
    urllib2.install_opener(opener)
    url = "http://lofi.e-hentai.org/?f_search=Chinese&f_apply=Search&page=%d" % page
    data = pq(url=url)(".ig")
    print("----start processing---")
    while len(data):
        print('page ==> ' + str(page) + ' url ==> ' + url)
        parse_data(data, only_url)
        page += 1


# 得到主页列表
def parse_data(data, only_url):
    global urlFile
    print("data is \r\n")
    print(vars(data))
    # 对于列表的每一个元素进行循环
    for i in data:
        # 得到当前本子的连接
        doc = pq(i).find("a").attr("href")[-10:-1] + '/'
        print("doc is " + doc)
        try:
            # 拿到链接
            url_file_name = os.getcwd() + '/hentai/' + pq(i).find("a").attr("href")[-10:-1] + '.txt'
            print(url_file_name)
            urlFile = open(url_file_name, 'w')

            # 拿到文件名(即E绅士随机生成的字符串)
            os.mkdir(dir + doc)
            # 创建新目录
            print("mkdir on " + (dir + doc))
            copy_to_fs(i, doc, only_url)
            final()
        except Exception:
            print("already exist")
            continue


# 将图片下载到文件系统
def copy_to_fs(index, doc, only_url):
    # 拿到当前本子的第一页的连接
    content_link = pq(url=pq(index).find("a").attr("href")).find("a:first").attr("href")
    print("content_link is " + content_link)
    # 前一个页面的连接是无，后一个页面的连接就是现在的链接
    pre_link = ""
    next_link = "this"
    # 如果之前的连接和现在的连接相同，说明爬虫完毕，否则继续下载
    while pre_link != next_link:
        # 下一页的连接就是当前的图片href
        next_link = pq(content_link).find("#sd").find("a").attr("href")
        to_next_link(content_link, doc, only_url)
        # 要进入到下一个页面了，将前一个页面的连接指向内容连接，将内容连接指向下一个连接，并继续抓取
        pre_link = content_link
        content_link = next_link


# content_link 是图片的连接
# doc 是当前的本子的随机码
def to_next_link(content_link, doc, only_url):
    global img_link
    try:
        # 拿到图片连接的源地址
        img_link = pq(content_link).find("#sm").attr("src")
        file_name = (img_link[-8:]).replace("/", "-")
        if os.path.exists(dir + doc + file_name):
            print("already exists")
            return
        print("writing " + (img_link))
        # 写入url文件
        urlFile.write(img_link + ";\n")
        # 写入之后刷新
        urlFile.flush()
        # 是否当前只要连接
        if not only_url:
            print("filename will be " + file_name)
            print("download " + (dir + doc + file_name))
            urllib.urlretrieve(img_link, dir + doc + file_name)
            print("download complete")
    except Exception:
        print("error page" + img_link)


def final():
    urlFile.close()


init_web()
