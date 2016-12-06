package vo;

public class Address {

	private String sido;
	private String sigungu;
	private String eubmyundong;
	private String streetName;
	private String buildingName;
	private String postCode;
	private String detail;
	
	public Address(String sido, String sigungu, String eubmyundong, String streetName, String buildingName,
			String postCode, String detail) {
		super();
		this.sido = sido;
		this.sigungu = sigungu;
		this.eubmyundong = eubmyundong;
		this.streetName = streetName;
		this.buildingName = buildingName;
		this.postCode = postCode;
		this.detail = detail;
	}
	
	public Address(String str) {

	}
	
	public String getSido() {
		return sido;
	}
	public void setSido(String sido) {
		this.sido = sido;
	}
	public String getSigungu() {
		return sigungu;
	}
	public void setSigungu(String sigungu) {
		this.sigungu = sigungu;
	}
	public String getEubmyundong() {
		return eubmyundong;
	}
	public void setEubmyundong(String eubmyundong) {
		this.eubmyundong = eubmyundong;
	}
	public String getStreetName() {
		return streetName;
	}
	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}
	public String getBuildingName() {
		return buildingName;
	}
	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}
	public String getPostCode() {
		return postCode;
	}
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}

	
}
