import requests;
import time
import json
import csv

headers = {
    "X-Requested-With": "XMLHttpRequest",
    "User-Agent": "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36"
    "(KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36",
}

def remove_callback(name):
    begin=name.find('call')
    end=name.find('&')
    new_name=name[0:begin]+name[end:len(name)-1]
    return new_name

file_name2="E:\\tag.txt"

tag_url="https://api.bilibili.com/x/tag/archive/tags?callback=jqueryCallback_bili_2807734809640481&aid=667733080&jsonp=jsonp&_=1587703698109"
tag_url=remove_callback(tag_url)

tag_response=requests.get(tag_url,headers).json()
time.sleep(0.6)


tag_file=open(file_name2,'a+')

for i in tag_response['data']:
    tag_file.write(i['tag_name']+'\n')

tag_file.close()
