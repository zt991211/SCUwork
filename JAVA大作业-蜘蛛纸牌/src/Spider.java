

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class Spider extends JFrame{
	
	//���ͱ�������ʾ�Ѷȵȼ�Ϊ����
	public static final int EASY = 1;
    //���ͱ�������ʾ�Ѷȵȼ�Ϊ����ͨ
	public static final int NATURAL = 2;
    //���ͱ�������ʾ�Ѷȵȼ�Ϊ����
	public static final int HARD = 3;
    //�趨��ʼ�Ѷȵȼ�Ϊ��
	private int grade = Spider.EASY;
    private Container pane = null;
    //����ֽ������
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
     **���캯��
     */
    public Spider(){
    	//�ı�ϵͳĬ������
		Font font = new Font("Dialog", Font.PLAIN, 12);
		java.util.Enumeration keys = UIManager.getDefaults().keys();
		while (keys.hasMoreElements()) {
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if (value instanceof javax.swing.plaf.FontUIResource) {
				UIManager.put(key, font);
			}
		}
		setTitle("֩����");
        setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        //���ÿ�ܵĴ�С
		setSize(1024, 742);
        
		//����SpiderMenuBar���󣬲������ڿ��֮��
		setJMenuBar(new SpiderMenuBar(this));
        pane = this.getContentPane();
        //���ñ�����ɫ
		pane.setBackground(new Color(0, 112, 26));
        //�����ֹ��������ó�Ϊnull
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
	 **��ʼ����Ϸ
	 */
	public void newGame(){
        this.randomCards();
        this.setCardsLocation();
        this.setGroundLabelZOrder();
        this.deal();
    }

    /**
	 **����ֵ��int
	 **�����Ƶ�����
	  */
	public int getC(){
        return c;
    }

    /**
	 **���õȼ�
	 */
	public void setGrade(int grade){
        this.grade = grade;
    }

    /**
	 **ֽ�Ƴ�ʼ��
	 */
	public void initCards(){
        //���ֽ���ѱ���ֵ��������ӿ�ܵ��������ȥ
		if (cards[0] != null){
            for (int i = 0; i < 104; i++){
                pane.remove(cards[i]);
            }
        }
        
		int n = 0;
        //ͨ���Ѷȵȼ���Ϊn��ֵ
		if (this.grade == Spider.EASY){
            n = 1;
        }
        else if (this.grade == Spider.NATURAL){
            n = 2;
        }
        else{
            n = 4;
        }
      //Ϊcard��ֵ  
		for (int i = 1; i <= 8; i++){
            for (int j = 1; j <= 13; j++){
                cards[(i - 1) * 13 + j - 1] = new PKCard((i % n + 1) + "-" + j,
                        this);
            }
        }
        
		//���ֽ�Ƴ�ʼ��
		this.randomCards();
    }

    /**
	 **ֽ���������
	 */
	public void randomCards(){
        PKCard temp = null;
        //��������ƺ�
		for (int i = 0; i < 52; i++){
            int a = (int) (Math.random() * 104);
            int b = (int) (Math.random() * 104);
            temp = cards[a];
            cards[a] = cards[b];
            cards[b] = temp;
        }
    }

    /**
	 **���û�ԭ
	 */
	public void setNA(){
        a = 0;
        n = 0;
    }

    /**
	 **����ֽ�Ƶ�λ��
	 */
	public void setCardsLocation(){
        table = new Hashtable();
        c = 0;
        finish = 0;
        n = 0;
        a = 0;
        int x = 883;
        int y = 580;
		//��ʼ����չ����ֽ��
        for (int i = 0; i < 6; i++){
            for (int j = 0; j < 10; j++){
                int n = i * 10 + j;
                pane.add(cards[n]);
                //��cardת����
				cards[n].turnRear();
                //��card���ڹ̶���λ����
				cards[n].moveto(new Point(x, y));
                //��card��λ�ü������Ϣ����
				table.put(new Point(x, y), cards[n]);
            }
            x += 10;
        }
		
		x = 20;
        y = 45;
        //��ʼ��������ʾ��ֽ��
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
	 **����ֵ��void
	 **��������ʾ���ƶ��Ĳ���
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
	 **����ֵ��void
	 **��������Ϸ����
	 */
	public void deal()
    {
        this.setNA();
        //�ж�10�����Ƿ����
		for (int i = 0; i < 10; i++){
            if (this.getLastCardLocation(i) == null){
                JOptionPane.showMessageDialog(this, "�п�λ���ܷ��ƣ�", "��ʾ",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
        }
        int x = 20;
        
		for (int i = 0; i < 10; i++){
            Point lastPoint = this.getLastCardLocation(i);
            //������Ӧ���������ϡ�
			if (c == 0){
                lastPoint.y += 5;
			}
            //������Ӧ���������ϡ�
			else{
                lastPoint.y += 20;
			}
            
			table.remove(cards[c + i].getLocation());
            cards[c + i].moveto(lastPoint);
            table.put(new Point(lastPoint), cards[c + i]);
            cards[c + i].turnFront();
            cards[c + i].setCanMove(true);
            
			//�����card�ƶ���������ָ����˳�������� 
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
	 **����ֵ��PKCard����
	 **���������card�����������
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
	 **����ֵ��PKCard����
	 **������ȡ��card�����һ����
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
	 **����ֵ��Point����
	 **������ȡ�õ�column�����һ���Ƶ�λ��
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
	 **����ֵ��void
	 **����������groundLable���
	 */
	public void setGroundLabelZOrder(){
        for (int i = 0; i < 10; i++){
            //�����groundLable�ƶ���������ָ����˳��������˳��105+i��ȷ���˻��������˳�򣻾������˳����������һ�����ƣ����������˳�����������һ�����ơ�������ص��ĵط������нϵ�˳�����������Ǿ��нϸ�˳�������� 
			pane.setComponentZOrder(groundLabel[i], 105 + i);
        }
    }

    /*
	 **����ֵ��void
	 **�������ж�ֽ�Ƶİڷ��Ƿ����
	 */
	public void haveFinish(int column){
        Point point = this.getLastCardLocation(column);
        PKCard card = (PKCard) this.table.get(point);
        do{
            this.table.remove(point);
            card.moveto(new Point(20 + finish * 10, 580));
            //������ƶ���������ָ����˳�������� 
			pane.setComponentZOrder(card, 1);
            //��ֽ���µ������Ϣ����Hashtable
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
        //���8����ȫ����ϳɹ�������ʾ�ɹ��ĶԻ���
		if (finish == 8){
            JOptionPane.showMessageDialog(this, "��ϲ�㣬˳��ͨ����", "�ɹ�",
                    JOptionPane.PLAIN_MESSAGE);
        }
        if (card != null){
            card.turnFront();
            card.setCanMove(true);
        }
    }
}
