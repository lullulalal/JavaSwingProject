package server;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import manager.Interface;
import vo.Address;
import vo.Category;
import vo.Evaluation;
import vo.Member;
import vo.Restaurant;

public class ServerManager implements Interface{

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
		
		try(PreparedStatement ps = connection.prepareStatement(sql);) {
			
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
		try(Statement st = connection.createStatement();) {
			
			String sql = "select id, password from users where id = '" + member.getId() 
							+ "' and password = '" + member.getPassword() + "'";
			try(ResultSet rs = st.executeQuery(sql);){
				if(rs.next()){
					userList.put(member.getId(), oos);
					return true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			ConnectionManager.close(connection);
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
				
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	public boolean evaluateResataurant(Evaluation evaluation, Restaurant restaurant) {
		
		boolean rtn = false;
		
		Connection conn = ConnectionManager.getConnection();
		try {
				String sql = "select * from evaluations where id=? and location=?";
				try(PreparedStatement pstmt = conn.prepareCall(sql)){
					pstmt.setString(1, evaluation.getUser().getId());
					pstmt.setString(2, restaurant.getCategory().getLocation().toString());
					
					try(ResultSet rs = pstmt.executeQuery();) {
						while(rs.next()){
							return rtn;
						}
					}
				}catch (SQLException e) {
					e.printStackTrace();
					return rtn;
				}
				
				sql = "insert into evaluations values(?, ?, ?, ?)";
				try(PreparedStatement pstmt = conn.prepareCall(sql)){
					pstmt.setString(1, evaluation.getUser().getId());
					pstmt.setString(2, restaurant.getCategory().getLocation().toString());
					pstmt.setDouble(3, evaluation.getAverage());
					pstmt.setString(4, evaluation.getComment());
					pstmt.executeUpdate();
					rtn = true;
				} catch (SQLException e) {
					e.printStackTrace();
				}

		}  finally {
			ConnectionManager.close(conn);
		}
		
		return rtn;
	}
	
	@Override
	public boolean insertRestaurant(Restaurant restaurant) {
		Connection conn = ConnectionManager.getConnection();
		
		String sql = "insert into stanby values(?, ?, ?, ?, ?,"
											+ " ?, ?, ?, ?, ?, "
											+ " ?, ?, ?, ?)";
		
		try(PreparedStatement pstmt = conn.prepareCall(sql);){
			pstmt.setString(1, restaurant.getRestaurantName());
			pstmt.setString(2, restaurant.getCategory().getLocation().toString());
			pstmt.setInt(3, restaurant.getCategory().getType());
			pstmt.setString(4, restaurant.getPrice());
			pstmt.setString(5, restaurant.getOperationHour());
			pstmt.setString(6, restaurant.getCategory().getEvaluation().getUser().getId());
			pstmt.setString(7, restaurant.getCategory().getEvaluation().getComment());
			pstmt.setDouble(8, restaurant.getCategory().getEvaluation().getTaste());
			pstmt.setDouble(9, restaurant.getCategory().getEvaluation().getService());
			pstmt.setDouble(10, restaurant.getCategory().getEvaluation().getHygiene());
			pstmt.setDouble(11, restaurant.getCategory().getEvaluation().getAverage());
	
			StringBuilder strbImagePath = new StringBuilder();
			for(ImageIcon icon : restaurant.getImages()){
		
				try {
					Image img = icon.getImage();

					BufferedImage bi = new BufferedImage(img.getWidth(null),img.getHeight(null),BufferedImage.TYPE_INT_RGB);

					Graphics2D g2 = bi.createGraphics();
					g2.drawImage(img, 0, 0, null);
					g2.dispose();
			
					String imagePath = "d:/" + Long.toString( System.currentTimeMillis() ) + ".jpg";
					ImageIO.write(bi, "jpg", new File(imagePath));
					
					strbImagePath.append(imagePath);
					strbImagePath.append(";");
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			pstmt.setString(12, strbImagePath.toString());
			
			StringBuilder strbMenu = new StringBuilder();
			for(String s : restaurant.getMenu()) {
				strbMenu.append(s);
				strbMenu.append(";");
			}

			pstmt.setString(13, strbMenu.toString());
			
			pstmt.setInt(14, restaurant.getRecommendNum());
			
			pstmt.executeUpdate();
			
		} catch (SQLException e){
			e.printStackTrace();
			return false;
		} finally{
			ConnectionManager.close(conn);
		}
		
		return true;
	}

	@Override
	public boolean recommendRestaurant(Restaurant restaurant) {
		Connection conn = ConnectionManager.getConnection();
		String sql = "update restaurants set recommend=? where location=?";
		
		try {
			try(PreparedStatement pstmt = conn.prepareCall(sql)){
				pstmt.setInt(1, restaurant.getRecommendNum());
				pstmt.setString(2, restaurant.getCategory().getLocation().toString());
				
				pstmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
			
			if (restaurant.getRecommendNum() >= Restaurant.BOUNDARY_OF_ASCEND ){
				sql = "insert into Restaurants select * from stanby where location=?";
				try(PreparedStatement pstmt = conn.prepareCall(sql)){
					pstmt.setString(1, restaurant.getCategory().getLocation().toString());
					pstmt.executeUpdate();
					
				} catch (SQLException e) {
					e.printStackTrace();
					return false;
				}
				
				sql = "delete stanby where location=?";
				try(PreparedStatement pstmt = conn.prepareCall(sql)){
					pstmt.setString(1, restaurant.getCategory().getLocation().toString());
					pstmt.executeUpdate();
					
				} catch (SQLException e) {
					e.printStackTrace();
					return false;
				}
				
			}
		} finally {
			ConnectionManager.close(conn);
		}
		
		return true;
	}
	
	@Override
	public void askRestaurant(Category category, Member member, boolean isRandom) {
		
	}

	@Override
	public void replyRestaurant(Category category, Restaurant restaurant, Member to, Member from) {
		
	}

	
}
