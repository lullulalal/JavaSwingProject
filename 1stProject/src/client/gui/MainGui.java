package client.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import client.ClientManager;
import client.ClientReceiver;
import client.Config;
import client.LoginStatement;
import vo.Address;
import vo.Category;
import vo.Evaluation;
import vo.Member;
import vo.Restaurant;

public class MainGui extends JFrame implements ActionListener{
	
	private static MainGui MAIN_GUI;
	
	private static final int LOGOUT = 0;
	private static final int LOGIN_USER = 1;
	private static final int LOGIN_VALUER = 2;
	
	public static Color MOUSEOVER_COLOR;
	
	private int guiId = ClientReceiver.MAIN_GUI_ID;
	
	//private int guiId = ClientReceiver.getGuiID();
	private LinkedBlockingQueue<Object> queue = new LinkedBlockingQueue<>();
	
	private ClientManager manager = new ClientManager();
	
	private final int width = 320;
	private final int height = 500; 
	
	private int p_inInitHeight = 50;
	
	JButton btn_one;
	JButton btn_two;
	JButton btn_three;
	JButton btn_four;
	
	JScrollPane scrollPane;
	JPanel p_in;
	
	JComboBox combo_loc;
	JComboBox combo_type;
	JComboBox combo_score;
	private JLabel lb_new;
	private JLabel lb_newmsg;
	
	void buttonSet(int permission){
		switch(permission){
		case LOGOUT:
			btn_one.setVisible(false);
			btn_two.setVisible(false);
			btn_three.setText("로그인");
			btn_four.setText("회원가입");
			break;
		case LOGIN_USER:
			btn_one.setVisible(false);
			btn_two.setVisible(false);
			btn_three.setText("추천받기");
			btn_four.setText("로그아웃");
			break;
		case LOGIN_VALUER:
			btn_one.setText("맛집등록");
			btn_two.setText("대기맛집");
			btn_three.setText("추천받기");
			btn_four.setText("로그아웃");
			btn_one.setVisible(true);
			btn_two.setVisible(true);
			btn_three.setVisible(true);
			btn_four.setVisible(true);
			break;
		}
	}
	
	public static MainGui getMainGui(){
		return MAIN_GUI;
	}
	
