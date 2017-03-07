package client.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import client.ClientManager;
import client.ClientReceiver;
import client.LoginStatement;
import vo.Evaluation;
import vo.Restaurant;

public class UserEvaluationsGUI extends JDialog {
	// private int guiId = ClientReceiver.getGuiID();
	// private LinkedBlockingQueue<Object> queue = new LinkedBlockingQueue<>();
	private ClientManager manager = new ClientManager();

	private JPanel north;
	private JLabel score5;
	private JLabel score4;
	private JLabel score3;
	private JLabel score2;
	private JLabel score1;
	private JTextArea comments;
	private JButton btn_insert;
	private JButton btn_cancel;
	private JLabel lbl_red5 = new JLabel();
	private JLabel lbl_red4 = new JLabel();
	private JLabel lbl_red3 = new JLabel();
	private JLabel lbl_red2 = new JLabel();
	private JLabel lbl_red1 = new JLabel();
	private String selected;
	private Evaluation evaluation = new Evaluation();
	private Restaurant restaurant;

	public UserEvaluationsGUI(Restaurant r, JDialog jd) {
		super(jd, Dialog.ModalityType.APPLICATION_MODAL);
		this.restaurant = r;

		Point p = jd.getLocation();

		this.setBounds(p.x + 100, p.y + 200, 250, 230);
		// this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));
		north();
		south();
		center();
		this.setVisible(true);
	}

	public void north() {
		north = new JPanel();
		this.add(north, BorderLayout.NORTH);

		ImageIcon image5 = new ImageIcon("img/score5.png");
		ImageIcon image4 = new ImageIcon("img/score.png");
		ImageIcon image3 = new ImageIcon("img/score3.png");
		ImageIcon image2 = new ImageIcon("img/score2.png");
		ImageIcon image1 = new ImageIcon("img/score1.png");

		score5 = new JLabel(image5);
		score4 = new JLabel(image4);
		score3 = new JLabel(image3);
		score2 = new JLabel(image2);
		score1 = new JLabel(image1);

		north.add(score5);
		north.add(score4);
		north.add(score3);
		north.add(score2);
		north.add(score1);

		score5.addMouseListener(new MouseAction());
		score4.addMouseListener(new MouseAction());
		score3.addMouseListener(new MouseAction());
		score2.addMouseListener(new MouseAction());
		score1.addMouseListener(new MouseAction());
	}

	class MouseAction extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getSource() == score5) {
				ImageIcon red = new ImageIcon("img/red5.png");
				lbl_red5 = new JLabel(red);
				north.remove(score5);
				north.remove(lbl_red4);
				north.remove(lbl_red3);
				north.remove(lbl_red2);
				north.remove(lbl_red1);
				north.add(lbl_red5);
				north.add(score4);
				north.add(score3);
				north.add(score2);
				north.add(score1);
				north.revalidate();
				north.repaint();

				selected = "score5";
			}
			if (e.getSource() == score4) {
				ImageIcon red = new ImageIcon("img/red4.png");
				lbl_red4 = new JLabel(red);
				north.remove(score4);
				north.remove(lbl_red5);
				north.remove(lbl_red3);
				north.remove(lbl_red2);
				north.remove(lbl_red1);
				north.add(score5);
				north.add(lbl_red4);
				north.add(score3);
				north.add(score2);
				north.add(score1);
				north.revalidate();
				north.repaint();

				selected = "score4";
			}
			if (e.getSource() == score3) {
				ImageIcon red = new ImageIcon("img/red3.png");
				lbl_red3 = new JLabel(red);
				north.remove(score3);
				north.remove(lbl_red5);
				north.remove(lbl_red4);
				north.remove(lbl_red2);
				north.remove(lbl_red1);
				north.add(score5);
				north.add(score4);
				north.add(lbl_red3);
				north.add(score2);
				north.add(score1);
				north.revalidate();
				north.repaint();

				selected = "score3";
			}
			if (e.getSource() == score2) {
				ImageIcon red = new ImageIcon("img/red2.png");
				lbl_red2 = new JLabel(red);
				north.remove(score2);
				north.remove(lbl_red5);
				north.remove(lbl_red4);
				north.remove(lbl_red3);
				north.remove(lbl_red1);
				north.add(score5);
				north.add(score4);
				north.add(score3);
				north.add(lbl_red2);
				north.add(score1);
				north.revalidate();
				north.repaint();

				selected = "score2";
			}
			if (e.getSource() == score1) {
				ImageIcon red = new ImageIcon("img/red1.png");
				lbl_red1 = new JLabel(red);
				north.remove(score1);
				north.remove(lbl_red5);
				north.remove(lbl_red4);
				north.remove(lbl_red3);
				north.remove(lbl_red2);
				north.add(score5);
				north.add(score4);
				north.add(score3);
				north.add(score2);
				north.add(lbl_red1);
				north.revalidate();
				north.repaint();

				selected = "score1";
			}
		}
	}

	public void center() {
		JScrollPane scroll = new JScrollPane();
		comments = new JTextArea();
		comments.setFont(new Font("³ª´®¹Ù¸¥°íµñ", Font.PLAIN, 12));
		scroll.setViewportView(comments);
		this.add(scroll, BorderLayout.CENTER);
	}

	public void south() {
		JPanel south = new JPanel();
		this.add(south, BorderLayout.SOUTH);

		btn_insert = new JButton("insert");
		btn_cancel = new JButton("cancel");

		btn_insert.setFont(new Font("³ª´®¹Ù¸¥°íµñ", Font.PLAIN, 14));
		btn_cancel.setFont(new Font("³ª´®¹Ù¸¥°íµñ", Font.PLAIN, 14));

		south.add(btn_insert);
		south.add(btn_cancel);

		btn_insert.addActionListener(new Action());
		btn_cancel.addActionListener(new Action());
	}

	class Action implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == btn_insert) {
				if (selected == "score5") {
					evaluation.setAverage(5);
				} else if (selected == "score4") {
					evaluation.setAverage(4);
				} else if (selected == "score3") {
					evaluation.setAverage(3);
				} else if (selected == "score2") {
					evaluation.setAverage(2);
				} else if (selected == "score1") {
					evaluation.setAverage(1);
				}
				if (comments != null) {
					evaluation.setUser(LoginStatement.getLoginUser());
					evaluation.setComment(comments.getText());

					manager.evaluateRestaurant(DetailRestaurantGui.getDetailRIfoDlg().getGuiID(),
							evaluation, restaurant);
					UserEvaluationsGUI.this.dispose();
				}
			} else if (e.getSource() == btn_cancel) {
				UserEvaluationsGUI.this.dispose();
			}
		}
	}
}
