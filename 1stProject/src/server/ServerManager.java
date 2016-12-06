package server;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import manager.Interface;
import vo.Category;
import vo.Evaluation;
import vo.Member;
import vo.Restaurant;

public class ServerManager implements Interface{

	@Override
	public boolean join(Member member) {
		return false;
	}
	
	@Override
	public boolean login(Member member) {
		return false;
	}
	
	@Override
	public boolean logout(Member member) {
		return false;
	}
	
	@Override
	public ArrayList<Restaurant> showList(Category category, int num) {
		
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
					ResultSet rs = pstmt.executeQuery();
					while(rs.next()){
						return rtn;
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
			
					String imagePath = Long.toString( System.currentTimeMillis() ) + ".jpg";
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
