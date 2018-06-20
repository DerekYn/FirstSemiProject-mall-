package product.model;

public class ProductImagefileVO {

	private int imgfileno;      // 시퀀스로 입력받음
	private int pcode;        // 제품번호(foreign key)
	private String pimgfilename; // 제품이미지파일명
	
	public ProductImagefileVO() { }
	
	public ProductImagefileVO(int imgfileno, int pcode, String pimgfilename) {
		this.imgfileno = imgfileno;
		this.pcode = pcode;
		this.pimgfilename = pimgfilename;
	}

	public int getImgfileno() {
		return imgfileno;
	}

	public void setImgfileno(int imgfileno) {
		this.imgfileno = imgfileno;
	}

	public int getPcode() {
		return pcode;
	}

	public void setPcode(int pcode) {
		this.pcode = pcode;
	}

	public String getPimgfilename() {
		return pimgfilename;
	}

	public void setPimgfilename(String pimgfilename) {
		this.pimgfilename = pimgfilename;
	}

	

	
	
}
