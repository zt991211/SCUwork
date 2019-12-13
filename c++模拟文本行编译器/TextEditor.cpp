#include "TextEditor.h"
#include<iostream>
#include<fstream>
#include<cstring>
#include<cstdio> 
using namespace std;
char text1[105];
char rs[105];
TextEditor::TextEditor()
{
	list=new LinkList();
	std::cout<<"Welcome to my_zt line editor"<<endl;
	std::cout<<"Please input your infile name"<<endl;
	std::cout<<">>>";
}

TextEditor::~TextEditor()
{
	delete list;
}

bool TextEditor::openfile(char*s){
	ifstream in(s);
	if(in.is_open()){
		while(!in.eof()){
			in.getline(text1,100);
			list->insertline(text1);
		}
		if(list->getcur()->text[0]==0&&list->get_tot_num()==list->get_cur_num()){
			list->deleteline();
		}
		in.close();
		return true;
	}
	else return false;
}

void TextEditor::getfilename(){
	std::cout<<filename<<endl;
}

void TextEditor::create_meau(){
	  std::cout<<"all operations!!!"<<endl;
	  std::cout<<"i(insert),d(delete),s(show),b(firstline),l(lastline),h(help),a(sysinfo)"<<endl;
	  std::cout<<"p(preline),R(replace),n(nextline),w(write),f(find),e(exit),c(curline)"<<endl;
	  std::cout<<"please input your commend"<<endl;
}

void TextEditor::commend_operation(char s){
	if(s=='h'){
	  std::cout<<"all operations!!!"<<endl;
	  std::cout<<"i(insert),d(delete),s(show),b(firstline),l(lastline),h(help),a(sysinfo)"<<endl;
	  std::cout<<"p(preline),R(replace),n(nextline),w(write),f(find),e(exit),c(curline)"<<endl;
	}
	if(s=='i'){
		std::cout<<"please input your line content"<<endl;
		std::cout<<">>>";
		getchar();
		cin.getline(text1,100);
		list->insertline(text1);
	}
	if(s=='d'){
		int input;
		std::cout<<"delete this line or some letter(1 or 2)"<<endl;
		std::cout<<">>>";
		std::cin>>input;
		if(input==1){
			list->deleteline();
		}
		if(input==2){
			char x;
			std::cout<<"you want to delete which letter?"<<endl;
			std::cout<<">>>";
			std::cin>>x;
			list->delete_letter(x);
		}
		if(input<1||input>2){
			std::cout<<"wrong operation"<<endl;
		}
	}
	if(s=='s'){
		int input;
		std::cout<<"show the whole file or a line(1 or 2)"<<endl;
		std::cout<<">>>";
		std::cin>>input;
		if(input==1){
			list->showfile();
		}
		if(input==2){
			int x;
			std::cout<<"you want to show which line?"<<endl;
			std::cout<<">>>";
			std::cin>>x;
			list->showline(x);
		}
		if(input<1||input>2){
			std::cout<<"wrong operation"<<endl;
		}
	}
	if(s=='a'){
		cout<<"system-name: "<<"my_zt line editor"<<endl;
		cout<<"system-developer: "<<"zhangtong"<<endl;
		cout<<"system-version: "<<"1.0"<<endl;
		cout<<"system-belong: "<<"SCU"<<endl;
		cout<<"thanks for using it"<<endl;
	}
	if(s=='c'){
		list->getcurline();
	}
	if(s=='b'){
		list->tofirstline();
	}
	if(s=='l'){
		list->tolastline();
	}
	if(s=='p'){
		list->pre_line();
	}
	if(s=='n'){
		list->next_line();
	}
	if(s=='R'){
		int input;
		std::cout<<"replace the whole line or a string(1 or 2)"<<endl;
		std::cout<<">>>";
		std::cin>>input;
		if(input==1){
			std::cout<<"the replace content is: "<<endl;
			std::cout<<">>>";
			getchar();
			cin.getline(text1,100);
			list->replace_line(text1);
		}
		if(input==2){
			std::cout<<"you want to replace which string to which string?"<<endl;
			std::cout<<"from:: ";
			std::cin>>text1;
			std::cout<<"to:: ";
			std::cin>>rs;
			list->replace_string(text1,rs);
		}
		if(input<1||input>2){
			std::cout<<"wrong operation"<<endl;
		}
	}
	if(s=='f'){
		std::cout<<"you want to find which string?"<<endl;
		std::cout<<">>>";
		std::cin>>text1;
		list->find_string(text1);
	}
	if(s=='w'){
		std::cout<<"please input your output filename"<<endl;
		std::cout<<">>>";
		std::cin>>text1;
		ofstream out(text1);
		if(out.is_open()){
		   node*temp=list->gethead()->next;
		   while(temp!=list->getrear()){
		    	out<<temp->text<<endl;
		    	temp=temp->next;
		   }
		   out.close();
	   }
	}
}
