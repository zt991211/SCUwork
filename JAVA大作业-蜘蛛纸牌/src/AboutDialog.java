

import javax.swing.*;
import java.awt.*;


/*
 **“关于”窗口
 */
public class AboutDialog extends JDialog
{
	JPanel jMainPane = new JPanel();

	JTabbedPane jTabbedPane = new JTabbedPane();
	private JPanel jPanel1 = new JPanel();
	private JPanel jPanel2 = new JPanel();

	private JTextArea jt1 = new JTextArea("将电脑多次分发给你的牌按照相同的花色由大至小排列起来。直到桌面上的牌全都消失。"); 
	private JTextArea jt2 = new JTextArea("由于该项目耗时挺多，希望大家在开源学习的时候多多支持下！");

	/*
	 **构造函数
	 */
	public AboutDialog()
	{
		setTitle("蜘蛛牌");
		setSize(300,200);
		setResizable(false);
		setDefaultCloseOperation (WindowConstants.DISPOSE_ON_CLOSE); 
		
		Container c = this.getContentPane();
		
		jt1.setSize(260,200);
		jt2.setSize(260,200);
		
		jt1.setEditable(false);
		jt2.setEditable(false);
		
		jt1.setLineWrap(true); 
		jt2.setLineWrap(true); 

		jt1.setFont(new Font("楷体_GB2312", java.awt.Font.BOLD, 13));
		jt1.setForeground(Color.blue);

		jt2.setFont(new Font("楷体_GB2312", java.awt.Font.BOLD, 13));
		jt2.setForeground(Color.black);
		
		jPanel1.add(jt1);
		jPanel2.add(jt2);
		
		jTabbedPane.setSize(300,200);
		jTabbedPane.addTab("游戏规则", null, jPanel1, null);
		jTabbedPane.addTab("声明", null, jPanel2, null);
		
		jMainPane.add(jTabbedPane);
		c.add(jMainPane);

		pack();
		this.setVisible(true);
	}
} 
