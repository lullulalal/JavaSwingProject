package server;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import vo.Address;
import vo.Category;
import vo.Evaluation;
import vo.Member;
import vo.Restaurant;

public class test {

	public ArrayList<Restaurant> showList(Category category, int num) {
		ArrayList<Restaurant> showList = new ArrayList<>();
		Connection connection = ConnectionManager.getConnection();
		try {
			Statement st = connection.createStatement();
			String sql = "select * from restaurants where location like '%" + category.getLocation().toString() + "%' "
					+ "and food_type = " + category.getType() + "" + " and average_score = "
					+ category.getEvaluation().getAverage() + "";
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				System.out.println("while");
				String restaurantName = rs.getString("restaurant_name");
				String location = rs.getString("location");
				Address address = new Address(location);
				int foodType = rs.getInt("food_type");
				String price = rs.getString("price_range");
				String operationHour = rs.getString("operation_Hour");

				String id = rs.getString("id");
				Statement st2 = connection.createStatement();
				String sql2 = "select * from users where id = '" + id + "'";
				ResultSet rs2 = st2.executeQuery(sql2);
				Member evaluator = null;
				if (rs2.next()) {
					String password = rs2.getString("password");
					String name = rs2.getString("name");
					int permission = rs2.getInt("permission");
					String birth = rs2.getString("birth");
					evaluator = new Member(id, password, name, permission, birth);
				}
				st2.close();
				rs2.close();

				String evaluatorComments = rs.getString("comments");
				double taste_score = rs.getDouble("taste_score ");
				double service_score = rs.getDouble("service_score");
				double hygiene_score = rs.getDouble("hygiene_score");
				double average_score = rs.getDouble("average_score");
				Evaluation evaluation = new Evaluation(taste_score, service_score, hygiene_score, average_score,
						evaluatorComments, evaluator);
				Category categories = new Category(address, foodType, evaluation);

				String image_path = rs.getString("image_path");
				String[] splitedImage = image_path.split(";");
				ImageIcon icon = null;
				ArrayList<ImageIcon> imageList = new ArrayList<>();
				for (int i = 0; i < splitedImage.length; i++) {
					icon = new ImageIcon(this.getClass().getResource(splitedImage[i]));
					imageList.add(icon);
				}
				String menus = rs.getString("menus");
				String[] splitedMenus = menus.split(";");
				ArrayList<String> menuList = new ArrayList<>();
				for (int i = 0; i < splitedMenus.length; i++) {
					menuList.add(splitedMenus[i]);
				}
				int recommendNum = rs.getInt("Recommend");

				Statement st3 = connection.createStatement();
				String sql3 = "select eval.id, eval.score, eval.comments from evaluations eval, restaurants rest "
						+ "where eval.location = rest.location";
				ResultSet rs3 = st3.executeQuery(sql3);
				ArrayList<Evaluation> userEvaluation = new ArrayList<>();
				while (rs3.next()) {
					String userId = rs3.getString("ID");
					double score = rs3.getDouble("SCORE");
					String comment = rs3.getString("COMMENTS");
					Member member = new Member();
					member.setId(userId);
					Evaluation evaluate = new Evaluation();
					evaluate.setAverage(score);
					evaluate.setUser(member);
					evaluate.setComment(comment);
					userEvaluation.add(evaluate);
				}
				Restaurant restaurant = new Restaurant(restaurantName, price, operationHour, categories, imageList,
						userEvaluation, menuList, recommendNum);
				showList.add(restaurant);
				st3.close();
				rs3.close();
			}
			st.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.close(connection);
		}
		if (num == 0) {
			num = showList.size();
		}
		return showList;
	}

	public static void main(String[] args) {
		test t = new test();

		Address address = new Address("서울특별시 종로구 자하문로16길 4");
		// address.setSido("서울특별시");
		// address.set
		// address.setStreetName("자하문로16길");
		// address.setBuildPrimaryNo("4");

		// Member m = new Member();

		Evaluation evaluation = new Evaluation();
		evaluation.setAverage(4);

		Category category = new Category(address, 2, evaluation);

		int num = 0;

		ArrayList<Restaurant> list = t.showList(category, num);

		System.out.println(list);
	}
}
