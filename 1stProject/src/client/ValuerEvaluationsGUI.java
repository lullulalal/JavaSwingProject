package client;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import java.awt.GridLayout;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class ValuerEvaluationsGUI extends JFrame{

	private JButton btn_insert;
	private JButton btn_cancel;
	private JComboBox sido;
	private JComboBox gungu;
	private JTextField specific;
	private JPanel location;
	private JComboBox food_type;
	private JTextField restaurantName;
	private JPanel score;
	private JComboBox score_taste;
	private JComboBox score_service;
	private JComboBox score_hygeine;
	private JPanel center;
	private JPanel label;
	private JLabel comments;
	
	public ValuerEvaluationsGUI() {
		this.setBounds(525,150,330, 500);
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		north();
		south();
		center();
		this.setVisible(true);
	}
	
	public void south(){
		JPanel south = new JPanel();
		getContentPane().add(south, BorderLayout.SOUTH);
		
		btn_insert = new JButton("insert");
		south.add(btn_insert);
		
		btn_cancel = new JButton("cancel");
		south.add(btn_cancel);
	}
	
	public void north(){
		JPanel north = new JPanel();
		getContentPane().add(north, BorderLayout.NORTH);
		north.setLayout(new GridLayout(4,1));
		
		location = new JPanel();
		north.add(location);
		
		sido = new JComboBox();
		location.add(sido);
		sido.setModel(new DefaultComboBoxModel(new String[] {"서울특별시"}));
		
		gungu = new JComboBox();
		location.add(gungu);
		gungu.setModel(new DefaultComboBoxModel(new String[] {"강남구", "관악구", "용산구", "영등포구"}));
		
		specific = new JTextField();
		specific.setColumns(10);
		location.add(specific);
		
		JPanel type = new JPanel();
		north.add(type);
		
		food_type = new JComboBox();
		food_type.setModel(new DefaultComboBoxModel(new String[] {"한식","중식","일식","양식"}));
		type.add(food_type);
		
		JLabel rName = new JLabel("   점포명: ");
		type.add(rName);
		restaurantName = new JTextField(10);
		type.add(restaurantName);
		
		score = new JPanel();
		north.add(score);
		
		score_taste = new JComboBox();
		score_taste.setModel(new DefaultComboBoxModel<>(new String[] {"taste","★★★★★","★★★★","★★★","★★","★"}));
		score.add(score_taste);
		
		score_service = new JComboBox();
		score_service.setModel(new DefaultComboBoxModel<>(new String[] {"service","★★★★★","★★★★","★★★","★★","★"}));
		score.add(score_service);
		
		score_hygeine = new JComboBox();
		score_hygeine.setModel(new DefaultComboBoxModel<>(new String[] {"hygeine","★★★★★","★★★★","★★★","★★","★"}));
		score.add(score_hygeine);
		
		label = new JPanel();
		north.add(label);
		label.setLayout(new BorderLayout(0, 0));
		
		comments = new JLabel("comments");
		comments.setVerticalAlignment(SwingConstants.BOTTOM);
		comments.setHorizontalAlignment(SwingConstants.LEFT);
		label.add(comments);
	}
	
	public void center(){
		center = new JPanel();
		getContentPane().add(center, BorderLayout.CENTER);
		center.setLayout(new GridLayout(2,0));
		
		JScrollPane scroll = new JScrollPane();
		JTextArea comments = new JTextArea();
		scroll.setViewportView(comments);
		center.add(scroll);
		
		
	}
	
	class Action implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
		}
		
	}
	
	public static void main(String[] args) {
		new ValuerEvaluationsGUI();
	}

}
