package client.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import client.ClientManager;
import client.ClientReceiver;
import client.Config;
import vo.Address;
import vo.Category;
import vo.Member;
import javax.swing.JButton;

public class AskRestaurant extends JDialog{
	
	private JPanel north;
	private JPanel center;
	private JPanel recommend;
	private ImageIcon image;
	private JLabel lbl_area;
	private JLabel lbl_type;
	private JLabel lbl_score;
	private JLabel lbl_location;
	
	private int guiId = ClientReceiver.getGuiID();
	private LinkedBlockingQueue<Object> queue = new LinkedBlockingQueue<>();
	private ClientManager manager = new ClientManager();
	
	public AskRestaurant(Category category, Member member) {
		ClientReceiver.addQueue(guiId, queue);
		new Thread(new Handler(this)).start();
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		this.setTitle(member.getId() + "님이 배고파요!");
		this.setBounds(screenSize.width - 260, screenSize.height - 190, 260, 190);
		
		Font font = new Font("나눔고딕", Font.BOLD, 12);
		
		//north 라벨
		north = new JPanel();
		FlowLayout flowLayout = (FlowLayout) north.getLayout();
		flowLayout.setAlignment(FlowLayout.CENTER);
		getContentPane().add(north, BorderLayout.NORTH);
		
		String area = category.getLocation().getSigungu().toString();
		lbl_area = new JLabel(area);
		lbl_area.setFont(font);
		lbl_area.setForeground(new Color(255, 33, 9));
		north.add(lbl_area);
		
		JLabel lbl_1 = new JLabel("의");
		lbl_1.setFont(font);
		north.add(lbl_1);
		
		JLabel lbl_2 = new JLabel("평점");
		lbl_2.setFont(font);
		north.add(lbl_2);
		
		String score = null;
		if (category.getEvaluation().getAverage() >= 5.0) {
			score = "5.0";
		} else if (category.getEvaluation().getAverage() >= 4.0) {
			score = "4.0";
		} else if (category.getEvaluation().getAverage() >= 3.0) {
			score = "3.0";
		} else if (category.getEvaluation().getAverage() >= 2.0) {
			score = "2.0";
		} else if (category.getEvaluation().getAverage() >= 1.0) {
			score = "1.0";
		}
		lbl_score = new JLabel(score);
		lbl_score.setFont(font);
		lbl_score.setForeground(new Color(255, 33, 9));
		north.add(lbl_score);
		
		JLabel lbl_3 = new JLabel("이상");
		lbl_3.setFont(font);
		north.add(lbl_3);
		
		String type = null;
		if (category.getType() == 1) {
			type = "한식";
		} else if (category.getType() == 2) {
			type = "중식";
		} else if (category.getType() == 3) {
			type = "일식";
		} else if (category.getType() == 4) {
			type = "양식";
		}
		lbl_type = new JLabel(type);
		lbl_type.setFont(font);
		lbl_type.setForeground(new Color(255, 33, 9));
		north.add(lbl_type);
		
		JLabel lbl_4 = new JLabel("맛집");
		lbl_4.setFont(font);
		north.add(lbl_4);
		
		
		//center 추천하기/안하기 버튼
		JPanel center = new JPanel();
		center.setLayout(null);
		getContentPane().add(center, BorderLayout.CENTER);
		
		ImageIcon getEene = new ImageIcon("img/eeneBlack.png");
		JLabel lbl_eene = new JLabel(getEene);
		lbl_eene.setSize(32, 32);
		lbl_eene.setLocation(27, 6);
		center.add(lbl_eene);
		
		JButton btn_recomm = new JButton("추천하기");
		btn_recomm.setBounds(70, 6, 114, 32);
		btn_recomm.setFont(new Font("나눔고딕", Font.BOLD, 14));
		btn_recomm.setForeground(new Color(0, 0, 0));
		center.add(btn_recomm);
		btn_recomm.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			//	category.getEvaluation().setUser(member);
				manager.showList(ClientReceiver.MAIN_GUI_ID, category, 0, Config.RESTAURANT_TABLE, member);
				dispose();
				//ClientReceiver.deleteQueue(guiId);
			}
		});
		
		ImageIcon getJump = new ImageIcon("img/jumpGray.png");
		JLabel lbl_jump = new JLabel(getJump);
		lbl_jump.setSize(32, 32);
		lbl_jump.setLocation(27, 45);
		center.add(lbl_jump);
		
		JButton btn_recommX = new JButton("건너뛰기");
		btn_recommX.setBounds(70, 45, 114, 32);
		btn_recommX.setFont(new Font("나눔고딕", Font.BOLD, 14));
		btn_recommX.setForeground(new Color(75, 75, 75));
		center.add(btn_recommX);
		btn_recommX.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ClientReceiver.deleteQueue(guiId);
			}
		});
		
		this.setVisible(true);
	}
	
	private class Handler implements Runnable {
		AskRestaurant gui;

		public Handler(AskRestaurant gui) {
			this.gui = gui;
		}

		@Override
		public void run() {
			while (true) {
				try {
					Object[] receive = (Object[]) queue.take();
					String proto = (String) receive[1];

					switch (proto) {
					case "exit":
						if (gui != null) {
							System.out.println("dispose");
							gui.dispose();
						}
						return;
					}

				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
