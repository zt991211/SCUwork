

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.ButtonGroup;

public class SpiderMenuBar extends JMenuBar{
    
	//生成spider框架对象
    Spider main = null;
   
    //生成菜单组
    JMenu jNewGame = new JMenu("游戏");
    JMenu jHelp = new JMenu("帮助");
    
    //生成菜单项
    JMenuItem jItemAbout = new JMenuItem("关于");
    JMenuItem jItemOpen = new JMenuItem("开局");
    JMenuItem jItemPlayAgain = new JMenuItem("重新发牌");
   
    //生成单选框
    JRadioButtonMenuItem jRMItemEasy = new JRadioButtonMenuItem("简单：单一花色");
    JRadioButtonMenuItem jRMItemNormal = new JRadioButtonMenuItem("中级：双花色");
    JRadioButtonMenuItem jRMItemHard = new JRadioButtonMenuItem("高级：四花色");;
    
    JMenuItem jItemExit = new JMenuItem("退出");
    JMenuItem jItemValid = new JMenuItem("显示可行操作");
    
    
    /**
     **构造函数，生成JMenuBar的图形界面
     */
    public SpiderMenuBar(Spider spider){
        
        this.main = spider;
        
        /**
         **初始化“游戏”菜单栏
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

		//为组件添加事件监听并实现
        //“开局”
		jItemOpen.addActionListener(new java.awt.event.ActionListener() { 
            public void actionPerformed(java.awt.event.ActionEvent e) {    
                main.newGame();
            }
        });

        //“重新发牌”
		jItemPlayAgain.addActionListener(new java.awt.event.ActionListener() { 
            public void actionPerformed(java.awt.event.ActionEvent e) {    
                if(main.getC() < 60){
                    main.deal();
                }
            }
        });

        //"显示可行操作"
		jItemValid.addActionListener(new java.awt.event.ActionListener() { 
            public void actionPerformed(java.awt.event.ActionEvent e) {    
                new Show().start();
            }
        });
        
        //“退出”
		jItemExit.addActionListener(new java.awt.event.ActionListener() { 
            public void actionPerformed(java.awt.event.ActionEvent e) {    
                main.dispose();
                System.exit(0);
            }
        });
		
		//“简单级别”默认已选
		jRMItemEasy.setSelected(true);
        
		//“简单级别”
		jRMItemEasy.addActionListener(new java.awt.event.ActionListener() { 
            public void actionPerformed(java.awt.event.ActionEvent e) {    
                main.setGrade(Spider.EASY);
                main.initCards();
                main.newGame();
            }
        });
        
        //“中级”
		jRMItemNormal.addActionListener(new java.awt.event.ActionListener() { 
            public void actionPerformed(java.awt.event.ActionEvent e) {    
                main.setGrade(Spider.NATURAL);
                main.initCards();
                main.newGame();
            }
        });

        //“高级”
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
        
        //“关于”
		jItemAbout.addActionListener(new java.awt.event.ActionListener() { 
            public void actionPerformed(java.awt.event.ActionEvent e) {    
                new AboutDialog();
            }
        });
    }

    /**
     **构造线程：显示可以执行的操作
     */
    class Show extends Thread{
        public void run(){
            main.showEnableOperator();
        }
    }
 }
