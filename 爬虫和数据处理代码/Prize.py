import requests
from bs4 import BeautifulSoup
import time
headers = {
    "X-Requested-With": "XMLHttpRequest",
    "User-Agent": "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36"
    "(KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36",
}

file_name="E:\\诺贝尔奖数据\\info.txt"

url="http://awards.gtird.com/nobelprize.aspx?key=&type=&time="
file = open(file_name, 'a+',encoding='utf-8')

def process(url):
    response=requests.get(url)
    response.encoding="utf-8"
    content=response.text
    soup=BeautifulSoup(content,'html.parser')
    list=soup.select(".nobelprize .info li")
    for i in range(0,len(list),4):
        info=list[i+1].string
        born=list[i+2].string
        share=list[i+3].string
        file.write(info+'-'+born+'-'+share+'\n')


def go_run():
    for i in range(1901,2019):
        tmp_url=url+str(i)
        process(tmp_url)
        time.sleep(0.6)

page2_url="http://awards.gtird.com/nobelprize.aspx?&key=&&type=&time="
page2_year=[1963,1972,1973,1974,1975,1977,1978,1979,1980,1981,1986,1988,1990,1993,1994,1995,1996,
            1997,1998,2000,2001,2002,2003,2004,2005,2007,2008,2009,2010,2011,2013,2014,2015,2016,
            2017,2018]

def get_page2():
    for i in page2_year:
        tmp_url=page2_url+str(i)+'&page=2'
        process(tmp_url)
        time.sleep(0.6)

#go_run()
get_page2()
file.close()