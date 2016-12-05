package server;

import java.util.ArrayList;

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

	
}
