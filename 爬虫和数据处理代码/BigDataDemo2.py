import csv

#每个月的销量
months={}
#每个类别的销量
category={}
#四种行为的比例
behaviour={}
def addDATA(type,cate,month):
    if type not in behaviour:
        behaviour[type]=1
    else:
        behaviour[type]+=1
    if cate not in category:
        category[cate]=1
    else:
        category[cate]+=1
    if month not in months:
        months[month]=1
    else:
        months[month]+=1

read_file="F:\\大二下\\大数据学习\\user\\raw_user.csv"
wirte_file="F:\\大二下\\大数据学习\\user\\data.txt"
num=0
with open(read_file,'r',encoding='utf-8') as f:
    reader=csv.reader(f)
    for row in reader:
        if num==0:
           num+=1
           continue
        type=int(row[2])
        cate=int(row[4])
        date=row[5].strip().split(' ')
        month=date[0].split('-')[1]
        addDATA(type,cate,month)

file=open(wirte_file,'a',encoding='utf-8')
for item in months:
    file.write(str(item)+":"+str(months[item])+'\n')
file.write("\n")
cnt=0
for k in sorted(category,key=category.__getitem__,reverse=True):
    file.write(str(k)+":"+str(category[k])+"\n")
    cnt+=1
    if cnt==10:
        break
file.write("\n")
for item in behaviour:
    file.write(str(item)+":"+str(behaviour[item])+"\n")
file.close()