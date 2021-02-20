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
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;

public class mainUI extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static int AdminLoginStatus = 0;
	static int LoginStatus = 0;
	static String username = null;
	static String number1;
	static String itemNumber;
	static int aitemNumber;
	static int number;
	static JPanel p1 = new JPanel();
	private static int n = 0;
	private int x = 0;
	private JButton[] buyBt;
	private JMenuBar menu;
	private JMenuItem Login1, AdminLogin, exitLogin, info1, refresh, Search, AscSort, DescSort;
	private JMenu Login, info, UI, Sort;
	private JScrollPane sp;

	public mainUI() throws ClassNotFoundException, SQLException {// 闲置物品交易平台主界面
		this.setTitle("高校闲置物品交易平台");
		this.setVisible(true);
		this.setSize(400, 500);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.getContentPane().setBackground(Color.white);
		menu = new JMenuBar();// 菜单栏
		//
		Login = new JMenu("登录");
		Login1 = new JMenuItem("游客登录");
		AdminLogin = new JMenuItem("管理员登录");
		exitLogin = new JMenuItem("退出登录");
		//
		info = new JMenu("我的");
		info1 = new JMenuItem("个人信息");
		//
		UI = new JMenu("界面");
		refresh = new JMenuItem("刷新");
		Search = new JMenuItem("查找");
		Sort = new JMenu("价格排序");
		AscSort = new JMenuItem("升序");
		DescSort = new JMenuItem("降序");
		//
		p1.setBackground(Color.white);
		p1.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		sp = new JScrollPane(p1);
		this.add(sp);
		this.setJMenuBar(menu);
		menu.add(Login);
		menu.add(info);
		menu.add(UI);
		Login.add(Login1);
		Login.add(AdminLogin);
		Login.add(exitLogin);
		info.add(info1);
		UI.add(refresh);
		UI.add(Search);
		UI.add(Sort);
		Sort.add(AscSort);
		Sort.add(DescSort);
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/mysql?" + "useUnicode=true&characterEncoding=utf8&useSSL=false", "root",
				"mysql");
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("select*from aitemrelease where owner = 'admin'");
		rs.last();
		int rowCount = rs.getRow();
		buyBt = new JButton[rowCount];
		rs.beforeFirst();
		for (int i = 0; i < buyBt.length; i++) {
			buyBt[i] = new JButton("购买");
		}
		while (rs.next()) {// 公布闲置物品数据到平台
			Box box = Box.createVerticalBox();
			number = rs.getInt(7);
			number1 = String.valueOf(number);
			box.setName(number1);
			JLabel name = new JLabel("用户名：" + rs.getString(1) + "");
			JLabel title = new JLabel("标题：" + rs.getString(3) + "");
			JLabel information = new JLabel("详细内容：" + rs.getString(4) + "");
			JLabel prize = new JLabel("价格：" + rs.getInt(5) + "");
			buyBt[x].setName(number1);
			box.add(name);
			box.add(title);
			box.add(information);
			box.add(prize);
			box.add(buyBt[x]);
			x++;
			JSeparator sep = new JSeparator();
			sep.setPreferredSize(new Dimension(this.getWidth(), 5));
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

		ActionListener BuyListener = new ActionListener() {// 进入相对应的购买界面
			public void actionPerformed(ActionEvent event) {
				for (int t = 0; t < buyBt.length; t++) {
					if (event.getSource().equals(buyBt[t])) {

						itemNumber = buyBt[t].getName();
						aitemNumber = Integer.parseInt(itemNumber);
						try {
							new Buy();
						} catch (ClassNotFoundException | SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		};
		ActionListener LoginListener = new ActionListener() {// 物品发布者登录
			public void actionPerformed(ActionEvent event) {
				if (LoginStatus == 0 && AdminLoginStatus == 0) {
					LOGIN login = new LOGIN();
					login.Login();

				} else if (LoginStatus == 1) {
					JOptionPane.showMessageDialog(null, "您已登录物品发布者");
				} else if (AdminLoginStatus == 1) {
					JOptionPane.showMessageDialog(null, "您已登录管理员");
				}
			}
		};
		ActionListener AdminLoginListener = new ActionListener() {// 管理员登录
			public void actionPerformed(ActionEvent event) {
				if (LoginStatus == 0 && AdminLoginStatus == 0) {
					Admin adminL = new Admin();
					adminL.Login();
				} else if (LoginStatus == 1 && AdminLoginStatus == 0) {
					JOptionPane.showMessageDialog(null, "您已登录物品发布者,请先退出登录！");
				} else if (LoginStatus == 0 && AdminLoginStatus == 1) {
					JOptionPane.showMessageDialog(null, "您已登录了管理者！");
				}
			}
		};
		ActionListener ExitLoginListener = new ActionListener() {// 退出登录
			public void actionPerformed(ActionEvent event) {
				if (LoginStatus == 1 || AdminLoginStatus == 1) {
					JOptionPane.showMessageDialog(null, "退出成功!!");
					LoginStatus = 0;
					AdminLoginStatus = 0;
				} else if (LoginStatus == 0 && AdminLoginStatus == 0) {
					JOptionPane.showMessageDialog(null, "您还没有登录！");
				}

			}
		};
		ActionListener infoListener = new ActionListener() {// 查看个人信息
			public void actionPerformed(ActionEvent event) {
				if (LoginStatus == 0 && AdminLoginStatus == 0) {
					JOptionPane.showMessageDialog(null, "您还没有登录！");
				} else if (LoginStatus == 1) {
					try {
						SELLER seller = new SELLER();
						seller.info(username);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else if (AdminLoginStatus == 1) {
					try {
						AdminUI.AdminUI(username);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		};

		ActionListener RefreshListener = new ActionListener() {// 刷新界面
			public void actionPerformed(ActionEvent event) {
				try {
					Refresh.Refresh();
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		ActionListener SearchListener = new ActionListener() {// 查找界面
			public void actionPerformed(ActionEvent event) {
				 new SearchUI();
			}
		};
		ActionListener AscSortListener = new ActionListener() {// 升序
			public void actionPerformed(ActionEvent event) {
				try {
					SortUI.AscSort();
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		ActionListener DescSortListener = new ActionListener() {// 降序
			public void actionPerformed(ActionEvent event) {
				try {
					SortUI.DescSort();
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		for (int t = 0; t < buyBt.length; t++) {
			buyBt[t].addActionListener(BuyListener);
		}
		Login1.addActionListener(LoginListener);
		AdminLogin.addActionListener(AdminLoginListener);
		info1.addActionListener(infoListener);
		exitLogin.addActionListener(ExitLoginListener);
		refresh.addActionListener(RefreshListener);
		Search.addActionListener(SearchListener);
		AscSort.addActionListener(AscSortListener);
		DescSort.addActionListener(DescSortListener);
	}

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		new mainUI();

	}

}
