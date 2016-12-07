package vo;

public class Member {
	
	private String id;
	private String password;
	private String name;
	private int permission;
	private String birth;
	
	public static final int USER = 1;
	public static final int VALUER = 2;
	
	public Member(String id, String password, String name, int permission, String birth) {
		this.id = id;
		this.password = password;
		this.name = name;
		this.permission = permission;
		this.birth = birth;
	}
	
	public Member() {

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getPermission() {
		return permission;
	}

	public void setPermission(int permission) {
		this.permission = permission;
	}
	
	public String getBirth() {
		return birth;
	}
	
	public void setBirth(String birth) {
		this.birth = birth;
	}
	
	
}
