package demo.model;

import java.util.Date;

import javax.validation.constraints.NotNull;

public class Demo {
	@NotNull(message="{name}")
	private String name;
	@NotNull
	private Integer age;
	@NotNull
	private Float height;
	@NotNull
	private Date birthDay;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public Float getHeight() {
		return height;
	}
	public void setHeight(Float height) {
		this.height = height;
	}
	public Date getBirthDay() {
		return birthDay;
	}
	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}
}
