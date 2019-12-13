

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class Spider extends JFrame{
	
	//整型变量，表示难度等级为：简单
	public static final int EASY = 1;
    //整型变量，表示难度等级为：普通
	public static final int NATURAL = 2;
    //整型变量，表示难度等级为：难
	public static final int HARD = 3;
    //设定初始难度等级为简单
	private int grade = Spider.EASY;
    private Container pane = null;
    //生成纸牌数组
	private PKCard cards[] = new PKCard[104];
    private JLabel clickLabel = null;
    private int c = 0;
    private int n = 0;
    private int a = 0;
    private int finish = 0;
    Hashtable table = null;
    private JLabel groundLabel[] = null;

    public static void main(String[] args){
        Spider spider = new Spider();
		spider.setVisible(true);
    }

    /**
     **构造函数
     */
    public Spider(){
    	//改变系统默认字体
		Font font = new Font("Dialog", Font.PLAIN, 12);
		java.util.Enumeration keys = UIManager.getDefaults().keys();
		while (keys.hasMoreElements()) {
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if (value instanceof javax.swing.plaf.FontUIResource) {
				UIManager.put(key, font);
			}
		}
		setTitle("蜘蛛牌");
        setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        //设置框架的大小
		setSize(1024, 742);
        
		//生成SpiderMenuBar对象，并放置在框架之上
		setJMenuBar(new SpiderMenuBar(this));
        pane = this.getContentPane();
        //设置背景颜色
		pane.setBackground(new Color(0, 112, 26));
        //将布局管理器设置成为null
		pane.setLayout(null);
        clickLabel = new JLabel();
        clickLabel.setBounds(883, 606, 121, 96);
        pane.add(clickLabel);
        
		clickLabel.addMouseListener(new MouseAdapter(){
            public void mouseReleased(MouseEvent me){
                if (c < 60){
					Spider.this.deal();
				}
            }
        });
        
		this.initCards();
        this.randomCards();
        this.setCardsLocation();
        groundLabel = new JLabel[10];
        
		int x = 20;
        for (int i = 0; i < 10; i++)
        {
            groundLabel[i] = new JLabel();
            groundLabel[i]
                    .setBorder(javax.swing.BorderFactory
                            .createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
            groundLabel[i].setBounds(x, 25, 71, 96);
            x += 101;
            this.pane.add(groundLabel[i]);
        }
        
		this.setVisible(true);
        this.deal();
        
		this.addKeyListener(new KeyAdapter(){
            class Show extends Thread{
                public void run(){
                    Spider.this.showEnableOperator();
                }
            }

            public void keyPressed(KeyEvent e){
                if (finish != 8) if (e.getKeyCode() == KeyEvent.VK_D && c < 60){
                    Spider.this.deal();
                }
                else if (e.getKeyCode() == KeyEvent.VK_M){
                    new Show().start();
                }
            }
        });
    }

    /**
	 **开始新游戏
	 */
	public void newGame(){
        this.randomCards();
        this.setCardsLocation();
        this.setGroundLabelZOrder();
        this.deal();
    }

    /**
	 **返回值：int
	 **返回牌的数量
	  */
	public int getC(){
        return c;
    }

    /**
	 **设置等级
	 */
	public void setGrade(int grade){
        this.grade = grade;
    }

    /**
	 **纸牌初始化
	 */
	public void initCards(){
        //如果纸牌已被赋值，即将其从框架的面板中移去
		if (cards[0] != null){
            for (int i = 0; i < 104; i++){
                pane.remove(cards[i]);
            }
        }
        
		int n = 0;
        //通过难度等级，为n赋值
		if (this.grade == Spider.EASY){
            n = 1;
        }
        else if (this.grade == Spider.NATURAL){
            n = 2;
        }
        else{
            n = 4;
        }
      //为card赋值  
		for (int i = 1; i <= 8; i++){
            for (int j = 1; j <= 13; j++){
                cards[(i - 1) * 13 + j - 1] = new PKCard((i % n + 1) + "-" + j,
                        this);
            }
        }
        
		//随机纸牌初始化
		this.randomCards();
    }

    /**
	 **纸牌随机分配
	 */
	public void randomCards(){
        PKCard temp = null;
        //随机生成牌号
		for (int i = 0; i < 52; i++){
            int a = (int) (Math.random() * 104);
            int b = (int) (Math.random() * 104);
            temp = cards[a];
            cards[a] = cards[b];
            cards[b] = temp;
        }
    }

    /**
	 **设置还原
	 */
	public void setNA(){
        a = 0;
        n = 0;
    }

    /**
	 **设置纸牌的位置
	 */
	public void setCardsLocation(){
        table = new Hashtable();
        c = 0;
        finish = 0;
        n = 0;
        a = 0;
        int x = 883;
        int y = 580;
		//初始化待展开的纸牌
        for (int i = 0; i < 6; i++){
            for (int j = 0; j < 10; j++){
                int n = i * 10 + j;
                pane.add(cards[n]);
                //将card转向背面
				cards[n].turnRear();
                //将card放在固定的位置上
				cards[n].moveto(new Point(x, y));
                //将card的位置及相关信息存入
				table.put(new Point(x, y), cards[n]);
            }
            x += 10;
        }
		
		x = 20;
        y = 45;
        //初始化表面显示的纸牌
		for (int i = 10; i > 5; i--){
            for (int j = 0; j < 10; j++){
                int n = i * 10 + j;
                if (n >= 104) continue;
                pane.add(cards[n]);
                cards[n].turnRear();
                cards[n].moveto(new Point(x, y));
                table.put(new Point(x, y), cards[n]);
                x += 101;
            }
            x = 20;
            y -= 5;
        }
    }

    /**
	 **返回值：void
	 **方法：显示可移动的操作
	 */
	public void showEnableOperator(){
        int x = 0;
        out: while (true){
            Point point = null;
            PKCard card = null;
            do{
                if (point != null){
					n++;
				}
                point = this.getLastCardLocation(n);
                while (point == null){
                    point = this.getLastCardLocation(++n);
                    if (n == 10) n = 0;
                    x++;
                    if (x == 10) break out;
                }
                card = (PKCard) this.table.get(point);
            }
            while (!card.isCardCanMove());
            while (this.getPreviousCard(card) != null
                    && this.getPreviousCard(card).isCardCanMove()){
                card = this.getPreviousCard(card);
            }
            if (a == 10){
				a = 0;
			}
            for (; a < 10; a++){
                if (a != n){
                    Point p = null;
                    PKCard c = null;
                    do{
                        if (p != null){
							a++;
						}
						p = this.getLastCardLocation(a);
                        int z = 0;
                        while (p == null){
                            p = this.getLastCardLocation(++a);
                            if (a == 10) a = 0;
                            if (a == n) a++;
                            z++;
                            if (z == 10) break out;
                        }
                        c = (PKCard) this.table.get(p);
                    }
                    while (!c.isCardCanMove());
                    if (c.getCardValue() == card.getCardValue() + 1){
                        card.flashCard(card);
                        try{
                            Thread.sleep(800);
                        }
                        catch (InterruptedException e){
                            e.printStackTrace();
                        }
                        c.flashCard(c);
                        a++;
                        if (a == 10){
							n++;
						}
                        break out;
                    }
                }
            }
            n++;
            if (n == 10){
				n = 0;
			}
            x++;
            if (x == 10){
				break out;
			}
        }
    }

    /*
	 **返回值：void
	 **方法：游戏运行
	 */
	public void deal()
    {
        this.setNA();
        //判断10列中是否空列
		for (int i = 0; i < 10; i++){
            if (this.getLastCardLocation(i) == null){
                JOptionPane.showMessageDialog(this, "有空位不能发牌！", "提示",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
        }
        int x = 20;
        
		for (int i = 0; i < 10; i++){
            Point lastPoint = this.getLastCardLocation(i);
            //这张牌应“背面向上”
			if (c == 0){
                lastPoint.y += 5;
			}
            //这张牌应“正面向上”
			else{
                lastPoint.y += 20;
			}
            
			table.remove(cards[c + i].getLocation());
            cards[c + i].moveto(lastPoint);
            table.put(new Point(lastPoint), cards[c + i]);
            cards[c + i].turnFront();
            cards[c + i].setCanMove(true);
            
			//将组件card移动到容器中指定的顺序索引。 
			this.pane.setComponentZOrder(cards[c + i], 1);
            
			Point point = new Point(lastPoint);
            if (cards[c + i].getCardValue() == 1){
                int n = cards[c + i].whichColumnAvailable(point);
                point.y -= 240;
                PKCard card = (PKCard) this.table.get(point);
                if (card != null && card.isCardCanMove()){
                    this.haveFinish(n);
                }
            }
            x += 101;
        }
        c += 10;
    }

	/*
	 **返回值：PKCard对象
	 **方法：获得card上面的那张牌
	 */
	public PKCard getPreviousCard(PKCard card){
        Point point = new Point(card.getLocation());
        point.y -= 5;
        card = (PKCard) table.get(point);
        if (card != null){
			return card;
		}
        point.y -= 15;
        card = (PKCard) table.get(point);
        return card;
    }

    /**
	 **返回值：PKCard对象
	 **方法：取得card下面的一张牌
	 */
	public PKCard getNextCard(PKCard card){
        Point point = new Point(card.getLocation());
        point.y += 5;
        card = (PKCard) table.get(point);
        if (card != null)
			return card;
        point.y += 15;
        card = (PKCard) table.get(point);
        return card;
    }

    /**
	 **返回值：Point对象
	 **方法：取得第column列最后一张牌的位置
	 */
	public Point getLastCardLocation(int column){
        Point point = new Point(20 + column * 101, 25);
        PKCard card = (PKCard) this.table.get(point);
        if (card == null) return null;
        while (card != null){
            point = card.getLocation();
            card = this.getNextCard(card);
        }
        return point;
    }

    public Point getGroundLabelLocation(int column){
        return new Point(groundLabel[column].getLocation());
    }

    /*
	 **返回值：void
	 **方法：放置groundLable组件
	 */
	public void setGroundLabelZOrder(){
        for (int i = 0; i < 10; i++){
            //将组件groundLable移动到容器中指定的顺序索引。顺序（105+i）确定了绘制组件的顺序；具有最高顺序的组件将第一个绘制，而具有最低顺序的组件将最后一个绘制。在组件重叠的地方，具有较低顺序的组件将覆盖具有较高顺序的组件。 
			pane.setComponentZOrder(groundLabel[i], 105 + i);
        }
    }

    /*
	 **返回值：void
	 **方法：判断纸牌的摆放是否完成
	 */
	public void haveFinish(int column){
        Point point = this.getLastCardLocation(column);
        PKCard card = (PKCard) this.table.get(point);
        do{
            this.table.remove(point);
            card.moveto(new Point(20 + finish * 10, 580));
            //将组件移动到容器中指定的顺序索引。 
			pane.setComponentZOrder(card, 1);
            //将纸牌新的相关信息存入Hashtable
			this.table.put(card.getLocation(), card);
            card.setCanMove(false);
            point = this.getLastCardLocation(column);
            if (point == null)
                card = null;
            else
                card = (PKCard) this.table.get(point);
        }
        while (card != null && card.isCardCanMove());
        finish++;
        //如果8付牌全部组合成功，则显示成功的对话框
		if (finish == 8){
            JOptionPane.showMessageDialog(this, "恭喜你，顺利通过！", "成功",
                    JOptionPane.PLAIN_MESSAGE);
        }
        if (card != null){
            card.turnFront();
            card.setCanMove(true);
        }
    }
}
