//five philosophers
//if you want more philosophers,just change it
#include<iostream>
#include<fstream>
#include<windows.h>
#include<cstdio>
#include<conio.h>
#define thread_sleep(n) Sleep(n*1000)
using namespace std;
HANDLE thread_handle[20];
HANDLE chopsticks[5];//five chopsticks
HANDLE maxSignal;//break the circle to avoid deadlock 
HANDLE oneWrite;//make sure the only output
int thread_number=0;
struct People{
     int id;
     double time;
};
People people[20];
void philosopherThread(void *p){
	int m_id=((People *)(p))->id;
	double m_time=((People *)(p))->time;
	int left_chopstick,right_chopstick;
	left_chopstick=m_id-1;
	right_chopstick=(m_id==5?0:m_id);
	//judge the number
	WaitForSingleObject(maxSignal,INFINITE);
	//fetch chopsticks
    WaitForSingleObject(chopsticks[left_chopstick],INFINITE);
    WaitForSingleObject(chopsticks[right_chopstick],INFINITE);
	//eat
	WaitForSingleObject(oneWrite,INFINITE);
	cout<<"philosopher "<<m_id<<" begins to eat a meal"<<endl;
	ReleaseSemaphore(oneWrite,1,NULL);
	thread_sleep(m_time);
	WaitForSingleObject(oneWrite,INFINITE);
	cout<<"philosopher "<<m_id<<" finishes eating"<<endl;
	ReleaseSemaphore(oneWrite,1,NULL);
    ReleaseSemaphore(chopsticks[right_chopstick],1,NULL);
    ReleaseSemaphore(chopsticks[left_chopstick],1,NULL);
    ReleaseSemaphore(maxSignal,1,NULL);
}
int main(){
	DWORD thread_id;
	//file operation
	ifstream in;
    in.open("Philosopher.txt");
    if(!in){
    	cout<<"open test file error!"<<endl;
    	return 0;
    }
    while(in){
        in>>people[thread_number].id;
        in>>people[thread_number].time;
        thread_number++;
    }
	//init the Semaphore
	for(int i=0;i<5;i++){
		chopsticks[i]=CreateSemaphore(NULL,1,1,NULL);
	}
	maxSignal=CreateSemaphore(NULL,4,4,NULL);
	oneWrite=CreateSemaphore(NULL,1,1,NULL);
    //create thread
    for(int i=0;i<5;i++){
    	thread_handle[i]=CreateThread(NULL,0,(LPTHREAD_START_ROUTINE)(philosopherThread),&people[i],0,&thread_id);
    }
    //wait for all threads
    DWORD wait_for_all=WaitForMultipleObjects(thread_number,thread_handle,true,-1);
    _getch();
	return 0;
}