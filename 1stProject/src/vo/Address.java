package vo;

public class Address {

	private String sido;
	private String sigungu;
	private String eubmyundong;
	private String streetName;
	private String buildingName;
	private String postCode;
	private String streetPrimaryNo;
	private String streetSecondaryNo;
	private String dong;
	private String ri;
	private String detail;
	
	private String completedAddress;
	
	public Address() {
		/*super();
		this.sido = sido;
		this.sigungu = sigungu;
		this.eubmyundong = eubmyundong;
		this.streetName = streetName;
		this.buildingName = buildingName;
		this.postCode = postCode;
		this.detail = detail;*/
	}
	
	public Address(String str) {
		completedAddress = str;
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
	
	//lullulalal
	@Override
	public String toString() {
		String rtnStr = null;
		if(sido != null){
			StringBuilder strb = new StringBuilder();
			strb.append("(");
			strb.append(postCode);
			strb.append(") ");
			if (sido != null) strb.append(sido);
			if (sigungu != null) strb.append(" " + sigungu);
			if (eubmyundong != null) strb.append(" " + eubmyundong);
			if (streetName != null) strb.append(" " + streetName);
			if (streetPrimaryNo != null) strb.append(" " + streetPrimaryNo);
			if (streetSecondaryNo != null) strb.append("-" + streetSecondaryNo);
			strb.append(" (");
			if (dong != null) strb.append(dong);
			if (ri != null) strb.append(" ," + ri);
			if (buildingName != null) strb.append(" ," + buildingName);
			strb.append(")");
			rtnStr = strb.toString();
		}
		else
			rtnStr = completedAddress;
		
		return rtnStr;
	}
}
