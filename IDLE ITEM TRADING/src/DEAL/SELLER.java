package DEAL;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class SELLER extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static String name;

	public void info(String username) throws Exception {

		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/mysql?" + "useUnicode=true&characterEncoding=utf8&useSSL=false", "root",
				"mysql");
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("select*from account where account='" + username + "'");
		while (rs.next()) {
			name = rs.getString(3);
		}
		Box box = Box.createHorizontalBox();
		Box text1 = Box.createVerticalBox();
		JFrame info = new JFrame("个人信息");
		info.setSize(300, 300);
		info.setVisible(true);
		info.setLocationRelativeTo(null);
		info.setLayout(new FlowLayout());
		info.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JLabel label = new JLabel("用户名：" + name);
		JButton ReleaseBt = new JButton("物品发布");
		JButton MyReleaseBt = new JButton("我发布的");
		JButton PswChangeBt = new JButton("修改密码");
		JButton OrderListBt = new JButton("订单列表");
		JButton MyOrderBt = new JButton("交易订单");
		text1.add(label);
		text1.add(PswChangeBt);
		text1.add(ReleaseBt);
		text1.add(MyReleaseBt);
		text1.add(OrderListBt);
		text1.add(MyOrderBt);
		box.add(text1);
		info.add(box);
		ActionListener PasswordChangeListener = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				PswChange pswchange = new PswChange();
				try {
					pswchange.pswChange();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		ActionListener ItemReleaseListener = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
					new ItemRelease();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		ActionListener MyReleaseListener = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
					MyRelease.MyRelease();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		ActionListener OrderListListener = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
					OrderList order=new OrderList();
					order.OrderList();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		ActionListener MyOrderListener = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
					MyOrder.MyOrder();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		PswChangeBt.addActionListener(PasswordChangeListener);
		ReleaseBt.addActionListener(ItemReleaseListener);
		MyReleaseBt.addActionListener(MyReleaseListener);
		OrderListBt.addActionListener(OrderListListener);
		MyOrderBt.addActionListener(MyOrderListener);
		rs.close();
		stmt.close();
		con.close();
	}
}
