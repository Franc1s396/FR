package DEAL;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;

public class SearchUI  {
	/**
	 * 
	 */
	
	private JFrame SearchUI;
	private JLabel search, account, price, prizeS;
	private JTextField searchJT, accountJT, prizeJT1, prizeJT2;
	private JButton searchBT;
	private JPanel p1;
	private JRadioButton jr1, jr2;
	private ButtonGroup bg;
	private boolean sortBoolean = true;

	class search {
		private JFrame J;
		private int n = 0;
		private String number1;

		public void SearchUI(SinglyList<Integer> list) throws ClassNotFoundException, SQLException {
			J = new JFrame();
			J.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			J.setTitle("查找物品");
			J.setVisible(true);
			J.setSize(400, 500);
			J.setLocationRelativeTo(null);
			J.setLayout(new FlowLayout());
			J.getContentPane().setBackground(Color.white);
			p1 = new JPanel();
			JButton[] buyBt = new JButton[list.size()];
			p1.setBackground(Color.white);
			p1.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
			JScrollPane sp = new JScrollPane(p1);
			J.add(sp);
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/mysql?" + "useUnicode=true&characterEncoding=utf8&useSSL=false",
					"root", "mysql");
			Statement stmt = con.createStatement();
			for (int i = 0; i < list.size(); i++) {
				ResultSet rs = stmt.executeQuery("select*from aitemrelease where number= '" + list.get(i) + "'");
				while (rs.next()) {
					Box box = Box.createVerticalBox();
					number1 = String.valueOf(list.get(i));
					JLabel name = new JLabel("用户名：" + rs.getString(1) + "");
					JLabel title = new JLabel("标题：" + rs.getString(3) + "");
					JLabel information = new JLabel("详细内容：" + rs.getString(4) + "");
					JLabel prize = new JLabel("价格：" + rs.getInt(5) + "");
					buyBt[i] = new JButton("购买");
					buyBt[i].setName(number1);
					box.add(name);
					box.add(title);
					box.add(information);
					box.add(prize);
					box.add(buyBt[i]);
					p1.add(box);
					JSeparator sep = new JSeparator();
					sep.setPreferredSize(new Dimension(J.getWidth(), 5));
					sep.setBackground(new Color(153, 153, 153));
					p1.add(sep);
					n += 116;
					p1.setPreferredSize(new Dimension(385, n));
				}
				rs.close();
			}

