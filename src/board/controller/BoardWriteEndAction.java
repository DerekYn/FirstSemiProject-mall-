package board.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import common.controller.AbstractController;
import board.model.*;

public class BoardWriteEndAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		BoardDAO bdao = new BoardDAO();
	   	QnaVO qnaboarddata = new QnaVO();
	   	
		String realFolder="";
   		String saveFolder="boardupload";
   		
   		int fileSize = 5*1024*1024;
   		
   		realFolder=req.getRealPath(saveFolder);
   		
   		boolean result = false;
   		
   		try{
   			
   			MultipartRequest multi = null;
   			
   			multi = new MultipartRequest(req,
   					realFolder,
   					fileSize,
   					"UTF-8",
   					new DefaultFileRenamePolicy());
   			
   			qnaboarddata.setBoard_qna_userid(multi.getParameter("board_qna_userid"));
   			qnaboarddata.setBoard_qna_pw(multi.getParameter("board_qna_pw"));
   			qnaboarddata.setBoard_qna_subject(multi.getParameter("board_qna_subject"));
   			qnaboarddata.setBoard_qna_content(multi.getParameter("board_qna_content"));
   			qnaboarddata.setBoard_qna_file(
	   				multi.getFilesystemName((String)multi.getFileNames().nextElement()));
	   		
   			
   			result = bdao.boardInsert(qnaboarddata);
	   		
	   		if(result == false){
	   			super.alertMsg(req, "게시판 등록 실패", "javascript:history.back();");
	   			return;
	   		}
	   		System.out.println("게시판 등록 완료");
	   		
	   		super.setRedirect(true);
	   		super.setViewPage("boardList.do");
	   		
  		}catch(Exception ex){
   			ex.printStackTrace();
   		}
  		return;
	}  	
}