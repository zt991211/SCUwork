#include "LinkList.h"
#include<cstring>
#include<string>
#include<iostream>
using namespace std;
LinkList::LinkList()
{
	head=new node();
	rear=new node();
	head->next=rear;
	rear->pre=head;
	cur=head;
	tot_line_num=0;
	cur_line_num=0;
	tot_letter_num=0;
}

LinkList::~LinkList()
{
	node*temp=head;
	while(temp->next!=NULL){
		temp=temp->next;
		delete temp->pre;
	}
	delete temp;
}
node* LinkList::gethead(){
	return head;
}

node* LinkList::getrear(){
	return rear;
}

node* LinkList::getcur(){
	return cur;
}

int LinkList::get_tot_num(){
	return tot_line_num;
}

int LinkList::get_cur_num(){
	return cur_line_num;
}

int LinkList::get_tot_letter_num(){
	return tot_letter_num;
}

void LinkList::getcurline(){
	if(cur_line_num==0){
		cout<<"the file have no lines currently"<<endl;
		return;
	}
	cout<<"the current line is: "<<endl;
	cout<<cur_line_num<<":: "<<cur->text<<endl;
}

void LinkList::insertline(char*s){
	node*temp=new node();
	temp->linenum=cur_line_num+1;
	temp->letternum=strlen(s);
	strcpy(temp->text,s);
	temp->pre=cur;
	temp->next=cur->next;
	cur->next->pre=temp;
	cur->next=temp;
	cur=temp;
	tot_line_num++;
	cur_line_num++;
	tot_letter_num+=strlen(s);
}

void LinkList::deleteline(){
	if(tot_line_num==0){
		cout<<"wrong operation!"<<endl;
		cout<<"the current line can not be deleted"<<endl;
		return;
	}
	cur->pre->next=cur->next;
	cur->next->pre=cur->pre;
	tot_letter_num-=cur->letternum;
	node*temp=cur;
	cur=cur->pre;
	delete temp;
	temp=cur->next;
	while(temp!=rear){
		temp->linenum--;
		temp=temp->next; 
	}
	cur_line_num--;
	tot_line_num--;
}

void LinkList::delete_letter(char s){
	if(cur_line_num==0||cur==rear){
		cout<<"wrong operation"<<endl;
		cout<<"this line is empty"<<endl;
		return;
	}
	int forward=0;
	int count=0;
	for(int i=0;cur->text[i]!=0;i++){
		if(cur->text[i]==s){
			forward++;
			continue;
		}
		cur->text[i-forward]=cur->text[i];
		count++;
	}
	cur->text[count]=0;
	tot_letter_num-=(cur->letternum-count);
	cur->letternum=count;
	cout<<"the current line is: "<<endl;
	cout<<cur_line_num<<":: "<<cur->text<<endl;
}

void LinkList::pre_line(){
	if(cur_line_num==0){
		cout<<"wrong operation"<<endl;
		cout<<"sorry,this is the head"<<endl;
		return;
	}
	cur=cur->pre;
	cur_line_num--;
	cout<<"the current line is: "<<endl;
	if(cur_line_num==0){
		cout<<"0:: head line(can not be changed)"<<endl;
		return; 
	}
	cout<<cur_line_num<<":: "<<cur->text<<endl;
}

void LinkList::next_line(){
	if(cur_line_num==tot_line_num){
		cout<<"wrong operation"<<endl;
		cout<<"sorry,this is the rear"<<endl;
		return;
	}
	cur=cur->next;
	cur_line_num++;
	cout<<"the current line is: "<<endl;
	cout<<cur_line_num<<":: "<<cur->text<<endl;
}

void LinkList::showfile(){
	if(tot_line_num==0){
		cout<<"this file is empty now"<<endl;
		return;
	}
	node*temp=head->next;
	while(temp!=rear){
		cout<<temp->linenum<<":: "<<temp->text<<endl;
		temp=temp->next;
	}
	cout<<"------------------------"<<endl;
	cout<<"all file aboved"<<endl;
	cout<<"the total line number is: "<<tot_line_num<<endl;
	cout<<"the total letter number is: "<<tot_letter_num<<endl;
}

void LinkList::showline(int num){
	if(num>tot_line_num){
		cout<<"wrong operation"<<endl;
		cout<<"can not reach this line"<<endl;
		return;
	}
	cur=head;
	while(cur->linenum!=num){
		cur=cur->next;
	} 
	cout<<"this line is: "<<endl;
	cout<<num<<":: "<<cur->text<<endl;
}

void LinkList::replace_line(char*s){
	memset(cur->text,0,sizeof(cur->text));
	tot_letter_num-=cur->letternum;
	strcpy(cur->text,s);
	tot_letter_num+=strlen(s);
	cur->letternum=strlen(s);
	cout<<"the current line is: "<<endl;
	cout<<cur->linenum<<":: "<<cur->text<<endl; 
}

void LinkList::replace_string(char*s,char*p){
	string str=cur->text;
	string olds=s;
	string news=p;
	tot_letter_num-=cur->letternum;
	string::size_type pos=0;
	while((pos=str.find(olds,pos))!= string::npos)
	{
		str=str.replace(pos,olds.length(),news);
		if(news.length()>0)
		{
			pos+=news.length();
		}
	}
    memset(cur->text,0,sizeof(cur->text));
	strcpy(cur->text,str.c_str());
	cur->letternum=str.size();
	tot_letter_num+=str.size();
	cout<<"the current line is: "<<endl;
	cout<<cur->linenum<<":: "<<cur->text<<endl;
}

void LinkList::find_string(char*s){
	int len=strlen(s);
	int pos=-1;
	for(int i=0;i<=cur->letternum-len;i++){
		bool flag=false;
		for(int j=0;j<len;j++){
			if(s[j]!=cur->text[i+j]){
				flag=true;
				break;
			}
		}
		if(flag==false){
			pos=i;
			cout<<pos<<' ';
		}
	}
	cout<<endl;
	if(pos==-1){
		cout<<"can not find this string"<<endl;
	}
}

void LinkList::tofirstline(){
	if(tot_line_num==0){
		cout<<"wrong operation"<<endl;
		cout<<"this file have no first line"<<endl;
		return;
	}
	cur=head->next;
	cur_line_num=1;
	cout<<"the first line is: "<<endl;
	cout<<cur->linenum<<":: "<<cur->text<<endl;
}

void LinkList::tolastline(){
	if(tot_line_num==0){
		cout<<"wrong operation"<<endl;
		cout<<"this file have no last line"<<endl;
		return;
	}
	cur=rear->pre;
	cur_line_num=tot_line_num;
	cout<<"the last line is: "<<endl;
	cout<<cur->linenum<<":: "<<cur->text<<endl;
}
