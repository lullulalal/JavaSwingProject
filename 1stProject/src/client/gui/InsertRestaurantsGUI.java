package client.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import client.ClientManager;
import client.ClientReceiver;
import vo.Address;
import vo.Category;
import vo.Evaluation;
import vo.Member;
import vo.Restaurant;

public class InsertRestaurantsGUI extends JFrame {
	static int imageIndex = 0;
	
	private int guiId = ClientReceiver.getGuiID();
	private LinkedBlockingQueue<Object> queue = new LinkedBlockingQueue<>();
	private ClientManager manager = new ClientManager();
	
	
	private JButton btn_insert;
	private JButton btn_cancel;
	private JPanel center;
	private JLabel lblNewLabel;
	private JPanel panel;
	private JButton btn_open;
	private JFileChooser openFile;
	private JTextField menu;
	private ImageIcon icon;
	private JTextArea tf_comments;
	private ArrayList<ImageIcon> images = new ArrayList<>();
	private JLabel lbl_image;
	private Restaurant restaurant;
	private JPanel pnl_photobox;
	private HashMap<Integer, JLabel> lbl_imagelist = new HashMap<>();
	private HashMap<Integer, ImageIcon> imageList = new HashMap<>();
	
	private JPanel pnl_name;
	private FlowLayout flowLayout;
	private JComboBox<String> cb_type;
	private JLabel lbl_name;
	private JTextField tf_name;
	private JPanel pnl_location;
	private JButton btn_location;
	private JTextField tf_location;
	private JPanel pnl_call;
	private JLabel lbl_call;
	private JTextField tf_call;
	private JTextField tf_call2;
	private JTextField tf_call3;
	private JLabel lbl_bar;
	private JLabel lbl_bar2;
	private JPanel pnl_menu;
	private JLabel lbl_menu;
	private JTextField tf_menu;
	private JPanel pnl_time;
	private JLabel lbl_time;
	private JTextField tf_openH;
	private JLabel lbl_colonS;
	private JTextField tf_openM;
	private JLabel lbl_con;
	private JTextField tf_closeH;
	private JLabel lbl_colonC;
	private JTextField tf_closeM;
	private JPanel pnl_price;
	private JLabel lbl_price;
	private JTextField tf_startP;
	private JLabel lbl_won1;
	private JLabel lbl_conP;
	private JTextField tf_endP;
	private JLabel lbl_won2;
	private JPanel pnl_score;
	private JComboBox<String> cb_taste;
	private JComboBox<String> cb_service;
	private JComboBox<String> cb_hygiene;
	private JLabel lbl_comments;
	private JPanel imagePanel;

	public InsertRestaurantsGUI() {
		ClientReceiver.addQueue(guiId, queue);
		new Thread(new Handler(this)).start();

		this.setSize(350, 650);
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		north();
		south();
		center();
		this.setVisible(true);
	}

	private class Handler implements Runnable {
		JFrame gui;

		public Handler(JFrame gui) {
			this.gui = gui;
		}

