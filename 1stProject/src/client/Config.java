package client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import vo.Member;

public class Config implements Serializable{

	private static Config instance;
	public static final String RESTAURANT_TABLE = "restaurants";
	public static final String STANBY_TABLE = "stanby";
	
	public static int RESTAURANTS_UPDATE_COUNTER = 0;
	public static int STANBY_UPDATE_COUNTER = 0;
	
	private Config(){

	}

	public static Config getInstance(){
		if (instance == null){
			File file = new File(CONIG_PATH);
			if(file.exists() == true){
				try(ObjectInputStream oos = new ObjectInputStream(new FileInputStream(CONIG_PATH));) {
					instance = (Config) oos.readObject();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
			else
				instance = new Config();
		}
		return instance;
	}

	public static void test(){
		System.out.println("haha");
	}
	private static String CONIG_PATH = "config.dat";
	
	private boolean onAutoLogin;
	private Member autoLoginUser;

	public boolean getAutoLoginConfig(){
		return onAutoLogin;
	}
	
	public void setAutoLoginConfig(boolean state){
		onAutoLogin = state;
	}
	
	public Member getAutoLoginUser() {
		return autoLoginUser;
	}

	public void setAutoLoginUser(Member autoLoginUser) {
		this.autoLoginUser = autoLoginUser;
	}
	
	public void saveConfig(){
		try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CONIG_PATH));) {
			System.out.println("save");
			oos.writeObject(instance);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
