#include<iostream>
#include<cctype>
#include"linklist.h"
#include"TextEditor.h"
using namespace std;
char commend;
char a[100];
int main()
{
    while(1){
    cout<<"if you wanna exit this system,input q"<<endl;
    cout<<"if you wanna use this editor,input any char except q"<<endl;
    cout<<">>>";
	cin>>commend;
	if(commend=='q')break;
	if(commend!='q'){
		TextEditor*zt=new TextEditor();
		while(cin>>a){
		if(!zt->openfile(a)){
			cout<<"this file do not exist"<<endl;
			continue;
		}
		else{
			cout<<"this file open successfully"<<endl;
			cout<<"---------------------------"<<endl;
			break;
		}
	  } 
	  zt->create_meau();
	  while(1){
	    cout<<">>>";    
	    cin>>commend;
	    if(commend=='e')break;
	    if(!isalpha(commend)){
	    	cout<<"illegal input"<<endl;
	    	cout<<"please change input"<<endl;
	    	continue;
		}
	    zt->commend_operation(commend);
	   }
	   delete zt;
	}
}
	return 0;
}
