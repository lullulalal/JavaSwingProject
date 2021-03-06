package vo;

import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Restaurant implements Serializable{

	private String restaurantName;
	private String price;
	private String operationHour;
	private Category category;
	private ArrayList<ImageIcon> images;
	private ArrayList<Evaluation> userEvaluations;
	private ArrayList<String> menu;
	private ArrayList<String> recommender;
	private int recommendNum;
	
	public static final int BOUNDARY_OF_ASCEND = 0;
	
	public Restaurant(String restaurantName, String price, String operationHour, Category category,
			ArrayList<ImageIcon> images, ArrayList<Evaluation> userEvaluations, ArrayList<String> menu, int recommendNum, 
			ArrayList<String> recommender) {
		this.restaurantName = restaurantName;
		this.price = price;
		this.operationHour = operationHour;
		this.category = category;
		this.images = images;
		this.userEvaluations = userEvaluations;
		this.menu = menu;
		this.recommendNum = recommendNum;
		this.recommender = recommender;
	}
	
	public Restaurant(){
		
	}
	
	public ArrayList<String> getRecommender() {
		return recommender;
	}

	public void setRecommender(ArrayList<String> recommender) {
		this.recommender = recommender;
	}

	public String getRestaurantName() {

		return restaurantName;
		
	}
	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getOperationHour() {
		return operationHour;
	}
	public void setOperationHour(String operationHour) {
		this.operationHour = operationHour;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public ArrayList<ImageIcon> getImages() {
		return images;
	}
	public void setImages(ArrayList<ImageIcon> images) {
		this.images = images;
	}
	public ArrayList<Evaluation> getUserEvaluations() {
		return userEvaluations;
	}
	public void setUserEvaluations(ArrayList<Evaluation> userEvaluations) {
		this.userEvaluations = userEvaluations;
	}
	public ArrayList<String> getMenu() {
		return menu;
	}
	public void setMenu(ArrayList<String> menu) {
		this.menu = menu;
	}
	public int getRecommendNum() {
		return recommendNum;
	}
	public void plusRecommend() {
		recommendNum++;
	}
	
}
