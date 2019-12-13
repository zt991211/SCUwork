

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.ButtonGroup;

public class SpiderMenuBar extends JMenuBar{
    
	//����spider��ܶ���
    Spider main = null;
   
    //���ɲ˵���
    JMenu jNewGame = new JMenu("��Ϸ");
    JMenu jHelp = new JMenu("����");
    
    //���ɲ˵���
    JMenuItem jItemAbout = new JMenuItem("����");
    JMenuItem jItemOpen = new JMenuItem("����");
    JMenuItem jItemPlayAgain = new JMenuItem("���·���");
   
    //���ɵ�ѡ��
    JRadioButtonMenuItem jRMItemEasy = new JRadioButtonMenuItem("�򵥣���һ��ɫ");
    JRadioButtonMenuItem jRMItemNormal = new JRadioButtonMenuItem("�м���˫��ɫ");
    JRadioButtonMenuItem jRMItemHard = new JRadioButtonMenuItem("�߼����Ļ�ɫ");;
    
    JMenuItem jItemExit = new JMenuItem("�˳�");
    JMenuItem jItemValid = new JMenuItem("��ʾ���в���");
    
    
    /**
     **���캯��������JMenuBar��ͼ�ν���
     */
    public SpiderMenuBar(Spider spider){
        
        this.main = spider;
        
        /**
         **��ʼ������Ϸ���˵���
         */
        jNewGame.add(jItemOpen);
        jNewGame.add(jItemPlayAgain);
        jNewGame.add(jItemValid);
        
        jNewGame.addSeparator();
        jNewGame.add(jRMItemEasy);
        jNewGame.add(jRMItemNormal);
        jNewGame.add(jRMItemHard);
        
        jNewGame.addSeparator();
        
        jNewGame.add(jItemExit);
        
        ButtonGroup group = new ButtonGroup();
        group.add(jRMItemEasy);
        group.add(jRMItemNormal);
        group.add(jRMItemHard);

        jHelp.add(jItemAbout);

        this.add(jNewGame);
        this.add(jHelp);

		//Ϊ�������¼�������ʵ��
        //�����֡�
		jItemOpen.addActionListener(new java.awt.event.ActionListener() { 
            public void actionPerformed(java.awt.event.ActionEvent e) {    
                main.newGame();
            }
        });

        //�����·��ơ�
		jItemPlayAgain.addActionListener(new java.awt.event.ActionListener() { 
            public void actionPerformed(java.awt.event.ActionEvent e) {    
                if(main.getC() < 60){
                    main.deal();
                }
            }
        });

        //"��ʾ���в���"
		jItemValid.addActionListener(new java.awt.event.ActionListener() { 
            public void actionPerformed(java.awt.event.ActionEvent e) {    
                new Show().start();
            }
        });
        
        //���˳���
		jItemExit.addActionListener(new java.awt.event.ActionListener() { 
            public void actionPerformed(java.awt.event.ActionEvent e) {    
                main.dispose();
                System.exit(0);
            }
        });
		
		//���򵥼���Ĭ����ѡ
		jRMItemEasy.setSelected(true);
        
		//���򵥼���
		jRMItemEasy.addActionListener(new java.awt.event.ActionListener() { 
            public void actionPerformed(java.awt.event.ActionEvent e) {    
                main.setGrade(Spider.EASY);
                main.initCards();
                main.newGame();
            }
        });
        
        //���м���
		jRMItemNormal.addActionListener(new java.awt.event.ActionListener() { 
            public void actionPerformed(java.awt.event.ActionEvent e) {    
                main.setGrade(Spider.NATURAL);
                main.initCards();
                main.newGame();
            }
        });

        //���߼���
		jRMItemHard.addActionListener(new java.awt.event.ActionListener() { 
            public void actionPerformed(java.awt.event.ActionEvent e) {    
                main.setGrade(Spider.HARD);
                main.initCards();
                main.newGame();
            }
        });

		jNewGame.addMenuListener(new javax.swing.event.MenuListener() { 
            public void menuSelected(javax.swing.event.MenuEvent e) {    
                if(main.getC() < 60){
                    jItemPlayAgain.setEnabled(true);
                }
                else{
                    jItemPlayAgain.setEnabled(false);
                }
            }
            public void menuDeselected(javax.swing.event.MenuEvent e) {} 
            public void menuCanceled(javax.swing.event.MenuEvent e) {} 
        });
        
        //�����ڡ�
		jItemAbout.addActionListener(new java.awt.event.ActionListener() { 
            public void actionPerformed(java.awt.event.ActionEvent e) {    
                new AboutDialog();
            }
        });
    }

    /**
     **�����̣߳���ʾ����ִ�еĲ���
     */
    class Show extends Thread{
        public void run(){
            main.showEnableOperator();
        }
    }
 }
