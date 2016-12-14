package client;

import java.awt.BorderLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;

public class RecommendGUI extends JDialog{
	
	private JPanel north;
	private JPanel pnl_area;
	private JPanel pnl_type;
	private JPanel pnl_score;
	private JComboBox<String> cb_area;
	private JComboBox<String> cb_type;
	private JComboBox<String> cb_score;
	
	private JPanel panel;
	private JLabel lbl_random;
	private JLabel lbl_user;
	private JButton btn_random;
	private JButton btn_user;

	public RecommendGUI() {
		//super(main, Dialog.ModalityType.APPLICATION_MODAL);
		this.setSize(320,264);
		
		north = new JPanel();
		getContentPane().add(north,BorderLayout.NORTH);
		
		pnl_area = new JPanel();
		cb_area = new JComboBox<>();
		cb_area.setFont(new Font("맑은 고딕", Font.PLAIN, 11));
		cb_area.setModel(new DefaultComboBoxModel<>(new String[] {"지역","강남구", "강동구", "강북구", "강서구", "관악구", "광진구", "구로구", "금천구", "노원구", "도봉구", "동대문구", "동작구",
						"마포구", "서대문구", "서초구", "성동구", "성북구", "송파구", "양천구", "영등포구", "용산구", "은평구", "종로구", "중구", "중랑구"}));
		pnl_type = new JPanel();
		cb_type = new JComboBox<>();
		cb_type.setFont(new Font("맑은 고딕", Font.PLAIN, 11));
		cb_type.setModel(new DefaultComboBoxModel<>(new String[] {"종류","한식","중식","일식","양식"}));
		
		pnl_score = new JPanel();
		cb_score = new JComboBox<>();
		cb_score.setFont(new Font("맑은 고딕", Font.PLAIN, 11));
		cb_score.setModel(new DefaultComboBoxModel<>(new String[] {"평점","☆☆☆☆★","☆☆☆★★","☆☆★★★","☆★★★★","★★★★★"}));
		
		north.add(cb_area);
		north.add(cb_type);
		north.add(cb_score);
		
		panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		lbl_random = new JLabel(new ImageIcon("img/user.png"));
		lbl_random.setBounds(32, 15, 47, 49);
		panel.add(lbl_random);
		
		lbl_user = new JLabel(new ImageIcon("img/lbl_user.png"));
		lbl_user.setBounds(32, 75, 47, 49);
		panel.add(lbl_user);
		
		btn_random = new JButton("랜덤으로 추천받기");
		btn_random.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		btn_random.setBounds(95, 20, 133, 36);
		panel.add(btn_random);
		
		btn_user = new JButton("접속자에게 추천받기");
		btn_user.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		btn_user.setBounds(95, 82, 133, 36);
		panel.add(btn_user);
		
		//actionListener
		btn_random.addActionListener(new Action());
		btn_user.addActionListener(new Action());

		this.setVisible(true);
	}
	
	class Action implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == btn_random){
				String area = (String) cb_area.getSelectedItem();
				String type = (String) cb_type.getSelectedItem();
				double score = 0.0;
				if(cb_score.getSelectedItem() == "☆☆☆☆★"){
					score = 1.0;
				} else if(cb_score.getSelectedItem() == "☆☆☆★★"){
					score = 2.0;
				} else if(cb_score.getSelectedItem() == "☆☆★★★"){
					score = 3.0;
				} else if(cb_score.getSelectedItem() == "☆★★★★"){
					score = 4.0;
				} else if(cb_score.getSelectedItem() == "★★★★★"){
					score = 5.0;
				} RandomRecommGUI randomGui = new RandomRecommGUI(area, type, score);
				randomGui.setVisible(true);
			} else if(e.getSource() == btn_user){
				//현재 접속자들에게 추천요청메시지 broadcast
			} 
		}
		
	}
	
	public static void main(String[] args) {
		new RecommendGUI();
	}
	

}
