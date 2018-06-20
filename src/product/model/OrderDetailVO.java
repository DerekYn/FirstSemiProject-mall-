package product.model;

public class OrderDetailVO {
	
	private int odrdetailno;    // 주문 상세에 대한 항목들의 일련번호
	private int pcode;          // 주문한 물품의 제품코드
	private String odrcode;     // 주문한 물품들에 해당하는 주문번호
	private int odrprice;       // 주문할 당시의 가격
	private int oqty;           // 주문량
	private int deliverStatus;  // 배송상태
	private String deliverDate; // 배송완료 일자
	
	public OrderDetailVO() {}
	
	public OrderDetailVO(int odrdetailno, int pcode, String odrcode, int odrprice, int oqty, int deliverStatus, String deliverDate) {		
		this.odrdetailno = odrdetailno;
		this.pcode = pcode;
		this.odrcode = odrcode;
		this.odrprice = odrprice;
		this.oqty = oqty;
		this.deliverStatus = deliverStatus;
		this.deliverDate = deliverDate;
	}

	public int getOdrdetailno() {
		return odrdetailno;
	}

	public void setOdrdetailno(int odrdetailno) {
		this.odrdetailno = odrdetailno;
	}

	public int getPcode() {
		return pcode;
	}

	public void setPcode(int pcode) {
		this.pcode = pcode;
	}

	public String getOdrcode() {
		return odrcode;
	}

	public void setOdrcode(String odrcode) {
		this.odrcode = odrcode;
	}

	public int getOdrprice() {
		return odrprice;
	}

	public void setOdrprice(int odrprice) {
		this.odrprice = odrprice;
	}

	public int getOqty() {
		return oqty;
	}

	public void setOqty(int oqty) {
		this.oqty = oqty;
	}

	public int getDeliverStatus() {
		return deliverStatus;
	}

	public void setDeliverStatus(int deliverStatus) {
		this.deliverStatus = deliverStatus;
	}

	public String getDeliverDate() {
		return deliverDate;
	}

	public void setDeliverDate(String deliverDate) {
		this.deliverDate = deliverDate;
	}
	 
	
}
