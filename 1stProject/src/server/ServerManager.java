package server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import manager.Interface;
import vo.Category;
import vo.Evaluation;
import vo.Member;
import vo.Restaurant;

public class ServerManager implements Interface {

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
				return true;
			}
		} catch (SQLException e) {
		}
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
