package server;

import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;

import manager.Interface;
import vo.Address;
import vo.Category;
import vo.Evaluation;
import vo.Member;
import vo.Restaurant;

public class ServerManager implements Interface {

	private ObjectOutputStream oos;
	private static HashMap<String, ObjectOutputStream> userList = new HashMap<>();
	private ArrayList<Restaurant> showList = new ArrayList<>();
	
	public ServerManager(ObjectOutputStream oos) {
		this.oos = oos;
	}
	
	public ServerManager() {

	}
	
	@Override
	public boolean join(Member member) {
		Connection connection = ConnectionManager.getConnection();
		String sql = "insert into users(id, password, name, permission, birth) values(?,?,?,?,?)";
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(sql);
			ps.setString(1, member.getId());
			ps.setString(2, member.getPassword());
			ps.setString(3, member.getName());
			ps.setInt(4, Member.USER); //신규회원가입자는 모두 user 권한만을 가진다.
			ps.setString(5, member.getBirth());
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			ConnectionManager.close(connection);
		}
		return true;
	}

	@Override
	public boolean login(Member member) {
		Connection connection = ConnectionManager.getConnection();
		try {
			Statement st = connection.createStatement();
			String sql = "select id, password from users where id = '" + member.getId() 
							+ "' and password = '" + member.getPassword() + "'";
			ResultSet rs = st.executeQuery(sql);
			if(rs.next()){
				userList.put(member.getId(), oos);
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean logout(Member member) {
		userList.remove(member.getId());
		return true;
	}

	@Override
	public ArrayList<Restaurant> showList(Category category, int num) {
		Connection connection = ConnectionManager.getConnection();
		try {
			Statement st = connection.createStatement();
			String sql = "select * from restaurants "
					+ "where location = '" + category.getLocation() + "' "
					+ "and food_type = '" + category.getType() + "' "
					+ "and average_score = " + category.getEvaluation() + "";
			ResultSet rs = st.executeQuery(sql);
			Restaurant restaurant = null;
			Member user = null;
			Evaluation Evaluation = null;
			Category categories = null;
			while(rs.next()){
				String restaurantName = rs.getString("restaurant_name");
				String location = rs.getString("location");
				Address address = new Address(location);
				int type = rs.getInt("food_type");
				String price = rs.getString("price_range");
				String operationHour = rs.getString("operation_Hour");
				String id = rs.getString("id");
					Statement st2 = connection.createStatement();
					String sql2 = "select * from users where id = '" + id + "'";
					ResultSet rs2 = st2.executeQuery(sql2);
					if(rs2.next()){
						String password = rs2.getString("password");
						String name = rs2.getString("name");
						int permission = rs2.getInt("permission");
						String birth = rs2.getString("birth");
						user = new Member(id, password, name, permission, birth);
					}
				String comments = rs.getString("comments");
				int taste_score = rs.getInt("taste_score ");
				int service_score = rs.getInt("service_score");
				int hygiene_score = rs.getInt("hygiene_score");
				int average_score = rs.getInt("average_score");
				String image_path = rs.getString("image_path");
					String[] splitedImage = image_path.split(";");
					ImageIcon icon = null;
					ArrayList<ImageIcon> imageList = new ArrayList<>();
					for(int i = 0 ; i < splitedImage.length ; i++){
						icon = new ImageIcon(this.getClass().getResource(splitedImage[i]));
						imageList.add(icon);
					}
				String menus = rs.getString("menus");
				String[] splitedMenus = menus.split(";");
					ArrayList<String> menuList = new ArrayList<>();
					for(int i = 0 ; i < splitedMenus.length ; i++){
						menuList.add(splitedMenus[i]);
					}
				int Recommend = rs.getInt("Recommend");
				Evaluation = new Evaluation(taste_score, service_score, hygiene_score, average_score, comments, user);
				categories = new Category(address, type, Evaluation);
				
				Statement st3 = connection.createStatement();
				String sql3 = "select eval.* "
								+ "from evaluations eval, restaurants rest "
								+ "where eval.location = rest.location";
				ResultSet rs3 = st3.executeQuery(sql3);
				while(rs3.next()){
					
				}
				
				ArrayList<Evaluation> userEvalution = new ArrayList<>();
				
				restaurant = new Restaurant(restaurantName, price, operationHour, categories, imageList, userEvaluations, menuList, Recommend);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public boolean evaluateResataurant(Evaluation evalauation) {
		return false;
	}

	@Override
	public boolean insertRestaurant(Restaurant restaurant) {
		return false;
	}

	@Override
	public void askRestaurant(Category category, Member member, boolean isRandom) {
		
	}

	@Override
	public void replyRestaurant(Category category, Restaurant restaurant, Member to, Member from) {

	}

	//test main
	public static void main(String[] args) {
		
		Member m1 = new Member("lullulalal", "1234", "하은영", 1, "921226");
		
		ServerManager manager = new ServerManager();
		
		/*if(manager.join(m1)){
			System.out.println("등록성공");
		} else 
			System.out.println("등록실패");*/
		
		if(manager.login(m1))
			System.out.println("로그인 성공");
		else System.out.println("로그인 실패");
		
	}
	
}
