#ifndef LINKLIST_H
#define LINKLIST_H
#include<cstring>
#include<iostream>
using namespace std;
struct node{
	node*pre;
	node*next;
	char text[102];
	int linenum;
	int letternum;
	node(){
		pre=NULL;
		next=NULL;
		memset(text,0,sizeof(text));
		linenum=0;
		letternum=0;
	} 
};
class LinkList
{
	private:
		node*head;
		node*rear;
		node*cur;
		int tot_line_num;
		int cur_line_num;
		int tot_letter_num;
	public:
		LinkList();
		~LinkList();
		node*gethead();
		node*getrear();
		node*getcur();
		 int get_tot_num();
		 int get_cur_num();
		 int get_tot_letter_num();
		void getcurline();
		void tofirstline();
		void tolastline();
		void insertline(char*s);
		void deleteline();
		void delete_letter(char s);
		void pre_line();
		void next_line();
		void showfile();
		void showline(int num);
		void replace_line(char*s);
		void replace_string(char*s,char*p);
		void find_string(char*s);
};

#endif
