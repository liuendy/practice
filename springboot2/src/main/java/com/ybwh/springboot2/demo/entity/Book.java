package com.ybwh.springboot2.demo.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

public class Book {
	@TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    /**
     * �鼮��
     */
    private String bookName;

    /**
     * �鼮���
     */
    private String bookCode;

    /**
     * �鼮���1-�������2��ѧ��3-������
     */
    private Integer category;

    /**
     * �Ƿ���ɾ����1-�ǣ�0-��
     */
    private Integer isDel;

    /**
     * ��¼����ʱ��
     */
    private Date createTime;

    /**
     * ��¼����ʱ��
     */
    private Date updateTime;

    /**
     * ��¼������
     */
    private Integer createUser;

    /**
     * ��¼������
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
     * �鼮��
     * @return book_name �鼮��
     */
    public String getBookName() {
        return bookName;
    }

    /**
     * �鼮��
     * @param bookName �鼮��
     */
    public void setBookName(String bookName) {
        this.bookName = bookName == null ? null : bookName.trim();
    }

    /**
     * �鼮���
     * @return book_code �鼮���
     */
    public String getBookCode() {
        return bookCode;
    }

    /**
     * �鼮���
     * @param bookCode �鼮���
     */
    public void setBookCode(String bookCode) {
        this.bookCode = bookCode == null ? null : bookCode.trim();
    }

    /**
     * �鼮���1-�������2��ѧ��3-������
     * @return category �鼮���1-�������2��ѧ��3-������
     */
    public Integer getCategory() {
        return category;
    }

    /**
     * �鼮���1-�������2��ѧ��3-������
     * @param category �鼮���1-�������2��ѧ��3-������
     */
    public void setCategory(Integer category) {
        this.category = category;
    }

    /**
     * �Ƿ���ɾ����1-�ǣ�0-��
     * @return is_del �Ƿ���ɾ����1-�ǣ�0-��
     */
    public Integer getIsDel() {
        return isDel;
    }

    /**
     * �Ƿ���ɾ����1-�ǣ�0-��
     * @param isDel �Ƿ���ɾ����1-�ǣ�0-��
     */
    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    /**
     * ��¼����ʱ��
     * @return create_time ��¼����ʱ��
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * ��¼����ʱ��
     * @param createTime ��¼����ʱ��
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * ��¼����ʱ��
     * @return update_time ��¼����ʱ��
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * ��¼����ʱ��
     * @param updateTime ��¼����ʱ��
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * ��¼������
     * @return create_user ��¼������
     */
    public Integer getCreateUser() {
        return createUser;
    }

    /**
     * ��¼������
     * @param createUser ��¼������
     */
    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    /**
     * ��¼������
     * @return update_user ��¼������
     */
    public Integer getUpdateUser() {
        return updateUser;
    }

    /**
     * ��¼������
     * @param updateUser ��¼������
     */
    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }
}