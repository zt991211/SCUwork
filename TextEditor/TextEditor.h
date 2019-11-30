#ifndef TEXTEDITOR_H
#define TEXTEDITOR_H
#include<cstring>
#include"LinkList.h"
class TextEditor
{
	private:
       	LinkList*list;
        char filename[105];
	public:
		TextEditor();
		~TextEditor();
		bool openfile(char*s);
		void getfilename();
		void create_meau();
		void commend_operation(char s);
};

#endif
