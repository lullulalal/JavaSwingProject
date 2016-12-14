package client;

public class ClientMain {

	public static void main(String[] args) {
	    try
	    {
	        org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
	    }
	    catch(Exception e)
	    {
	        //TODO exception
	    }
		new Thread(new ClientReceiver()).start();
	}

}
