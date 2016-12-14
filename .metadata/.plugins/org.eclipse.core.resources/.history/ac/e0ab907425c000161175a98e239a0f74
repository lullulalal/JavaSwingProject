package vo;

public class Address {

	private String sido;
	private String sigungu;
	private String eubmyundong;
	private String streetName;
	private String buildingName;
	private String postCode;
	private String buildPrimaryNo;
	private String buildSecondaryNo;
	private String dong;
	private String ri;
	private String phone;//ÀüÈ­¹ø
	
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
	
	public String getBuildSecondaryNo() {
		return buildSecondaryNo;
	}

	public void setBuildSecondaryNo(String buildSecondaryNo) {
		this.buildSecondaryNo = buildSecondaryNo;
	}

	
	public String getBuildPrimaryNo() {
		return buildPrimaryNo;
	}

	public void setBuildPrimaryNo(String buildPrimaryNo) {
		this.buildPrimaryNo = buildPrimaryNo;
	}

	public void setDong(String dong) {
		this.dong = dong;
	}

	public void setRi(String ri) {
		this.ri = ri;
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	//lullulalal
	@Override
	public String toString() {
		String rtnStr = null;
		if(completedAddress == null){
			StringBuilder strb = new StringBuilder();
			
			if (postCode != null){
				strb.append("(");
				strb.append(postCode);
				strb.append(") ");
			}
			
			if (sido != null) strb.append(sido);
			if (sigungu != null) strb.append(" " + sigungu);
			if (eubmyundong != null) strb.append(" " + eubmyundong);
			if (streetName != null) strb.append(" " + streetName);
			if (buildPrimaryNo != null) strb.append(" " + buildPrimaryNo);
			if (buildSecondaryNo != null ) strb.append("-" + buildSecondaryNo);
			
			if( dong != null || ri != null || buildingName != null){
				strb.append(" (");
				if (dong != null) strb.append(dong);
				if (ri != null) strb.append(" ," + ri);
				if (buildingName != null) strb.append(" ," + buildingName);
				strb.append(")");
			}
			
			if (phone != null ) strb.append(" " + phone);
			rtnStr = strb.toString();
		}
		else
			rtnStr = completedAddress;
		
		return rtnStr;
	}
}
