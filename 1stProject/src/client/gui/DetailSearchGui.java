package client.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Font;

public class DetailSearchGui extends JDialog{
	private JTextField tf_rname;
	public DetailSearchGui(JFrame mainGui){
		
		super(mainGui, Dialog.ModalityType.APPLICATION_MODAL);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 292, 223);
		panel.setBackground(new Color(255, 255, 255));
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JCheckBox chk_rec = new JCheckBox("\uB0B4\uAC00 \uCD94\uCC9C\uD55C \uB9DB\uC9D1");
		chk_rec.setFont(new Font("³ª´®¹Ù¸¥°íµñ", Font.PLAIN, 14));
		chk_rec.setBounds(37, 101, 138, 23);
		panel.add(chk_rec);
		
		JLabel lbl_detail = new JLabel("»ó¼¼ °Ë»ö");
		lbl_detail.setFont(new Font("³ª´®¹Ù¸¥°íµñ", Font.PLAIN, 18));
		lbl_detail.setBounds(26, 10, 160, 44);
		panel.add(lbl_detail);
		
		JCheckBox chk_reg = new JCheckBox("\uB0B4\uAC00 \uB4F1\uB85D\uD55C \uB9DB\uC9D1");
		chk_reg.setFont(new Font("³ª´®¹Ù¸¥°íµñ", Font.PLAIN, 14));
		chk_reg.setBounds(37, 126, 149, 23);
		panel.add(chk_reg);
		
		tf_rname = new JTextField();
		tf_rname.setFont(new Font("³ª´®¹Ù¸¥°íµñ", Font.PLAIN, 14));
		tf_rname.setBounds(62, 64, 109, 21);
		panel.add(tf_rname);
		tf_rname.setColumns(10);
		
		JButton btn_find = new JButton("\uAC80\uC0C9");
		btn_find.setFont(new Font("³ª´®¹Ù¸¥°íµñ", Font.PLAIN, 14));
		btn_find.setBounds(71, 156, 65, 25);
		panel.add(btn_find);
		
		JLabel lbl_name = new JLabel("\uC774\uB984");
		lbl_name.setFont(new Font("³ª´®¹Ù¸¥°íµñ", Font.PLAIN, 14));
		lbl_name.setBounds(26, 67, 24, 15);
		panel.add(lbl_name);
		
		this.setBounds((int)mainGui.getLocation().getX() + 40, 
				(int)mainGui.getLocation().getY() + 170, 250, 280);
	
		this.setVisible(true);
	}
}
