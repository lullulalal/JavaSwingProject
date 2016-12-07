package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import vo.Address;
import vo.Category;
import vo.Evaluation;
import vo.Member;
import vo.Restaurant;

public class ServerThread implements Runnable {

	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private ServerManager manager = new ServerManager();

	public ServerThread(ObjectInputStream ois, ObjectOutputStream oos) {
		this.ois = ois;
		this.oos = oos;
	}

	@Override
	public void run() {
		boolean flag = true;
		while (flag) {
			try {
				Object[] protocol = (Object[]) ois.readObject();
				switch ((String) protocol[0]) {
				case "join":
					Member member1 = (Member) protocol[1];
					oos.writeObject(manager.join(member1));
					break;

				case "login":
					Member member2 = (Member) protocol[1];
					break;

				case "logout":
					Member member3 = (Member) protocol[1];
					break;
					
				case "showList":
					Category category1 = (Category) protocol[1];
					int showList = (int) protocol[2];
					break;
				
				//lullulalal
				case "insert":
					Restaurant restaurant = (Restaurant) protocol[1];
					boolean rstInsert = manager.insertRestaurant(restaurant);
					oos.writeObject(rstInsert);
					break;

				//lullulalal
				case "evaluate":
					Evaluation evaluation = (Evaluation) protocol[1];
					restaurant = (Restaurant) protocol[2];
					boolean rstEvaluation = manager.evaluateResataurant(evaluation, restaurant);
					oos.writeObject(rstEvaluation);
					break;
					
				//lullulalal
				case "recommend" :
					restaurant = (Restaurant) protocol[1];
					boolean rstRecommend = manager.recommendRestaurant(restaurant);
					oos.writeObject(rstRecommend);
					break;
					
				//lullulalal	
				case "findAddress" :
					Address address = (Address) protocol[1];
					ArrayList<Address> addresses = manager.findAddresses(address);
					oos.writeObject(addresses);
					break;

				case "askRestaurant":
					Category category2 = (Category) protocol[1];
					Member member4 = (Member) protocol[2];
					boolean isRandom = (boolean) protocol[3];
					
					manager.askRestaurant(category2, member4, isRandom);
					
					break;

				case "replyRestaurant":
					Restaurant restaurant2 = (Restaurant) protocol[1];
					Member memberTo = (Member) protocol[2];
					Member memberFrom = (Member) protocol[3];
					break;

				default:
					break;
				}
			} catch (ClassNotFoundException e) {
				System.out.println("ClassNotFoundException");
				e.printStackTrace();
			} catch (IOException e) {
				flag = false;
				System.out.println("IOException");
				e.printStackTrace();
			}
		}
	}

}
