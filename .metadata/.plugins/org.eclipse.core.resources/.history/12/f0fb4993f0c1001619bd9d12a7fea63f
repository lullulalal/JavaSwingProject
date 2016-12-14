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
		Object[] sendData = {guiId, "insert", restaurant};
		request(sendData);
	}

	public void login(int guiId, Member member) {
		Object[] sendData = {guiId, "login", member};
		request(sendData);
	}

	public void join(int guiId, Member member) {
		Object[] sendData = {guiId, "join", member};
		request(sendData);
	}

	public void evaluateRestaurant(int guiId, Evaluation evalauation, Restaurant restaurant) {
		Object[] sendData = {guiId, "evaluate", evalauation, restaurant};
		request(sendData);
	}

	public void recommendRestaurant(int guiId, Restaurant restaurant, Member valuer) {
		Object[] sendData = {guiId, "recommend", restaurant, valuer};
		request(sendData);
	}

	public void showList(int guiId, Category category, int num) {
		Object[] sendData = {guiId, "showList", category, num};
		request(sendData);
	}

	public void logout(int guiId, Member member) {
		Object[] sendData = {guiId, "logout", member};
		request(sendData);
	}

	public void askRestaurant(int guiId, Category category, Member member, boolean isRandom) {
		Object[] sendData = {guiId, "askRestaurant", category, member, isRandom};
		request(sendData);
	}

	public void replyRestaurant(int guiId, Restaurant restaurant, Member to, Member from) {
		Object[] sendData = {guiId, "replyRestaurant", restaurant, to, from};
		request(sendData);
	}

	public void findAddresses(int guiId, Address address) {
		Object[] sendData = {guiId, "findAddress", address};
		request(sendData);
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
