import csv
import re
filename="F:\\3月份榜单信息\\娱乐榜单.csv"
look=0
danmu=0
remark=0
store=0
giveout=0
admire=0
getcoin=0
def getDigit(text):
    first=text.find('：')
    digit=text.strip()[first+1:]
    return int(digit)
with open(filename,'r',encoding='utf-8') as f:
    reader=csv.reader(f)
    for row in reader:
        look+=getDigit(row[1])
        danmu+=getDigit(row[2])
        remark+=getDigit(row[3])
        store+=getDigit(row[4])
        giveout+=getDigit(row[5])
        admire+=getDigit(row[6])
        getcoin+=getDigit(row[7])
txtfile="F:\\3月份榜单信息\\data.txt"
f1=open(txtfile,'a',encoding='utf-8')
f1.write(str(look)+' '+str(danmu)+' '+str(remark)+' '+str(store)+' '+str(giveout)+' '+str(admire)+' '+str(getcoin)+'\n')
f1.close()

