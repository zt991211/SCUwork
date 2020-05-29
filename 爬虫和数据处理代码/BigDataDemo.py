write_dir="F:\\大二下\\大数据学习\\temperature.txt"
writer=open(write_dir,'w')
def process_file(name):
    file=open(name)
    for line in file:
        index1=line.find('99999')
        date=line[index1+5:index1+13]
        index=line.find('N9')
        value=line[index+3:index+7]
        pos=-1
        for i in range(4):
            if value[i] == '0':
                continue
            else:
                pos=i
                break
        value=int(value[pos:4])
        if line[index+2]=='-':
            writer.write(date+' -'+str(value)+'\n')
        if line[index+2]=='+':
            writer.write(date+' +'+str(value)+'\n')
    file.close()
name1="F:\\大二下\\大数据学习\\1901.txt"
name2="F:\\大二下\\大数据学习\\1902.txt"
process_file(name1)
process_file(name2)
writer.close()
