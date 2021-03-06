package client.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import client.ClientManager;
import client.ClientReceiver;
import client.Config;
import client.LoginStatement;
import vo.Category;
import vo.Evaluation;
import vo.Member;
import vo.Restaurant;

public class DetailRestaurantGui extends JDialog{

	private static DetailRestaurantGui drGui;
	
	private JTabbedPane tabbedPane;
	private ClientManager manager = new ClientManager();
	
	private int imageIndex = 0;
	
	private int guiId = ClientReceiver.getGuiID();
	private LinkedBlockingQueue<Object> queue = new LinkedBlockingQueue<>();
	private JScrollPane pRecentely;
	
	public int getGuiID(){
		return guiId;
	}
	
	private DetailRestaurantGui() {
		
		ClientReceiver.addQueue(guiId, queue);
		new Thread(new Handler(this)).start();
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
		panel.setPreferredSize(new Dimension(420, 530));
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		panel.add(tabbedPane);
		tabbedPane.setPreferredSize(new Dimension(420, 520));
		tabbedPane.addMouseListener(new MouseAdapter(){
			public void mouseReleased(MouseEvent e) {
				System.out.println("������Գ�");
				pRecentely = (JScrollPane) tabbedPane.getSelectedComponent();
				imageIndex = 0;
			}	
		});
		
		this.addWindowListener(new WindowHandler());
		this.setVisible(false);

	}
	
	public static DetailRestaurantGui getDetailRIfoDlg(){
		if (drGui == null){
			drGui = new DetailRestaurantGui();
		}
		return drGui;
	}

