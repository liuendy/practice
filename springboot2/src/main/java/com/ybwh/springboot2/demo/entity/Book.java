package com.ybwh.springboot2.demo.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

public class Book {
	@TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    /**
     * 书籍名
     */
    private String bookName;

    /**
     * 书籍编号
     */
    private String bookCode;

    /**
     * 书籍类别（1-计算机，2文学，3-建筑）
     */
    private Integer category;

    /**
     * 是否已删除（1-是，0-否）
     */
    private Integer isDel;

    /**
     * 记录创建时间
     */
    private Date createTime;

    /**
     * 记录更新时间
     */
    private Date updateTime;

    /**
     * 记录创建人
     */
    private Integer createUser;

    /**
     * 记录更新人
     */
    private Integer updateUser;

    /**
     * 
     * @return id 
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * @param id 
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 书籍名
     * @return book_name 书籍名
     */
    public String getBookName() {
        return bookName;
    }

    /**
     * 书籍名
     * @param bookName 书籍名
     */
    public void setBookName(String bookName) {
        this.bookName = bookName == null ? null : bookName.trim();
    }

    /**
     * 书籍编号
     * @return book_code 书籍编号
     */
    public String getBookCode() {
        return bookCode;
    }

    /**
     * 书籍编号
     * @param bookCode 书籍编号
     */
    public void setBookCode(String bookCode) {
        this.bookCode = bookCode == null ? null : bookCode.trim();
    }

    /**
     * 书籍类别（1-计算机，2文学，3-建筑）
     * @return category 书籍类别（1-计算机，2文学，3-建筑）
     */
    public Integer getCategory() {
        return category;
    }

    /**
     * 书籍类别（1-计算机，2文学，3-建筑）
     * @param category 书籍类别（1-计算机，2文学，3-建筑）
     */
    public void setCategory(Integer category) {
        this.category = category;
    }

    /**
     * 是否已删除（1-是，0-否）
     * @return is_del 是否已删除（1-是，0-否）
     */
    public Integer getIsDel() {
        return isDel;
    }

    /**
     * 是否已删除（1-是，0-否）
     * @param isDel 是否已删除（1-是，0-否）
     */
    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    /**
     * 记录创建时间
     * @return create_time 记录创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 记录创建时间
     * @param createTime 记录创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 记录更新时间
     * @return update_time 记录更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 记录更新时间
     * @param updateTime 记录更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 记录创建人
     * @return create_user 记录创建人
     */
    public Integer getCreateUser() {
        return createUser;
    }

    /**
     * 记录创建人
     * @param createUser 记录创建人
     */
    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    /**
     * 记录更新人
     * @return update_user 记录更新人
     */
    public Integer getUpdateUser() {
        return updateUser;
    }

    /**
     * 记录更新人
     * @param updateUser 记录更新人
     */
    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }
}