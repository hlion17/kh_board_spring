package web.common;

public class Pagination {

	private int total;
	private int curPage;
	private int listCnt;
	private int startIndex;
	private int endIndex;
	
	private int pageCnt;
	
	private int blockSize;
	private int blockCnt;
	private int curBlock;
	private int startPage;
	private int endPage;
	private int prevPage;
	private int nextPage;
	
	public Pagination(int curPage, int listCnt, int total) {
		this.curPage = curPage;
		this.total = total;
		this.listCnt = listCnt;
		this.blockSize = 10;  // 페이지 블럭 당 페이지 10개로 설정
		
		// 총 페이지 수 계산
		setPageCnt(listCnt);
		// 총 블럭수 계산
		setBlockCnt(pageCnt);
		// 블럭 세팅
		blockSetting(curPage);
		
		// startIndex
		setIndex(curPage);
	}
	
	private void setPageCnt(int listCnt) {
		this.pageCnt = (int) Math.ceil(total / (double)listCnt);
	}
	
	private void setBlockCnt(int pageCnt) {
		this.blockCnt = (int) Math.ceil(pageCnt * 1.0 / blockSize);
	}
	
	private void blockSetting(int curPage) {
		setCurBlock(curPage);
		this.startPage = (curBlock - 1) * blockSize + 1;
		this.endPage = startPage + blockSize - 1;
		
		if (endPage > pageCnt) {
			this.endPage = pageCnt;
		}
		this.prevPage = curPage - 1;
		this.nextPage = curPage + 1;
	}
	
	private void setCurBlock(int curPage) {
		this.curBlock = (int) ((curPage - 1) / blockSize) + 1;
	}
	 
	private void setIndex(int curPage ) {
//		this.startIndex = (pageCnt - curPage) * listCnt + 1;
//		this.endIndex = startIndex + listCnt - 1;
		this.startIndex = (curPage - 1) * listCnt + 1;
		this.endIndex = startIndex + listCnt - 1;
	}

	
	// getter
	public int getStartIndex() {
		return startIndex;
	}
	
	public int getEndIndex() {
		return endIndex;
	}

	public int getStartPage() {
		return startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public int getPrevPage() {
		return prevPage;
	}

	public int getNextPage() {
		return nextPage;
	}

	public int getCurPage() {
		return curPage;
	}

	public int getPageCnt() {
		return pageCnt;
	}

	@Override
	public String toString() {
		return "Pagination [total=" + total + ", curPage=" + curPage + ", listCnt=" + listCnt + ", startIndex="
				+ startIndex + ", endIndex=" + endIndex + ", pageCnt=" + pageCnt + ", blockSize=" + blockSize
				+ ", blockCnt=" + blockCnt + ", curBlock=" + curBlock + ", startPage=" + startPage + ", endPage="
				+ endPage + ", prevPage=" + prevPage + ", nextPage=" + nextPage + "]";
	}
	
	
}
