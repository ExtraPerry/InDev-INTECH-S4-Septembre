package com.example.BotApi.model.Contract;

import com.example.BotApi.repository.ItemRepository;

//Class.
public class PageFormat {
	
	//Attributes.			//The book refers to the Set<T> that will be instanced into many subLists or pages.
	private int page;		//The page from which it should pull inside of the book. [0 <-> +infinite].
	private int size;		//The size of the page. Note this will change what each page has though. So always ask for the same page size.
	private String sort;	//The value by which the book of pages should be sorted. Should refer to the attribute name of the requested class.
	
	//Constructors.
	public PageFormat() {
		
	}
	public PageFormat(final int page, final int size, final String sort) {
		this.setPage(page);
		this.setSize(size);
		this.setSort(sort);
	}
	
	//Getters & Setters
	public int getPage() {
		return page;
	}
	private void setPage(final int page) {
		this.page = page;
	}
	public int getSize() {
		return size;
	}
	private void setSize(final int size) {
		this.size = size;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(final String sort) {
		this.sort = sort;
	}
	
}
