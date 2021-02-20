package DEAL;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;

public class OrderList {
	private static int x = 0;
	private static int n = 0;
	private static JPanel p1;
	private static JFrame J;
	private static int deliver;

	public static void Order(boolean status, String username) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/mysql?" + "useUnicode=true&characterEncoding=utf8&useSSL=false", "root",
				"mysql");
		Statement stmt = con.createStatement();
		ResultSet rs = null;
		if (status) {
			rs = stmt.executeQuery("select*from aitemrelease where account = '" + username + "' and owner !='admin'  ");
		} else {
			rs = stmt.executeQuery("select*from aitemrelease where owner !='admin' ");
		}
		rs.last();
		int rowCount = rs.getRow();
		JButton[] OrderBt = new JButton[rowCount];
		rs.beforeFirst();
		if (!status) {
			for (int i = 0; i < OrderBt.length; i++) {
				OrderBt[i] = new JButton("删除");
			}
		}else {
			for (int i = 0; i < OrderBt.length; i++) {
				OrderBt[i] = new JButton("发货");
		}
		}
		while (rs.next()) {// 公布闲置物品数据到平台
			deliver = rs.getInt(8);
			Box box = Box.createVerticalBox();
			mainUI.number = rs.getInt(7);
			mainUI.number1 = String.valueOf(mainUI.number);
			box.setName(mainUI.number1);
			JLabel title = new JLabel("标题：" + rs.getString(3) + "");
			JLabel information = new JLabel("详细内容：" + rs.getString(4) + "");
			JLabel prize = new JLabel("价格：" + rs.getInt(5) + "");
			JLabel deliverS = new JLabel();
			if (deliver == 1) {
				deliverS = new JLabel("订单状况：已发货");
			} else {
				deliverS = new JLabel("订单状况：未发货");
			}
			OrderBt[x].setName(mainUI.number1);
			box.add(title);
			box.add(information);
			box.add(prize);
			box.add(deliverS);
			box.add(OrderBt[x]);
			x++;
			JSeparator sep = new JSeparator();
			sep.setPreferredSize(new Dimension(J.getWidth(), 5));
			sep.setBackground(new Color(153, 153, 153));
			box.add(sep);
			p1.add(box);
			n += 116;
			p1.setPreferredSize(new Dimension(300, n));
		}
		rs.close();
		rs = null;
		stmt.close();
		stmt = null;
		x = 0;
		n = 0;
		ActionListener OrderListener = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				for (int t = 0; t < OrderBt.length; t++) {
					if (event.getSource().equals(OrderBt[t])) {
						mainUI.itemNumber = OrderBt[t].getName();
						mainUI.aitemNumber = Integer.parseInt(mainUI.itemNumber);
						try {
							if (deliver == 0) {
								Class.forName("com.mysql.jdbc.Driver");
								Connection con = DriverManager.getConnection(
										"jdbc:mysql://localhost:3306/mysql?"
												+ "useUnicode=true&characterEncoding=utf8&useSSL=false",
										"root", "mysql");
								PreparedStatement pre = con.prepareStatement(
										"update aitemrelease set deliver=? where number='" + mainUI.aitemNumber + "'");
								pre.setInt(1, 1);
								pre.executeUpdate();
								JOptionPane.showMessageDialog(null, "发货成功!");

								pre.close();
								con.close();
							} else {
								JOptionPane.showMessageDialog(null, "订单已经发货啦!");
							}
						} catch (ClassNotFoundException | SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		};
		for (int t = 0; t < OrderBt.length; t++) {
			OrderBt[t].addActionListener(OrderListener);
		}
	}

	public void OrderList() throws ClassNotFoundException, SQLException {
		J = new JFrame();
		J.setTitle("订单列表");
		J.setVisible(true);
		J.setSize(400, 500);
		J.setLocationRelativeTo(null);
		J.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		J.getContentPane().setBackground(Color.white);
		p1 = new JPanel();
		p1.setBackground(Color.white);
		p1.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		JScrollPane sp = new JScrollPane(p1);
		J.add(sp);
		if (mainUI.LoginStatus == 1) {
			Order(true, mainUI.username);
		} else if (mainUI.AdminLoginStatus == 1) {
			String admin = "admin";
			Order(false, admin);
		}
	}
}
