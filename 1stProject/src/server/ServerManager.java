package server;

import java.util.ArrayList;

import manager.Interface;
import vo.Category;
import vo.Evaluation;
import vo.Member;
import vo.Restaurant;

public class ServerManager implements Interface{

	@Override
	public boolean insertRestaurant(Restaurant restaurant) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean login(Member member) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean join(Member member) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean evaluateResataurant(Evaluation evalauation) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<Restaurant> showList(Category category, int num) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean logout(Member member) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void askRestaurant(Category category, Member member, boolean isRandom) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void replyRestaurant(Category category, Restaurant restaurant, Member to, Member from) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
