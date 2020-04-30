#include<iostream>
#include<windows.h>
#include<conio.h>
#include<cstdio>
#include<fstream>
#define thread_sleep(n) Sleep(n*1000)
using namespace std;
//people information
struct People{
	int id;
    char role;
    double delay;
    double time;
};
HANDLE thread_handle[20];//use to create thread
HANDLE WriterSignal;//make sure writer data can be right;
HANDLE RWOperation;//make sure book is used only by one person.
HANDLE readerSignal;//make sure reader data can be right.
People people[20];
int thread_number=0;
int readCount=0;
int waitingCount=0;
//writer thread
void writerThread(void *p){
     DWORD m_delay;
     DWORD m_time;
     int m_id;
     m_delay=((People*)(p))->delay;
     m_time=((People*)(p))->time;
     m_id=((People*)(p))->id;
     thread_sleep(m_delay);
     cout<<"writer "<<m_id<<" sent a write request"<<endl;
     WaitForSingleObject(WriterSignal,INFINITE);
     waitingCount++;
     ReleaseSemaphore(WriterSignal,1,NULL);
     WaitForSingleObject(RWOperation,INFINITE);
     waitingCount--;
     cout<<"writer "<<m_id<<" begins to write book"<<endl;
     thread_sleep(m_time);
     cout<<"writer "<<m_id<<" has finished writing"<<endl;
     ReleaseSemaphore(RWOperation,1,NULL);
}
//reader thread
void readerThread(void *p){
     DWORD m_delay;
     DWORD m_time;
     int m_id;
     m_delay=((People*)(p))->delay;
     m_time=((People*)(p))->time;
     m_id=((People*)(p))->id;
     thread_sleep(m_delay);
     cout<<"reader "<<m_id<<" set a read request"<<endl;
     while(true){
     	if(waitingCount==0)break;
     }
     WaitForSingleObject(readerSignal,INFINITE);
     if(readCount==0){
     	WaitForSingleObject(RWOperation,INFINITE);
     }
     readCount++;
     ReleaseSemaphore(readerSignal,1,NULL);
     cout<<"reader "<<m_id<<" begins to read book"<<endl;
     thread_sleep(m_time);
     cout<<"reader "<<m_id<<" has finished reading"<<endl;
     WaitForSingleObject(readerSignal,INFINITE);
     readCount--;
     if(readCount==0){
     	ReleaseSemaphore(RWOperation,1,NULL);
     }
     ReleaseSemaphore(readerSignal,1,NULL);
}
int main(){
	DWORD thread_id;
	//file operation
    ifstream in;
    in.open("RWtest.txt");
    if(!in){
    	cout<<"open test file error!"<<endl;
    	return 0;
    }
    while(in){
        in>>people[thread_number].id;
        in>>people[thread_number].role;
        in>>people[thread_number].delay;
        in>>people[thread_number].time;
        thread_number++;
    }
    //init the Semaphore
    WriterSignal=CreateSemaphore(NULL,1,1,NULL);
    RWOperation=CreateSemaphore(NULL,1,1,NULL);
    readerSignal=CreateSemaphore(NULL,1,1,NULL);
    //create thread
    for(int i=0;i<thread_number;i++){
    	if(people[i].role=='R'){
    		thread_handle[i]=CreateThread(NULL,0,(LPTHREAD_START_ROUTINE)(readerThread),&people[i],0,&thread_id);
    	}
    	if(people[i].role=='W'){
    		thread_handle[i]=CreateThread(NULL,0,(LPTHREAD_START_ROUTINE)(writerThread),&people[i],0,&thread_id);
    	}
    }
    DWORD wait_for_all=WaitForMultipleObjects(thread_number,thread_handle,true,-1);
    cout<<"all test finished,ok"<<endl;
    _getch();
    return 0;
}