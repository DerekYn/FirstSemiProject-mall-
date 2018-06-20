package product.model;

public class CartVO {
	private int cartno;  			// 장바구니 번호
	private String fk_userid;   	// 회원ID
	private int fk_pcode;			// 상품코드
	private int poqty;				// 상품수량
	
	private ProductVO item; // 제품정보객체  // 제품가격, 제품이미지도 불러와야 되니까 그게 있는 ProductVO를 땡겨온다. 
	
	public CartVO() {}
	
	public CartVO(int cartno, String fk_userid, int fk_pcode, int poqty) {		
		this.cartno = cartno;
		this.fk_userid = fk_userid;
		this.fk_pcode = fk_pcode;
		this.poqty = poqty;
		this.item = item;
	}

	public int getCartno() {
		return cartno;
	}

	public void setCartno(int cartno) {
		this.cartno = cartno;
	}

	public String getFk_userid() {
		return fk_userid;
	}

	public void setFk_userid(String fk_userid) {
		this.fk_userid = fk_userid;
	}

	public int getFk_pcode() {
		return fk_pcode;
	}

	public void setFk_pcode(int fk_pcode) {
		this.fk_pcode = fk_pcode;
	}

	public int getPoqty() {
		return poqty;
	}

	public void setPoqty(int poqty) {
		this.poqty = poqty;
	}
	
	public ProductVO getItem() {
		return item;
	}

	public void setItem(ProductVO item) {
		this.item = item;
	}
	
	  
	 
}
