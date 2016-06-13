package cn.bluemobi.entity.system;

import cn.bluemobi.util.helper.ValidateHelper;

/**
 * 描述:分页属性类
 * 
 * @author:Ray
 * @since: 2008-12-17
 * **/
public class Page {

	/**
	 * 每页显示的记录数
	 * 
	 * 
	 */
	private Integer pageSize = null; 
	
	/**
	 * 当前页码
	 * 
	 * 
	 */
	private Integer pageNo = null; 
	
	/**
	 * 总页数
	 * 
	 * 
	 */
	private Integer pageCount = null; 
	
	
	/**
	 * 数据总条数
	 * 
	 * 
	 */
	private Integer totalCount = null; 

	public Page() {
	}

	public Page(Object pageNo, Object pageSize, Object pageCount) {
		if (ValidateHelper.isNullOrEmpty(pageNo)) {
			this.pageNo = 1;
		} else {
			this.pageNo = Integer.parseInt(pageNo.toString());
		}
		if (ValidateHelper.isNullOrEmpty(pageCount)) {
			this.pageCount = -1;
		} else {
			this.pageCount = Integer.parseInt(pageCount.toString());
		}
		if (ValidateHelper.isNullOrEmpty(pageSize)) {
			this.pageSize = 10;
		} else {
			this.pageSize = Integer.parseInt(pageSize.toString());
		}
		if (this.pageNo <= 0)
			this.pageNo = 1;
	}

	public Page(Object pageNo, Object pageSize, Object pageCount, Object totalCount) {
		this.pageNo = Integer.parseInt(pageNo.toString());
		this.pageSize = Integer.parseInt(pageSize.toString());
		this.pageCount = Integer.parseInt(pageCount.toString());
		if (this.pageNo <= 0)
			pageNo = 1;
		this.totalCount = Integer.parseInt(totalCount.toString());
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageCount() {
		return pageCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
}
