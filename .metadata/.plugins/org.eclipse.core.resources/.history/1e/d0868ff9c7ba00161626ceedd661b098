package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import vo.Category;
import vo.Evaluation;
import vo.Member;
import vo.Restaurant;

public class ServerThread implements Runnable {

	private ObjectInputStream ois;
	private ObjectOutputStream oos;

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
					break;

				case "login":
					Member member2 = (Member) protocol[1];
					break;

				case "showList":
					Category category1 = (Category) protocol[1];
					int showList = (int) protocol[2];
					break;

				case "insert":
					Restaurant restaurant = (Restaurant) protocol[1];
					break;

				case "evaluate":
					Evaluation evaluation = (Evaluation) protocol[1];
					break;

				case "logout":
					Member member3 = (Member) protocol[1];
					break;

				case "askRestaurant":
					Category category2 = (Category) protocol[1];
					Member member4 = (Member) protocol[2];
					boolean isRecommend = (boolean) protocol[3];
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
			} catch (IOException e) {
				flag = false;
				System.out.println("IOException");
			}
		}
	}

}
