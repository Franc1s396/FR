package DEAL;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class PswChange {
	public void pswChange() throws Exception {
		JFrame PasswordChange = new JFrame();
		PasswordChange.setTitle("修改密码界面");
		PasswordChange.setLocationRelativeTo(null);
		PasswordChange.setLayout(new FlowLayout());
		PasswordChange.setVisible(true);
		PasswordChange.setSize(450, 120);
		PasswordChange.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JLabel cpasswordJLabel = new JLabel("修改密码");
		JLabel sure = new JLabel("确认密码");
		JTextField newpassword = new JTextField(13);
		JTextField password1 = new JTextField(12);
		JButton sure1 = new JButton("确定");
		PasswordChange.add(cpasswordJLabel);
		PasswordChange.add(newpassword);
		PasswordChange.add(sure);
		PasswordChange.add(password1);
		PasswordChange.add(sure1);
		ActionListener cpswListener = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				PreparedStatement pre = null;
				String newpsw = newpassword.getText();
				String newpsw1 = password1.getText();
				try {
					if (mainUI.LoginStatus == 1) {
						if (newpsw.equals(newpsw1)) {
							Class.forName("com.mysql.jdbc.Driver");
							Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql?"
									+ "useUnicode=true&characterEncoding=utf8&useSSL=false", "root", "mysql");
							pre = con.prepareStatement("update account set password=? where account=?");
							pre.setString(2, mainUI.username);
							pre.setString(1, newpsw);
							pre.executeUpdate();
							JOptionPane.showMessageDialog(null, "修改成功");
							PasswordChange.setVisible(false);
							pre.close();
							con.close();
						} else {
							JOptionPane.showMessageDialog(null, "新密码与确认密码不符");
						}
					} else if (mainUI.AdminLoginStatus == 1) {
						if (newpsw.equals(newpsw1)) {
							Class.forName("com.mysql.jdbc.Driver");
							Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql?"
									+ "useUnicode=true&characterEncoding=utf8&useSSL=false", "root", "mysql");
							pre = con.prepareStatement("update adminaccount set password=? where account=?");
							pre.setString(2, mainUI.username);
							pre.setString(1, newpsw);
							pre.executeUpdate();
							JOptionPane.showMessageDialog(null, "修改成功");
							PasswordChange.setVisible(false);
							pre.close();
							con.close();
						} else {
							JOptionPane.showMessageDialog(null, "新密码与确认密码不符");
						}
					}
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		};
		sure1.addActionListener(cpswListener);
	}
}
