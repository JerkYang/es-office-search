package cn.itstar.es.office.search.ftr.utils;


import java.util.List;

public class PageUtil<T> {

	private List<T> list;// 封装实体
	private String keyword;// 搜索关键词
	private long total;// 总记录数
	private long curPage;// 当前页
	private long pageSize;// 每页记录数
	private long pageCount;// 总页数

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public long getTotal() {
		return total;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public long getCurPage() {
		return curPage;
	}

	public void setCurPage(long curPage) {
		this.curPage = curPage;
	}

	public long getPageSize() {
		return pageSize;
	}

	public void setPageSize(long pageSize) {
		this.pageSize = pageSize;
	}

	public long getPageCount() {
		pageCount = (total + pageSize - 1) / pageSize;
		return pageCount;
	}

}
