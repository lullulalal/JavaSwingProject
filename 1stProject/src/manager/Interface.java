package manager;

import java.util.ArrayList;

import vo.Category;
import vo.Evaluation;
import vo.Member;
import vo.Restaurant;

public interface Interface {
	public boolean insertRestaurant(Restaurant restaurant);
	public boolean login(Member member);
	public boolean join(Member member);
	public boolean evaluateResataurant(Evaluation evalauation, Restaurant restaurant);
	public boolean recommendRestaurant(Restaurant restaurant);
	public ArrayList<Restaurant> showList(Category category, int num);
	public boolean logout(Member member);
	public void askRestaurant(Category category, Member member, boolean isRandom);
	public void replyRestaurant(Category category, Restaurant restaurant, Member to, Member from);
}