			ActionListener BuyListener = new ActionListener() {// 进入相对应的购买界面
				public void actionPerformed(ActionEvent event) {
					for (int t = 0; t < buyBt.length; t++) {
						if (event.getSource().equals(buyBt[t])) {
							String number2 = buyBt[t].getName();
							int number3 = Integer.parseInt(number2);
							mainUI.aitemNumber = number3;
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
			for (int t = 0; t < buyBt.length; t++) {
				buyBt[t].addActionListener(BuyListener);
			}
		}
	}

	public SearchUI() {
		SearchUI = new JFrame();
		SearchUI.setVisible(true);
		SearchUI.setLayout(null);
		SearchUI.setTitle("查找物品界面");
		SearchUI.setSize(300, 210);
		SearchUI.setLocationRelativeTo(null);
		SearchUI.setBackground(Color.white);
		search = new JLabel("关键字:");
		account = new JLabel("用户:");
		price = new JLabel("价格区间:");
		prizeS = new JLabel("——");
		searchJT = new JTextField(10);
		accountJT = new JTextField(10);
		prizeJT1 = new JTextField(5);
		prizeJT2 = new JTextField(5);
		searchBT = new JButton("查找");
		jr1 = new JRadioButton("升序");
		jr2 = new JRadioButton("降序");
		bg = new ButtonGroup();
		bg.add(jr1);
		bg.add(jr2);
		search.setBounds(15, -30, 100, 100);
		account.setBounds(15, -5, 100, 100);
		price.setBounds(15, 20, 100, 100);
		searchJT.setBounds(150, 10, 120, 20);
		accountJT.setBounds(150, 35, 120, 20);
		prizeJT1.setBounds(150, 60, 50, 20);
		prizeS.setBounds(200, 20, 150, 100);
		prizeJT2.setBounds(220, 60, 50, 20);
		searchBT.setBounds(100, 130, 80, 30);
		jr1.setBounds(40, 90, 60, 20);
		jr2.setBounds(140, 90, 60, 20);
		SearchUI.add(search);
		SearchUI.add(account);
		SearchUI.add(price);
		SearchUI.add(searchJT);
		SearchUI.add(accountJT);
		SearchUI.add(prizeJT1);
		SearchUI.add(new JLabel("————"));
		SearchUI.add(prizeJT2);
		SearchUI.add(searchBT);
		SearchUI.add(prizeS);
		SearchUI.add(jr1);
		SearchUI.add(jr2);
		ActionListener SearchListener = new ActionListener() {//
			public void actionPerformed(ActionEvent event) {
				try {
					search Search = new search();
					String search = searchJT.getText();
					String account = accountJT.getText();
					String price1 = prizeJT1.getText();
					String price2 = prizeJT2.getText();

					SinglyList<Integer> list = new SinglyList<>();// 用单链表存储查询到的物品号
					Class.forName("com.mysql.jdbc.Driver");
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql?"
							+ "useUnicode=true&characterEncoding=utf8&useSSL=false", "root", "mysql");
					Statement stmt = con.createStatement();

					if (search.equals("") && account.equals("") && price1.equals("") && price2.equals("")) {// 没有输入任何关键字
						JOptionPane.showMessageDialog(null, "请输入关键字!");
					} else if (!search.equals("") && account.equals("") && price1.equals("") && price2.equals("")) {// 只输入了关键字
						ResultSet rs = stmt.executeQuery("select*from aitemrelease");
						while (rs.next()) {
							if (rs.getString(3).indexOf(search) != -1 || rs.getString(4).indexOf(search) != -1) {
								list.insert(rs.getInt(7));
							}
						}
						Search.SearchUI(list);
						rs.close();
					} else if (search.equals("") && !account.equals("") && price1.equals("") && price2.equals("")) {// 只输入了用户名，结果出现该用户所发布的物品
						ResultSet rs1 = stmt.executeQuery("select*from aitemrelease where account='" + account + "'");
						while (rs1.next()) {
							list.insert(rs1.getInt(7));
						}
						Search.SearchUI(list);
						rs1.close();
					} else if (search.equals("") && account.equals("") && !price1.equals("") && !price2.equals("")) {// 只输入了价格区间，结果出现符合价格区间的物品
						int priceNo1 = Integer.parseInt(price1);
						int priceNo2 = Integer.parseInt(price2);
						ResultSet rs2 = stmt.executeQuery(
								"select*from aitemrelease where price between " + priceNo1 + " and " + priceNo2 + "");
						while (rs2.next()) {
							list.insert(rs2.getInt(7));
						}
						Search.SearchUI(list);
						rs2.close();
					} else if (!search.equals("") && !account.equals("") && price1.equals("") && price2.equals("")) {// 输入了关键字还有用户名，结果同理可得
						ResultSet rs3 = stmt.executeQuery("select*from aitemrelease where account='" + account + "'");
						while (rs3.next()) {
							if (rs3.getString(3).indexOf(search) != -1 || rs3.getString(4).indexOf(search) != -1) {
								list.insert(rs3.getInt(7));
							}
						}
						Search.SearchUI(list);
						rs3.close();
					} else if (!search.equals("") && account.equals("") && !price1.equals("") && !price2.equals("")) {// 输入了关键字还有价格区间，结果同理可得
						int priceNo1 = Integer.parseInt(price1);
						int priceNo2 = Integer.parseInt(price2);
						ResultSet rs4 = stmt.executeQuery(
								"select*from aitemrelease where price between " + priceNo1 + " and " + priceNo2 + "");
						while (rs4.next()) {
							if (rs4.getString(3).indexOf(search) != -1 || rs4.getString(4).indexOf(search) != -1) {
								list.insert(rs4.getInt(7));
							}
						}
						Search.SearchUI(list);
						rs4.close();
					} else if (!search.equals("") && !account.equals("") && !price1.equals("") && !price2.equals("")) {// 输入了所有关键字，结果同理可得
						int priceNo1 = Integer.parseInt(price1);
						int priceNo2 = Integer.parseInt(price2);
						ResultSet rs5 = stmt.executeQuery("select*from aitemrelease where account='" + account
								+ "' price between " + priceNo1 + " and " + priceNo2 + "");
						while (rs5.next()) {
							if (rs5.getString(3).indexOf(search) != -1 || rs5.getString(4).indexOf(search) != -1) {
								list.insert(rs5.getInt(7));
							}
						}
						Search.SearchUI(list);
						rs5.close();
					}
					list.clear();
					stmt.close();
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
		searchBT.addActionListener(SearchListener);
	}

}
