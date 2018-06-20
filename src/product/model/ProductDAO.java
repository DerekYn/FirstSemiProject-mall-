package product.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ProductDAO implements InterproductDAO {
	private DataSource ds = null;
	// 객체변수 ds는 아파치 톰캣이 제공하는 DBCP(DB Connection Pool)이다.
	
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	/* 
		== MemoDAO 생성자에서 해야 할 일은  == 
		아파치 톰캣이 제공하는 DBCP(DB Connection Pool) 객체인 ds 를 얻어오는 것이다.		
	*/
	
	public ProductDAO() {
		
		try {
			// 3. Code example 에서 복붙
			Context initContext = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc/myoracle");		
		} catch (NamingException e) {
			e.printStackTrace();
		}
		
	}
	
	// *** 사용한 자원을 반납하는  close() 메소드 생성하기 *** //
	public void close() {
		try {
			if(rs != null) {
				rs.close();
				rs = null;
			}
			
			if(pstmt != null) {
				pstmt.close();
				pstmt = null;
			}
			
			if(conn != null) {
				conn.close();
				conn = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 좋아요 비율이 높은 순으로 HIT리스트 뽑아오기 //
	@Override
	public List<HashMap<String, String>> getHitList(String category) throws SQLException {
		List<HashMap<String, String>> hitlist = null;
		
		try {
			
			if(category == null || "".equalsIgnoreCase(category) || "all".equalsIgnoreCase(category)) {
				category = "";
			}
				
			else {
				System.out.println("category2-1 : " + category);
				switch(category) {
					case "outer":
						category = " where fk_pcategory = 1000 ";
						break;
					case "top":
						category = " where fk_pcategory = 2000 ";
						break;
					case "bottom":
						category = " where fk_pcategory = 3000 ";
						break;
					case "acc":
						category = " where fk_pcategory = 4000 ";
						break;
					case "suit":
						category = " where fk_pcategory = 5000 ";
						break;
					default:
						category = "";
						break;
				}
				System.out.println("category2-2 : " + category);
			}
			
			conn = ds.getConnection();
			
			String sql = " select pname, fk_pcategory "
					   + " from "
					   + " ( "
					   + "   select rownum as rno, pname, likedislikevalue, fk_pcategory "
					   + "   from "
					   + "   ( "
					   + "     select A.pname, round(2 * nvl(likecnt, 0)/(nvl(likecnt, 0)+nvl(dislikecnt, 0))*5) * 5 as likedislikevalue, fk_pcategory "
					   + "     from view_like A full join view_dislike B"
					   + "     on A.pname = B.pname "
					   + "     left join tbl_product C "
					   + "     on A.pname = C.pname "
					   + category
					   + "     order by likedislikevalue desc, A.pname asc "
					   + "   ) "
					   + " ) "
					   + " where rno <= 3 ";
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			int cnt = 0;
			List<String> namelist = null;
			
			while(rs.next()) {
				cnt++;
				
				if(cnt == 1) {
					namelist = new ArrayList<String>();
				}
				
				namelist.add(rs.getString("pname"));
			}
			
			hitlist = new ArrayList<HashMap<String, String>>();
			
			if(namelist == null) {
				return null;
			}
			
			if(namelist.size() == 0) {
				return null;
			}
			
			for(String pname : namelist) {
				sql = " select * "
					+ " from "
					+ " ( "
					+ "   select rownum AS rno, pcode, pname, fk_pcategory, pcolor, psize, pimage, pqty, price, saleprice, pstatus, point, pcontent, pinputdate "
					+ "   from "
					+ "   ( "
					+ "     select * "
					+ "     from tbl_product "
					+ "     where pname = ? "
					+ "   ) "
					+ " ) "
					+ " where rno = 1 ";
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, pname);
				
				rs = pstmt.executeQuery();
				rs.next();
				
				HashMap<String, String> pmap = new HashMap<String, String>();
				
				pmap.put("pcode", rs.getString("pcode"));
				pmap.put("pname", rs.getString("pname"));
				pmap.put("fk_pcategory", rs.getString("fk_pcategory"));
				pmap.put("pcolor", rs.getString("pcolor"));
				pmap.put("psize", rs.getString("psize"));
				pmap.put("pimage", rs.getString("pimage"));
				pmap.put("pqty", rs.getString("pqty"));
				pmap.put("price", rs.getString("price"));
				pmap.put("saleprice", rs.getString("saleprice"));
				pmap.put("pstatus", rs.getString("pstatus"));
				pmap.put("point", rs.getString("point"));
				pmap.put("pcontent", rs.getString("pcontent"));
				pmap.put("pinputdate", rs.getString("pinputdate"));
				
				hitlist.add(pmap);
			}
			
		} finally {
			close();
		}
		
		return hitlist;
	}

	// 제품번호로 제품 데이터 전체 가져오기 //
	@Override
	public HashMap<String, String> getProductByPcode(String pcode) throws SQLException {
		HashMap<String, String> pmap = null;
		
		try {
			
			conn = ds.getConnection();
			
			String sql = " select pcode, pname, fk_pcategory, pcolor, psize, pimage, pqty, price, saleprice, pstatus, point, pcontent, pinputdate "
					   + " from tbl_product "
					   + " where pcode = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, pcode);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				pmap = new HashMap<String, String>();
				pmap.put("pcode", pcode);
				pmap.put("pname", rs.getString("pname"));
				pmap.put("fk_pcategory", rs.getString("fk_pcategory"));
				pmap.put("pcolor", rs.getString("pcolor"));
				pmap.put("psize", rs.getString("psize"));
				pmap.put("pimage", rs.getString("pimage"));
				pmap.put("pqty", rs.getString("pqty"));
				pmap.put("price", rs.getString("price"));
				pmap.put("saleprice", rs.getString("saleprice"));
				pmap.put("pstatus", rs.getString("pstatus"));
				pmap.put("point", rs.getString("point"));
				pmap.put("pcontent", rs.getString("pcontent"));
				pmap.put("pinputdate", rs.getString("pinputdate"));
			}
			
		} finally {
			close();
		}
		
		return pmap;
	}

	// 모든 제품목록 가져오기 //
	@Override
	public List<HashMap<String, String>> getAllProduct() throws SQLException {
		List<HashMap<String, String>> plist = null;
		
		try {
			
			conn = ds.getConnection();
			
			String sql = " SELECT pcode, pname, fk_pcategory, pcolor, psize, pimage, pqty, price, saleprice, pstatus, point, pcontent, pinputdate "
					   + " FROM tbl_product "
					   + " order by pname desc ";
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			int cnt = 0;
			
			while(rs.next()) {
				cnt++;

				if(cnt == 1) {
					plist = new ArrayList<HashMap<String, String>>();
				}
				
				HashMap<String, String> pmap = new HashMap<String, String>();
				pmap.put("pcode", rs.getString("pcode"));
				pmap.put("pname", rs.getString("pname"));
				pmap.put("fk_pcategory", rs.getString("fk_pcategory"));
				pmap.put("pcolor", rs.getString("pcolor"));
				pmap.put("psize", rs.getString("psize"));
				pmap.put("pimage", rs.getString("pimage"));
				pmap.put("pqty", rs.getString("pqty"));
				pmap.put("price", rs.getString("price"));
				pmap.put("saleprice", rs.getString("saleprice"));
				pmap.put("pstatus", rs.getString("pstatus"));
				pmap.put("point", rs.getString("point"));
				pmap.put("pcontent", rs.getString("pcontent"));
				pmap.put("pinputdate", rs.getString("pinputdate"));
				
				plist.add(pmap);
			}
			
		} finally {
			close();
		}
		
		return plist;
	}

	// 모든 제품목록 가져오기 (최신순, 높은가격순, 낮은가격순) //
	@Override
	public List<HashMap<String, String>> getAllProduct(String order, String category, int startRno, int endRno) throws SQLException {
		List<HashMap<String, String>> plist = null;
		
		String sql = "";
		
		try {
			
			conn = ds.getConnection();
			
			if(category == null || "".equals(category) || "all".equalsIgnoreCase(category)) {
				sql = " select pname "
					+ " from tbl_product "
					+ " group by pname "
					+ " order by pname ";
				
				pstmt = conn.prepareStatement(sql);
			}
			
			else if("new".equalsIgnoreCase(category) || "best".equalsIgnoreCase(category)) {
				System.out.println("category1-1 : " + category);
				switch(category) {
					case "new":
						category = "1";
						break;
					case "best":
						category = "2";
						break;
					default:
						category = "1";
						break;
				}
				System.out.println("category1-2 : " + category);
				
				sql = " select pname "
					+ " from tbl_product "
					+ " where pstatus = ? "
					+ " group by pname "
					+ " order by pname ";
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, category);
			}
			
			else {
				System.out.println("category2-1 : " + category);
				switch(category) {
					case "outer":
						category = "1000";
						break;
					case "top":
						category = "2000";
						break;
					case "bottom":
						category = "3000";
						break;
					case "acc":
						category = "4000";
						break;
					case "suit":
						category = "5000";
						break;
					default:
						category = "1000";
						break;
				}
				System.out.println("category2-2 : " + category);
				
				sql = " select pname "
					+ " from tbl_product A join tbl_category B "
					+ " on A.fk_pcategory = B.code "
					+ " where code = ? "
					+ " group by pname "
					+ " order by pname ";
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, category);
			}
			
			rs = pstmt.executeQuery();
			
			List<String> namelist = new ArrayList<String>();
			
			while(rs.next()) {
				namelist.add(rs.getString("pname"));
			}
			
			int cnt = 0;
			
			for(String pname : namelist) {
				
				cnt++;

				if(cnt == 1) {
					plist = new ArrayList<HashMap<String, String>>();
				}
				
				sql = " select * "
					+ " from "
					+ " ( "
					+ "   select rownum AS rno, pcode, pname, fk_pcategory, pcolor, psize, pimage, pqty, price, saleprice, pstatus, point, pcontent, to_char(pinputdate, 'yyyymmdd') as pinputdate "
					+ "   from "
					+ "   ( "
					+ "     select * "
					+ "     from tbl_product "
					+ "     where pname = ? "
					+ "   ) "
					+ " ) "
					+ " where rno = 1 ";
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, pname);
				
				rs = pstmt.executeQuery();
				if(rs.next()) {
					HashMap<String, String> pmap = new HashMap<String, String>();
					
					pmap.put("pcode", rs.getString("pcode"));
					pmap.put("pname", rs.getString("pname"));
					pmap.put("fk_pcategory", rs.getString("fk_pcategory"));
					pmap.put("pcolor", rs.getString("pcolor"));
					pmap.put("psize", rs.getString("psize"));
					pmap.put("pimage", rs.getString("pimage"));
					pmap.put("pqty", rs.getString("pqty"));
					pmap.put("price", rs.getString("price"));
					pmap.put("saleprice", rs.getString("saleprice"));
					pmap.put("pstatus", rs.getString("pstatus"));
					pmap.put("point", rs.getString("point"));
					pmap.put("pcontent", rs.getString("pcontent"));
					pmap.put("pinputdate", rs.getString("pinputdate"));
					
					plist.add(pmap);
				}
			}
			

			System.out.println("네리 : " + namelist.size());
			System.out.println("피리 : " + plist.size());
			
			if(order == null || "".equalsIgnoreCase(order) || "new".equalsIgnoreCase(order)) {
				
				Collections.sort(plist, new Comparator<HashMap<String, String>>() {
					@Override
					public int compare(HashMap<String, String> o1, HashMap<String, String> o2) {
						if(Integer.parseInt(o1.get("pinputdate")) < Integer.parseInt(o2.get("pinputdate"))) {
							return 1;
						}
						
						else if(Integer.parseInt(o1.get("pinputdate")) > Integer.parseInt(o2.get("pinputdate"))) {
							return -1;
						}
						
						else {
							return 0;
						}
					}
				});
				
			}
			
			else if("high".equalsIgnoreCase(order)) {
				
				Collections.sort(plist, new Comparator<HashMap<String, String>>() {
					@Override
					public int compare(HashMap<String, String> o1, HashMap<String, String> o2) {
						if(Integer.parseInt(o1.get("saleprice")) < Integer.parseInt(o2.get("saleprice"))) {
							return 1;
						}
						
						else if(Integer.parseInt(o1.get("saleprice")) > Integer.parseInt(o2.get("saleprice"))) {
							return -1;
						}
						
						else {
							return 0;
						}
					}
				});
				
			}
			
			else if("low".equalsIgnoreCase(order)) {
				
				Collections.sort(plist, new Comparator<HashMap<String, String>>() {
					@Override
					public int compare(HashMap<String, String> o1, HashMap<String, String> o2) {
						if(Integer.parseInt(o1.get("saleprice")) > Integer.parseInt(o2.get("saleprice"))) {
							return 1;
						}
						
						else if(Integer.parseInt(o1.get("saleprice")) < Integer.parseInt(o2.get("saleprice"))) {
							return -1;
						}
						
						else {
							return 0;
						}
					}
				});
				
			}
			
			int i = 0;
			
			Iterator<HashMap<String, String>> iter = plist.iterator();
			
			while(iter.hasNext()) {
				i++;
				iter.next();
				
				if(i >= startRno && i <= endRno) {
					continue;
				}
				
				iter.remove();
			}
			
		} finally {
			close();
		}
		
		return plist;
	}

	// 제품 추가하기 //
	@Override
	public int addProduct(int pcode, int pqty) throws SQLException {
		int result = 0;
		
		try {
			
			conn = ds.getConnection();
			
			String sql = " insert into tbl_productadd(idx, fk_pcode, pqty)"
					   + " values(seq_tbl_productadd.nextval, ?, ?) ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pcode);
			pstmt.setInt(2, pqty);
			
			result = pstmt.executeUpdate();
			
			if(result > 0) {
				sql = " update tbl_product set pqty = pqty + ? "
					+ " where pcode = ? ";
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, pqty);
				pstmt.setInt(2, pcode);
				
				result = pstmt.executeUpdate();
			}
			
		} finally {
			close();
		}
		
		return result;
	}
	
	// *** tbl_cart 테이블(장바구니 테이블)을 페이징 처리해서 조회해주는 메소드 *** //
	@Override
	public List<CartVO> getCartList(String fk_userid, int currentShowPageNo, int sizePerPage) throws SQLException {
		
		List<CartVO> cartList = null;
		
		try {
			
			conn = ds.getConnection();
			
			String sql = "  SELECT RNO, cartno, fk_userid, fk_pcode, poqty " 
					+"		 ,pname ,fk_pcategory "
					+"		 , pcolor "
					+"  	 , psize, pimage, price, saleprice "
					+"  	 ,pstatus ,point " 
					+" 		FROM " 
					+"  (SELECT rownum AS RNO, cartno, fk_userid, fk_pcode, poqty, pname, " 
					+"    fk_pcategory, pcolor, psize, pimage, price, saleprice , pstatus, point " 
					+"  FROM " 
					+"    (SELECT B.cartno, B.fk_userid, B.fk_pcode, B.poqty , A.pname, A.fk_pcategory, " 
					+"      A.pcolor,  A.psize, A.pimage, A.pqty, A.price, A.saleprice, " 
					+"      A.pstatus, A.point, A.pcontent, A.pinputdate " 
					+"    FROM tbl_product A JOIN tbl_cart B " 
					+"    ON A.pcode = B.fk_pcode " 
					+"    WHERE B.fk_userid = ? " 
					+"    ORDER BY B.cartno DESC " 
					+"    )V " 
					+"  )T " 
					+"	WHERE T.RNO BETWEEN ? AND ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, fk_userid);
			pstmt.setInt(2, (currentShowPageNo*sizePerPage)-(sizePerPage-1) ); // 공식
			pstmt.setInt(3, (currentShowPageNo*sizePerPage) ); // 공식
			
			rs = pstmt.executeQuery();
			
			int cnt = 0;
			while(rs.next()) {
				cnt++;
				
				if(cnt==1) {
					cartList = new ArrayList<CartVO>();
				}
				
				int cartno = rs.getInt("cartno");
				fk_userid = rs.getString("fk_userid");
			    int fk_pcode = rs.getInt("fk_pcode");
			    int poqty = rs.getInt("poqty");
			    String pname = rs.getString("pname");
			    String fk_pcategory = rs.getString("fk_pcategory");
			    int pcolor = rs.getInt("pcolor");
			    int psize = rs.getInt("psize");
			    String pimage = rs.getString("pimage");			    
			    int price = rs.getInt("price");
			    int saleprice = rs.getInt("saleprice");
			    int pstatus = rs.getInt("pstatus");			    
			    int point = rs.getInt("point");			   
			    
			    ProductVO item = new ProductVO();		    
			    
			    item.setPcode(fk_pcode); 					// 제품코드
			    item.setPname(pname);    					// 제품명
			    item.setFk_pcategory(fk_pcategory); 		// 카테고리
				item.setPcolor(pcolor); 					// 색상
				item.setPsize(psize);      					// 사이즈
				item.setPimage(pimage); 					// 대표이미지				
				item.setPrice(price);   					// 원가
				item.setSaleprice(saleprice);     			// 할인가
				item.setPstatus(pstatus);      				// 제품 NEW / BEST 구분
				item.setPoint(point);              			// 제품포인트 
							    
			    item.setTotalPriceTotalPoint(poqty);	
			   			    
			    CartVO cvo = new CartVO();
			    cvo.setCartno(cartno);
			    cvo.setFk_userid(fk_userid);
			    cvo.setFk_pcode(fk_pcode);
			    cvo.setPoqty(poqty);			    
			    cvo.setItem(item);
			    
			    cartList.add(cvo);
								
			}// end of while ------------
			
		} finally {
			close();
		}
		
		return cartList;
	}// end of List<CartVO> getCartList(String fk_userid, int currentShowPageNo, int sizePerPage)------
	
	
	// *** fk_userid 별 장바구니 전체갯수 조회 메소드  *** //
	@Override
	public int getTotalCartCount(String fk_userid) throws SQLException {
		int cnt = 0;
		
		try {
			 conn = ds.getConnection();
			 // DBCP객체 ds를 통해 context.xml에서 이미 설정된 Connection 객체를 빌려오는 것이다.
			
			 String sql = " select count(*) AS CNT " 
					 	+ " from tbl_cart "  
					 	+ " where fk_userid = ? ";
			 
			 pstmt = conn.prepareStatement(sql);
			 pstmt.setString(1, fk_userid);
			 
			 rs = pstmt.executeQuery();
			 
			 rs.next();
			 
			 cnt = rs.getInt("CNT");
			 
		} finally {
			close();
		}
		
		return cnt;
	}// end of getTotalCartCount(String fk_userid)--------

	// *** tbl_cart (장바구니 테이블)에 수량을 변경해주는 메소드 *** //
	@Override
	public int editCart(String poqty, String cartno) throws SQLException {
		
		int result = 0;
		
		try {
			
			conn=ds.getConnection();							
			
			String sql = "";							
			
			if(Integer.parseInt(poqty) == 0) {				// 0이라면 장바구니에서 없애겠다라는 말.
				sql = " delete from tbl_cart  "
					+ " where cartno = ? ";		
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, cartno);
				
			}
			else {	
				sql = " update tbl_cart set poqty = ? "
					+ " where cartno = ? ";			
					
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, poqty);
					pstmt.setString(2, cartno);
			}
								
				result = pstmt.executeUpdate();
												
		}finally {
			close();
		}			
		
		return result;
	}// end of public int editCart(String poqty, String cartno) -------------


	// *** 장바구니에서 특정 제품 삭제하기전  해당 장바구니 번호의 소유주(userid)가 누구인지를 알려주는 메소드 생성하기 *** //   
		@Override
		public String getUseridByCartno(String cartno) throws SQLException {
		
			String fk_userid = null;
			
			try {
				 conn = ds.getConnection();
				 // DBCP객체 ds를 통해 context.xml에서 이미 설정된 Connection 객체를 빌려오는 것이다.
				
				 String sql = " select fk_userid "
			               +  "	from tbl_cart "
						   +  " where cartno = to_number(?) ";
				 
				 pstmt = conn.prepareStatement(sql);
				 pstmt.setString(1, cartno);
				 
				 rs = pstmt.executeQuery();
				 
				 rs.next();
				 
				 fk_userid = rs.getString("fk_userid");
				 
			} finally {
				close();
			}
			
			return fk_userid;
		}// end of getUseridByCartno(String cartno)-----------------
	
	
		// *** 장바구니에서 특정 제품 삭제하는 메소드 생성하기 *** // 
		@Override
		public int deleteCartno(String cartno) throws SQLException {
			
			int result = 0;
			
			try {
				 conn = ds.getConnection();
				 // DBCP객체 ds를 통해 context.xml에서 이미 설정된 Connection 객체를 빌려오는 것이다.
				
				 String sql = " delete from tbl_cart "
			               +  " where cartno = ? "; 
				 
				 pstmt = conn.prepareStatement(sql);
				 pstmt.setString(1, cartno);
				 
				 result = pstmt.executeUpdate();
				 
			} finally {
				close();
			}
			
			return result;
		}// end of deleteCartno(String cartno)--------------------
	
	
		// *** jsp_product 테이블에서 조회해오는 메소드 *** //
		@Override
		public List<ProductVO> getProductVOListByPspec(int startRno, int endRno) throws SQLException {
			
			List<ProductVO> productList =null;
			
			try {
				
				conn = ds.getConnection();
				// DBCP객체 ds를 통해 context.xml에서 이미 설정된 Connection 객체를 빌려오는 것이다.
								
				String sql = " SELECT * " 
							+" FROM " 
							+" (SELECT row_number() over (order by pcode DESC) AS rno , " 
							+"    pcode, pname, fk_pcategory, pcolor, psize, pimage, " 
							+"    pqty, price, saleprice, pstatus, point, pcontent, pinputdate  " 
							+"    FROM tbl_product " 
							+"  )V " 
							+" WHERE V.rno BETWEEN ? AND ? " ;
				
				pstmt = conn.prepareStatement(sql);	
																				
				pstmt.setInt(1, startRno);												
				pstmt.setInt(2, endRno);		
				
				rs = pstmt.executeQuery();
					
				int cnt=0;
				while(rs.next()) {
					cnt++;
					if(cnt == 1) {
						productList = new ArrayList<ProductVO>();
					}
					
					int pcode = rs.getInt("pcode");
				    String pname	= rs.getString("pname");
				    String fk_pcategory = rs.getString("fk_pcategory");
					int pcolor = rs.getInt("pcolor");
					int psize = rs.getInt("psize");					
				    String pimage = rs.getString("pimage");				   
				    int pqty = rs.getInt("pqty");
				    int price = rs.getInt("price");
				    int saleprice = rs.getInt("saleprice");
				    int pstatus = rs.getInt("pstatus");
				    int point = rs.getInt("point");
				    String pcontent = rs.getString("pcontent");				  
				    String pinputdate = rs.getString("pinputdate");
				    
				    ProductVO pvo = new ProductVO(pcode, pname, fk_pcategory, pcolor, psize, pimage, pqty, price, saleprice, pstatus, point, pcontent, pinputdate);
				    
				    productList.add(pvo);					
					
				}// end of while--------
														
			} finally{
				close();
			}						
			return productList;
		}// end of List<ProductVO> getProductVOListByPspec(int startRno, int endRno) ----------

		
		// *** tbl_product 테이블에서 제품1개 정보만 출력해주는 메소드 *** //
		@Override
		public ProductVO getProductOneByPcode(String str_pcode) throws SQLException {
			
			ProductVO pvo = null;
			
			try {
				conn = ds.getConnection();
				// DBCP객체 ds를 통해 context.xml에서 이미 설정된 Connection 객체를 빌려오는 것이다.
				
				String sql = " select * "     
						+   "  from tbl_product "
						+   "  where pcode = ? " ;		// 넘버타입이지만 문자가와도 상관없다 익셉션처리만 해주면된다 // 장난칠때 asdasdasd막치면 오류 . 메소드를 호출한 곳에서 처리해줘야한다.
				
				pstmt = conn.prepareStatement(sql); 
				pstmt.setString(1, str_pcode);
				
				rs = pstmt.executeQuery();	// rs가 존재할수도있고 안할수도있다. (장난치면 없으니까)
								
				boolean isExists = rs.next(); 
				
				if(isExists) {	// 존재한다면
					
					int pcode = rs.getInt("pcode");
				    String pname	= rs.getString("pname");
				    String fk_pcategory = rs.getString("fk_pcategory");
				    int pcolor = rs.getInt("pcolor");
				    int psize = rs.getInt("psize");
				    String pimage = rs.getString("pimage");				    
				    int pqty = rs.getInt("pqty");
				    int price = rs.getInt("price");
				    int saleprice = rs.getInt("saleprice");
				    int pstatus = rs.getInt("pstatus");
				    int point = rs.getInt("point");
				    String pcontent = rs.getString("pcontent");				   
				    String pinputdate = rs.getString("pinputdate");
				   
				    // 초기치 null 대신 new를 해준다. 여기에 값을 넣어주겠다.
				    pvo = new ProductVO(pcode, pname, fk_pcategory, pcolor, psize, pimage, pqty, price, saleprice, pstatus, point, pcontent, pinputdate);  				
				}
							
			} finally{
				close();
			}
			
			return pvo;
		}// end of public ProductVO getProductOneByPcode(String str_pcode) ---------------------

		
		// *** 주문코드(명세서번호) 시퀀스  값 따오는 메소드 *** //
		@Override
		public int getSeq_order_detail() throws SQLException {
			int result = 0;
			
			try {
				conn = ds.getConnection();
				// DBCP객체 ds를 통해 context.xml에서 이미 설정된 Connection 객체를 빌려오는 것이다.
				
				String sql = " select seq_order_detail.nextval AS seq "     
						 +   " from dual ";
				
				pstmt = conn.prepareStatement(sql); 
								
				rs = pstmt.executeQuery();
				
				rs.next();
				
				result = rs.getInt("seq");
															
			} finally{
				close();
			}						
			return result;			
		}// end of public int getSeq_jsp_order() ----------

				
		// *** 장바구니 제품 삭제(delete)하는 메소드 *** HashMap 사용//
		@Override
		public int deleteProduct(String str) throws SQLException {
			int n =0;
			
			try {
							
				conn = ds.getConnection();
				// DBCP객체 ds를 통해  context.xml 에서 이미 설정된 Connection 객체를 빌려오는 것이다.
				
				// 편지 작성
				String sql = " delete from tbl_cart "
						   + " where fk_pcode in (" + str + ") " ;
							
				pstmt = conn.prepareStatement(sql); 	// 편지를 배달해주는 우편배달부 생성
										
				n = pstmt.executeUpdate();			// DML 문이니까  executeUpdate. 
				
			}finally {
				close();
			}
				
			return n;
			
		}// end of public int deleteProduct(String str)---------

		
		
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		// *** 주문하기(transaction 처리) 메소드 *** //
		/*
			==== *** 트랜잭션 Transaction 처리하기 *** ==== (모든게 성공이면 commit; 하나라도 실패하면 rollback;)
			1.	주문개요 테이블(jsp_order)에 입력(insert)
			2.	주문상세 테이블(jsp_order_detail)에 입력(insert)
			3.	구매하는 사용자의 coin 컬럼의 값을 구매한 가격만큼 감하고,
			point 컬럼의 값은 구매한 포인트만큼 증가하며(update),
			4.	주문한 제품의 잔고량은 주문량만큼 감해야 하고(update),
			5.	장바구니에서 주문을 한 경우라면 장바구니 비우기(status 컬럼을 0으로 변경하는 update) - 실제로는 delete를 사용한다.
			
			를 해주는 DAO에서 만든 메소드를 호출하기
		*/
		
		@Override
		public int add_Order_OrderDetail(String odrcode, String userid, int sumtotalprice, int sumtotalpoint,
				String[] pcodeArr, String[] poqtyArr, String[] salepriceArr, String[] cartnoArr) throws SQLException {
		
			int n1=0, n2=0, n3=0, n4=0, n5=0;	//	n2=0, 
		//	n1=> jsp_order에 insert (첫번째 실행되어진 결과물), n2=> jsp_order_detail에 insert (두번째 실행되어진 결과물)....

					
			try {
				conn = ds.getConnection();
				// DBCP객체 ds를 통해 context.xml에서 이미 설정된 Connection 객체를 빌려오는 것이다.
				
				// *** 트랜잭션 Transaction 처리하기 ***
				// 오토커밋해지.(트랜잭션처리이기 때문에) 
				// 수동커밋으로 전환 --> 모든 DML문이 성공되었을 경우에만 commit; 하고, 하나라도 실패하면 모두 rollback; 시키기 위함이다. 
				conn.setAutoCommit(false); // 기본은 무조건 오토커밋(true) 이 메소드를 할 때만 수동커밋이다 라는 뜻.
				
				// 1. 주문개요 테이블(jsp_order)에 입력(insert)
				String sql = " insert into tbl_order(odrcode, fk_userid, odrtotalPrice, odrtotalPoint, odrdate) "
						   + " values(?,?,?,?,default) ";
				
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, odrcode);
				pstmt.setString(2, userid);
				pstmt.setInt(3, sumtotalprice);
				pstmt.setInt(4, sumtotalpoint);
				
				n1 = pstmt.executeUpdate();
				if(n1 != 1) {	// n1이 성공인지 실패인지 알아야한다. 1이 아니라면 실패
					conn.rollback();
					return 0;		// 1이 아니면 실패니까 DB는 롤백하고 끝내자. 
				}// end of if----------------				
				
				if(n1 == 1) {	// insert 성공이라면
				// 2. 주문상세 테이블(jsp_order_detail)에 입력(insert)	
					for(int i=0; i<pcodeArr.length; i++) {	// insert를 배열의 갯수만큼 해야되니까 for문을 돌린다. pnumArr나 oqty배열의 길이는 같다. 장바구니에서 넘어온거니까
						
						sql =" insert into tbl_order_detail(odrdetailno, odrcode, pcode, oqty, odrprice, deliverStatus, deliverDate) "
						   + " values(seq_order_detail.nextval, ?, to_number(?), to_number(?), to_number(?), 1, default) ";		// 1 이면 배송준비라고 생각하고
						// odrseqnum => 1,2,3... -뒤의 숫자.
						
						pstmt = conn.prepareStatement(sql);
						pstmt.setString(1, odrcode);
						pstmt.setString(2, pcodeArr[i]); // pnumArr[i] 자체는 string. 유효성검사를 다해서 상관없다. 찝찝하면 to_number()
						pstmt.setString(3, poqtyArr[i]);	
						pstmt.setString(4, salepriceArr[i]);	
						
						n2 = pstmt.executeUpdate();
						
						if(n2 != 1) {
							conn.rollback();
							return 0;
						}// end of if----------------
						
					}// end of for-----------------
					
				}// end of if----------------
				
				if(n2==1) {	// 위의 모든 것이 성공했다면
				// 3. point 컬럼의 값은 구매한 포인트만큼 증가하며(update)
					
					sql = " update tbl_userinfo set point = point + ? "	// 현재 포인트에서 +
						+ " where userid = ? ";
					
					pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, sumtotalpoint);		
					pstmt.setString(2, userid);
					
					n3 = pstmt.executeUpdate();
					
					if(n3 != 1) {
						conn.rollback();
						return 0;
					}// end of if----------------					
					
				}// end of if----------------
		
				if(n3 == 1) {
				// 4. 주문한 제품의 잔고량은 주문량만큼 감해야 하고(update)
					for(int i=0; i<pcodeArr.length; i++) {		// 주문한 제품이 여러개 일 수 있으니 for
						sql = " update tbl_product set pqty = pqty - to_number(?) "		// 현재 잔고량에서 - 주문량 	 // to_number는 유효성검사해서 안해줘도 되지만 찝찝하면 해라
							+ " where pcode = to_number(?) ";							// 어떤 제품인지				
						
						pstmt = conn.prepareStatement(sql);
						pstmt.setString(1, poqtyArr[i]);
						pstmt.setString(2, pcodeArr[i]);
						
						n4 = pstmt.executeUpdate();
						
						if(n4!=1) {
							conn.rollback();
							return 0;
						}// end of if-------
						
					}// end of for-------
					
				}// end of if----------------
				
				// *** 장바구니에 있던 것을 주문한 경우 라면 장바구니 비우기를 하도록 한다. *** //				
				if(cartnoArr !=null && n4==1) {	// 장바구니에 있고 && n4가 성공했다면
				//	5. 장바구니에서 주문을 한 경우라면 장바구니 비우기(status 컬럼을 0으로 변경하는 update) - 실제로는 delete를 사용한다.				
					for(int i=0; i<cartnoArr.length; i++) {
						
						sql= " delete from tbl_cart "
						   + " where cartno = to_number(?) ";
						
						pstmt = conn.prepareStatement(sql);
						pstmt.setString(1, cartnoArr[i]);
						
						n5 = pstmt.executeUpdate();
						
						if(n5 != 1) {
							conn.rollback();
							return 0;
						}
					}// end of for---------------------
								
				}// end of if------------------
				
				// 최종적으로 커밋을 해줘야 한다.				
				// *** 바로주문하기 인 경우의 commit 하기 *** //
				if(cartnoArr == null && n1+n2+n3+n4 == 4) {	// cartno가 없고 && 다 성공한값을 더해서 4라면
					conn.commit();
					return 1;
				}
				
				// *** 장바구니인 경우의 commit 하기 *** //
				else if(cartnoArr != null && n1+n2+n3+n4+n5 == 5) { // cartno가 있고 && 다 성공한값을 더해서 5라면
					conn.commit();
					return 1;
				}
				else {	// 굳이 할 필욘없다. 
					conn.rollback();
					return 0;
				}
				
			}finally {				
				close();
			}	
							
		}// end of int add_Order_OrderDetail()-----------
				
		
		
		// *** 최근 1년 이내까지 주문한 주문내역을 조회해오는 메소드 *** //
		@Override
		public List<HashMap<String, String>> getOrderList(String userid, int currentShowPageNo, int sizePerPage) throws SQLException {
			
			List<HashMap<String, String>> orderList = null;
			
			try {
				
				conn = ds.getConnection();
				// DBCP객체 ds를 통해 context.xml에서 이미 설정된 Connection 객체를 빌려오는 것이다.
				
				String sql =  " SELECT odrcode, fk_userid, odrdate, odrdetailno, pcode, "
							+ " 	   oqty, odrprice, deliverstatus, pcolor,psize, pname, pimage, price, saleprice, point  "
							+ " FROM "
							+ " ( "
							+ " SELECT rownum AS rno, odrcode, fk_userid, odrdate, odrdetailno, "
							+ "		   pcode, oqty, odrprice, deliverstatus, pname, pimage, "
							+ " 	   price, saleprice, point, pcolor, psize "
							+ " FROM "
							+ " 	(SELECT odrcode, fk_userid, odrdate, odrdetailno, "
							+ "		 		pcode, oqty, odrprice, deliverstatus, pname, pimage,  "
							+ "				price,saleprice,point , pcolor, psize"
							+ " FROM "
							+ " (SELECT A.odrcode, A.fk_userid, TO_CHAR(A.odrdate, 'yyyy-mm-dd hh24:mi:ss') AS odrdate "
							+ "   , B.odrdetailno, B.pcode, B.oqty, B.odrprice , "
							+ "        CASE B.deliverStatus  WHEN 1 THEN '주문완료'  "
							+ "								 WHEN 2 THEN '배송시작' "
							+ "								 WHEN 3 THEN '배송완료' "
							+ "								 END AS deliverStatus, "
							+ " C.pcolor, C.pname, C.pimage,C.price,  C.saleprice, C.point, C.psize  "
							+ " FROM tbl_order A INNER JOIN tbl_order_detail B "
							+ " ON A.odrcode = B.odrcode INNER JOIN tbl_product C  "
							+ " ON B.pcode = C.pcode "
							+ " WHERE 1=1 "; 
				if(!"admin".equals(userid)) {
				    sql += "   AND A.fk_userid = ? ";
				}
				    sql += "  AND months_between(sysdate, A.odrdate) <= 12 " 
						+"    ) V " 
						+"  ORDER BY V.odrcode DESC " 
						+"  ) T"
						+")S " 
						+"WHERE S.rno BETWEEN ? AND ?";
				
				System.out.println(sql);    
				    
				pstmt = conn.prepareStatement(sql);
				
				if(!"admin".equals(userid)) {
					pstmt.setString(1, userid);
					pstmt.setInt(2, (currentShowPageNo*sizePerPage)-(sizePerPage-1) ); // 공식
					pstmt.setInt(3, (currentShowPageNo*sizePerPage) ); // 공식
				}
				else {
					pstmt.setInt(1, (currentShowPageNo*sizePerPage)-(sizePerPage-1) ); // 공식
					pstmt.setInt(2, (currentShowPageNo*sizePerPage) ); // 공식
				}
							
				rs = pstmt.executeQuery();
				
				int cnt = 0;
				while(rs.next()) {
					cnt++;

					if(cnt==1)
						orderList = new ArrayList<HashMap<String, String>>();

					String odrcode = rs.getString("odrcode");
					String fk_userid = rs.getString("fk_userid");
					String odrdate = rs.getString("odrdate");
					String odrdetailno = rs.getString("odrdetailno");
					String pcode = rs.getString("pcode");
					String oqty = rs.getString("oqty");
					String odrprice = rs.getString("odrprice");
					String deliverstatus = rs.getString("deliverstatus");
					String pname = rs.getString("pname");
					String psize = rs.getString("psize");
					String pcolor = rs.getString("pcolor");
					String pimage = rs.getString("pimage");
					String price = rs.getString("price");
					String saleprice = rs.getString("saleprice");
					String point = rs.getString("point");
					
					HashMap<String, String> ordermap = new HashMap<String, String>();
					ordermap.put("odrcode", odrcode);
					ordermap.put("fk_userid", fk_userid);
					ordermap.put("odrdate", odrdate);
					ordermap.put("odrdetailno", odrdetailno);
					ordermap.put("pcode", pcode);
					ordermap.put("oqty", oqty);
					ordermap.put("odrprice", odrprice);
					ordermap.put("deliverstatus", deliverstatus);
					ordermap.put("pname", pname);
					ordermap.put("pcolor", pcolor);
					ordermap.put("psize", psize);
					ordermap.put("pimage", pimage);
					ordermap.put("price", price);
					ordermap.put("saleprice", saleprice);
					ordermap.put("point", point);
									
					orderList.add(ordermap);
				    
				}// end of while-----------------	
										
			} finally{
				close();
			}		
			
			return orderList;
			
		}
		
		
		// *** 제품번호들에 해당하는 제품목록을 조회해오는 메소드 *** //
		@Override
		public List<ProductVO> getJumunfinishProductList(String pcodes) throws SQLException {
			List<ProductVO> productList =null;
			
			try {
				conn = ds.getConnection();
				// DBCP객체 ds를 통해 context.xml에서 이미 설정된 Connection 객체를 빌려오는 것이다.
				
				String sql = " select * "     
						+   "  from tbl_product "
						+   "  where pcode in ("+pcodes+") ";		// in은 ? 하면 잘 안된다.
				
				pstmt = conn.prepareStatement(sql); 				
				
				rs = pstmt.executeQuery();
				
				int cnt = 0;
				while(rs.next()) {
					cnt++;

					if(cnt==1)
						productList = new ArrayList<ProductVO>();

					int pcode = rs.getInt("pcode");
				    String pname	= rs.getString("pname");
				    String fk_pcategory = rs.getString("fk_pcategory");
				    int pcolor = rs.getInt("pcolor");
				    int psize = rs.getInt("psize");				    
				    String pimage = rs.getString("pimage");				    
				    int pqty = rs.getInt("pqty");
				    int price = rs.getInt("price");
				    int saleprice = rs.getInt("saleprice");
				    int pstatus = rs.getInt("pstatus");
				    int point = rs.getInt("point");
				    String pcontent = rs.getString("pcontent");				    
				    String pinputdate = rs.getString("pinputdate");
				    
				    ProductVO pvo = new ProductVO(pcode, pname, fk_pcategory, pcolor, psize, pimage, pqty, price, saleprice, pstatus, point, pcontent, pinputdate);  
				    
				    productList.add(pvo);
				    
				}// end of while-----------------
							
			} finally{
				close();
			}
						
			return productList;
		}
		// *** userid 별 최근 1년 이내까지 주문한 주문갯수 조회 메소드 생성하기 *** //
		@Override
		public int getTotalOrderCount(String userid) 
			throws SQLException {

			int cnt = 0;
			
			try {
				 conn = ds.getConnection();
				 // DBCP객체 ds를 통해 context.xml에서 이미 설정된 Connection 객체를 빌려오는 것이다.
				
				 String sql = " SELECT COUNT(*) AS CNT " 
							 +" FROM tbl_order A " 
							 +" INNER JOIN tbl_order_detail B " 
							 +" ON A.odrcode = B.odrcode "
							 +" WHERE 1=1 "; 
				 
				 if(!"admin".equals(userid)) {
					 sql += "AND A.fk_userid = ? "; 
				 }
				 	 sql += "AND months_between(sysdate, A.odrdate) <= 12";
				 
				 pstmt = conn.prepareStatement(sql);
				 
				 if(!"admin".equals(userid)) {
					 pstmt.setString(1, userid);
				 }
				 
				 rs = pstmt.executeQuery();
				 
				 rs.next();
				 
				 cnt = rs.getInt("CNT");
				 
			} finally {
				close();
			}
			
			return cnt;		
		}// end of getTotalOrderCount(String userid)--------------------

		// *** 제품 선호도를 알아오는 메소드 *** //
		@Override
		public List<HashMap<String, String>> getLikeDislikeCnt() throws SQLException {
			
			List<HashMap<String, String>> maplist = null;
			try {
				conn = ds.getConnection();
				
				String sql = " SELECT * " 
							+" FROM " 
							+" ("
							+" 	  SELECT rownum AS rno, pname, likecnt, dislikecnt, totalcnt " 
							+"    FROM " 
							+"    ("
							+"		SELECT A.pname, NVL(A.Likecnt, 0) AS likecnt, " 
							+"      	   NVL(B.Dislikecnt, 0) AS dislikecnt, " 
							+"      	   ( NVL(A.Likecnt, 0)+NVL(B.Dislikecnt, 0) ) AS totalcnt " 
							+"    	FROM view_tbl_lkiecnt A FULL JOIN view_tbl_dislkiecnt B " 
							+"    	ON A.pname = B.pname " 
							+"    	ORDER BY totalcnt DESC " 
							+"    )V " 
							+"  )T " 
							+" WHERE rno BETWEEN 1 AND 3";
				
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				
				int cnt = 0;
				while(rs.next()) {
					cnt++;
					
					if(cnt == 1) {
						maplist = new ArrayList<HashMap<String, String>>();
					}
					
					String pname = rs.getString("pname");
					String likecnt = rs.getString("likecnt");
					String dislikecnt = rs.getString("dislikecnt");
					
					HashMap<String, String> map = new HashMap<String, String>();
					map.put("pname", pname);
					map.put("likecnt", likecnt);
					map.put("dislikecnt", dislikecnt);
					
					maplist.add(map);
					
				}// end of while -----------------------------------
				
			} finally {
				close();
			}
			
			return maplist;
		}// end of getLikeDislikeCnt()-------------------------------------------
	
		
		// *** tbl_cart 테이블(장바구니 테이블)에 물건을 입력해주는  메소드 *** // 
		@Override
		public int addCart(String userid, String fk_pcode, String poqty) throws SQLException {
			int result = 0;
			
			try {
				conn = ds.getConnection();
				// DBCP객체 ds를 통해 context.xml에서 이미 설정된 Connection 객체를 빌려오는 것이다.
				
				/*
					먼저 장바구니 테이블 (jsp_cart)에 새로운 제품을 넣는 것인지,
					아니면 또 다시 제품을 추가로 더 구매하는 것인지 알기 위해서
					사용자가 장바구니에 넣으려고 하는 제품번호가 장바구니 테이블에
					이미 있는지 먼저 장바구니번호(cartno)의 값을 알아온다.
				*/
				
				String sql = " select cartno "		
						   + " from tbl_cart "
						   + " where fk_userid = ? and "
						   + "       fk_pcode = ?  ";
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, userid);
				pstmt.setString(2, fk_pcode);
				
				rs = pstmt.executeQuery();
				
			
				
				if(rs.next()) { // 있으면  true
					// 이미 장바구니 테이블에 담긴 제품이라면
					// update 해준다.
					
					int cartno = rs.getInt("cartno"); // cartno는  rs에서 가져올 수 있다
					
					sql = " update tbl_cart set poqty = poqty + ? "		// 기존 poqty에 더해준다.  
						+ " where cartno = ? ";							
					
					pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, Integer.parseInt(poqty));
					pstmt.setInt(2, cartno);
					
					result = pstmt.executeUpdate();
						
				}
				else {
					// 장바구니 테이블에 없는 제품이라면
					// insert 해준다.
					
					sql = " insert into tbl_cart(cartno, fk_userid, fk_pcode, poqty) "
					   +  " values(seq_tbl_cart.nextval, ?,to_number(?),to_number(?) ) ";
					// pnum과 oqty는 DB에서 number타입으로 해두었다. 우리는 유효성검사를 해서 String타입이지만 (숫자로 되어진 문자열) 그래서 sql문에서 바꿔준다. to_number
					
					pstmt = conn.prepareStatement(sql); 
					pstmt.setString(1, userid);
					pstmt.setString(2, fk_pcode);
					pstmt.setString(3, poqty);
					
					result = pstmt.executeUpdate();					
				}
													
			} finally{
				close();
			}
			return result;
		}// end of addCart(String userid, String fk_pcode, String poqty)-----------------------------------
	
	
		// 별점 구하기 //
		@Override
		public String getStarpoint(String pname) throws SQLException {
			String starpoint = "0";
			
			try {
				
				conn = ds.getConnection();
				
				String sql = " select A.pname, round(2 * nvl(likecnt, 0)/(nvl(likecnt, 0)+nvl(dislikecnt, 0))*5) * 5 as likedislikevalue "
						   + " from view_like A full join view_dislike B "
						   + " on A.pname = B.pname"
						   + " where A.pname = ? ";
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, pname);
				
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					starpoint = rs.getString("likedislikevalue");
				}
				
			} finally {
				close();
			}
			
			return starpoint;
		}// end of getStarpoint(String pname) --------------------------------------------------------

	
		@Override
		public List<CategoryVO> getCategoryList() throws SQLException {
			List<CategoryVO> categoryList = null;
			
			try {
				conn = ds.getConnection();
				// DBCP객체 ds를 통해 context.xml에서 이미 설정된 Connection 객체를 빌려오는 것이다.
				
				String sql = " select cnum, code, cname "
						   + " from tbl_category "
						   + " order by cnum ";
				
				pstmt = conn.prepareStatement(sql); 
				
				rs = pstmt.executeQuery();
				
				int cnt = 0;
				while(rs.next()) {
					cnt++;

					if(cnt==1)
						categoryList = new ArrayList<CategoryVO>();

					int cnum = rs.getInt("cnum");
				    String code = rs.getString("code");
				    String cname = rs.getString("cname");
				    
				    CategoryVO categoryvo = new CategoryVO(cnum, code, cname); 
				    
				    categoryList.add(categoryvo);
				    
				}// end of while-----------------
							
			} finally{
				close();
			}		
			
			return categoryList;
		}//end of getCategoryList()------------------------------------------------------------


		@Override
		public int getPnumOfProduct() throws SQLException {
			int result = 0;
			
			try {
				conn = ds.getConnection();
				// DBCP객체 ds를 통해 context.xml에서 이미 설정된 Connection 객체를 빌려오는 것이다.
				
				String sql = " select seq_tbl_product.nextval AS seq "     
						+   "  from dual ";
				
				pstmt = conn.prepareStatement(sql); 
				
				
				rs = pstmt.executeQuery();
				
				rs.next();
				
				result = rs.getInt("seq");
							
			} finally{
				close();
			}
			
			return result;
		}//end of getPnumOfProduct()---------------------------------------------

		//*** 신규제품등록을 해주는 메소드구현***//
		@Override
		public int productInsert(ProductVO pvo) throws SQLException {
			int result = 0;
			
			try {
				conn=ds.getConnection();
				
				String sql = " insert into tbl_product(pcode, pname, fk_pcategory, pcolor, psize, pimage, pqty, price, saleprice, pstatus, point, pcontent, pinputdate) " 
							+ " values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, default) ";
				
				int point = (int)Math.round(pvo.getSaleprice()*2/100);
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, pvo.getPcode());
				pstmt.setString(2, pvo.getPname());
				pstmt.setString(3, pvo.getFk_pcategory());
				pstmt.setInt(4, pvo.getPcolor());
				pstmt.setInt(5, pvo.getPsize());
				pstmt.setString(6, pvo.getPimage());
				pstmt.setInt(7, pvo.getPqty());
				pstmt.setInt(8, pvo.getPrice());
				pstmt.setInt(9, pvo.getSaleprice());
				pstmt.setInt(10, pvo.getPstatus());
				pstmt.setInt(11, point );
				pstmt.setString(12, pvo.getPcontent());
				
				result = pstmt.executeUpdate();
				
			} finally {
				close();
			}
			
			return result;
		}//end of productInsert(ProductVO pvo)-------------------------------------------------------------------------------

		//***신규제품등록 시 추가 이미지 파일 정보를 jsp_product_imagefile테이블에 insert해주는 메소드구현하기***//
		@Override
		public int product_imagefile_Insert(int pcode, String attachFilename) throws SQLException {
			int result = 0;
			
			try {
				conn=ds.getConnection();
				
				String sql = " insert into tbl_product_image(imgfileno, pcode, pimgfilename) "
							+" values(seq_tbl_product_image.nextval, ?, ? ) ";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, pcode);
				pstmt.setString(2, attachFilename);
							
				result = pstmt.executeUpdate();
				
			} finally {
				close();
			}
			
			return result;
			
		}//end of product_imagefile_Insert(int pnum, String attachFilename)------------------------------------------


		@Override
		public ProductVO getProductOneByPnum(String str_pcode) throws SQLException {
			ProductVO pvo = null;
			
			try {
				conn = ds.getConnection();
				// DBCP객체 ds를 통해 context.xml에서 이미 설정된 Connection 객체를 빌려오는 것이다.
				
				String sql = " select * "     
						+   "  from tbl_product "
						+   "  where pcode = ? ";
				
				pstmt = conn.prepareStatement(sql); 
				pstmt.setString(1, str_pcode);
				
				rs = pstmt.executeQuery();
				
				boolean isExists = rs.next();
				
				if(isExists) {
					int pcode = rs.getInt("pcode");
				    String pname	= rs.getString("pname");
				    String fk_pcategory = rs.getString("fk_pcategory");
				    int pcolor = rs.getInt("pcolor");
				    int psize = rs.getInt("psize");
				    String pimage = rs.getString("pimage");
				    int pqty = rs.getInt("pqty");
				    int price = rs.getInt("price");
				    int saleprice = rs.getInt("saleprice");
				    int pstatus = rs.getInt("pstatus");
				    int point = rs.getInt("point");
				    String pcontent = rs.getString("pcontent");
				    String pinputdate = rs.getString("pinputdate");
				    
				    pvo = new ProductVO(pcode, pname, fk_pcategory, pcolor, psize, pimage, pqty, price, saleprice, pstatus, point, pcontent, pinputdate); 	
				}
										
			} finally{
				close();
			}
			
			return pvo;
		}// end of getProductOneByPnum(String pcode)----------------


		@Override
		public List<ProductImagefileVO> getProductImagefileByPnum(String str_pcode) throws SQLException {
			List<ProductImagefileVO> imgFileList = null;
			
			try {
				conn = ds.getConnection();
				// DBCP객체 ds를 통해 context.xml에서 이미 설정된 Connection 객체를 빌려오는 것이다.
				
				String sql = " select imgfileno, pcode, pimgfilename "
						   + " from tbl_product_image "
						   + " where pcode = to_number(?) "
						   + " order by imgfileno ";
				
				pstmt = conn.prepareStatement(sql); 
				pstmt.setString(1, str_pcode);
				
				rs = pstmt.executeQuery();
				
				int cnt = 0;
				while(rs.next()) {
					cnt++;

					if(cnt==1)
						imgFileList = new ArrayList<ProductImagefileVO>();

					int imgfileno = rs.getInt("imgfileno");
				    int pcode = rs.getInt("pcode");
				    String pimgfilename = rs.getString("pimgfilename");
				    
				    ProductImagefileVO imgfilevo = new ProductImagefileVO(imgfileno, pcode, pimgfilename); 
				    
				    imgFileList.add(imgfilevo);
				    
				}// end of while-----------------
							
			} finally{
				close();
			}		
			
			return imgFileList;
		}// end of getProductImagefileByPnum(String pcode)---------------

		// ** tbl_like 테이블에 insert 를 해주는 메소드 ** //
		@Override
		public int insertLike(String userid, String pcode, String comments) throws SQLException {
			int result = 0;
			
			try {
				
				conn = ds.getConnection();
				
				String sql = " select count(*) AS COUNT "
						+" from tbl_likedislike "
						+" where fk_pcode = ? and fk_userid = ? ";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, pcode);
				pstmt.setString(2, userid);
							
				rs= pstmt.executeQuery();
				rs.next();
				int n = rs.getInt("COUNT");
				
				if(n == 0) {			
				
				sql = " insert into tbl_likedislike(fk_pcode, fk_userid, likedislike, comments)  "
					+ " values(?, ?, 1, ?) ";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, pcode);
				pstmt.setString(2, userid);
				pstmt.setString(3, comments);
				
			
				} else {
					sql = " update tbl_likedislike set likedislike ='1'  "
							+ " where fk_pcode = ? and fk_userid = ?  ";
							
						pstmt = conn.prepareStatement(sql);
						pstmt.setString(1, pcode);
						pstmt.setString(2, userid);
				}			
				result = pstmt.executeUpdate();
			} finally {
				close();
			}
			
			return result;
		}// end of insertLike(String userid, String pcode, String comments) --------------------------
		
		// ** tbl_dislike 테이블에 insert 를 해주는 메소드 ** //
		@Override
		public int insertDislike(String userid, String pcode,  String comments) throws SQLException {
			int result = 0;
			

			try {
				
				conn = ds.getConnection();
				
				String sql = " select count(*) AS COUNT "
							+" from tbl_likedislike "
							+" where fk_pcode =? and fk_userid = ? ";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, pcode);
				pstmt.setString(2, userid);
							
				rs= pstmt.executeQuery();
				rs.next();
			
				
				int n = rs.getInt("COUNT");
				
				
				if(n == 0) {			
				
				sql = " insert into tbl_likedislike(fk_pcode, fk_userid, likedislike)  "
					+ " values(?,?,2) ";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, pcode);
				pstmt.setString(2, userid);

				
			
				} else if(n>0){
					sql = " update tbl_likedislike set likedislike ='2'  "
							+ " where fk_pcode = ? and fk_userid = ?  ";
							
						pstmt = conn.prepareStatement(sql);
						pstmt.setString(1, pcode);
						pstmt.setString(2, userid);
				}			
				result = pstmt.executeUpdate();
			} finally {
				close();
			}
			
			return result;
		}// end of insertDislike(String userid, String pcode)---------------------------------------


		// ** tbl_like 테이블과 tbl_dislike 테이블에서 특정 제품에 대한 count(*)를 조회하는 추상 메소드 ** //
		@Override
		public HashMap<String, String> getLikeDislikeCount(String pcode) throws SQLException {
			HashMap<String, String> map = null;
			
			try {
				conn = ds.getConnection();
				String sql = " select (select count(*) "
							+"         from tbl_likedislike "
							+"         where fk_pcode = ? and likedislike = 1) AS LIKECNT, "
							+"         (select count(*) "
							+"         from tbl_likedislike "
							+"         where fk_pcode = ? and likedislike = 2) AS DISLIKECNT "
							+" from dual ";
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, pcode);
				pstmt.setString(2, pcode);
				
				rs = pstmt.executeQuery();
				
				rs.next();
				
				map = new HashMap<String, String>();
				
				String likecnt = rs.getString("likecnt");
				String dislikecnt = rs.getString("dislikecnt");
				
				map.put("likecnt", likecnt);
				map.put("dislikecnt", dislikecnt);
				
				
			} finally {
				close();
			}
			return map;
		}// end of getLikeDislikeCount(String pcode)


		@Override
		public List<LikedislikeVO> getProductVOListByUserid(int startRno, int endRno, String pcode) throws SQLException {
			List<LikedislikeVO> memoList = null;
			
			try {
				 	conn = ds.getConnection();
					
					String sql = " SELECT * " 
							+" FROM " 
							+"  (SELECT row_number() over(order by fk_pcode DESC)AS rno, " 
							+"    fk_pcode, " 
							+"    fk_userid , " 
							+"    likedislike, " 
							+"    comments " 
							+"  FROM tbl_likedislike " 
							+"  WHERE fk_pcode = ? and comments is not null " 
							+"  )V " 
							+" WHERE rno BETWEEN ? AND ? ";
							

					
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, pcode);
					pstmt.setInt(2, startRno);
					pstmt.setInt(3, endRno);
					
					rs = pstmt.executeQuery();
					
					int cnt =0;
					while(rs.next()) {
						cnt++;
						if(cnt==1)
							memoList = new ArrayList<LikedislikeVO>();
						
						int fk_pcode = rs.getInt("fk_pcode");
					    String fk_userid = rs.getString("fk_userid");
					    int likedislike = rs.getInt("likedislike");
					    String comments = rs.getString("comments");

					    
					    LikedislikeVO memovo = new LikedislikeVO(fk_pcode, fk_userid, likedislike, comments);  
					    
					    memoList.add(memovo);
						
					}
					
				
			} finally {
				close();
			}
			
			return memoList;
		}//end of getProductVOListByPspec(String pspec, int startRno, int endRno)-------------------------------------------

		//**tbl_likedislike에 한줄평 update 하는 메소드 **//
		@Override
		public int getupdatememo(String userid, String pcode, String comments, String num) throws SQLException {
			int result = 0;
			int m = Integer.parseInt(num);
			int cnt = 0;
			System.out.println("확인용 m :" + m);

			try {
				
				conn = ds.getConnection();
				
				String sql = " select count(*) AS COUNT "
							+" from tbl_likedislike "
							+" where fk_pcode = ? and fk_userid = ? ";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, pcode);
				pstmt.setString(2, userid);
							
				rs= pstmt.executeQuery();
				rs.next();
								
				cnt = rs.getInt("count");
				
				if(cnt == 0) {
					// insert 해주기
					sql = " insert into tbl_likedislike(fk_pcode, fk_userid, comments)  "
							+ " values(?,?,?) ";
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, pcode);
					pstmt.setString(2, userid);
					pstmt.setString(3, comments);
			
					result = pstmt.executeUpdate();
					
				}else {
					
					sql = " select count(*) AS COUNT "
							+" from tbl_likedislike "
							+" where fk_pcode = ? and fk_userid = ? and comments is not null ";
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, pcode);
					pstmt.setString(2, userid);
								
					rs= pstmt.executeQuery();
					rs.next();
									
					cnt = rs.getInt("count");
					if(cnt == 1 && m == 1) {
						result = 0;
					}else {				
						if(m == 2 || m == 1) {
							
							sql = " update tbl_likedislike set comments = ?  "
									+ " where fk_pcode = ? and fk_userid = ? ";
							pstmt = conn.prepareStatement(sql);
							pstmt.setString(1, comments);
							pstmt.setString(2, pcode);
							pstmt.setString(3, userid);
		
							result = pstmt.executeUpdate();
						}else if( m == 3) {
							
							sql = " update tbl_likedislike set comments = null  "
									+ " where fk_pcode = ? and fk_userid = ? ";
							pstmt = conn.prepareStatement(sql);
							pstmt.setString(1, pcode);
							pstmt.setString(2, userid);
		
							pstmt.executeUpdate();
							result = 2;
						}
					}
				}
				
				
				
			} finally {
				close();
			}
			
			return result;
		}//end of getupdatememo(String userid, String pcode, String comments)--------------------------------------

		//**tbl_likedislike에 한줄평 select 하는 메소드 **//
		@Override
		public String getshowcomment(String userid, String pcode) throws SQLException {
			String result = "고객님이 작성하신 글이 없으니 아래에 한줄평 작성하기 버튼을 클릭해 주세요";
			

			try {
				
				conn = ds.getConnection();
				
				String sql = " select nvl(comments,1)as comments "
							+" from tbl_likedislike "
							+" where fk_pcode = ? and fk_userid = ? and comments is not null ";
				
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, pcode);
				pstmt.setString(2, userid);
							
				rs = pstmt.executeQuery();

				if (rs.next()) {

						result = rs.getString("comments");

				}
				
				System.out.println(result);
			} finally {
				close();
			}
			
			return result;
		}


		@Override
		public int totalMemoCount(String pcode) throws SQLException {
			int n=0;
			 try {
				 conn = ds.getConnection();
					
					String sql = " select count(*) AS CNT "
							   + " from tbl_likedislike "
							   + " where fk_pcode = ? ";
							
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, pcode);
					
					rs = pstmt.executeQuery();
					rs.next();
					n = rs.getInt("CNT");
					
			} finally {
				close();
			}
			return n;
		}//end of totalMemoCount()---------------------------	

		@Override
		public List<HashMap<String, String>> getBestProduct(String category, int startRno, int endRno)
				throws SQLException {
			
			List<HashMap<String, String>> plist = null;
			
			String sql = "";
			
			try {
				
				conn = ds.getConnection();
				
				if(category == null || "".equals(category) || "all".equalsIgnoreCase(category)) {
					sql = " select pname "
						+ " from tbl_product "
						+ " group by pname "
						+ " order by pname ";
					
					pstmt = conn.prepareStatement(sql);
				}
				
				else if("new".equalsIgnoreCase(category) || "best".equalsIgnoreCase(category)) {
					System.out.println("category1-1 : " + category);
					switch(category) {
						case "new":
							category = "1";
							break;
						case "best":
							category = "2";
							break;
						default:
							category = "1";
							break;
					}
					System.out.println("category1-2 : " + category);
					
					sql = " select pname "
						+ " from tbl_product "
						+ " where pstatus = ? "
						+ " group by pname "
						+ " order by pname ";
					
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, category);
				}
				
				else {
					System.out.println("category2-1 : " + category);
					switch(category) {
						case "outer":
							category = "1000";
							break;
						case "top":
							category = "2000";
							break;
						case "bottom":
							category = "3000";
							break;
						case "acc":
							category = "4000";
							break;
						case "suit":
							category = "5000";
							break;
						default:
							category = "1000";
							break;
					}
					System.out.println("category2-2 : " + category);
					
					sql = " select pname "
						+ " from tbl_product A join tbl_category B "
						+ " on A.fk_pcategory = B.code "
						+ " where code = ? "
						+ " group by pname "
						+ " order by pname ";
					
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, category);
				}
				
				rs = pstmt.executeQuery();
				
				List<String> namelist = new ArrayList<String>();
				
				while(rs.next()) {
					namelist.add(rs.getString("pname"));
				}
				
				int cnt = 0;
				
				for(String pname : namelist) {
					
					cnt++;

					if(cnt == 1) {
						plist = new ArrayList<HashMap<String, String>>();
					}
					
					sql = " select * "
						+ " from "
						+ " ( "
						+ "   select rownum AS rno, pcode, pname, fk_pcategory, pcolor, psize, pimage, pqty, price, saleprice, pstatus, point, pcontent, to_char(pinputdate, 'yyyymmdd') as pinputdate "
						+ "   from "
						+ "   ( "
						+ "     select * "
						+ "     from tbl_product "
						+ "     where pname = ? "
						+ "   ) "
						+ " ) "
						+ " where rno = 1 ";
					
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, pname);
					
					rs = pstmt.executeQuery();
					if(rs.next()) {
						HashMap<String, String> pmap = new HashMap<String, String>();
						
						pmap.put("pcode", rs.getString("pcode"));
						pmap.put("pname", rs.getString("pname"));
						pmap.put("fk_pcategory", rs.getString("fk_pcategory"));
						pmap.put("pcolor", rs.getString("pcolor"));
						pmap.put("psize", rs.getString("psize"));
						pmap.put("pimage", rs.getString("pimage"));
						pmap.put("pqty", rs.getString("pqty"));
						pmap.put("price", rs.getString("price"));
						pmap.put("saleprice", rs.getString("saleprice"));
						pmap.put("pstatus", rs.getString("pstatus"));
						pmap.put("point", rs.getString("point"));
						pmap.put("pcontent", rs.getString("pcontent"));
						pmap.put("pinputdate", rs.getString("pinputdate"));
						
						plist.add(pmap);
					}
				}
				
			}finally {
				close();
			}
			
			return plist;
			
		}

		@Override
		public int deleteProducts(String pcode) throws SQLException {
			int m = 0;
			int result = 0;
			
			try {
				
				conn = ds.getConnection();
				
				String sql = " delete from tbl_likedislike "
							+" where fk_pcode = ?  ";
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, pcode);
				
				pstmt.executeUpdate();
				
					
				sql = " delete from tbl_product_image "
					 +" where pcode = ?  ";
					
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, pcode);
				
				m = pstmt.executeUpdate();
					
				
				sql = " delete from tbl_product "
					+ " where pcode = ?  ";
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, pcode);
				
				result = pstmt.executeUpdate();
					
				
				
			} finally {
				close();
			}
			
			return result;
		}
		
		
		// 모든 제품목록 가져오기 //
		@Override
		public List<HashMap<String, String>> getAllProducts() throws SQLException {
			List<HashMap<String, String>> plist = null;
			
			try {
				
				conn = ds.getConnection();
				
				String sql = " SELECT pcode, pname, fk_pcategory, pcolor, psize, pimage, pqty, price, saleprice, pstatus, point, pcontent, pinputdate "
						   + " FROM tbl_product "
						   + " order by 3,1 asc ";
				
				pstmt = conn.prepareStatement(sql);
				
				rs = pstmt.executeQuery();
				
				int cnt = 0;
				
				while(rs.next()) {
					cnt++;

					if(cnt == 1) {
						plist = new ArrayList<HashMap<String, String>>();
					}
					
					HashMap<String, String> pmap = new HashMap<String, String>();
					pmap.put("pcode", rs.getString("pcode"));
					pmap.put("pname", rs.getString("pname"));
					pmap.put("fk_pcategory", rs.getString("fk_pcategory"));
					if(Integer.parseInt(rs.getString("pcolor")) == 0) {
						pmap.put("pcolor", "검정");
					}else if(rs.getString("pcolor").equals("1")) {
						pmap.put("pcolor", "네이비");
					}else if(rs.getString("pcolor").equals("2")) {
						pmap.put("pcolor", "화이트");
					}else {
						pmap.put("pcolor", "그 외");
					}
					pmap.put("psize", rs.getString("psize"));
					pmap.put("pimage", rs.getString("pimage"));
					pmap.put("pqty", rs.getString("pqty"));
					pmap.put("price", rs.getString("price"));
					pmap.put("saleprice", rs.getString("saleprice"));
					pmap.put("pstatus", rs.getString("pstatus"));
					pmap.put("point", rs.getString("point"));
					pmap.put("pcontent", rs.getString("pcontent"));
					pmap.put("pinputdate", rs.getString("pinputdate"));
					
					plist.add(pmap);
				}
				
			} finally {
				close();
			}
			
			return plist;
		}
		
		
		
		
		
}

