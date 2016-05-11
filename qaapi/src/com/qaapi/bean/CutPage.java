package com.qaapi.bean;

import static com.qaapi.util.QaapiStatic.DEFAULT_PAGE_SIZE;

import java.io.Serializable;

public class CutPage implements Serializable{

	private Integer nowPage;
	private Integer pageSize;

	private Integer dataCount;
	private Integer pageCount;

	/**
	 * 验证CutPage类中的各变量是否合法，若不合法，则置为默认值
	 * @return
	 */
	public void format() {
		if(nowPage > pageCount){
			nowPage = pageCount;
		}
		if (nowPage <= 0){
			nowPage = 1;
		}
		if(pageSize <= 0) {
			pageSize = DEFAULT_PAGE_SIZE;
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

	public Integer getDataCount() {
		return dataCount;
	}

	public void setDataCount(Integer dataCount) {
		this.dataCount = dataCount;
	}

	public Integer getPageCount() {
		return pageCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}
}
