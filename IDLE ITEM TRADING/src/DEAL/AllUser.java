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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;

public class AllUser {
	private int x = 0;
	private int n = 0;
	private JPanel p1;
	private JFrame J;
	private String account;

	public AllUser() throws ClassNotFoundException, SQLException {
		J = new JFrame();
		J.setTitle("所有物品发布者信息");
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
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/mysql?" + "useUnicode=true&characterEncoding=utf8&useSSL=false", "root",
				"mysql");
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("select*from account");
		rs.last();
		int rowCount = rs.getRow();
		JButton[] DeleteBT = new JButton[rowCount];
		rs.beforeFirst();
		for (int i = 0; i < DeleteBT.length; i++) {
			DeleteBT[i] = new JButton("删除");
		}
		while (rs.next()) {// 公布闲置物品数据到平台
			Box box = Box.createVerticalBox();
			account = rs.getString(1);
			box.setName(rs.getString(1));
			JLabel account = new JLabel("账号：" + rs.getString(1) + "");
			JLabel password = new JLabel("密码：" + rs.getString(2) + "");
			JLabel username = new JLabel("用户名：" + rs.getString(3) + "");
			JLabel contact = new JLabel("联系方式：" + rs.getString(4) + "");
			DeleteBT[x].setName(rs.getString(1));
			box.add(account);
			box.add(password);
			box.add(username);
			box.add(contact);
			box.add(DeleteBT[x]);
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
		ActionListener DeleteListener = new ActionListener() {// 进入相对应的查看界面
			public void actionPerformed(ActionEvent event) {
				PreparedStatement pre = null;
				try {
					Class.forName("com.mysql.jdbc.Driver");
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql?"
							+ "useUnicode=true&characterEncoding=utf8&useSSL=false", "root", "mysql");
					pre = con.prepareStatement("delete from account where account='" + account + "'");
					pre.executeUpdate();
					pre.close();
					con.close();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		};
		for (int t = 0; t < DeleteBT.length; t++) {
			DeleteBT[t].addActionListener(DeleteListener);
		}
	}
}