		@Override
		public void run() {
			while (true) {
				try {
					Object[] receive = (Object[]) queue.take();
					String proto = (String) receive[1];
					switch (proto) {
					case "insert":
						boolean result = (boolean) receive[2];
						if (result) {
							JOptionPane.showMessageDialog(gui, "등록성공");
						} else
							JOptionPane.showMessageDialog(gui, "등록실패");
					case "exit":
						if (gui != null) {
							gui.dispose();
							System.exit(0);
						}
						return;
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void south() {
		JPanel south = new JPanel();
		getContentPane().add(south, BorderLayout.SOUTH);

		btn_insert = new JButton("insert");
		south.add(btn_insert);
		btn_insert.addActionListener(new Action());

		btn_cancel = new JButton("cancel");
		south.add(btn_cancel);
		btn_cancel.addActionListener(new Action());
	}

	public void north() {
		JPanel north = new JPanel();
		north.setLayout(new GridLayout(8, 0));
		getContentPane().add(north, BorderLayout.NORTH);

		pnl_name = new JPanel();
		flowLayout = (FlowLayout) pnl_name.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		cb_type = new JComboBox<>();
		cb_type.setModel(new DefaultComboBoxModel<>(new String[] { "한식", "중식", "일식", "양식" }));
		lbl_name = new JLabel("상호명 ");;
		tf_name = new JTextField(14);
		pnl_name.add(cb_type);
		pnl_name.add(lbl_name);
		pnl_name.add(tf_name);

		pnl_location = new JPanel();
		FlowLayout flowLayout_4 = (FlowLayout) pnl_location.getLayout();
		flowLayout_4.setAlignment(FlowLayout.LEFT);
		btn_location = new JButton("주소찾기");
		tf_location = new JTextField(18);
		// tf_location.setText(FindAddressGUI.location.toString());
		pnl_location.add(btn_location);
		pnl_location.add(tf_location);

		pnl_call = new JPanel();
		FlowLayout flowLayout_5 = (FlowLayout) pnl_call.getLayout();
		flowLayout_5.setAlignment(FlowLayout.LEFT);
		lbl_call = new JLabel("전화번호");
		tf_call = new JTextField(3);
		tf_call2 = new JTextField(3);
		tf_call3 = new JTextField(4);
		lbl_bar = new JLabel("-");
		lbl_bar2 = new JLabel("-");
		pnl_call.add(lbl_call);
		pnl_call.add(tf_call);
		pnl_call.add(lbl_bar);
		pnl_call.add(tf_call2);
		pnl_call.add(lbl_bar2);
		pnl_call.add(tf_call3);

		pnl_menu = new JPanel();
		FlowLayout flowLayout_2 = (FlowLayout) pnl_menu.getLayout();
		flowLayout_2.setAlignment(FlowLayout.LEFT);
		lbl_menu = new JLabel("메뉴 ");
		tf_menu = new JTextField(21);
		pnl_menu.add(lbl_menu);
		pnl_menu.add(tf_menu);

		pnl_time = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) pnl_time.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		lbl_time = new JLabel("영업시간 ");
		tf_openH = new JTextField(2);
		lbl_colonS = new JLabel(":");
		tf_openM = new JTextField(2);
		lbl_con = new JLabel("~");
		tf_closeH = new JTextField(2);
		lbl_colonC = new JLabel(":");
		tf_closeM = new JTextField(2);
		pnl_time.add(lbl_time);
		pnl_time.add(tf_openH);
		pnl_time.add(lbl_colonS);
		pnl_time.add(tf_openM);
		pnl_time.add(lbl_con);
		pnl_time.add(tf_closeH);
		pnl_time.add(lbl_colonC);
		pnl_time.add(tf_closeM);

		pnl_price = new JPanel();
		FlowLayout flowLayout_3 = (FlowLayout) pnl_price.getLayout();
		flowLayout_3.setAlignment(FlowLayout.LEFT);
		lbl_price = new JLabel("가격대 ");
		tf_startP = new JTextField(5);
		lbl_won1 = new JLabel("원");
		lbl_conP = new JLabel("~");
		tf_endP = new JTextField(5);
		lbl_won2 = new JLabel("원");
		pnl_price.add(lbl_price);
		pnl_price.add(tf_startP);
		pnl_price.add(lbl_won1);
		pnl_price.add(lbl_conP);
		pnl_price.add(tf_endP);
		pnl_price.add(lbl_won2);

		pnl_score = new JPanel();
		cb_taste = new JComboBox<>();
		cb_taste.setFont(new Font("굴림", Font.PLAIN, 10));
		cb_taste.setModel(
				new DefaultComboBoxModel<>(new String[] { "맛", "☆☆☆☆★", "☆☆☆★★", "☆☆★★★", "☆★★★★", "★★★★★" }));
		cb_service = new JComboBox<>();
		cb_service.setFont(new Font("굴림", Font.PLAIN, 10));
		cb_service.setModel(
				new DefaultComboBoxModel<>(new String[] { "서비스", "☆☆☆☆★", "☆☆☆★★", "☆☆★★★", "☆★★★★", "★★★★★" }));
		cb_hygiene = new JComboBox<>();
		cb_hygiene.setFont(new Font("굴림", Font.PLAIN, 10));
		cb_hygiene.setModel(
				new DefaultComboBoxModel<>(new String[] { "청결도", "☆☆☆☆★", "☆☆☆★★", "☆☆★★★", "☆★★★★", "★★★★★" }));
		pnl_score.add(cb_taste);
		pnl_score.add(cb_service);
		pnl_score.add(cb_hygiene);

		lbl_comments = new JLabel("comments");
		lbl_comments.setVerticalAlignment(SwingConstants.BOTTOM);
		lbl_comments.setHorizontalAlignment(SwingConstants.LEFT);

		// north에 달기
		north.add(pnl_name);
		north.add(pnl_location);
		north.add(pnl_call);
		north.add(pnl_menu);
		north.add(pnl_time);
		north.add(pnl_price);
		north.add(pnl_score);
		north.add(lbl_comments);
		
		//actionListener
		btn_location.addActionListener(new Action());
	}

	public void center() {
		center = new JPanel();
		getContentPane().add(center, BorderLayout.CENTER);
		GridLayout gl_center = new GridLayout(2, 0);
		gl_center.setVgap(15);
		center.setLayout(gl_center);

		// 코멘트박스
		JScrollPane scroll = new JScrollPane();
		tf_comments = new JTextArea();
		scroll.setViewportView(tf_comments);
		center.add(scroll);

		imagePanel = new JPanel();
		center.add(imagePanel);
		imagePanel.setLayout(new BorderLayout(0, 0));

		lblNewLabel = new JLabel("photo");
		lblNewLabel.setVerticalAlignment(SwingConstants.BOTTOM);
		imagePanel.add(lblNewLabel, BorderLayout.NORTH);

		panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.TRAILING);
		imagePanel.add(panel, BorderLayout.SOUTH);

		btn_open = new JButton("Open");
		panel.add(btn_open);
		btn_open.addActionListener(new Action(this));

		openFile = new JFileChooser();
		pnl_photobox = new JPanel();
		lbl_image = new JLabel();

		FlowLayout flowLayout_1 = (FlowLayout) pnl_photobox.getLayout();
		flowLayout_1.setAlignment(FlowLayout.CENTER);
		lbl_image = new JLabel("사진을 추가해주세요!");
		pnl_photobox.add(lbl_image);
		imagePanel.add(pnl_photobox, BorderLayout.CENTER);
	}

	class Action implements ActionListener {
		InsertRestaurantsGUI gui;
		
		public Action(){
			
		}

		public Action(InsertRestaurantsGUI	gui){
			this.gui = gui;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			if(e.getActionCommand().equals("test")){
				tf_location.setText(FindAddressGUI.location.toString());
			}
			
			if (e.getSource() == btn_open) {
				int choosedFile = openFile.showOpenDialog(btn_open);
				if (choosedFile == JFileChooser.APPROVE_OPTION) {
					File file = openFile.getSelectedFile();
					icon = new ImageIcon(file.getPath());
					//images.add(icon);

					pnl_photobox.remove(lbl_image);
					
					FlowLayout flowLayout_1 = (FlowLayout) pnl_photobox.getLayout();
					flowLayout_1.setAlignment(FlowLayout.LEFT);

					ImageIcon iconLabel = new ImageIcon(
							new ImageIcon(file.getPath()).getImage().getScaledInstance(35, 33, Image.SCALE_DEFAULT));
					
					lbl_image = new JLabel(iconLabel);
					
					lbl_image.addMouseListener(new MouseAdapter() {
						int mapIndex = imageIndex;
						@Override
						public void mouseClicked(MouseEvent e) {
							super.mouseClicked(e);
							
							
							lbl_imagelist.remove(mapIndex);
							imageList.remove(mapIndex);
						//	pnl_photobox=null;
							//pnl_photobox.repaint();
							
							System.out.println("mouse " + lbl_imagelist.size());
		
							imagePanel.remove(pnl_photobox);
							
							JPanel pnl_ah = new JPanel();
							FlowLayout flowlayout = (FlowLayout) pnl_ah.getLayout();
							flowlayout.setAlignment(FlowLayout.LEFT);
			
							imagePanel.add(pnl_ah, BorderLayout.CENTER);
							//gui.repaint();
						//	pnl_ah.repaint();
							pnl_photobox = pnl_ah;
							for(Map.Entry<Integer, JLabel> entry : lbl_imagelist.entrySet()){
								pnl_photobox.add(entry.getValue());
							}
							gui.revalidate();
							gui.repaint();
							//
							//images.remove(o)
						}
					});
					
					lbl_imagelist.put(imageIndex++, lbl_image);
					imageList.put(imageIndex++, icon);
					
					for(Map.Entry<Integer, JLabel> entry : lbl_imagelist.entrySet()){
						pnl_photobox.add(entry.getValue());
					}
					
					
				}
			}
			
			if(e.getSource() == btn_location){
				System.out.println("생성");
				FindAddressGUI findAddress = new FindAddressGUI(this, InsertRestaurantsGUI.this);
				//findAddress.setVisible(true);
			}

			if (e.getSource() == btn_insert) {
				for(Map.Entry<Integer, ImageIcon> entry : imageList.entrySet()){
					images.add(entry.getValue());
				}
				String name = tf_name.getText();
				String price = tf_startP.getText() + "~" + tf_endP.getText();
				String Hour = tf_openH.getText() + ":" + tf_openM.getText() + "~" + tf_closeH.getText() + ":" + tf_closeM.getText();
				Address location = new Address();
				location.setPostCode(FindAddressGUI.location.getPostCode());
				location.setSido(FindAddressGUI.location.getSido());
				location.setSigungu(FindAddressGUI.location.getSigungu());
				location.setStreetName(FindAddressGUI.location.getStreetName());
				location.setEubmyundong(FindAddressGUI.location.getEubmyundong());
				location.setBuildPrimaryNo(FindAddressGUI.location.getBuildPrimaryNo());
				location.setBuildSecondaryNo(FindAddressGUI.location.getBuildSecondaryNo());
				location.setBuildingName(FindAddressGUI.location.getBuildingName());
				location.setPhone(tf_call.getText() + tf_call2.getText() + tf_call3.getText());
				
				Double taste = 0.0;
				if (cb_taste.getSelectedItem() == "★★★★★") {
					taste = 5.0;
				} else if (cb_taste.getSelectedItem() == "☆★★★★") {
					taste = 4.0;
				} else if (cb_taste.getSelectedItem() == "☆☆★★★") {
					taste = 3.0;
				} else if (cb_taste.getSelectedItem() == "☆☆☆★★") {
					taste = 2.0;
				} else if (cb_taste.getSelectedItem() == "☆☆☆☆★") {
					taste = 1.0;
				}
				Double service = 0.0;
				if (cb_service.getSelectedItem() == "★★★★★") {
					taste = 5.0;
				} else if (cb_service.getSelectedItem() == "☆★★★★") {
					taste = 4.0;
				} else if (cb_service.getSelectedItem() == "☆☆★★★") {
					taste = 3.0;
				} else if (cb_service.getSelectedItem() == "☆☆☆★★") {
					taste = 2.0;
				} else if (cb_service.getSelectedItem() == "☆☆☆☆★") {
					taste = 1.0;
				}
				Double hygiene = 0.0;
				if (cb_hygiene.getSelectedItem() == "★★★★★") {
					taste = 5.0;
				} else if (cb_hygiene.getSelectedItem() == "☆★★★★") {
					taste = 4.0;
				} else if (cb_hygiene.getSelectedItem() == "☆☆★★★") {
					taste = 3.0;
				} else if (cb_hygiene.getSelectedItem() == "☆☆☆★★") {
					taste = 2.0;
				} else if (cb_hygiene.getSelectedItem() == "☆☆☆☆★") {
					taste = 1.0;
				}
				Double average = (taste + service + hygiene) / 3;
				String comment = tf_comments.getText();
				Member user = new Member();
				user.setId("equal0"); // singleton Member class 만들어서 수정해야함
				Evaluation evaluation = new Evaluation(taste, service, hygiene, average, comment, user);
				int type = 0;
				if (cb_type.getSelectedItem() == "한식") {
					type = Category.KOREAN;
				} else if (cb_type.getSelectedItem() == "중식") {
					type = Category.CHINA;
				} else if (cb_type.getSelectedItem() == "일식") {
					type = Category.JAPAN;
				} else if (cb_type.getSelectedItem() == "양식") {
					type = Category.WESTERN;
				}
				Category category = new Category();
				category.setLocation(location);
				category.setEvaluation(evaluation);
				category.setType(type);
				String menus = tf_menu.getText();
				String[] splitedMenus = menus.split(",");
				ArrayList<String> menuList = new ArrayList<>();
				for (int i = 0; i < splitedMenus.length; i++) {
					menuList.add(splitedMenus[i]);
				} // 스플릿 예외가 발생하지 않도록 처리해야함
				restaurant = new Restaurant();
				restaurant.setRestaurantName(name);
				restaurant.setCategory(category);
				restaurant.setPrice(price);
				restaurant.setOperationHour(Hour);
				restaurant.setImages(images);
				restaurant.setMenu(menuList);

				manager.insertRestaurant(guiId, restaurant);
			} else if (e.getSource() == btn_cancel) {
				ClientReceiver.deleteQueue(guiId);
			}
		}
	}

}
