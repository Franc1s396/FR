package DEAL;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Admin {
	public void Login() {
		JFrame login = new JFrame("管理员登录界面");
		login.setSize(400, 120);
		login.setLocationRelativeTo(null);
		login.setLayout(new FlowLayout());
		login.setVisible(true);
		login.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JLabel usrname = new JLabel("用户名:");
		JLabel psw = new JLabel("密码:");
		JTextField username = new JTextField(13);
		JTextField password = new JTextField(12);
		JButton LoginButton = new JButton("登录");
		login.add(usrname);
		login.add(username);
		login.add(psw);
		login.add(password);
		login.add(LoginButton);
		ActionListener LoginListener = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
					Class.forName("com.mysql.jdbc.Driver");
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql?"
							+ "useUnicode=true&characterEncoding=utf8&useSSL=false", "root", "mysql");
					String username1 = username.getText();
					String paw1 = password.getText();
					Statement stmt = con.createStatement();
					ResultSet rs = stmt.executeQuery(
							"select*from adminaccount where account='" + username1 + "'and password='" + paw1 + "'");
					if (rs.next()) {
						JOptionPane.showMessageDialog(null, "登录成功");
						mainUI.username=username1;
						mainUI.AdminLoginStatus=1;
						login.dispose();
					} else {
						JOptionPane.showMessageDialog(null, "登录失败");
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
		LoginButton.addActionListener(LoginListener);
	}

}
