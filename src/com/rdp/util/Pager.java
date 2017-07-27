package com.rdp.util;

import java.util.ArrayList;
import java.util.List;

//
/**
 * 通用分页类
 * @author gjz
 * @create in 2013-11-4 23:00
 */
public class Pager {


	protected int[] pageSizeList = { 10, 25, 50, 100, 200, 300, 500 };
	protected int pageSize = 10;
	protected int curPage = 1;
	protected int rowCount = 0;
	protected int pageCount = 1;
	protected int startIndex = 1;
	protected int endIndex = 1;
	protected int firstCurPage = 1;
	protected int preCurPage = 1;
	protected int nextCurPage = 1;
	protected int lastCurPage = 1;


	protected List resultList;
	
	public Pager(int pageSize, int curPage, int rowCount, List resultList) {
		this.pageSize = pageSize;
		this.curPage = curPage;
		this.rowCount = rowCount;
		this.resultList = resultList;
		
		if (rowCount % pageSize == 0) {
			this.pageCount = rowCount / pageSize;
		} else {
			this.pageCount = rowCount / pageSize + 1;
		}
		this.startIndex = pageSize * (curPage - 1);
		this.endIndex = this.startIndex + resultList.size();
		
		this.lastCurPage = this.pageCount;
		if (this.curPage > 1) this.preCurPage = this.curPage -1;
		if (this.curPage == this.lastCurPage){
			this.nextCurPage = this.lastCurPage;
		} else {
			this.nextCurPage = this.curPage + 1;
		}
	}
	
	@SuppressWarnings("unchecked")
	public Object[] getPageSizeIndexs() {
		List result = new ArrayList(pageSizeList.length);
		for (int i = 0; i < pageSizeList.length; i++) {
			result.add(String.valueOf(pageSizeList[i]));
		}
		Object[] indexs = (result.toArray());
		return indexs;
	}
	
	@SuppressWarnings("unchecked")
	public Object[] getCurPageIndexs() {
		List result = new ArrayList(pageCount);
		for (int i = 0; i < pageCount; i++) {
			result.add(String.valueOf(i + 1));
		}
		Object[] indexs = (result.toArray());
		return indexs;
	}

	public int getEndIndex() {
		return endIndex;
	}

	public void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int[] getPageSizeList() {
		return pageSizeList;
	}

	public void setPageSizeList(int[] pageSizeList) {
		this.pageSizeList = pageSizeList;
	}

	public List getResultList() {
		return resultList;
	}

	public void setResultList(List resultList) {
		this.resultList = resultList;
	}

	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public int getFirstCurPage() {
		return firstCurPage;
	}

	public void setFirstCurPage(int firstCurPage) {
		this.firstCurPage = firstCurPage;
	}

	public int getLastCurPage() {
		return lastCurPage;
	}

	public void setLastCurPage(int lastCurPage) {
		this.lastCurPage = lastCurPage;
	}

	public int getNextCurPage() {
		return nextCurPage;
	}

	public void setNextCurPage(int nextCurPage) {
		this.nextCurPage = nextCurPage;
	}

	public int getPreCurPage() {
		return preCurPage;
	}

	public void setPreCurPage(int preCurPage) {
		this.preCurPage = preCurPage;
	}

	public Pager(){};
}
