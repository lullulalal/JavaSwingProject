package client.gui;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import client.ClientManager;
import client.ClientReceiver;
import vo.Address;

public class FindAddressGUI extends JDialog {

	private String si_do;
	private String si_gun_gu;
	private String street_name;
	// north
	private JPanel north;
	private JPanel pnl_sigu;
	private JComboBox<String> si;
	private JComboBox<String> gu;

	private JPanel pnl_search;
	private JLabel lbl_search;
	private JTextField tf_search;
	private JButton btn_search;
	// center
	private JScrollPane center;
	private JList addressList;
	// south
	private JPanel south;
	private JButton btn_complete;
	private JButton btn_cancel;

	private int guiId = ClientReceiver.getGuiID();
	private LinkedBlockingQueue<Object> queue = new LinkedBlockingQueue<>();
	private ClientManager manager = new ClientManager();
	private ArrayList<Address> addresses;
	public static Address location;
	
	ActionListener insertGuiAction;

	public FindAddressGUI(ActionListener action,InsertRestaurantsGUI parents) {
		super(parents, Dialog.ModalityType.APPLICATION_MODAL);
		
		ClientReceiver.addQueue(guiId, queue);
		new Thread(new Handler(this)).start();

		this.insertGuiAction = action;
		
		this.setTitle("상세주소검색");
		this.setSize(320, 400);
		//this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);

		// north
		north = new JPanel();
		north.setLayout(new GridLayout(2, 0));

		pnl_sigu = new JPanel();
		FlowLayout flowLayout = (FlowLayout) pnl_sigu.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		si = new JComboBox<>();
		si.setModel(new DefaultComboBoxModel<>(new String[] { "서울특별시" }));
		gu = new JComboBox<>();
		gu.setModel(new DefaultComboBoxModel<>(
				new String[] { "강남구", "강동구", "강북구", "강서구", "관악구", "광진구", "구로구", "금천구", "노원구", "도봉구", "동대문구", "동작구",
						"마포구", "서대문구", "서초구", "성동구", "성북구", "송파구", "양천구", "영등포구", "용산구", "은평구", "종로구", "중구", "중랑구" }));
		pnl_sigu.add(si);
		pnl_sigu.add(gu);

		pnl_search = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) pnl_search.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		lbl_search = new JLabel("도로명");
		tf_search = new JTextField(14);
		btn_search = new JButton("검색");
		pnl_search.add(lbl_search);
		pnl_search.add(tf_search);
		pnl_search.add(btn_search);

		north.add(pnl_sigu);
		north.add(pnl_search);

		// center
		center = new JScrollPane();
		addressList = new JList<>();
		center.setViewportView(addressList);

		// south
		south = new JPanel();
		btn_complete = new JButton("완료");
		btn_cancel = new JButton("취소");
		south.add(btn_complete);
		south.add(btn_cancel);

		// 패널붙이기
		getContentPane().add(north, BorderLayout.NORTH);
		getContentPane().add(center, BorderLayout.CENTER);
		getContentPane().add(south, BorderLayout.SOUTH);

		// actionListener
		btn_search.addActionListener(new Action());
		btn_complete.addActionListener(new Action());

		this.setVisible(true);
	}

	private class Handler implements Runnable {
		FindAddressGUI gui;

		public Handler(FindAddressGUI gui) {
			this.gui = gui;
		}

		@Override
		public void run() {
			while (true) {
				try {
					Object[] receive = (Object[]) queue.take();
					String proto = (String) receive[1];

					switch (proto) {
					case "findAddress":
						addresses = (ArrayList<Address>) receive[2];
						addressList.setListData(addresses.toArray());
						break;
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

	class Action implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == btn_search) {
				si_do = (String) si.getSelectedItem();
				si_gun_gu = (String) gu.getSelectedItem();
				street_name = (String) tf_search.getText();
				Address address = new Address();
				address.setSido(si_do);
				address.setSigungu(si_gun_gu);
				address.setStreetName(street_name);
				manager.findAddresses(guiId, address);
			}
			if (e.getSource() == btn_complete) {
				location = (Address) addressList.getSelectedValue();
				ActionEvent equal0Event = new ActionEvent("", 0, "test");
				insertGuiAction.actionPerformed(equal0Event);
				if (location != null) {
					ClientReceiver.deleteQueue(guiId);
				}
			} else if (e.getSource() == btn_cancel) {
				ClientReceiver.deleteQueue(guiId);
			}
		}
	}
}
