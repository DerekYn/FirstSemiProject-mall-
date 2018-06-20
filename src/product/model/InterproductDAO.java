package product.model;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public interface InterproductDAO {
	// 좋아요 비율이 높은 순으로 HIT리스트 가져오기 //
	List<HashMap<String, String>> getHitList(String category) throws SQLException;
	
	// 제품번호로 제품 데이터 전체 가져오기 //
	HashMap<String, String> getProductByPcode(String pcode) throws SQLException;
	
	// 모든 제품목록 가져오기 //
	List<HashMap<String, String>> getAllProduct() throws SQLException;
		
	// 모든 제품목록 가져오기 (최신순, 높은가격순, 낮은가격순) //
	List<HashMap<String, String>> getAllProduct(String order, String category, int startRno, int endRno) throws SQLException;
	
	// 제품 추가하기 //
	int addProduct(int pcode, int pqty) throws SQLException;
	
	// 별점 구하기 //
	String getStarpoint(String pname) throws SQLException;
	
	// *** tbl_cart 테이블(장바구니 테이블)을 페이징 처리해서 조회해주는 추상 메소드 *** //
	List<CartVO> getCartList(String fk_userid, int currentShowPageNo, int sizePerPage)throws SQLException; 
	
	// *** fk_userid 별 장바구니 전체갯수 조회 추상메소드  *** //
	int getTotalCartCount(String fk_userid)throws SQLException; 
	
	// *** tbl_cart (장바구니 테이블)에 수량을 변경해주는 추상 메소드 *** //
	int editCart(String poqty,String cartno)throws SQLException;
	
	// *** 장바구니에서 특정 제품 삭제하기전  해당 장바구니 번호의 소유주(userid)가 누구인지를 알려주는 추상 메소드 *** //   
	String getUseridByCartno(String cartno) throws SQLException;
	
	// *** 장바구니에서 특정 제품 삭제하는 추상 메소드 *** // 
	int deleteCartno(String cartno) throws SQLException;
	
	// *** tbl_product 테이블에서  추상 메소드 *** //
	List<ProductVO> getProductVOListByPspec(int startRno, int endRno)throws SQLException;
	
	// *** tbl_product 테이블에서 제품1개 정보만 출력해주는 추상 메소드 *** //
	ProductVO getProductOneByPcode(String pcode)throws SQLException;	
	
	// *** 주문코드(명세서번호)시퀀스 값 따오는 추상 메소드 *** //
	int getSeq_order_detail() throws SQLException;
	
	// *** 장바구니 제품 삭제(delete)하는 추상 메소드 *** HashMap 사용//
	int deleteProduct(String str)throws SQLException;
	
	
	// *** 선택주문하기(transaction 처리) 추상메소드 *** //
		/*
	     ==== *** Transaction 처리하기 *** ====
	     1. 주문개요 테이블(jsp_order)에 입력(insert)
	     2. 주문상세 테이블(jsp_order_detail)에 입력(insert) 
	     3. 구매하는 사용자의 coin 컬럼의 값을 구매한 가격만큼 감하고,
	        point 컬럼의 값은 구매한 포인트만큼 증가하며(update),
	     4. 주문한 제품의 잔고량은 주문량 만큼 감해야 하고(update),
	     5. 장바구니에서 주문을 한 것이라면 장바구니 비우기(status 컬럼을 0 으로 변경하는 update) 
	               를 해주는 DAO에서 만든 메소드 호출하기        
	    */	
	int add_Order_OrderDetail(String odrcode        // 주문코드(명세서번호)
				            , String userid         // 사용자ID 
				            , int sumtotalprice     // 주문총액
				            , int sumtotalpoint     // 주문총포인트
				            , String[] pcodeArr      // 제품번호 배열
				            , String[] poqtyArr      // 주문량 배열 
				            , String[] salepriceArr // 주문할 당시의 해당 제품의 판매단가 
				            , String[] cartnoArr    // 장바구니번호 배열
				           	) throws SQLException;

	
	
	// *** 최근 1년 이내까지 주문한 주문내역을 조회해오는 추상메소드 *** //	
	List<HashMap<String, String>> getOrderList(String userid, int currentShowPageNo, int sizePerPage) throws SQLException;  
	
		
	// *** 제품번호들에 해당하는 제품목록을 조회해오는 추상메소드 *** //
	List<ProductVO> getJumunfinishProductList(String pcodes) throws SQLException;  	
		
	// *** userid 별 최근 1년 이내까지 주문한 주문갯수 조회 추상메소드  *** //
	int getTotalOrderCount(String userid) throws SQLException;
	
	// *** 제품 선호도를 알아오는 추상메소드 *** //
	List<HashMap<String, String>> getLikeDislikeCnt() throws SQLException;
	
	// *** tbl_cart 테이블(장바구니 테이블)에 물건을 입력해주는 추상 메소드 *** // 
	int addCart(String userid, String fk_pcode, String poqty) throws SQLException;
	
	
	//***tbl_category 테이블에서 카테고리코드(code)와 카테고리명(cname)을 가져오는 추상메소드***///
		List<CategoryVO> getCategoryList()throws SQLException;
			
	//tbl_product 테이블에 신규제품으로 insert되어질
	//제품번호 시퀀스 seq_tbl_product.nextval을 따오는 추상메소드***//
	int getPnumOfProduct()throws SQLException;

	//*** 신규제품등록을 해주는 추상메소드***//
	int productInsert(ProductVO pvo)throws SQLException;
	
	//신규제품등록 시 추가 이미지 파일 정보를 tbl_product_image테이블에 insert해주는 추상 메소드***//
	int product_imagefile_Insert(int pcode, String attachFilename)throws SQLException;
		
	// *** tbl_product 테이블에서 제품1개 정보만 출력해주는 추상 메소드 *** //
	ProductVO getProductOneByPnum(String pcode) throws SQLException;
		
	// *** jsp_product_imagefile 테이블에서 복수개 이미지 파일을 출력해주는 추상 메소드 *** //
	List<ProductImagefileVO> getProductImagefileByPnum(String pcode) throws SQLException;
	
	// ** tbl_likedislike 테이블에 좋아요insert 를 해주는 추상 메소드 ** //
	int insertLike(String userid, String pcode, String comments) throws SQLException;
		
	// ** tbl_likedislike 테이블에 싫어요insert 를 해주는 추상 메소드 ** //
	int insertDislike(String userid, String pcode, String comments) throws SQLException;
	
	// ** tbl_likedislike 테이블에서 특정 제품에 대한 count(*)를 조회하는 추상 메소드 ** //
	HashMap<String, String> getLikeDislikeCount(String pcode) throws SQLException;
	
	//** tbl_product테이블에서 데이터 중 5개 행만 조회해오는 추상메소드 **//
	List<LikedislikeVO> getProductVOListByUserid(int startRno, int endRno, String pcode)throws SQLException;
	
	//**tbl_likedislike에 한줄평 insert 하는 메소드 **//
	int getupdatememo(String userid,String  pcode,String comments, String num)throws SQLException;
	
	//**tbl_likedislike에 한줄평 select 하는 메소드 **//
	String getshowcomment(String userid,String pcode)throws SQLException;
	
	
	//**상품평의 전체 갯수를 조회해 오는 추상 메소드***//
	int totalMemoCount(String pcode) throws SQLException;
	
	// BEST 제품목록 가져오기 (최신순, 높은가격순, 낮은가격순) //
	public List<HashMap<String, String>> getBestProduct(String category, int startRno, int endRno) throws SQLException;
	
	// 상품을 지워주는 메소드
	int deleteProducts(String pcode)throws SQLException;
	
	// 모든 제품목록 가져오기 //
	List<HashMap<String, String>> getAllProducts() throws SQLException;
	
}
