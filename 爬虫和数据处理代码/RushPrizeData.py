import csv
namelist={}
countrylist=[]
id=0
#用于区分国家
def HashMap(name):
    if name in namelist:
        return namelist[name]
    else:
        global id
        namelist[name]=id
        add_country(name,id)
        id+=1
        return id-1
#国家的类
class Country:
    def __init__(self,name,id):
        self.id=id
        self.name=name
        self.info={}
        for i in range(1901,2019):
            self.info[i]=0
        self.tot_prize=0
    def add_prize(self,year):
        self.info[year]+=1
        self.tot_prize+=1
    def combine_data(self):
        for i in range(1902,2019):
            self.info[i]+=self.info[i-1]
#添加一个新的国家
def add_country(name,id):
    tmp=Country(name,id)
    countrylist.append(tmp)

#用于处理数据中某些国家名名称已经更新
def update_country_name(name):
    if '(' not in name:
        return name.strip()
    else:
        index1=name.find('now')
        index2=name.find(')')
        return name[index1+4:index2].strip()

#处理原始数据
file=open("E:\\诺贝尔奖数据\\info.txt")
for line in file:
    grids=line.split('-')
    grids[0]=grids[0].strip()
    grids[1] = grids[1].strip()
    grids[2] = grids[2].strip()
    mid=grids[1].split(',')
    year=int(grids[0][-4:])
    name=update_country_name(mid[len(mid)-1])
    getid=HashMap(name)
    countrylist[getid].add_prize(year)
file.close()

#整理原始数据
for i in range(id):
    countrylist[i].combine_data()

#将数据写入csv文件中
csv_file=open("E:\\Book.csv",'w',encoding='utf-8',newline='')
writer=csv.writer(csv_file)
content=[]
content.append("country")
for i in range(1901,2019):
    content.append(i)
writer.writerow(content)
for i in range(id):
    content.clear()
    content.append(countrylist[i].name)
    for j in range(1901,2019):
        content.append(countrylist[i].info[j])
    writer.writerow(content)
