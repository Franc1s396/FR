package DEAL;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class AdminUI {

	public static void AdminUI(String username) {

		Box box = Box.createHorizontalBox();
		Box text1 = Box.createVerticalBox();
		JFrame info = new JFrame("个人信息");
		info.setSize(300, 300);
		info.setVisible(true);
		info.setLocationRelativeTo(null);
		info.setLayout(new FlowLayout());
		info.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JLabel label = new JLabel("管理员：" + username);
		JButton SearchBT = new JButton("查找信息");
		JButton AllOrderBT = new JButton("所有订单信息");
		JButton PswChangeBt = new JButton("修改管理员密码");
		JButton AllItemBT = new JButton("所有物品发布信息");
		JButton UserBT = new JButton("物品发布者管理");
		JButton IO = new JButton("导出文件");
		text1.add(label);
		text1.add(PswChangeBt);
		text1.add(SearchBT);
		text1.add(AllOrderBT);
		text1.add(AllItemBT);
		text1.add(UserBT);
		text1.add(IO);
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
		ActionListener AllOrderListener = new ActionListener() {
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
		ActionListener SearchListener = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
					new SearchUI();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		ActionListener AllItemListener = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
					MyRelease.MyRelease();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		ActionListener AllUserListener = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
					 new AllUser();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		AllOrderBT.addActionListener(AllOrderListener);
		PswChangeBt.addActionListener(PasswordChangeListener);
		SearchBT.addActionListener(SearchListener);
		AllItemBT.addActionListener(AllItemListener);
		UserBT.addActionListener(AllUserListener);
	}

}
