package com.ybwh.springboot1;

import javax.persistence.Column;

/**
 * 组织的简要信息
 *
 * @author: Fan Beibei
 * @date: 2018/7/11 18:37
 * @Modified By:
 */
public class OrganizationShortInfoDto {
    /**
     * 组织机构ID
     */
	@Column(name = "organization_id")
    private Long organizationId;

    /**
     * 263部门id
     */
	@Column(name = "org_id_263")
    private Integer orgId263;


    /**
     * 组织机构名称
     */
	@Column(name = "name")
    private String name;


    /**
     * 父机构ID
     */
	@Column(name = "parent_id")
    private Long parentId;

    /**
     * 目录级别
     */
	@Column(name = "level")
    private Integer level;


    /**
     * 0:正常 1:删除
     */
	@Column(name = "status")
    private Integer status;


    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public Integer getOrgId263() {
        return orgId263;
    }

    public void setOrgId263(Integer orgId263) {
        this.orgId263 = orgId263;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "OrganizationShortInfoDto{" +
                "organizationId=" + organizationId +
                ", orgId263=" + orgId263 +
                ", name='" + name + '\'' +
                ", parentId=" + parentId +
                ", level=" + level +
                ", status=" + status +
                '}';
    }
}
