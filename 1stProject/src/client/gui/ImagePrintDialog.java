package client.gui;

import java.awt.Dialog;
import java.awt.Image;
import java.awt.Point;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;

public class ImagePrintDialog extends JDialog{
	
	public ImagePrintDialog(ImageIcon img, JDialog parents){
		
		super(parents, Dialog.ModalityType.APPLICATION_MODAL);
		
		//JPanel panel = new JPanel();
		//getContentPane().add(panel, BorderLayout.NORTH);
		//panel.setPreferredSize(new Dimension(500, 400));
		//panel.setBackground(new Color(0, 0, 0));
		
		Image hi = img.getImage().getScaledInstance(700, 550 , Image.SCALE_DEFAULT);
		ImageIcon resized = new ImageIcon(hi);
		
		JLabel lblNewLabel = new JLabel(resized);
		this.add(lblNewLabel);
		
		Point p = MainGui.getMainGui().getLocation();
		
		this.setBounds(p.x - 390,p.y,750,600);
		this.setVisible(true);
	}
}
