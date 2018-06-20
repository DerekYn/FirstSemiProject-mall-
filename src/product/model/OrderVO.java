package product.model;

public class OrderVO {
	
	private String odrcode;		// 회원의 주문번호
	private String fk_userid;   // 회원 테이블에서 참조한 회원ID
	private int odrtotalPrice;	// 해당 주문번호에 대한 주문 총 금액
	private int odrtotalPoint;  // 해당 주문번호에 대한 적립될 총 포인트
	private String odrdate;		// 해당 주문 일자
	
	public OrderVO() {}
	
	public OrderVO(String odrcode, String fk_userid, int odrtotalPrice, int odrtotalPoint, String odrdate) {		
		this.odrcode = odrcode;
		this.fk_userid = fk_userid;
		this.odrtotalPrice = odrtotalPrice;
		this.odrtotalPoint = odrtotalPoint;
		this.odrdate = odrdate;
	}

	public String getOdrcode() {
		return odrcode;
	}

	public void setOdrcode(String odrcode) {
		this.odrcode = odrcode;
	}

	public String getFk_userid() {
		return fk_userid;
	}

	public void setFk_userid(String fk_userid) {
		this.fk_userid = fk_userid;
	}

	public int getOdrtotalPrice() {
		return odrtotalPrice;
	}

	public void setOdrtotalPrice(int odrtotalPrice) {
		this.odrtotalPrice = odrtotalPrice;
	}

	public int getOdrtotalPoint() {
		return odrtotalPoint;
	}

	public void setOdrtotalPoint(int odrtotalPoint) {
		this.odrtotalPoint = odrtotalPoint;
	}

	public String getOdrdate() {
		return odrdate;
	}

	public void setOdrdate(String odrdate) {
		this.odrdate = odrdate;
	}
	
	
}
