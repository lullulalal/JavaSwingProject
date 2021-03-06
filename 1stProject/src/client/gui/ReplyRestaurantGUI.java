package client.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import vo.Member;
import vo.Restaurant;

public class ReplyRestaurantGUI extends JDialog {

	private JPanel north;
	private JLabel lbl_message;
	private JPanel center;
	private JPanel recommend;
	private ImageIcon image;
	private JLabel lbl_image;
	private JLabel lbl_name;
	private JLabel lbl_type;
	private JLabel lbl_score;
	private JLabel lbl_location;

	public ReplyRestaurantGUI(Restaurant restaurant,Member requestor, Member member) {

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		this.setTitle(requestor.getId());
		this.setBounds(screenSize.width - 260, screenSize.height - 190, 260, 190);

		north = new JPanel();
		FlowLayout flowLayout = (FlowLayout) north.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		getContentPane().add(north, BorderLayout.NORTH);

		lbl_message = null;
		if (member.getId().equals("�ܰ��� �߷�׻�")) {
			lbl_message = new JLabel("���� �? ��õ����!");
		} else
			lbl_message = new JLabel(member.getId() + "���� ��õ�� ��!");
		north.add(lbl_message);
		lbl_message.setFont(new Font("��������", Font.BOLD, 13));
		lbl_message.setForeground(new Color(255, 33, 9));


		center = new JPanel();
		center.setLayout(null);
		getContentPane().add(center, BorderLayout.CENTER);

		recommend = new JPanel();
		recommend.setLayout(null);
		recommend.setBounds(3, 1, 200, 74);
		recommend.setBackground(new Color(255, 190, 141));
		center.add(recommend);
		recommend.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("�����");
				
				DetailRestaurantGui.getDetailRIfoDlg().addPanel(restaurant, null);
				
				ReplyRestaurantGUI.this.dispose();
				super.mouseClicked(e);
			}
		});

		image = restaurant.getImages().get(3);
		Image getIcon = image.getImage().getScaledInstance(90, 70, Image.SCALE_DEFAULT);
		ImageIcon setImage = new ImageIcon(getIcon);

		lbl_image = new JLabel(setImage);
		lbl_image.setBounds(2, 2, 90, 70);
		recommend.add(lbl_image);
		
		lbl_name = new JLabel(restaurant.getRestaurantName());
		lbl_name.setBounds(96, 19, 105, 20);
		lbl_name.setFont(new Font("��������", Font.BOLD, 16));
		recommend.add(lbl_name);

		String type = null;
		if (restaurant.getCategory().getType() == 1) {
			type = "�� ��";
		} else if (restaurant.getCategory().getType() == 2) {
			type = "�� ��";
		} else if (restaurant.getCategory().getType() == 3) {
			type = "�� ��";
		} else if (restaurant.getCategory().getType() == 4) {
			type = "�� ��";
		}
		lbl_type = new JLabel(type);
		lbl_type.setFont(new Font("�����ٸ�����", Font.PLAIN, 10));
		recommend.add(lbl_type);
		lbl_type.setBounds(96, 39, 105, 20);
		
		
		String score = null;
		if (restaurant.getCategory().getEvaluation().getAverage() < 1.5) {
			score = "�١١١١�";
		} else if (restaurant.getCategory().getEvaluation().getAverage() < 2.5) {
			score = "�١١١ڡ�";
		} else if (restaurant.getCategory().getEvaluation().getAverage() < 3.5) {
			score = "�١١ڡڡ�";
		} else if (restaurant.getCategory().getEvaluation().getAverage() < 4.5) {
			score = "�١ڡڡڡ�";
		} else if (restaurant.getCategory().getEvaluation().getAverage() <= 5.0) {
			score = "�ڡڡڡڡ�";
		}
		lbl_score = new JLabel(score);
		lbl_score.setBounds(140, 0,105,20);
		lbl_score.setFont(new Font("�����ٸ�����", Font.PLAIN, 12));
		recommend.add(lbl_score);
		
		String address = restaurant.getCategory().getLocation().toString();
		String[] splitedAdr = address.split(" ");
		
		lbl_location = new JLabel(splitedAdr[2] + " " +splitedAdr[3]);
		lbl_location.setFont(new Font("�����ٸ�����", Font.PLAIN, 10));
		lbl_location.setBounds(96,57,105,20);
		recommend.add(lbl_location);
		
		repaint();
		
		new Thread(new PopupTime()).start();

		this.setVisible(true);

	}
	
	class PopupTime implements Runnable{

		@Override
		public void run() {
			try {
				Thread.sleep(20000); //10��
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			dispose();
			
		}
		
	}
}
