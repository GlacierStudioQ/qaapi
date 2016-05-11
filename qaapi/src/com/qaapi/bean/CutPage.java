package com.qaapi.bean;

public class CutPage {

	private Integer nowPage;
	private Integer pageSize;

	private Integer dateCount;
	private Integer pageCount;

	/**
	 * 验证CutPage类中的各变量是否合法
	 * @return
	 */
	public boolean validate() {
		if (nowPage <= 0 || pageSize <= 0) {
			return false;
		} else {
			return true;
		}
	}

	public Integer getNowPage() {
		return nowPage;
	}

	public void setNowPage(Integer nowPage) {
		this.nowPage = nowPage;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getDateCount() {
		return dateCount;
	}

	public void setDateCount(Integer dateCount) {
		this.dateCount = dateCount;
	}

	public Integer getPageCount() {
		return pageCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}
}
