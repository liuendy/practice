package com.entity;

import java.util.Date;

public class OrganizationBaseInfo {
    /**
     * 组织机构ID
     */
    private Long organizationId;

    /**
     * 组织机构名称
     */
    private String name;

    /**
     * 地域隶属
     */
    private String city;

    /**
     * 源目录
     */
    private String source;

    /**
     * 目录级别
     */
    private Integer level;

    /**
     * 事业部
     */
    private String division;

    /**
     * 汇报源目录ID
     */
    private String leaderId;

    /**
     * 事业部负责人ID
     */
    private String divisionLeaderId;

    /**
     * 部门性质
     */
    private String orgCharacter;

    /**
     * 父机构ID
     */
    private Long parentId;

    /**
     * 编制人数
     */
    private Integer authorizedStrength;

    /**
     * 在编人数
     */
    private Integer employeeNum;

    /**
     * 核心业务介绍
     */
    private String businessIntro;

    /**
     * 业务范围
     */
    private String businessScope;

    /**
     * 办公点
     */
    private String officeLocation;

    /**
     * 0:正常 1:删除
     */
    private Integer status;

    /**
     * 数据插入时间
     */
    private Date createTs;

    /**
     * 数据更新时间
     */
    private Date updateTs;

    /**
     * 组织机构ID
     * @return organization_id 组织机构ID
     */
    public Long getOrganizationId() {
        return organizationId;
    }

    /**
     * 组织机构ID
     * @param organizationId 组织机构ID
     */
    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    /**
     * 组织机构名称
     * @return name 组织机构名称
     */
    public String getName() {
        return name;
    }

    /**
     * 组织机构名称
     * @param name 组织机构名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 地域隶属
     * @return city 地域隶属
     */
    public String getCity() {
        return city;
    }

    /**
     * 地域隶属
     * @param city 地域隶属
     */
    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    /**
     * 源目录
     * @return source 源目录
     */
    public String getSource() {
        return source;
    }

    /**
     * 源目录
     * @param source 源目录
     */
    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }

    /**
     * 目录级别
     * @return level 目录级别
     */
    public Integer getLevel() {
        return level;
    }

    /**
     * 目录级别
     * @param level 目录级别
     */
    public void setLevel(Integer level) {
        this.level = level;
    }

    /**
     * 事业部
     * @return division 事业部
     */
    public String getDivision() {
        return division;
    }

    /**
     * 事业部
     * @param division 事业部
     */
    public void setDivision(String division) {
        this.division = division == null ? null : division.trim();
    }

    /**
     * 汇报源目录ID
     * @return leader_id 汇报源目录ID
     */
    public String getLeaderId() {
        return leaderId;
    }

    /**
     * 汇报源目录ID
     * @param leaderId 汇报源目录ID
     */
    public void setLeaderId(String leaderId) {
        this.leaderId = leaderId == null ? null : leaderId.trim();
    }

    /**
     * 事业部负责人ID
     * @return division_leader_id 事业部负责人ID
     */
    public String getDivisionLeaderId() {
        return divisionLeaderId;
    }

    /**
     * 事业部负责人ID
     * @param divisionLeaderId 事业部负责人ID
     */
    public void setDivisionLeaderId(String divisionLeaderId) {
        this.divisionLeaderId = divisionLeaderId == null ? null : divisionLeaderId.trim();
    }

    /**
     * 部门性质
     * @return org_character 部门性质
     */
    public String getOrgCharacter() {
        return orgCharacter;
    }

    /**
     * 部门性质
     * @param orgCharacter 部门性质
     */
    public void setOrgCharacter(String orgCharacter) {
        this.orgCharacter = orgCharacter == null ? null : orgCharacter.trim();
    }

    /**
     * 父机构ID
     * @return parent_id 父机构ID
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * 父机构ID
     * @param parentId 父机构ID
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * 编制人数
     * @return authorized_strength 编制人数
     */
    public Integer getAuthorizedStrength() {
        return authorizedStrength;
    }

    /**
     * 编制人数
     * @param authorizedStrength 编制人数
     */
    public void setAuthorizedStrength(Integer authorizedStrength) {
        this.authorizedStrength = authorizedStrength;
    }

    /**
     * 在编人数
     * @return employee_num 在编人数
     */
    public Integer getEmployeeNum() {
        return employeeNum;
    }

    /**
     * 在编人数
     * @param employeeNum 在编人数
     */
    public void setEmployeeNum(Integer employeeNum) {
        this.employeeNum = employeeNum;
    }

    /**
     * 核心业务介绍
     * @return business_intro 核心业务介绍
     */
    public String getBusinessIntro() {
        return businessIntro;
    }

    /**
     * 核心业务介绍
     * @param businessIntro 核心业务介绍
     */
    public void setBusinessIntro(String businessIntro) {
        this.businessIntro = businessIntro == null ? null : businessIntro.trim();
    }

    /**
     * 业务范围
     * @return business_scope 业务范围
     */
    public String getBusinessScope() {
        return businessScope;
    }

    /**
     * 业务范围
     * @param businessScope 业务范围
     */
    public void setBusinessScope(String businessScope) {
        this.businessScope = businessScope == null ? null : businessScope.trim();
    }

    /**
     * 办公点
     * @return office_location 办公点
     */
    public String getOfficeLocation() {
        return officeLocation;
    }

    /**
     * 办公点
     * @param officeLocation 办公点
     */
    public void setOfficeLocation(String officeLocation) {
        this.officeLocation = officeLocation == null ? null : officeLocation.trim();
    }

    /**
     * 0:正常 1:删除
     * @return status 0:正常 1:删除
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 0:正常 1:删除
     * @param status 0:正常 1:删除
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 数据插入时间
     * @return create_ts 数据插入时间
     */
    public Date getCreateTs() {
        return createTs;
    }

    /**
     * 数据插入时间
     * @param createTs 数据插入时间
     */
    public void setCreateTs(Date createTs) {
        this.createTs = createTs;
    }

    /**
     * 数据更新时间
     * @return update_ts 数据更新时间
     */
    public Date getUpdateTs() {
        return updateTs;
    }

    /**
     * 数据更新时间
     * @param updateTs 数据更新时间
     */
    public void setUpdateTs(Date updateTs) {
        this.updateTs = updateTs;
    }
}