	public MainGui(){
		ClientReceiver.addQueue(guiId, queue);
		new Thread(new Handler(this)).start();
		
		//gui가 close 될 때 ClientReceiver.deleteQueue(guiId); 를 호출해주어야한다.!!
		this.addWindowListener(new windowHandler());
		
		this.setSize(width, height);
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		
		 Font font = new Font("나눔바른고딕", Font.PLAIN, 14);
		 
		//-------------------------------------------------------------------------------------------------
		JPanel p_top = new JPanel();
		p_top.setPreferredSize( new Dimension( width, height / 25 ) );
		getContentPane().add(p_top);
		p_top.setLayout(new BoxLayout(p_top, BoxLayout.X_AXIS));
		
		JPanel p_madeBy = new JPanel();
		p_madeBy.setPreferredSize( new Dimension( 300, height / 20 ) );
		p_top.add(p_madeBy);
		p_madeBy.setLayout(null);
		
		lb_new = new JLabel(new ImageIcon(""));
		lb_new.setBounds(114, 0, 43, 20);
		p_madeBy.add(lb_new);
		
		lb_newmsg = new JLabel("");
		lb_newmsg.setBounds(189, 0, 43, 20);
		p_madeBy.add(lb_newmsg);
		
		//JButton btn_close = new JButton("...");
		//p_top.add(btn_close);
		//btn_close.setPreferredSize( new Dimension( 20, 20 ) );
		//-------------------------------------------------------------------------------------------------
		
		JPanel p_menu = new JPanel();
		p_menu.setPreferredSize( new Dimension( width, height / 15 ) );
		getContentPane().add(p_menu);
		p_menu.setLayout(null);
		
		btn_one = new JButton("one");
		btn_one.setBounds(12, 5, 70, 24);
		btn_one.addActionListener(this);
		p_menu.add(btn_one);
		btn_one.setFont(font);
		btn_one.setPreferredSize( new Dimension( 70, 24 ) );
		
		btn_two = new JButton("two");
		btn_two.setBounds(87, 5, 70, 24);
		btn_two.addActionListener(this);
		p_menu.add(btn_two);
		btn_two.setFont(font);
		btn_two.setPreferredSize( new Dimension( 70, 24 ) );
		
		btn_three = new JButton("three");
		btn_three.setBounds(162, 5, 70, 24);
		btn_three.addActionListener(this);
		p_menu.add(btn_three);
		btn_three.setFont(font);
		btn_three.setPreferredSize( new Dimension( 70, 24 ) );
		
		btn_four = new JButton("four");
		btn_four.setBounds(237, 5, 70, 24);
		btn_four.addActionListener(this);
		p_menu.add(btn_four);
		btn_four.setFont(font);
		btn_four.setPreferredSize( new Dimension( 70, 24 ) );
		//-------------------------------------------------------------------------------------------------
		
		JPanel p_search = new JPanel();
		p_search.setPreferredSize( new Dimension( width, height / 15 ) );
		getContentPane().add(p_search);
		
		String[] loc = {"지 역", "강남구", "강동구", "강북구", "강서구", "관악구", "광진구", "구로구", "금천구", "노원구", "도봉구", "동대문구", "동작구",
				"마포구", "서대문구", "서초구", "성동구", "성북구", "송파구", "양천구", "영등포구", "용산구", "은평구", "종로구", "중구", "중랑구"};
		combo_loc = new JComboBox(loc);
		p_search.add(combo_loc);
		combo_loc.setFont(font);
		combo_loc.setPreferredSize( new Dimension( 90, 24 ) );
		combo_loc.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				RefreshList();
			}
			
		});
		
		String[] type = {"종  류", Category.S_KOREAN, 
				Category.S_JAPAN, Category.S_CHINA, 
				Category.S_WESTERN};
		combo_type = new JComboBox(type);
		p_search.add(combo_type);
		combo_type.setFont(font);
		combo_type.setPreferredSize( new Dimension( 70, 24 ) );
		combo_type.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				RefreshList();
			}
			
		});
		
		
		String[] score = {"☆☆☆☆☆", "★☆☆☆☆", "★★☆☆☆", "★★★☆☆", "★★★★☆", "★★★★★"};
		combo_score = new JComboBox(score);
		p_search.add(combo_score);
		combo_score.setFont(font);
		combo_score.setPreferredSize( new Dimension( 100, 24 ) );
		combo_score.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				RefreshList();
			}
			
		});
		
		
		//JButton btn_search = new JButton("\uAC80\uC0C9");
		//p_search.add(btn_search);
		//btn_search.addActionListener(this);
		//btn_search.setPreferredSize( new Dimension( height / 20 , height / 20 ) );
		//-------------------------------------------------------------------------------------------------
		
		JPanel p_list = new JPanel();
		p_list.setPreferredSize( new Dimension( width/2, 500-60 ) );
		getContentPane().add(p_list);
		p_list.setLayout(new BoxLayout(p_list, BoxLayout.X_AXIS));
		
		scrollPane = new JScrollPane();
		p_list.add(scrollPane);
		
		viewListPanelInit();

		//-----------------------------------------------------------
		manager.showList(guiId, new Category(), 5, Config.RESTAURANT_TABLE, null);
		if(Config.getInstance().getAutoLoginConfig() == true){
			manager.login(ClientReceiver.MAIN_GUI_ID, Config.getInstance().getAutoLoginUser());
		}
		
		Timer timer = new Timer(1000, new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				manager.checkStatement(guiId);
			}
			
		});
		timer.start();
		
		MAIN_GUI = this;
		
		buttonSet(LOGOUT);
		this.pack();
		this.setVisible(true);
		
	}

	private void viewListPanelInit(){
		p_inInitHeight = 0;
		p_in = new JPanel();
		scrollPane.setViewportView(p_in);
		p_in.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		p_in.setPreferredSize( new Dimension( 280 , p_inInitHeight ) );
	}
	
	private void addRestaurantPanel(Restaurant restaurant, Member from) {
		
		
		//boolean alreadyRecommend = false;
		ArrayList<String> recommenders = 
				(ArrayList<String>)restaurant.getRecommender();
		
		if(LoginStatement.getLoginUser() == null) 
			MOUSEOVER_COLOR = new Color(255,102,102);
		else{
			int i = 0;
			for(i = 0; i < recommenders.size(); ++i){
				if(recommenders.get(i).equals(LoginStatement.getLoginUser().getId())){
					MOUSEOVER_COLOR = new Color(255,225,0);
					break;
				}
			}	
			
			if(i == recommenders.size())
				MOUSEOVER_COLOR = new Color(255,102,102);
		}

		
		JPanel p_outerRestaurant = new JPanel();
		p_outerRestaurant.setLayout(null);
		p_outerRestaurant.setPreferredSize( new Dimension( 270 , 200 ) );
		
		p_outerRestaurant.setBackground(new Color(230,230,230));
		
		p_outerRestaurant.setCursor(new Cursor(Cursor.HAND_CURSOR));
		p_outerRestaurant.addMouseMotionListener(new MouseMotionListener() {
			
			Color mouseOverColor = new Color(MOUSEOVER_COLOR.getRed(),
					MOUSEOVER_COLOR.getGreen(),
					MOUSEOVER_COLOR.getBlue());
			
			@Override
			public void mouseDragged(MouseEvent e) {
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				final int x = e.getX();
				final int y = e.getY();
				
				final Rectangle bound = new Rectangle(30, 30, 205, 150);
				System.out.println(bound);
				System.out.println("x = " + x + "y = " + y);
				if (bound != null && bound.contains(x, y)) {
					p_outerRestaurant.setBackground(mouseOverColor);
					
				}
				else
					p_outerRestaurant.setBackground(new Color(230,230,230));
			}
			
		});
		
		JPanel p_restaurant = new JPanel();
		p_restaurant.setLayout(new BorderLayout(0, 0));
		//p_restaurant.setPreferredSize( new Dimension( 250 , 200 ) );
		p_restaurant.setBackground(new Color(255,245,238));
		ImageIcon getIIcon = restaurant.getImages().get(3);
		Image hi = getIIcon.getImage().getScaledInstance(270, 150 , Image.SCALE_DEFAULT);
		ImageIcon newIIcon = new ImageIcon(hi);
		
		JLabel lb_image = new JLabel(newIIcon);
		//lb_image.setPreferredSize( new Dimension( 150 , 100 ) );
		p_restaurant.add(lb_image, BorderLayout.NORTH);
		
		Font font = new Font("나눔바른고딕", Font.PLAIN, 11);
		
		JLabel lb_address = new JLabel(restaurant.getCategory().getLocation().toString());
		p_restaurant.add(lb_address, BorderLayout.SOUTH);
		lb_address.setPreferredSize( new Dimension( 200 , 15 ) );
		lb_address.setFont(font);
		
		JLabel lb_rName = new JLabel(restaurant.getRestaurantName());
		p_restaurant.add(lb_rName, BorderLayout.WEST);
		lb_rName.setPreferredSize( new Dimension( 100 , 15 ) );
		Font fontRname = new Font("나눔바른고딕", Font.BOLD, 18);
		lb_rName.setFont(fontRname);
		
		JLabel lb_type = new JLabel(Category.getStringFoodType(restaurant.getCategory().getType()));
		p_restaurant.add(lb_type, BorderLayout.CENTER);
		lb_type.setFont(font);
		
		double avg = restaurant.getCategory().getEvaluation().getAverage();

		JLabel lb_score = new JLabel(Evaluation.getStarRateFromScore(avg));
		p_restaurant.add(lb_score , BorderLayout.EAST);
		Font fontScore = new Font("나눔바른고딕", Font.PLAIN, 18);
		lb_score.setFont(fontScore);
		
		p_restaurant.setBounds(4, 4, 262, 192);
		
		p_outerRestaurant.addMouseListener(new MouseAdapter(){
			Restaurant r = restaurant;
			Member m = from;
			@Override
			public void mouseReleased(MouseEvent e) {
				//Member from = ;
				DetailRestaurantGui.getDetailRIfoDlg().addPanel(r, m);
			}
		});
		
		p_outerRestaurant.add(p_restaurant);
		p_in.add(p_outerRestaurant);
		this.pack();
	}
	
	private class Handler implements Runnable{
		JFrame gui ;
		
		public Handler(JFrame gui){
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
						
					case "replyRestaurant":
						Restaurant restaurant = (Restaurant) receive[2];
						Member requestor = (Member) receive[3];
						Member member = (Member) receive[4];
						new ReplyRestaurantGUI(restaurant, requestor, member);
						break;
					case "askRestaurant":
						Category category = (Category) receive[2];
						Member member2 = (Member) receive[3];
						new AskRestaurant(category,member2);
						break;
						
				/*	case "recommend":
						
						boolean recommendRst = (boolean)receive[2];
						
						if(recommendRst == true){
							JOptionPane.showMessageDialog(DetailRestaurantGui.getDetailRIfoDlg(), "추천 했습니다~!");
						}
						else{
							JOptionPane.showMessageDialog(DetailRestaurantGui.getDetailRIfoDlg(), "한번만 추천 가능 합니다~!");
						}
						break;
						
					case "evaluate":
						
						boolean evaluateRst = (boolean)receive[2];
						
						if(evaluateRst == true){
							JOptionPane.showMessageDialog(DetailRestaurantGui.getDetailRIfoDlg(), "등록 성공~");
							
						}
						else{
							JOptionPane.showMessageDialog(DetailRestaurantGui.getDetailRIfoDlg(), "한번만 등록 가능 합니다~!");
						}
						
						break;*/
						
					case "insert" :
						System.out.println("client - insert test");
						boolean insertRst = (boolean)receive[2];
						if (insertRst == true){
							manager.showList(guiId, new Category() , 5, Config.RESTAURANT_TABLE, null);
						}else{
							JOptionPane.showMessageDialog(gui, 
									"이미 등록 된 식당 입니다.");
						}
						break;
	
					case "check" :
						if(LoginStatement.getLoginUser() == null)
							break;
						int serverRUpCount = (int)receive[2];
						int serverSUpCount = (int)receive[3];
						if(serverSUpCount != Config.STANBY_UPDATE_COUNTER){
							lb_new.setIcon(new ImageIcon("resource/new.png"));
							Config.STANBY_UPDATE_COUNTER = serverSUpCount;
						}
						
						break;
						
					case"login":
						Member tryLoginUser = (Member)receive[2];
						if(tryLoginUser != null){
							LoginStatement.setLogInUser(tryLoginUser);
							SwingUtilities.invokeLater(() -> {
								System.out.println("permission -> " + tryLoginUser.getPermission());
								buttonSet(tryLoginUser.getPermission());
							});
							//if(Config.getInstance().getAutoLoginConfig() == true){
							Config.getInstance().setAutoLoginUser(tryLoginUser);
							//}
							Config.getInstance().saveConfig();
							gui.setTitle(LoginStatement.getLoginUser().getId());
							manager.showList(guiId, new Category(), 5, Config.RESTAURANT_TABLE, null);
							Config.STANBY_UPDATE_COUNTER = 0;
							Config.RESTAURANTS_UPDATE_COUNTER = 0;
						} else {
							JOptionPane.showMessageDialog(gui, "아이디 또는 비밀번호가 틀렸습니다.");
							Config.getInstance().setAutoLoginConfig(false);
						}
						break;
						
					case "showList":
						System.out.println("showList");
						
						SwingUtilities.invokeLater(() -> {
							viewListPanelInit();
							ArrayList<Restaurant> rList = (ArrayList<Restaurant>)receive[2];
							System.out.println(rList.size());
							for(Restaurant r : rList){
								p_in.setPreferredSize(new Dimension(280, p_inInitHeight+=210));
								addRestaurantPanel(r, (Member)receive[3]);
							}
							
						});
						break;
						
					case "exit" :
						if(gui != null) {
							System.exit(0);
							//gui.dispose();
						}
						return;
					}
					
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private class windowHandler extends WindowAdapter{
		public void windowClosing (WindowEvent e){
			ClientReceiver.deleteQueue(guiId);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()){
		case "맛집등록":
			InsertRestaurantsGUI insertGui = new InsertRestaurantsGUI();
			break;
			
		case "대기맛집":
			lb_new.setIcon(null);
			manager.showList(guiId, new Category() , 0, Config.STANBY_TABLE, null);
			break;
			
		case "추천받기":
			new RecommendGUI(this);
			
			break;
		
		case "회원가입":
			new JoinGui(this);
			break;
			
		case "로그아웃":
			buttonSet(LOGOUT);
			manager.logout(guiId, LoginStatement.getLoginUser());
			LoginStatement.setLogOutUser();
			setTitle("");
			lb_new.setIcon(null);
			manager.showList(guiId, new Category(), 5, Config.RESTAURANT_TABLE, null);
			break;
			
		case "로그인":
			new LogInGui(this);
			break;
			
		case "검색":
			new DetailSearchGui(MainGui.this);
			break;
		}
		
	}
	
	private void RefreshList(){
		Category searchInfo = new Category();
		
		String loc = (String)combo_loc.getSelectedItem();
		String type = (String)combo_type.getSelectedItem();
		String score = (String)combo_score.getSelectedItem();
		
		double dScore = Evaluation.getScoreFromStarRate(score);
		
		if(dScore > 0){
			Evaluation evaluInfo = new Evaluation();
			evaluInfo.setAverage(dScore);
			searchInfo.setEvaluation(evaluInfo);
		}
		
		if(!loc.equals("지 역")){
			Address locInfo = new Address();
			locInfo.setSido("서울특별시");
			locInfo.setSigungu(loc);
			searchInfo.setLocation(locInfo);
		}
		searchInfo.setType(Category.getIntFoodType(type));
		
		manager.showList(guiId, searchInfo , 0, Config.RESTAURANT_TABLE, null);
	}
}
