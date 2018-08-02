package com.ybwh.springboot1;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity 
@Table(name = "t_employee_organization_data_auth", uniqueConstraints = { @UniqueConstraint(columnNames = { "id" }) })
public class EmployeeOrganizationDataAuth {
    /**
     * 主键
     */
	@Id
	@SequenceGenerator(name = "t_employee_organization_data_auth")
	@Column(name = "id")
    private Long id;

    /**
     * 员工ID
     */
	@Column(name = "employee_id")
    private Long employeeId;

    /**
     * 组织ID，仅代表组织的直属人员
     */
	@Column(name = "organization_id")
    private Long organizationId;

    /**
     * 是否已删除,1-是,0-否
     */
	@Column(name = "is_del")
    private Integer isDel;

    /**
     * 数据创建人ID
     */
	@Column(name = "create_id")
    private Long createId;

    /**
     * 数据更新人ID
     */
	@Column(name = "update_id")
    private Long updateId;

    /**
     * 数据创建时间
     */
	@Column(name = "create_ts")
    private Date createTs;

    /**
     * 数据更新时间
     */
	@Column(name = "update_ts")
    private Date updateTs;

    /**
     * 主键
     * @return id 主键
     */
    public Long getId() {
        return id;
    }

    /**
     * 主键
     * @param id 主键
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 员工ID
     * @return employee_id 员工ID
     */
    public Long getEmployeeId() {
        return employeeId;
    }

    /**
     * 员工ID
     * @param employeeId 员工ID
     */
    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * 组织ID，仅代表组织的直属人员
     * @return organization_id 组织ID，仅代表组织的直属人员
     */
    public Long getOrganizationId() {
        return organizationId;
    }

    /**
     * 组织ID，仅代表组织的直属人员
     * @param organizationId 组织ID，仅代表组织的直属人员
     */
    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    /**
     * 数据创建人ID
     * @return create_id 数据创建人ID
     */
    public Long getCreateId() {
        return createId;
    }

    /**
     * 数据创建人ID
     * @param createId 数据创建人ID
     */
    public void setCreateId(Long createId) {
        this.createId = createId;
    }

    /**
     * 数据更新人ID
     * @return update_id 数据更新人ID
     */
    public Long getUpdateId() {
        return updateId;
    }

    /**
     * 数据更新人ID
     * @param updateId 数据更新人ID
     */
    public void setUpdateId(Long updateId) {
        this.updateId = updateId;
    }

    /**
     * 数据创建时间
     * @return create_ts 数据创建时间
     */
    public Date getCreateTs() {
        return createTs;
    }

    /**
     * 数据创建时间
     * @param createTs 数据创建时间
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

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    @Override
    public String toString() {
        return "EmployeeOrganizationDataAuth{" +
                "id=" + id +
                ", employeeId=" + employeeId +
                ", organizationId=" + organizationId +
                ", isDel=" + isDel +
                ", createId=" + createId +
                ", updateId=" + updateId +
                ", createTs=" + createTs +
                ", updateTs=" + updateTs +
                '}';
    }
}