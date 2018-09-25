package com.ybwh.springboot2.common.mybatis.plugin.pagination;

import java.util.List;

public interface Pagination<T> {

	List<T> getList();

	int getTotal();

	int getPageSize();

	int getPageIndex();

	int getTotalPage();

}
