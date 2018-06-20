package product.model;

public class ProductVO {
	
	private int pcode;  				// 제품코드
	private String pname;      			// 제품명
	private String fk_pcategory; 		// 카테고리
	private int pcolor;   				// 색상
	private int psize;      			// 사이즈
	private String pimage; 				// 대표이미지
	private int pqty;       			// 재고량
	private int price;       			// 원가
	private int saleprice;     			// 할인가
	private int pstatus;       			// 제품 NEW / BEST 구분
	private int point;              	// 제품포인트 
	private String pcontent;      		// 상세 설명
	private String pinputdate;    		// 제품 등록일
	
	private int totalPrice;   	// 주문량 * 제품판매가(할인해서 팔 것이므로)		// totalPrice를 통해 주문테이블에 주문총액이란 것을 직접 넣어주겠다. 
	private int totalPoint;   	// 주문량 * 포인트점수
	
	public ProductVO() {}
	
	public ProductVO(int pcode, String pname, String fk_pcategory, int pcolor, int psize, String pimage, int pqty,
			int price, int saleprice, int pstatus, int point, String pcontent, String pinputdate) {		
		this.pcode = pcode;
		this.pname = pname;
		this.fk_pcategory = fk_pcategory;
		this.pcolor = pcolor;
		this.psize = psize;
		this.pimage = pimage;
		this.pqty = pqty;
		this.price = price;
		this.saleprice = saleprice;
		this.pstatus = pstatus;
		this.point = point;
		this.pcontent = pcontent;
		this.pinputdate = pinputdate;
	}
	
	
	// 오버로딩
	public ProductVO(int pcode, String pname, String fk_pcategory, int pcolor, int psize, String pimage, int pqty,
			int price, int saleprice, int pstatus, int point, String pcontent, String pinputdate, int totalPrice,int totalPoint) {		
		this.pcode = pcode;
		this.pname = pname;
		this.fk_pcategory = fk_pcategory;
		this.pcolor = pcolor;
		this.psize = psize;
		this.pimage = pimage;
		this.pqty = pqty;
		this.price = price;
		this.saleprice = saleprice;
		this.pstatus = pstatus;
		this.point = point;
		this.pcontent = pcontent;
		this.pinputdate = pinputdate;
		this.totalPrice = totalPrice;
		this.totalPoint = totalPoint;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

	public void setTotalPoint(int totalPoint) {
		this.totalPoint = totalPoint;
	}

	public int getPcode() {
		return pcode;
	}

	public void setPcode(int pcode) {
		this.pcode = pcode;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public String getFk_pcategory() {
		return fk_pcategory;
	}

	public void setFk_pcategory(String fk_pcategory) {
		this.fk_pcategory = fk_pcategory;
	}

	public int getPcolor() {
		return pcolor;
	}

	public void setPcolor(int pcolor) {
		this.pcolor = pcolor;
	}

	public int getPsize() {
		return psize;
	}

	public void setPsize(int psize) {
		this.psize = psize;
	}

	public String getPimage() {
		return pimage;
	}

	public void setPimage(String pimage) {
		this.pimage = pimage;
	}

	public int getPqty() {
		return pqty;
	}

	public void setPqty(int pqty) {
		this.pqty = pqty;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getSaleprice() {
		return saleprice;
	}

	public void setSaleprice(int saleprice) {
		this.saleprice = saleprice;
	}

	public int getPstatus() {
		return pstatus;
	}

	public void setPstatus(int pstatus) {
		this.pstatus = pstatus;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public String getPcontent() {
		return pcontent;
	}

	public void setPcontent(String pcontent) {
		this.pcontent = pcontent;
	}

	public String getPinputdate() {
		return pinputdate;
	}

	public void setPinputdate(String pinputdate) {
		this.pinputdate = pinputdate;
	}
	
	
	public void setTotalPriceTotalPoint(int oqty) {		// 이거 하나로 totalPrice와 totalPoint 두개를 얻어옴
		 // 총판매가(실제판매가 * 주문량) 입력하기
		 totalPrice = saleprice * oqty;
		 
		 // 총포인트(제품1개당 포인트 * 주문량) 입력하기
		 totalPoint = point * oqty;
	}
	public int getTotalPrice() {
		 return totalPrice;
	}
	 
	public int getTotalPoint() {
		 return totalPoint;
	}
	
	
}
