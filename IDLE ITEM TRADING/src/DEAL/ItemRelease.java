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

public class ItemRelease {

	public ItemRelease() {
		JFrame MyRe = new JFrame();
		MyRe.setTitle("物品发布");
		MyRe.setSize(300, 300);
		MyRe.setLocationRelativeTo(null);
		MyRe.setLayout(null);
		MyRe.setVisible(true);
		MyRe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JLabel title = new JLabel("标题:");
		JLabel detailInfo = new JLabel("详细说明:");
		JLabel prize = new JLabel("价格:");
		JTextField titleT = new JTextField(18);
		JTextField prizeT = new JTextField(18);
		JTextArea Info = new JTextArea();
		Info.setLineWrap(true);
		JButton sureBt = new JButton("发布");
		title.setBounds(35, -30, 100, 100);
		titleT.setBounds(70, 10, 150, 20);
		detailInfo.setBounds(10, -5, 100, 100);
		Info.setBounds(70, 35, 150, 130);
		prize.setBounds(35, 128, 100, 100);
		prizeT.setBounds(70, 170, 150, 20);
		sureBt.setBounds(100, 200, 80, 30);
		Border b1 = BorderFactory.createLineBorder(Color.black, 0);
		Border b2 = BorderFactory.createEtchedBorder();
		Info.setBorder(BorderFactory.createCompoundBorder(b1, b2));
		MyRe.add(title);
		MyRe.add(titleT);
		MyRe.add(detailInfo);
		MyRe.add(Info);
		MyRe.add(prize);
		MyRe.add(prizeT);
		MyRe.add(sureBt);
		ActionListener ItemReleaseListener = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				PreparedStatement pre = null;
				try {
					String title = titleT.getText();
					String prizeTT = prizeT.getText();
					int prize = Integer.parseInt(prizeTT);
					Class.forName("com.mysql.jdbc.Driver");
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql?"
							+ "useUnicode=true&characterEncoding=utf8&useSSL=false", "root", "mysql");
					Statement stmt = con.createStatement();
					ResultSet rs = stmt.executeQuery("select * from aitemrelease");
					rs.last();
					int rowCount = rs.getRow();
					if (rowCount == 0) {
						rowCount = 1;
					}
					pre = con.prepareStatement("insert into aitemrelease values(?,?,?,?,?,?,?,?)");
					pre.setString(1, SELLER.name);
					pre.setString(2, mainUI.username);
					pre.setString(3, title);
					pre.setString(4, Info.getText());
					pre.setInt(5, prize);
					pre.setString(6, "admin");
					pre.setInt(7, rowCount + 1);
					pre.setInt(8, 0);
					pre.executeUpdate();
					JOptionPane.showMessageDialog(null, "发布成功!");
					MyRe.dispose();
					pre.close();
					rs.close();
					stmt.close();
					con.close();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "请输入正确的价格");
				} finally {

					try {
						if (pre != null) {
							pre.close();
							pre = null;
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		};
		sureBt.addActionListener(ItemReleaseListener);
	}
}
