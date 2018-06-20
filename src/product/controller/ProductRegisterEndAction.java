package product.controller;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import common.controller.AbstractController;
import product.model.InterproductDAO;
import product.model.ProductDAO;
import product.model.ProductVO;

public class ProductRegisterEndAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String method = req.getMethod();
		
		if(!method.equals("POST")) {
			super.invalidPath(req);
			return;
		}
		//***첨부파일 받아서 WAS컴퓨터 디스크에 올려주기***//
				//1.WAS 컴퓨터 디스크의 어느 경로에 파일을 오려줄지 결정해야한다.
				HttpSession session =req.getSession();
				ServletContext svlCtx = session.getServletContext();
				String upDir = svlCtx.getRealPath("/img");
				
				//웹상에서 /images에 해당하는 실제 물리적인 디스크경로를 알아로는 것이다.
				
				System.out.println("===>확인용 : 첨부파일이 올라갈 upDir 절대 경로명 "+upDir);
				// C:\myjsp\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\semiProject\images
				
				MultipartRequest mtrequest = null;
				//일반적인 HttpServletRequest request 객체로서는 첨부파일을 받을 수가 없다. 그래서 MultipartRequest mtrequest  객체를 사용하면
				//폼상의 모든 값을 받아서 처리해준다.
				//2.파일을 받아서 올려주기
				//==> MultipartRequest mtrequest 객체의 생성자만 호출해주면 알아서첨부파일을 올려준다.
				try {
				mtrequest = new MultipartRequest(req, upDir, 10*1024*1024, "UTF-8", new DefaultFileRenamePolicy());
		
				}catch(IOException e) {
					super.alertMsg(req, "파일 업로드 실패 용량 10MB 이어야 합니다.", "javascript:history.back();");
					return;			
				}
				
				
				InterproductDAO pdao = new ProductDAO();
				
				//***새로운 제품등록시 HTML form에서 입력한 값들 얻어오기

				String fk_pcategory = mtrequest.getParameter("fk_pcategory");
				String pname = mtrequest.getParameter("pname");
				String pcolor = mtrequest.getParameter("pcolor");
				String psize = mtrequest.getParameter("psize");
				
				String pimage = mtrequest.getFilesystemName("pimage1");
				
				// 업로드되어진 시스템의 파일 이름을 얻어 올때는 
				// cos.jar 라이브러리에서 제공하는 MultipartRequest 객체의  getFilesystemName("form에서의 첨부파일 name명") 메소드를 사용 한다. 
				// 이때 업로드 된 파일이 없는 경우에는 null을 반환한다.
			
				String pqty = mtrequest.getParameter("pqty");
				String price = mtrequest.getParameter("price");
				String saleprice = mtrequest.getParameter("saleprice");
				String pstatus = mtrequest.getParameter("pstatus");
				String pcontent = mtrequest.getParameter("pcontent").replaceAll("\r\n", "<br/>");
				
				
				int pcode = pdao.getPnumOfProduct();
				//jsp_product 테이블에 신규제품으로 insert되어질
				//제품번호 시퀀스 seq_jsp_product_pnum.nextval을 따와서 변수에 넣어준다.

				ProductVO pvo = new ProductVO();
				
				pvo.setPcode(pcode);
				pvo.setPname(pname);
				pvo.setFk_pcategory(fk_pcategory);
				pvo.setPcolor(Integer.parseInt(pcolor));
				pvo.setPsize(Integer.parseInt(psize));
				pvo.setPimage(pimage);
				pvo.setPqty(Integer.parseInt(pqty));
				pvo.setPrice(Integer.parseInt(price));
				pvo.setSaleprice(Integer.parseInt(saleprice));
				pvo.setPstatus(Integer.parseInt(pstatus));
				pvo.setPcontent(pcontent);
				
				
				int n= pdao.productInsert(pvo);
				
				String str_attachCount = mtrequest.getParameter("attachCount");
				if(!"".equals(str_attachCount)) {
					
					int attachCount = Integer.parseInt(str_attachCount);
					for(int i=0; i<attachCount; i++) {
						
						String attachFilename = mtrequest.getFilesystemName("attach"+i);
						pdao.product_imagefile_Insert(pcode,attachFilename);
						
					}//end of for---------------------------------------------------------------------------
					
					
				}
				
				String msg = (n>0)?"제품등록 성공":"제품등록 실패";
				String loc = (n>0)?"productRegister.do":"javascript:history.back();";
				
				super.alertMsg(req, msg, loc);
				
			}

	}

