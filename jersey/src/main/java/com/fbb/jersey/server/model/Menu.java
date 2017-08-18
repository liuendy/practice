package com.fbb.jersey.server.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Menu {
	private Long menuId;
	private String menuName;
	private Float price;

	public Menu() {
	}

	public Menu(Long menuId, String menuName, Float price) {
		this.menuId = menuId;
		this.menuName = menuName;
		this.price = price;
	}

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Menu [menuId=" + menuId + ", menuName=" + menuName + ", price="
				+ price + "]";
	}
}
