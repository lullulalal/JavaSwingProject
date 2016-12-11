package client;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.concurrent.locks.ReentrantLock;

import vo.Address;
import vo.Category;
import vo.Evaluation;
import vo.Member;
import vo.Restaurant;

public class ClientManager  {

	private static ObjectOutputStream oos;
	private static ReentrantLock lock = new ReentrantLock();
	
	public static void setOos(ObjectOutputStream oos){
		ClientManager.oos = oos;
	}

	public void insertRestaurant(int guiId, Restaurant restaurant) {
	}

	public void login(int guiId, Member member) {
	}

	public void join(int guiId, Member member) {
	}

	public void evaluateRestaurant(int guiId, Evaluation evalauation, Restaurant restaurant) {
	}

	public void recommendRestaurant(int guiId, Restaurant restaurant, Member valuer) {
	}

	public void showList(int guiId, Category category, int num) {
	}

	public void logout(int guiId, Member member) {
	}

	public void askRestaurant(int guiId, Category category, Member member, boolean isRandom) {
	}

	public void replyRestaurant(int guiId, Restaurant restaurant, Member to, Member from) {
	}

	public void findAddresses(int guiId, Address address) {
	}
	
	public void test(){
		Object[] data = {0, "test"};
		request(data);
	}
	
	private void request(Object data){
		try {
			lock.lock();
			oos.writeObject(data);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			lock.unlock();
		}
	}
	
}
