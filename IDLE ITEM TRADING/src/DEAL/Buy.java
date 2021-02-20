package DEAL;

import java.awt.Color;
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

public class Buy {
	private JLabel name, title, information, prize, contact;
	private JButton buyBt = new JButton("确认购买");
	private String username;
	private String uname;
	private String owner;
	private int number;

	public Buy() throws ClassNotFoundException, SQLException {
		JFrame buy = new JFrame("购买窗口");
		buy.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		buy.setVisible(true);
		buy.setSize(250, 250);
		buy.setLocationRelativeTo(null);
		buy.getContentPane().setBackground(Color.white);
		buy.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		number = mainUI.aitemNumber;
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/mysql?" + "useUnicode=true&characterEncoding=utf8&useSSL=false", "root",
				"mysql");
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("select*from aitemrelease where number='" + number + "'");
		Box box = Box.createVerticalBox();
		while (rs.next()) {
			name = new JLabel("用户名：" + rs.getString(1));
			title = new JLabel("标题：" + rs.getString(3));
			information = new JLabel("详细内容：" + rs.getString(4));
			prize = new JLabel("价格：" + rs.getInt(5));
			username = rs.getString(1);
			owner = rs.getString(6);
			box.add(name);
			box.add(title);
			box.add(information);
			box.add(prize);
		}
		rs.close();
		ResultSet rss = stmt.executeQuery("select*from account where name='" + username + "'");
		while (rss.next()) {
			contact = new JLabel("联系方式：" + rss.getString(4));
			box.add(contact);
			box.add(buyBt);
		}
		rss.close();
		buy.add(box);
		ActionListener SureBuyListener = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
					Class.forName("com.mysql.jdbc.Driver");
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql?"
							+ "useUnicode=true&characterEncoding=utf8&useSSL=false", "root", "mysql");
					Statement stmt = con.createStatement();
					ResultSet rs1 = stmt.executeQuery("select*from aitemrelease where number='" + number + "'");
					while (rs1.next()) {
						uname = rs1.getString(2);
					}
					if (mainUI.LoginStatus == 0) {
						JOptionPane.showMessageDialog(null, "请登录后再购买！");
					}
					if (uname.equals(mainUI.username)) {
						JOptionPane.showMessageDialog(null, "你不能购买你自己发布的物品");
					} else if (mainUI.LoginStatus == 1 && uname.equals(mainUI.username) == false
							&& owner.equals("admin")) {
						PreparedStatement pre = con.prepareStatement("update aitemrelease set owner=? where number=?");
						pre.setInt(2, number);
						pre.setString(1, mainUI.username);
						buy.setVisible(false);
						JOptionPane.showMessageDialog(null, "购买成功！");
						pre.executeUpdate();
						pre.close();
					} else if (!owner.equals("admin")) {
						JOptionPane.showMessageDialog(null, "该物品已被购买！");
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		stmt.close();
		con.close();

		buyBt.addActionListener(SureBuyListener);
	}

}
