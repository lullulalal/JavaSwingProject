package vo;

import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Restaurant {
	
	private String restaurantName;
	private String price;
	private String operationHour;
	private Category category;
	private ArrayList<ImageIcon> images;
	private ArrayList<Evaluation> userEvaluations;
	private ArrayList<String> menu;
	private int state;
	
	public static final int STANDBY = 1;
	public static final int REGISTRATION = 2;
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
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	
	
}
