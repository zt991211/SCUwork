

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PKCard extends JLabel implements MouseListener,
        MouseMotionListener{

    //纸牌的位置
	Point point = null;
    Point initPoint = null;
    
	int value = 0;
    int type = 0;
    
	String name = null;
    Container pane = null;
    
	Spider main = null;
    
	boolean canMove = false;
    boolean isFront = false;
    PKCard previousCard = null;

    public void mouseClicked(MouseEvent arg0){
	}
    
    public void flashCard(PKCard card){
		//启动Flash线程
		new Flash(card).start();
		//不停的获得下一张牌，直到完成
		if(main.getNextCard(card) != null){
			card.flashCard(main.getNextCard(card));
		}
	}

    class Flash extends Thread{
		private PKCard card = null;
		
		public Flash(PKCard card){
			this.card = card;
		}
		
		/*
		 **线程的run()方法
		 **为纸牌的正面设置白色图片
		 */
		public void run(){
			boolean is = false;
			ImageIcon icon = new ImageIcon("images/white.gif");
			for (int i = 0; i < 4; i++){
				try{
					Thread.sleep(200);
				}
				catch (InterruptedException e){
					e.printStackTrace();
				}
				
				if (is){
					this.card.turnFront();
					is = !is;
				}
				else{
					this.card.setIcon(icon);
					is = !is;
				}
				// 根据当前外观将card的UI属性重置
				card.updateUI();
			}
		}
	}

    /**
	 **点击鼠标
	 */
	public void mousePressed(MouseEvent mp){
        point = mp.getPoint();
        main.setNA();
        this.previousCard = main.getPreviousCard(this);
    }

    /**
	 **释放鼠标
	 */
	public void mouseReleased(MouseEvent mr){
		Point point = ((JLabel) mr.getSource()).getLocation();
		//判断可行列
		int n = this.whichColumnAvailable(point);
		if (n == -1 || n == this.whichColumnAvailable(this.initPoint)){
			this.setNextCardLocation(null);
			main.table.remove(this.getLocation());
			this.setLocation(this.initPoint);
			main.table.put(this.initPoint, this);
			return;
		}
		
		point = main.getLastCardLocation(n);
		boolean isEmpty = false;
		PKCard card = null;
		if (point == null){
			point = main.getGroundLabelLocation(n);
			isEmpty = true;
		}
		else{
			card = (PKCard) main.table.get(point);
		}
		
		if (isEmpty || (this.value + 1 == card.getCardValue())){
			point.y += 40;
			if (isEmpty) point.y -= 20;
			this.setNextCardLocation(point);
			main.table.remove(this.getLocation());
			point.y -= 20;
			this.setLocation(point);
			main.table.put(point, this);
			this.initPoint = point;
			if (this.previousCard != null){
				this.previousCard.turnFront();
				this.previousCard.setCanMove(true);
			}
			
			this.setCanMove(true);
		}
		else{
			this.setNextCardLocation(null);
			main.table.remove(this.getLocation());
			this.setLocation(this.initPoint);
			main.table.put(this.initPoint, this);
			return;
		}
        point = main.getLastCardLocation(n);
        card = (PKCard) main.table.get(point);
        if (card.getCardValue() == 1){
			point.y -= 240;
			card = (PKCard) main.table.get(point);
			if (card != null && card.isCardCanMove()){
				main.haveFinish(n);
			}
		}
	}
	
	/*
	 **方法：放置纸牌
	 */
	public void setNextCardLocation(Point point){
		PKCard card = main.getNextCard(this);
		if (card != null){
			if (point == null){
				card.setNextCardLocation(null);
				main.table.remove(card.getLocation());
				card.setLocation(card.initPoint);
				main.table.put(card.initPoint, card);
			}
			else{
				point = new Point(point);
				point.y += 20;
				card.setNextCardLocation(point);
				point.y -= 20;
				main.table.remove(card.getLocation());
				card.setLocation(point);
				main.table.put(card.getLocation(), card);
				card.initPoint = card.getLocation();
			}
		}
	}

    /**
	 **返回值：int
	 **方法：判断可用列
	 */
	public int whichColumnAvailable(Point point){
		int x = point.x;
		int y = point.y;
		int a = (x - 20) / 101;
		int b = (x - 20) % 101;
		if (a != 9){
			if (b > 30 && b <= 71){
				a = -1;
			}
			else if (b > 71){
				a++;
			}
		}
		else if (b > 71){
			a = -1;
		}
		
		if (a != -1){
			Point p = main.getLastCardLocation(a);
			if (p == null) p = main.getGroundLabelLocation(a);
			b = y - p.y;
			if (b <= -96 || b >= 96){
				a = -1;
			}
		}
		return a;
	}

    public void mouseEntered(MouseEvent arg0){
    }

    public void mouseExited(MouseEvent arg0){
    }

    /**
	 **用鼠标拖动纸牌
	 */
	public void mouseDragged(MouseEvent arg0){
        if (canMove){
			int x = 0;
			int y = 0;
			Point p = arg0.getPoint();
			x = p.x - point.x;
			y = p.y - point.y;
			this.moving(x, y);
		}
	}

    /**
	 **返回值：void
	 **方法：移动（x，y）个位置
	 */
	public void moving(int x, int y){
        PKCard card = main.getNextCard(this);
        Point p = this.getLocation();
        
		//将组件移动到容器中指定的顺序索引。 
		pane.setComponentZOrder(this, 1);
        
		//在Hashtable中保存新的节点信息
		main.table.remove(p);
        p.x += x;
        p.y += y;
        this.setLocation(p);
        main.table.put(p, this);
        if (card != null) card.moving(x, y);
    }

    public void mouseMoved(MouseEvent arg0){
    }

    /**
     **构造函数
     */
    public PKCard(String name, Spider spider){
        super();
        this.type = new Integer(name.substring(0, 1)).intValue();
        this.value = new Integer(name.substring(2)).intValue();
        this.name = name;
        this.main = spider;
        this.pane = this.main.getContentPane();
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.setIcon(new ImageIcon("images/rear.gif"));
        this.setSize(71, 96);
        this.setVisible(true);
    }

    /**
	 **返回值：void
	 **方法：令纸牌显示正面
	 */
	public void turnFront(){
        this.setIcon(new ImageIcon("images/" + name + ".gif"));
        this.isFront = true;
    }

    /**
	 **返回值：void
	 **方法：令纸牌显示背面
	 */
	public void turnRear(){
        this.setIcon(new ImageIcon("images/rear.gif"));
        this.isFront = false;
        this.canMove = false;
    }

    /**
	 **返回值：void
	 **方法：将纸牌移动到点point
	 */
	public void moveto(Point point){
        this.setLocation(point);
        this.initPoint = point;
    }

    /**
	 **返回值：void
	 **方法：判断牌是否能移动
	 */
	public void setCanMove(boolean can){
        this.canMove = can;
        PKCard card = main.getPreviousCard(this);
        if (card != null && card.isCardFront()){
            if (!can){
                if (!card.isCardCanMove()){
                    return;
				}
                else{
					card.setCanMove(can);
				}
			}
            else{
                if (this.value + 1 == card.getCardValue()
                        && this.type == card.getCardType()){
					card.setCanMove(can);
				}
				else{
					card.setCanMove(false);
				}
            }
        }
    }

    /**
	 **返回值：boolean
	 **方法：判断card是否是正面
	 */
	public boolean isCardFront(){
        return this.isFront;
    }

    /*
	 **返回值：boolean
	 **方法：返回是否能够移动
	 */
	public boolean isCardCanMove(){
        return this.canMove;
    }

    /**
	 **返回值：int
	 **方法：获得card的内容值
	 */
	public int getCardValue(){
        return value;
    }

    /**
	 **返回值：int
	 **方法：获得card的类型
	 */
	public int getCardType(){
        return type;
    }
}
