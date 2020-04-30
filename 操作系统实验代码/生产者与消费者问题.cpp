#include<iostream>
#include<windows.h>
#include<deque>
#include<fstream>
#include<cstdio>
#include<conio.h>
using namespace std;
struct People{
	int id;
    char role;
};
People people[20];
HANDLE thread_handle[20];//use to create thread
int thread_number=0;
HANDLE full;
HANDLE empty;
HANDLE PCmutex;//make sure one operation anytime
deque<int>depository;
void producerThread(void *p){
     int m_id;
     m_id=((People*)(p))->id;
     WaitForSingleObject(empty,INFINITE);
     WaitForSingleObject(PCmutex,INFINITE);
     printf("producer %d places a product\n",m_id);
     depository.push_back(1);
     ReleaseMutex(PCmutex);
     ReleaseSemaphore(full,1,NULL);
}
void consumerThread(void *p){
	 int m_id;
     m_id=((People*)(p))->id;
     WaitForSingleObject(full,INFINITE);
     WaitForSingleObject(PCmutex,INFINITE);
     printf("consumer %d has consumed a product\n",m_id);
     depository.pop_front();
     ReleaseMutex(PCmutex);
     ReleaseSemaphore(empty,1,NULL);
}
int main(){
    DWORD thread_id;
	//file operation
    ifstream in;
    in.open("PCtest.txt");
    if(!in){
    	cout<<"open test file error!"<<endl;
    	return 0;
    }
    while(in){
        in>>people[thread_number].id;
        in>>people[thread_number].role;
        thread_number++;
    }
    //init the mutex and semaphore
    full=CreateSemaphore(NULL,0,10,NULL);
    empty=CreateSemaphore(NULL,10,10,NULL);
    PCmutex=CreateMutex(NULL, false, NULL);
    //create thread
    for(int i=0;i<thread_number;i++){
    	if(people[i].role=='P'){
           thread_handle[i]=CreateThread(NULL,0,(LPTHREAD_START_ROUTINE)(producerThread),&people[i],0,&thread_id);
    	}
    	if(people[i].role=='C'){
           thread_handle[i]=CreateThread(NULL,0,(LPTHREAD_START_ROUTINE)(consumerThread),&people[i],0,&thread_id);
    	}
    }
    DWORD wait_for_all=WaitForMultipleObjects(thread_number,thread_handle,true,-1);
    _getch();
	return 0;
}