	public void addPanel(Restaurant r, Member from){
		imageIndex = 0;
		
		Point mainGuiP = MainGui.getMainGui().getLocation();
		this.setBounds(mainGuiP.x-412, mainGuiP.y, 465, 605);
		
		JScrollPane scrollPane = new JScrollPane();
		//System.out.println(tabbedPane+ " " + scrollPane);
		tabbedPane.addTab(r.getRestaurantName(), null, scrollPane, null);
		tabbedPane.setSelectedComponent(scrollPane);
		
		JPanel p_restaurant = new JPanel();
		scrollPane.setViewportView(p_restaurant);
		p_restaurant.setPreferredSize(new Dimension(370, 450));
		pRecentely = scrollPane;
		
		JLabel lb_rname = new JLabel(r.getRestaurantName());
		lb_rname.setFont(new Font("�����ٸ�����", Font.PLAIN, 16));
		p_restaurant.add(lb_rname);
		lb_rname.setPreferredSize(new Dimension(220, 20));
		
		JLabel lb_evaluate = new JLabel("�� ��");
		lb_evaluate.setFont(new Font("�����ٸ�����", Font.PLAIN, 16));
		p_restaurant.add(lb_evaluate);
		lb_evaluate.setPreferredSize(new Dimension(110, 20));
		
		JButton btn_delete = new JButton("X");
		btn_delete.setFont(new Font("�����ٸ�����", Font.PLAIN, 10));
		p_restaurant.add(btn_delete);
		btn_delete.setPreferredSize(new Dimension(27, 15));
		btn_delete.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(tabbedPane.getTabCount() > 1)
					tabbedPane.remove(scrollPane);
				else{
					manager.showList(ClientReceiver.MAIN_GUI_ID, new Category(), 0, Config.RESTAURANT_TABLE, null);
					drGui.setVisible(false);
					tabbedPane.removeAll();
				}
					
			}
		});
	
		JPanel p_image_in = new JPanel();
		p_restaurant.add(p_image_in);
		p_image_in.setPreferredSize(new Dimension(200, 120));
		
		JPanel p_gap = new JPanel();
		p_restaurant.add(p_gap);
		p_gap.setPreferredSize(new Dimension(15, 120));
		p_gap.setLayout(new GridLayout(0, 1, 0, 0));
		
		ArrayList<ImageIcon> images  = r.getImages();
		ArrayList<ImageIcon> resizeds = new ArrayList<>();
		
		
		
		for(ImageIcon m : images){
			Image hi = m.getImage().getScaledInstance(200, 120 , Image.SCALE_DEFAULT);
			ImageIcon newIIcon = new ImageIcon(hi);
			resizeds.add(newIIcon);
		}
		
		JLabel lbl_image = new JLabel(resizeds.get(0));
		p_image_in.add(lbl_image);
		lbl_image.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				new ImagePrintDialog(images.get(imageIndex), drGui);
				
				
			}
		});
		
		JButton btn_img1 = new JButton("");
		p_gap.add(btn_img1);
		btn_img1.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				lbl_image.setIcon(resizeds.get(0));
				imageIndex = 0;
			}
		});
		
		JButton btn_img2 = new JButton("");
		p_gap.add(btn_img2);
		if(resizeds.size() >= 2){
			btn_img2.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					lbl_image.setIcon(resizeds.get(1));
					imageIndex = 1;
				}
			});
		}
		
		JButton btn_img3 = new JButton("");
		p_gap.add(btn_img3);
		if(resizeds.size() >= 3){
			btn_img3.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					lbl_image.setIcon(resizeds.get(2));
					imageIndex = 2;
				}
			});
		}
		
		JButton btn_img4 = new JButton("");
		p_gap.add(btn_img4);
		if(resizeds.size() >= 4){
			btn_img4.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					lbl_image.setIcon(resizeds.get(3));
					imageIndex = 3;
				}
			});
		}
		
		JPanel p_evaluate_in = new JPanel();
		p_restaurant.add(p_evaluate_in);
		p_evaluate_in.setPreferredSize(new Dimension(130, 120));
		
		JLabel lbl_type_t = new JLabel("��������   :  " + Category.getStringFoodType(
				r.getCategory().getType()));
		lbl_type_t.setFont(new Font("�����ٸ�����", Font.PLAIN, 14));
		p_evaluate_in.add(lbl_type_t);
		lbl_type_t.setPreferredSize(new Dimension(110, 15));
		
		JLabel lbl_taste_t = new JLabel("�� ");
		lbl_taste_t.setFont(new Font("�����ٸ�����", Font.PLAIN, 14));
		p_evaluate_in.add(lbl_taste_t);
		lbl_taste_t.setPreferredSize(new Dimension(55, 15));
		
		JLabel lbl_taste = new JLabel(
				Evaluation.getStarRateFromScore(r.getCategory().getEvaluation().getTaste()));
		lbl_taste.setFont(new Font("�����ٸ�����", Font.BOLD, 14));
		p_evaluate_in.add(lbl_taste);
		
		JLabel lbl_service_t = new JLabel("���� ");
		lbl_service_t.setFont(new Font("�����ٸ�����", Font.PLAIN, 14));
		p_evaluate_in.add(lbl_service_t);
		lbl_service_t.setPreferredSize(new Dimension(55, 15));
		
		JLabel lbl_service = new JLabel(
				Evaluation.getStarRateFromScore(r.getCategory().getEvaluation().getService()));
		lbl_service.setFont(new Font("�����ٸ�����", Font.BOLD, 14));
		p_evaluate_in.add(lbl_service);
		
		JLabel lbl_hygiene_t = new JLabel("û�ᵵ ");
		lbl_hygiene_t.setFont(new Font("�����ٸ�����", Font.PLAIN, 14));
		p_evaluate_in.add(lbl_hygiene_t);
		lbl_hygiene_t.setPreferredSize(new Dimension(55, 15));
		
		JLabel lbl_hygiene = new JLabel(
				Evaluation.getStarRateFromScore(r.getCategory().getEvaluation().getHygiene()));
		lbl_hygiene.setFont(new Font("�����ٸ�����", Font.BOLD, 14));
		p_evaluate_in.add(lbl_hygiene);
		
		JLabel lbl_average_t = new JLabel("�� ��");
		lbl_average_t.setFont(new Font("�����ٸ�����", Font.PLAIN, 14));
		p_evaluate_in.add(lbl_average_t);
		lbl_average_t.setPreferredSize(new Dimension(55, 15));
		
		JLabel lbl_average = new JLabel(
				Evaluation.getStarRateFromScore(r.getCategory().getEvaluation().getAverage()));
		lbl_average.setFont(new Font("�����ٸ�����", Font.BOLD, 14));
		p_evaluate_in.add(lbl_average);
		

		JLabel lbl_padding = new JLabel("");
		p_restaurant.add(lbl_padding);
		lbl_padding.setPreferredSize(new Dimension(360, 15));
		
		JLabel lbl_comment = new JLabel("���� �ڸ�Ʈ");
		p_restaurant.add(lbl_comment);
		lbl_comment.setPreferredSize(new Dimension(200, 25));
		lbl_comment.setFont(new Font("�����ٸ�����", Font.PLAIN, 16));
		
		JPanel p_gap2 = new JPanel();
		p_restaurant.add(p_gap2);
		p_gap2.setPreferredSize(new Dimension(15, 25));
		
		JLabel lbl_menu = new JLabel("�޴�");
		p_restaurant.add(lbl_menu);
		lbl_menu.setPreferredSize(new Dimension(130, 25));
		lbl_menu.setFont(new Font("�����ٸ�����", Font.PLAIN, 16));
		
		
		JScrollPane sp_comment = new JScrollPane();
		JTextArea ta_comment_in = new JTextArea(r.getCategory().getEvaluation().getComment());
		ta_comment_in.setLineWrap(true);
		ta_comment_in.setEditable(false);
		sp_comment.setViewportView(ta_comment_in);
		p_restaurant.add(sp_comment);
		ta_comment_in.setPreferredSize(new Dimension(200, 100));
		ta_comment_in.setFont(new Font("�����ٸ�����", Font.PLAIN, 12));

		JPanel p_gap3 = new JPanel();
		p_restaurant.add(p_gap3);
		p_gap3.setPreferredSize(new Dimension(15, 100));
		p_gap3.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel p_menu = new JPanel();
		p_restaurant.add(p_menu);
		p_menu.setPreferredSize(new Dimension(130, 100));
		
		JLabel lbl_menu1_i = new JLabel(r.getMenu().get(0));
		lbl_menu1_i.setFont(new Font("�����ٸ�����", Font.PLAIN, 14));
		p_menu.add(lbl_menu1_i);
		lbl_menu1_i.setPreferredSize(new Dimension(120, 15));
		
		if(r.getMenu().size() >= 2){
			JLabel lbl_menu2_i = new JLabel(r.getMenu().get(1));
			lbl_menu2_i.setFont(new Font("�����ٸ�����", Font.PLAIN, 14));
			p_menu.add(lbl_menu2_i);
			lbl_menu2_i.setPreferredSize(new Dimension(120, 15));
		}
		
		if(r.getMenu().size() >= 3){
			JLabel lbl_menu3_i = new JLabel(r.getMenu().get(2));
			lbl_menu3_i.setFont(new Font("�����ٸ�����", Font.PLAIN, 14));
			p_menu.add(lbl_menu3_i);
			lbl_menu3_i.setPreferredSize(new Dimension(120, 15));
		}
		
		if(r.getMenu().size() >= 4){
			JLabel lbl_menu4_i = new JLabel(r.getMenu().get(3));
			lbl_menu4_i.setFont(new Font("�����ٸ�����", Font.PLAIN, 14));
			p_menu.add(lbl_menu4_i);
			lbl_menu4_i.setPreferredSize(new Dimension(120, 15));
		}
		
		JLabel lbl_padding2 = new JLabel("");
		p_restaurant.add(lbl_padding2);
		lbl_padding2.setPreferredSize(new Dimension(360, 15));
		
		JLabel lbl_detail = new JLabel("�� ����");
		p_restaurant.add(lbl_detail);
		lbl_detail.setPreferredSize(new Dimension(170, 25));
		lbl_detail.setFont(new Font("�����ٸ�����", Font.PLAIN, 16));
	
		
		JLabel lbl_address_t = new JLabel("�ּ�");
		p_restaurant.add(lbl_address_t);
		lbl_address_t.setPreferredSize(new Dimension(175, 25));
		lbl_address_t.setFont(new Font("�����ٸ�����", Font.PLAIN, 16));
		
		
		JPanel p_detail = new JPanel();
		p_restaurant.add(p_detail);
		p_detail.setPreferredSize(new Dimension(170, 50));
		
		JLabel lbl_time= new JLabel("���� �ð� ");
		lbl_time.setFont(new Font("�����ٸ�����", Font.PLAIN, 12));
		p_detail.add(lbl_time);
		lbl_time.setPreferredSize(new Dimension(50, 15));
		
		JLabel lbl_time_i= new JLabel(r.getOperationHour());
		lbl_time_i.setFont(new Font("�����ٸ�����", Font.PLAIN, 12));
		p_detail.add(lbl_time_i);
		lbl_time_i.setPreferredSize(new Dimension(100, 15));
		
		JLabel lbl_price= new JLabel("���ݴ� ");
		lbl_price.setFont(new Font("�����ٸ�����", Font.PLAIN, 12));
		p_detail.add(lbl_price);
		lbl_price.setPreferredSize(new Dimension(50, 15));
		
		JLabel lbl_price_i= new JLabel(r.getPrice());
		lbl_price_i.setFont(new Font("�����ٸ�����", Font.PLAIN, 12));
		p_detail.add(lbl_price_i);
		lbl_price_i.setPreferredSize(new Dimension(100, 15));
		
		JPanel p_address = new JPanel();
		p_restaurant.add(p_address);
		p_address.setPreferredSize(new Dimension(190, 50));
			
		
		JScrollPane sp_address = new JScrollPane();
		JTextArea ta_address_in = new JTextArea(r.getCategory().getLocation().toString());
		ta_address_in.setLineWrap(true);
		ta_address_in.setEditable(false);
		sp_address.setViewportView(ta_address_in);
		p_address.add(sp_address);
		ta_address_in.setPreferredSize(new Dimension(175, 35));
		ta_address_in.setFont(new Font("�����ٸ�����", Font.PLAIN, 12));

		JPanel p_padding1 = new JPanel();
		p_restaurant.add(p_padding1);
		p_padding1.setPreferredSize(new Dimension(360, 36));
		
		//JButton btn_recommend = new JButton(new ImageIcon"resource/evaluate.png");
		JLabel lb_btn = null;
		
		if(from != null){
			//test 
			JLabel re = new JLabel("�� ������  " + "\"" + from.getId()+ "\"" +" �Կ��� ");
			re.setFont(new Font("�����ٸ�����", Font.PLAIN, 17));

			lb_btn = new JLabel(new ImageIcon("resource/recommend.png"));
			p_padding1.add(re);
			p_padding1.add(lb_btn);
			lb_btn.addMouseListener(new MouseAdapter(){
				public void mouseReleased(MouseEvent e) {
					manager.replyRestaurant(ClientReceiver.MAIN_GUI_ID,
							r, from, LoginStatement.getLoginUser());
				}
			});
			
		}else{
			if(LoginStatement.getLoginUser() != null){
				if(LoginStatement.getLoginUser().getPermission()
						== Member.USER) {
					
					JLabel re = new JLabel("����� ���� �ּ���~!");
					re.setFont(new Font("�����ٸ�����", Font.PLAIN, 17));
					
					p_padding1.add(re);
					lb_btn = new JLabel(new ImageIcon("resource/evaluate.png"));
					lb_btn.addMouseListener(new MouseAdapter(){
						public void mouseReleased(MouseEvent e) {
							new UserEvaluationsGUI(r, drGui);
						}
					});
					
				}
				else if(LoginStatement.getLoginUser().getPermission()
						== Member.VALUER){
					JLabel re = new JLabel("�������� ��õ �ϱ�~!");
					re.setFont(new Font("�����ٸ�����", Font.PLAIN, 17));
					
					p_padding1.add(re);
					lb_btn = new JLabel(new ImageIcon("resource/recommend.png"));
					lb_btn.addMouseListener(new MouseAdapter(){
						public void mouseReleased(MouseEvent e) {
							//ArrayList<String> rl = r.getRecommender();
							//rl.add(LoginStatement.getLoginUser().getId());
							manager.recommendRestaurant(guiId,
									r, LoginStatement.getLoginUser());
						}
					});
				}
				p_padding1.add(lb_btn);
			}
		}
		
		
		p_gap.add(btn_img4);
		if(resizeds.size() >= 4){
			btn_img4.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					lbl_image.setIcon(resizeds.get(3));
				}
			});
		}
		
		
		JLabel lbl_userevaluate = new JLabel("���� �� �� ��");
		p_restaurant.add(lbl_userevaluate);
		lbl_userevaluate.setPreferredSize(new Dimension(360, 25));
		lbl_userevaluate.setFont(new Font("�����ٸ�����", Font.PLAIN, 16));

		ArrayList<Evaluation> userElist = r.getUserEvaluations();
		
		int rsize = 520;
		for(Evaluation e : userElist){
			JPanel lbl_user = new JPanel();
			p_restaurant.add(lbl_user);
			lbl_user.setPreferredSize(new Dimension(370, 25));
			
			JLabel lbl_userid_i = new JLabel(e.getUser().getId());
			lbl_user.add(lbl_userid_i);
			lbl_userid_i.setPreferredSize(new Dimension(50, 25));
			lbl_userid_i.setFont(new Font("�����ٸ�����", Font.PLAIN, 12));
			
			JLabel lbl_usercomment_i = new JLabel(e.getComment());
			lbl_user.add(lbl_usercomment_i);
			lbl_usercomment_i.setPreferredSize(new Dimension(225, 25));
			lbl_usercomment_i.setFont(new Font("�����ٸ�����", Font.PLAIN, 12));
			
			JLabel lbl_userevaluate_i = new JLabel(Evaluation.getStarRateFromScore(e.getAverage()));
			lbl_user.add(lbl_userevaluate_i);
			lbl_userevaluate_i.setPreferredSize(new Dimension(55, 25));
			lbl_userevaluate_i.setFont(new Font("�����ٸ�����", Font.PLAIN, 12));
			
			p_restaurant.setPreferredSize(new Dimension(370, rsize += 28));
		}
		

		if (drGui.isVisible() == false)
			drGui.setVisible(true);
	}
	
	private class WindowHandler extends WindowAdapter{
		public void windowClosing (WindowEvent e){
			//�ӽ÷� 
			manager.showList(ClientReceiver.MAIN_GUI_ID, new Category(), 0, Config.RESTAURANT_TABLE, null);
			
			drGui.setVisible(false);
			tabbedPane.removeAll();
		}
	}
	
	private class Handler implements Runnable{
		DetailRestaurantGui gui ;
		
		public Handler(DetailRestaurantGui gui){
			this.gui = gui;
		}
		
		@Override
		public void run() {
			while(true) {
				try {
					Object[] receive = (Object[])queue.take();
					String proto = (String)receive[1];
					
					switch(proto){
					
					case "evaluate":
						
						boolean evaluateRst = (boolean)receive[2];
						
						if(evaluateRst == true){
							JOptionPane.showMessageDialog(DetailRestaurantGui.getDetailRIfoDlg(), "��� ����~");
							tabbedPane.remove(pRecentely);
							Restaurant r = (Restaurant)receive[3];
							Evaluation e = (Evaluation)receive[4];
							r.getUserEvaluations().add(0, e);
							addPanel(r, null);
						}
						else{
							JOptionPane.showMessageDialog(DetailRestaurantGui.getDetailRIfoDlg(), "�ѹ��� ��� ���� �մϴ�~!");
						}
						
						break;
						
					case "recommend":
						
						boolean recommendRst = (boolean)receive[2];
						
						if(recommendRst == true){
							JOptionPane.showMessageDialog(DetailRestaurantGui.getDetailRIfoDlg(), "��õ �߽��ϴ�~!");
							tabbedPane.remove(pRecentely);
							Restaurant r = (Restaurant)receive[3];
							Member m = (Member)receive[4];
							r.getRecommender().add(m.getId());
							addPanel(r, null);
						}
						else{
							JOptionPane.showMessageDialog(DetailRestaurantGui.getDetailRIfoDlg(), "�ѹ��� ��õ ���� �մϴ�~!");
						}
						break;
						
						
					//case "exit" :
					//	if(gui != null) {
					//		gui.dispose();
					//	}
					//	return;
					}
					
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
