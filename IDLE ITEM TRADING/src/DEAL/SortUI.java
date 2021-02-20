package DEAL;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSeparator;

public class SortUI {
	private static int x = 0;
	private static int n = 0;

	public static void AscSort() throws SQLException, ClassNotFoundException // 升序
	{
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/mysql?" + "useUnicode=true&characterEncoding=utf8&useSSL=false", "root",
				"mysql");
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT*FROM aitemrelease where owner='admin' ORDER BY price");
		mainUI.p1.removeAll();
		mainUI.p1.repaint();
		rs.last();
		int rowCount = rs.getRow();
		JButton[] buyBt = new JButton[rowCount];
		rs.beforeFirst();
		for (int i = 0; i < buyBt.length; i++) {
			buyBt[i] = new JButton("购买");
		}
		while (rs.next()) {
			Box box = Box.createVerticalBox();
			mainUI.number = rs.getInt(7);
			mainUI.number1 = String.valueOf(mainUI.number);
			box.setName(mainUI.number1);
			JLabel name = new JLabel("用户名：" + rs.getString(1) + "");
			JLabel title = new JLabel("标题：" + rs.getString(3) + "");
			JLabel information = new JLabel("详细内容：" + rs.getString(4) + "");
			JLabel prize = new JLabel("价格：" + rs.getInt(5) + "");
			buyBt[x].setName(mainUI.number1);
			box.add(name);
			box.add(title);
			box.add(information);
			box.add(prize);
			box.add(buyBt[x]);
			x++;
			JSeparator sep = new JSeparator();
			sep.setPreferredSize(new Dimension(500, 5));
			sep.setBackground(new Color(153, 153, 153));
			box.add(sep);
			mainUI.p1.add(box);
			n += 116;
			mainUI.p1.setPreferredSize(new Dimension(300, n));
		}
		mainUI.p1.revalidate();
		rs.close();
		rs = null;
		stmt.close();
		stmt = null;
		n = 0;
		x = 0;

		ActionListener BuyListener = new ActionListener() {// 进入相对应的购买界面
			public void actionPerformed(ActionEvent event) {
				for (int t = 0; t < buyBt.length; t++) {
					if (event.getSource().equals(buyBt[t])) {
						mainUI.itemNumber = buyBt[t].getName();
						mainUI.aitemNumber = Integer.parseInt(mainUI.itemNumber);
						try {
							new Buy();
						} catch (ClassNotFoundException | SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		};
		for (int t = 0; t < buyBt.length; t++) {
			buyBt[t].addActionListener(BuyListener);
		}
	}

	public static void DescSort() throws ClassNotFoundException, SQLException// 降序
	{
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/mysql?" + "useUnicode=true&characterEncoding=utf8&useSSL=false", "root",
				"mysql");
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT*FROM aitemrelease where owner='admin' ORDER BY price DESC");
		mainUI.p1.removeAll();
		mainUI.p1.repaint();
		rs.last();
		int rowCount = rs.getRow();
		JButton[] buyBt = new JButton[rowCount];
		rs.beforeFirst();
		for (int i = 0; i < buyBt.length; i++) {
			buyBt[i] = new JButton("购买");
		}
		while (rs.next()) {
			Box box = Box.createVerticalBox();
			mainUI.number = rs.getInt(7);
			mainUI.number1 = String.valueOf(mainUI.number);
			box.setName(mainUI.number1);
			JLabel name = new JLabel("用户名：" + rs.getString(1) + "");
			JLabel title = new JLabel("标题：" + rs.getString(3) + "");
			JLabel information = new JLabel("详细内容：" + rs.getString(4) + "");
			JLabel prize = new JLabel("价格：" + rs.getInt(5) + "");
			buyBt[x].setName(mainUI.number1);
			box.add(name);
			box.add(title);
			box.add(information);
			box.add(prize);
			box.add(buyBt[x]);
			x++;
			JSeparator sep = new JSeparator();
			sep.setPreferredSize(new Dimension(500, 5));
			sep.setBackground(new Color(153, 153, 153));
			box.add(sep);
			mainUI.p1.add(box);
			n += 116;
			mainUI.p1.setPreferredSize(new Dimension(300, n));
		}
		mainUI.p1.revalidate();
		rs.close();
		rs = null;
		stmt.close();
		stmt = null;
		x = 0;
		n = 0;
		ActionListener BuyListener = new ActionListener() {// 进入相对应的购买界面
			public void actionPerformed(ActionEvent event) {
				for (int t = 0; t < buyBt.length; t++) {
					if (event.getSource().equals(buyBt[t])) {
						mainUI.itemNumber = buyBt[t].getName();
						mainUI.aitemNumber = Integer.parseInt(mainUI.itemNumber);
						try {
							new Buy();
						} catch (ClassNotFoundException | SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		};
		for (int t = 0; t < buyBt.length; t++) {
			buyBt[t].addActionListener(BuyListener);
		}
	}

}
