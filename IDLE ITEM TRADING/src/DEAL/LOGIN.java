package DEAL;

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
import javax.swing.JTextField;

public class LOGIN extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void Login() {// 游客登录界面
		JFrame login = new JFrame("游客登录界面");
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
		JButton RegisterButton = new JButton("注册");
		login.add(usrname);
		login.add(username);
		login.add(psw);
		login.add(password);
		login.add(LoginButton);
		login.add(RegisterButton);
		ActionListener LoginListener = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
					String username1 = username.getText();
					String paw1 = password.getText();
					Class.forName("com.mysql.jdbc.Driver");
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql?"
							+ "useUnicode=true&characterEncoding=utf8&useSSL=false", "root", "mysql");
					Statement stmt = con.createStatement();
					ResultSet rs = stmt.executeQuery(
							"select*from account where account='" + username1 + "'and password='" + paw1 + "'");
					if (rs.next()) {
						JOptionPane.showMessageDialog(null, "登录成功!");
						login.dispose();
						mainUI.LoginStatus = 1;
						mainUI.username = username1;
					} else {
						JOptionPane.showMessageDialog(null, "登录失败,请重试!");
					}
					rs.close();
					rs = null;
					stmt.close();
					stmt = null;
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		};
		ActionListener RegisterListener = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				Register();
				login.setVisible(false);
			}
		};
		LoginButton.addActionListener(LoginListener);
		RegisterButton.addActionListener(RegisterListener);
	}

	public void Register() {// 游客注册界面
		JFrame Register = new JFrame("游客注册界面");
		Register.setSize(350, 200);
		Register.setLocationRelativeTo(null);
		Register.setLayout(new FlowLayout());
		Register.setVisible(true);
		Box box = Box.createHorizontalBox();
		Box text1 = Box.createVerticalBox();
		Box text2 = Box.createVerticalBox();
		JLabel name = new JLabel("用户名:");
		JLabel usrname = new JLabel("账号:");
		JLabel psw = new JLabel("密码:");
		JLabel repsw = new JLabel("确认密码:");
		JLabel contact = new JLabel("联系方式");
		JTextField JTname = new JTextField(13);
		JTextField username = new JTextField(13);
		JTextField password = new JTextField(12);
		JTextField password1 = new JTextField(12);
		JTextField contactT = new JTextField(12);
		JButton RegisterButton = new JButton("注册");
		text1.add(name);
		text1.add(usrname);
		text1.add(psw);
		text1.add(repsw);
		text1.add(contact);
		text2.add(JTname);
		text2.add(username);
		text2.add(password);
		text2.add(password1);
		text2.add(contactT);
		box.add(text1);
		box.add(Box.createHorizontalStrut(100));
		box.add(text2);
		Register.add(box);
		Register.add(RegisterButton);
		ActionListener RegisterListener = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
					Class.forName("com.mysql.jdbc.Driver");
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql?"
							+ "useUnicode=true&characterEncoding=utf8&useSSL=false", "root", "mysql");
					String name1 = JTname.getText();
					String username1 = username.getText();
					String paw1 = password.getText();
					String paw2 = password1.getText();
					String contact = contactT.getText();
					if (paw1.equals(paw2) && !paw1.equals("") && !name1.equals("") && !username1.equals("")
							&& !contact.equals("")) {
						PreparedStatement pre = con.prepareStatement("insert into account values(?,?,?,?)");
						pre.setString(1, username1);
						pre.setString(2, paw1);
						pre.setString(3, name1);
						pre.setString(4, contact);
						pre.executeUpdate();
						pre.close();
						pre = null;
						Register.dispose();
						JOptionPane.showMessageDialog(null, "注册成功");
					} else if (name1.equals("") || username1.equals("") || paw1.equals("") || paw2.equals("")
							|| contact.equals("")) {
						JOptionPane.showMessageDialog(null, "注册失败");
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

		RegisterButton.addActionListener(RegisterListener);
	}

}
