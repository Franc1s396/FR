package DEAL;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
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

public class MyRelease extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static int x = 0;
	private static int n = 0;
	private static JPanel p1;
	private static JFrame J;

	public static void MyRelease() throws ClassNotFoundException, SQLException {
		J = new JFrame();
		J.setTitle("我发布的物品");
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
		ResultSet rs = null;
		if (mainUI.LoginStatus == 1) {
			rs = stmt.executeQuery("select*from aitemrelease where account = '" + mainUI.username + "'");
		} else if (mainUI.AdminLoginStatus == 1) {
			rs = stmt.executeQuery("select*from aitemrelease");
		}
		rs.last();
		int rowCount = rs.getRow();
		JButton[] checkBT = new JButton[rowCount];
		rs.beforeFirst();
		for (int i = 0; i < checkBT.length; i++) {
			checkBT[i] = new JButton("修改");
		}
		while (rs.next()) {// 公布闲置物品数据到平台
			Box box = Box.createVerticalBox();
			mainUI.number = rs.getInt(7);
			mainUI.number1 = String.valueOf(mainUI.number);
			box.setName(mainUI.number1);
			JLabel name = new JLabel("用户名：" + rs.getString(1) + "");
			JLabel title = new JLabel("标题：" + rs.getString(3) + "");
			JLabel information = new JLabel("详细内容：" + rs.getString(4) + "");
			JLabel prize = new JLabel("价格：" + rs.getInt(5) + "");
			checkBT[x].setName(mainUI.number1);
			box.add(name);
			box.add(title);
			box.add(information);
			box.add(prize);
			box.add(checkBT[x]);
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
		ActionListener CheckListener = new ActionListener() {// 进入相对应的查看界面
			public void actionPerformed(ActionEvent event) {
				for (int t = 0; t < checkBT.length; t++) {
					if (event.getSource().equals(checkBT[t])) {
						mainUI.itemNumber = checkBT[t].getName();
						mainUI.aitemNumber = Integer.parseInt(mainUI.itemNumber);
						try {
							new CheckUI();
						} catch (ClassNotFoundException | SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		};
		for (int t = 0; t < checkBT.length; t++) {
			checkBT[t].addActionListener(CheckListener);
		}
	}

}
