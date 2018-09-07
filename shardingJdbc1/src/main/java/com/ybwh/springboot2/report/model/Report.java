package com.ybwh.springboot2.report.model;

import java.math.BigDecimal;
import java.util.Date;

public class Report {
    /**
     * 主键
     */
    private Long id;

    /**
     * 总销量
     */
    private Integer saleCount;

    /**
     * 销售额
     */
    private BigDecimal saleAmount;

    /**
     * 创建时间
     */
    private Date createTime;

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
     * 总销量
     * @return sale_count 总销量
     */
    public Integer getSaleCount() {
        return saleCount;
    }

    /**
     * 总销量
     * @param saleCount 总销量
     */
    public void setSaleCount(Integer saleCount) {
        this.saleCount = saleCount;
    }

    /**
     * 销售额
     * @return sale_amount 销售额
     */
    public BigDecimal getSaleAmount() {
        return saleAmount;
    }

    /**
     * 销售额
     * @param saleAmount 销售额
     */
    public void setSaleAmount(BigDecimal saleAmount) {
        this.saleAmount = saleAmount;
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
}