#include<iostream>
#include<cstdio>
#include<stack>
#include<cstring>
#include<string>
#include<cctype>
#include<cmath>
using namespace std;
stack<double>number;
stack<char>cal;
string x;
double count(double x,double y,char z){
	if(z=='+')return x+y;
	if(z=='-')return x-y;
	if(z=='*')return x*y;
	if(z=='/')return x/y;
}
int getid(char x){
	if(x=='(')return 1;
	if(x=='+'||x=='-')return 2;
	if(x=='*'||x=='/')return 3;
}
int main()
{
	cout<<"Welcome to two-stack exe----- :)"<<endl;
	cout<<"developer: SCU-zt"<<endl;
	cout<<endl;
	while(1){
		cout<<"Please input the expression"<<endl; 
		while(!number.empty()){
			number.pop();
		}
		while(!cal.empty()){
			cal.pop();
		}
		cin>>x;
		int len=x.size();
		if(x[0]=='-'){
			number.push(0.0);
		}
		double temp=0;
		bool ans=true;
		for(int i=0;i<len;){
			bool flag=false;
			int cnt=-1;
			while(isdigit(x[i])||x[i]=='.'){
		        if(x[i]=='.'){
		        	flag=true;
		        	i++;
		        	continue;
				}
				if(flag==false)temp=temp*10+(x[i]-'0');
				if(flag==true){
					double y=pow(10,cnt);
					temp=temp+(x[i]-'0')*1.0*y;
					cnt--;
				}
				i++;
				if(!(isdigit(x[i])||x[i]=='.')){
					number.push(temp);
					temp=0;
					break;
				}
				if(i==len)break;		
			}
			if(i==len)break;
			if(x[i]=='('){
				cal.push(x[i]);
				i++;
				if(x[i]=='-')number.push(0.0);
				continue;
			}
			if(x[i]==')'){
				while(number.size()>=2&&cal.top()!='('){
					char y=cal.top();
					cal.pop();
					double tx=number.top();
					number.pop();
					double ty=number.top();
					number.pop();
					double t=count(ty,tx,y);
					number.push(t);
				}
				if(cal.top()!='('){
					ans=false;
					break;
				}
				cal.pop();
				i++;
				continue;
			}
			if(cal.empty()){
				cal.push(x[i]);
				i++;
				continue;
			}
			if(((x[i]=='*'||x[i]=='/')&&(cal.top()=='+'||cal.top()=='-'))||(cal.top()=='(')){
				cal.push(x[i]);
				i++;
				continue;
			}
			while(number.size()>=2&&cal.size()>0&&(getid(x[i])<=getid(cal.top()))){
			char z=cal.top();
			cal.pop();
			double tx=number.top();
			number.pop();
			double ty=number.top();
			number.pop();
			double t=count(ty,tx,z);
			number.push(t);
		}
		    cal.push(x[i]);
		    i++;
		}
		if(ans==false){
			cout<<"expression error!"<<endl;
			cout<<endl;
			continue;
		}
		while(!cal.empty()&&number.size()>=2){
			char z=cal.top();
			cal.pop();
			double tx=number.top();
			number.pop();
			double ty=number.top();
			number.pop();
			double t=count(ty,tx,z);
			number.push(t);
		}
		if(!cal.empty()){
		cout<<"expression error!"<<endl;
		cout<<endl;
		continue;
	}
	    if(number.size()==0){
	        cout<<"expression error!"<<endl;
	        cout<<endl;
		    continue;
		}
		cout<<"the wonderful result is: "<<number.top()<<endl;
		cout<<endl;
	}
	return 0;
}
