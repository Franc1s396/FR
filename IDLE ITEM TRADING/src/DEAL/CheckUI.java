package DEAL;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class CheckUI {
	private static int deliver;

	public CheckUI() throws SQLException, ClassNotFoundException {
		JFrame CheckJF = new JFrame();
		CheckJF.setTitle("物品信息修改");
		CheckJF.setSize(300, 300);
		CheckJF.setLocationRelativeTo(null);
		CheckJF.setLayout(null);
		CheckJF.setVisible(true);
		CheckJF.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JLabel title = new JLabel("标题:");
		JLabel detailInfo = new JLabel("详细说明:");
		JLabel price = new JLabel("价格:");
		JTextField titleT = new JTextField(18);
		JTextField priceT = new JTextField(18);
		JTextArea Info = new JTextArea();
		Info.setLineWrap(true);
		JButton sureBt = new JButton("修改");
		JButton delBt = new JButton("删除");
		title.setBounds(35, -30, 100, 100);
		titleT.setBounds(70, 10, 150, 20);
		detailInfo.setBounds(10, -5, 100, 100);
		Info.setBounds(70, 35, 150, 130);
		price.setBounds(35, 128, 100, 100);
		priceT.setBounds(70, 170, 150, 20);
		sureBt.setBounds(60, 200, 80, 30);
		delBt.setBounds(150, 200, 80, 30);
		Border b1 = BorderFactory.createLineBorder(Color.black, 0);
		Border b2 = BorderFactory.createEtchedBorder();
		Info.setBorder(BorderFactory.createCompoundBorder(b1, b2));
		CheckJF.add(title);
		CheckJF.add(titleT);
		CheckJF.add(detailInfo);
		CheckJF.add(Info);
		CheckJF.add(price);
		CheckJF.add(priceT);
		CheckJF.add(sureBt);
		CheckJF.add(delBt);
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/mysql?" + "useUnicode=true&characterEncoding=utf8&useSSL=false", "root",
				"mysql");
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("select * from aitemrelease where number='" + mainUI.aitemNumber + "'");
		while (rs.next()) {
			String inprice = Integer.toString(rs.getInt(5));
			titleT.setText(rs.getString(3));
			priceT.setText(inprice);
			Info.setText(rs.getString(4));
			deliver = rs.getInt(8);
		}
		rs.close();
		stmt.close();
		con.close();
		ActionListener UpdateListener = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
					String title = titleT.getText();
					String prizeTT = priceT.getText();
					int price = Integer.parseInt(prizeTT);
					if (!title.equals("") && !Info.getText().equals("") && !priceT.getText().equals("")) {
						Class.forName("com.mysql.jdbc.Driver");
						Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql?"
								+ "useUnicode=true&characterEncoding=utf8&useSSL=false", "root", "mysql");
						PreparedStatement pre = con.prepareStatement(
								"update aitemrelease set title=?,information=?,price=? where number=?");
						pre.setString(1, title);
						pre.setString(2, Info.getText());
						pre.setInt(3, price);
						pre.setInt(4, mainUI.aitemNumber);
						pre.executeUpdate();
						JOptionPane.showMessageDialog(null, "修改成功!");
						CheckJF.setVisible(false);
						pre.close();
						rs.close();
						stmt.close();
						con.close();
					} else {
						JOptionPane.showMessageDialog(null, "标题、详细内容、价格均不能为空！");
					}
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "请输入正确的价格!");
				}
			}
		};
		ActionListener DeleteBtListener = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
					Class.forName("com.mysql.jdbc.Driver");
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql?"
							+ "useUnicode=true&characterEncoding=utf8&useSSL=false", "root", "mysql");
					PreparedStatement pre = con.prepareStatement("delete from aitemrelease where number=?");
					if (deliver == 0 && mainUI.LoginStatus == 1) {
						pre.setInt(1, mainUI.aitemNumber);
						pre.executeUpdate();
						JOptionPane.showMessageDialog(null, "删除成功！");
						CheckJF.dispose();
						pre.close();
						con.close();
					} else {
						JOptionPane.showMessageDialog(null, "物品已经被下单！");
						con.close();
						pre.close();
					}
					if (mainUI.AdminLoginStatus == 1) {
						pre.setInt(1, mainUI.aitemNumber);
						pre.executeUpdate();
						JOptionPane.showMessageDialog(null, "删除成功！");
						CheckJF.dispose();
						pre.close();
						con.close();
					}
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		sureBt.addActionListener(UpdateListener);
		delBt.addActionListener(DeleteBtListener);
	}
}
