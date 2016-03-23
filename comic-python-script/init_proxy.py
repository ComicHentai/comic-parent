# encoding:utf-8
import json
import requests
from pyquery import PyQuery as pq


def init(url):
    response = requests.get(url)
    html = pq(response.text)("tbody")
    table = html.children()
    i = 1
    list = []
    while i < len(table):
        ip = str(table[i].text)
        port = get_port(table[i + 1].find("img").attrib['src'].split('v')[-1])
        if port is None:
            i += 9
            continue
        list.append("http://" + str(ip) + ":" + str(port))
        i += 9
    return list


def low_secret_proxy():
    return init('http://proxy.mimvp.com/free.php?proxy=out_tp')


def high_secret_proxy():
    return init('http://proxy.mimvp.com/free.php?proxy=out_hp')


def get_port(img):
    ports = '''
    {
      "MpDgw": "8080",
      "MpTI4": "3128",
      "MpTIz": "8123",
      "MpAO0OO0O": "80",
      "NpDMO0O": "443",
      "MpDgz": "8083",
      "MpDg5": "8089",
      "MpDAw": "9090",
      "OpDg4": "8888",
      "MpQO0OO0O":"81"
    }
    '''
    ports = json.loads(ports)
    return ports.get(img)


def create_proxy():
    low_list = low_secret_proxy()
    high_list = high_secret_proxy()
    json_list = json.dumps(low_list + high_list)
    json_file = open('proxy.json', 'w')
    json_file.write(json_list)
    json_file.close()
