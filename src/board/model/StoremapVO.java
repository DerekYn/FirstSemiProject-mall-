package board.model;

public class StoremapVO {

	private int storeno;       // 점포no 
	private String storectg;   // 점포구분
	private String storeName;  // 점포명
	private double latitude;   // 위도
	private double longitude;  // 경도
	private int zindex;        // 우선순위(z-index) 점포no로 사용됨.
	private String tel;        // 전화번호
	private String addr;       // 주소
	private String sopen;     // 오픈시간
	private String sclose;    // 마감시간
	
	public StoremapVO() {}
	
	public StoremapVO(int storeno, String storectg, String storeName, double latitude, double longitude, int zindex, String tel,
			String addr, String sopen, String sclose) {
		this.storeno = storeno;
		this.storectg = storectg;
		this.storeName = storeName;
		this.latitude = latitude;
		this.longitude = longitude;
		this.zindex = zindex;
		this.tel = tel;
		this.addr = addr;
		this.sopen = sopen;
		this.sclose = sclose;
	}

	public int getZindex() {
		return zindex;
	}

	public void setZindex(int zindex) {
		this.zindex = zindex;
	}

	public int getStoreno() {
		return storeno;
	}

	public void setStoreno(int storeno) {
		this.storeno = storeno;
	}
	
	public String getStorectg() {
		return storectg;
	}

	public void setStorectg(String storectg) {
		this.storectg = storectg;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}


	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getSopen() {
		return sopen;
	}

	public void setSopen(String sopen) {
		this.sopen = sopen;
	}

	public String getSclose() {
		return sclose;
	}

	public void setSclose(String sclose) {
		this.sclose = sclose;
	}

	
	
}
