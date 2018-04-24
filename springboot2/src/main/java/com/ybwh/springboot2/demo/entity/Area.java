package com.ybwh.springboot2.demo.entity;

import java.util.Date;

public class Area {
    /**
     * ID
     */
    private Integer id;

    /**
     * 地区名
     */
    private String areaName;

    /**
     * 父级ID
     */
    private Integer parentId;

    /**
     * 简名
     */
    private String shortName;

    /**
     * 地区编码
     */
    private Integer areaCode;

    /**
     * 层级
     */
    private Integer level;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * id路径
     */
    private String idPath;

    /**
     * name路径
     */
    private String namePath;

    /**
     * 是否叶子节点(0-否;1-是)
     */
    private Integer isLeaf;

    /**
     * 是否被删除(0-否;1-是)
     */
    private Integer isDel;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private Integer createId;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 更新人
     */
    private Integer updateId;

    /**
     * ID
     * @return id ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * ID
     * @param id ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 地区名
     * @return area_name 地区名
     */
    public String getAreaName() {
        return areaName;
    }

    /**
     * 地区名
     * @param areaName 地区名
     */
    public void setAreaName(String areaName) {
        this.areaName = areaName == null ? null : areaName.trim();
    }

    /**
     * 父级ID
     * @return parent_id 父级ID
     */
    public Integer getParentId() {
        return parentId;
    }

    /**
     * 父级ID
     * @param parentId 父级ID
     */
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    /**
     * 简名
     * @return short_name 简名
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * 简名
     * @param shortName 简名
     */
    public void setShortName(String shortName) {
        this.shortName = shortName == null ? null : shortName.trim();
    }

    /**
     * 地区编码
     * @return area_code 地区编码
     */
    public Integer getAreaCode() {
        return areaCode;
    }

    /**
     * 地区编码
     * @param areaCode 地区编码
     */
    public void setAreaCode(Integer areaCode) {
        this.areaCode = areaCode;
    }

    /**
     * 层级
     * @return level 层级
     */
    public Integer getLevel() {
        return level;
    }

    /**
     * 层级
     * @param level 层级
     */
    public void setLevel(Integer level) {
        this.level = level;
    }

    /**
     * 排序
     * @return sort 排序
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * 排序
     * @param sort 排序
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * id路径
     * @return id_path id路径
     */
    public String getIdPath() {
        return idPath;
    }

    /**
     * id路径
     * @param idPath id路径
     */
    public void setIdPath(String idPath) {
        this.idPath = idPath == null ? null : idPath.trim();
    }

    /**
     * name路径
     * @return name_path name路径
     */
    public String getNamePath() {
        return namePath;
    }

    /**
     * name路径
     * @param namePath name路径
     */
    public void setNamePath(String namePath) {
        this.namePath = namePath == null ? null : namePath.trim();
    }

    /**
     * 是否叶子节点(0-否;1-是)
     * @return is_leaf 是否叶子节点(0-否;1-是)
     */
    public Integer getIsLeaf() {
        return isLeaf;
    }

    /**
     * 是否叶子节点(0-否;1-是)
     * @param isLeaf 是否叶子节点(0-否;1-是)
     */
    public void setIsLeaf(Integer isLeaf) {
        this.isLeaf = isLeaf;
    }

    /**
     * 是否被删除(0-否;1-是)
     * @return is_del 是否被删除(0-否;1-是)
     */
    public Integer getIsDel() {
        return isDel;
    }

    /**
     * 是否被删除(0-否;1-是)
     * @param isDel 是否被删除(0-否;1-是)
     */
    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    /**
     * 创建时间
     * @return create_time 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 创建人
     * @return create_id 创建人
     */
    public Integer getCreateId() {
        return createId;
    }

    /**
     * 创建人
     * @param createId 创建人
     */
    public void setCreateId(Integer createId) {
        this.createId = createId;
    }

    /**
     * 更新时间
     * @return update_time 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 更新时间
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 更新人
     * @return update_id 更新人
     */
    public Integer getUpdateId() {
        return updateId;
    }

    /**
     * 更新人
     * @param updateId 更新人
     */
    public void setUpdateId(Integer updateId) {
        this.updateId = updateId;
    }
}