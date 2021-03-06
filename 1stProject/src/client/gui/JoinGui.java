package client.gui;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import client.ClientManager;
import client.ClientReceiver;
import client.Config;
import client.LoginStatement;
//import client.gui.JoinGui.Handler;
import vo.Member;
import vo.Restaurant;

public class JoinGui extends JDialog implements ActionListener {
	private static final int WIDTH = 300;
	private static final int HEIGHT = 330;
	private JTextField tf_id;
	private JPasswordField tf_pwd;
	private JTextField tf_name;
	private JTextField tf_year;
	
	private int guiId = ClientReceiver.getGuiID();
	private LinkedBlockingQueue<Object> queue = new LinkedBlockingQueue<>();
	
	private ClientManager manager = new ClientManager();
	private JTextField tf_month;
	private JTextField tf_day;
	
	public JoinGui(MainGui gui){
		
		super(gui, Dialog.ModalityType.APPLICATION_MODAL);
		ClientReceiver.addQueue(guiId, queue);
		new Thread(new Handler(this)).start();
		this.addWindowListener(new windowHandler());
		this.setSize(WIDTH, HEIGHT);
		
		Font font = new Font("나눔바른고딕", Font.PLAIN, 12);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 292, 292);
		panel.setBackground(new Color(255, 255, 255));
		getContentPane().add(panel);
		panel.setLayout(null);
		
		tf_id = new JTextField();
		tf_id.setBounds(103, 72, 123, 21);
		panel.add(tf_id);
		tf_id.setColumns(10);
		
		tf_pwd = new JPasswordField();
		tf_pwd.setBounds(103, 103, 123, 21);
		panel.add(tf_pwd);
		tf_pwd.setColumns(10);
		
		tf_name = new JTextField();
		tf_name.setBounds(103, 134, 123, 21);
		panel.add(tf_name);
		tf_name.setColumns(10);
		
		tf_year = new JTextField();
		tf_year.setBounds(103, 165, 32, 21);
		panel.add(tf_year);
		tf_year.setColumns(10);
		tf_year.setDocument((new JTextFieldLimit(2)));
		
		
		Font fontJoinLb = new Font("나눔바른고딕", Font.PLAIN, 25);
		JLabel lb_join = new JLabel("회원가입");
		lb_join.setBounds(23, 10, 203, 52);
		panel.add(lb_join);
		lb_join.setFont(fontJoinLb);
		
		JButton btn_ok = new JButton("\uD655\uC778");
		btn_ok.setBounds(96, 205, 50, 25);
		panel.add(btn_ok);
		btn_ok.setFont(font);
		btn_ok.addActionListener(this);
		
		JLabel lb_id = new JLabel("\uC544\uC774\uB514");
		lb_id.setBounds(23, 72, 57, 15);
		panel.add(lb_id);
		lb_id.setFont(font);
		
		JLabel lb_pwd = new JLabel("\uBE44\uBC00\uBC88\uD638");
		lb_pwd.setBounds(23, 104, 57, 15);
		panel.add(lb_pwd);
		
		
		JLabel lb_name = new JLabel("\uC774   \uB984");
		lb_name.setBounds(23, 137, 57, 15);
		panel.add(lb_name);
		lb_name.setFont(font);
		
		JLabel lb_birth = new JLabel("\uC0DD   \uC77C");
		lb_birth.setBounds(23, 168, 57, 15);
		panel.add(lb_birth);
		lb_birth.setFont(font);
		
		tf_month = new JTextField();
		tf_month.setColumns(10);
		tf_month.setBounds(148, 165, 32, 21);
		panel.add(tf_month);
		tf_month.setDocument((new JTextFieldLimit(2)));
		
		tf_day = new JTextField();
		tf_day.setColumns(10);
		tf_day.setBounds(194, 165, 32, 21);
		panel.add(tf_day);
		tf_day.setDocument((new JTextFieldLimit(2)));
		
		this.setBounds((int)gui.getLocation().getX() + 40, 
				(int)gui.getLocation().getY() + 150, WIDTH, HEIGHT);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("확인")){
			String id = tf_id.getText();
			String pwd = new String(tf_pwd.getPassword());
			String name = tf_name.getText();
			String birth = tf_year.getText() + "/" + tf_month.getText() + "/" +tf_day.getText();
			
			if( "".equals(tf_year.getText()) ||
					"".equals(tf_month.getText()) ||
					"".equals(tf_day.getText()))
				return;
			
			if( !"".equals(id) && !"".equals(pwd) &&
				!"".equals(name) && !"".equals(birth)	){
				
				Member newUser = new Member( id, pwd, 
						name, Member.USER, birth);
				
				manager.join(guiId, newUser);
				
			}
		}
		
	}
	
	private class windowHandler extends WindowAdapter{
		public void windowClosing (WindowEvent e){
			ClientReceiver.deleteQueue(guiId);
		}
	}
	
	private class Handler implements Runnable{
		JoinGui gui ;
		
		public Handler(JoinGui gui){
			this.gui = gui;
		}
		
		@Override
		public void run() {
			while(true) {
				try {
					Object[] receive = (Object[])queue.take();
					String proto = (String)receive[1];
					
					switch(proto){
					case "test" :
						System.out.println("테스트입니다");
						break;
						
					case "join":
						boolean rst = (Boolean)receive[2];
						if (rst == false )
							JOptionPane.showMessageDialog(gui, "중복된 아이디 입니다.");
						else
							ClientReceiver.deleteQueue(guiId);
						break;
						
						
					case "exit" :
						if(gui != null) {
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
	
	class JTextFieldLimit extends PlainDocument {
		private int limit;
		private boolean toUppercase = false;

		JTextFieldLimit(int limit) {
			super();
			this.limit = limit;
		}

		JTextFieldLimit(int limit, boolean upper) {
			super();
			this.limit = limit;
			this.toUppercase = upper;
		}

		public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
			if (str == null) {
				return;
			}

			if ( (getLength() + str.length()) <= limit) {
				if (toUppercase) {
					str = str.toUpperCase();
				}
				super.insertString(offset, str, attr);
			}
		}
	}
}
