package com.entity;

import java.util.Date;

public class SimCard {
    /**
     * 主键
     */
    private Integer id;

    /**
     * ICCID号
     */
    private String iccid;

    /**
     * 运营商(0:未知  1:联通-麦谷  2:电信-翼车云  3:移动-鎏信)
     */
    private Integer operator;

    /**
     * SIM卡号
     */
    private String sim;

    /**
     * 设备IMSI号
     */
    private String imsi;

    /**
     * 设备IMEI号
     */
    private String imei;

    /**
     * 状态（0：库存 1：可测试 2：可激活 3：已激活 4：已停用 5：已失效 6：已注销）
     */
    private Integer status;

    /**
     * 套餐编号
     */
    private String packageSn;

    /**
     * 套餐名称
     */
    private String packageName;

    /**
     * 套餐描述
     */
    private String packageInfo;

    /**
     * 套餐周期（单位：天）
     */
    private Integer packagePeriod;

    /**
     * 当前周期内总量（单位：kb）
     */
    private Double amountUsage;

    /**
     * 当月用量（单位：kb）
     */
    private Double monthUsage;

    /**
     * 当前周期剩余用量（单位：kb）
     */
    private Double surplusUsage;

    /**
     * 卡剩余服务周期（单位：天）
     */
    private Integer surplusPeriod;

    /**
     * 卡到期日期
     */
    private Date expireDate;

    /**
     * 卡首次激活日期
     */
    private Date firstActive;

    /**
     * 卡最近激活日期
     */
    private Date lastActive;

    /**
     * 当月流量是否清零（1：是 0：否）
     */
    private Integer isReset;

    /**
     * 实名认证状态（0：未实名 1：已实名）
     */
    private Integer realNameStatus;

    /**
     * 更新时间
     */
    private Date freshDate;

    /**
     * 是否已删除（1:是，0:否）
     */
    private Integer isDel;

    /**
     * 数据创建时间
     */
    private Date createTime;

    /**
     * 数据创建人ID
     */
    private Integer createId;

    /**
     * 数据更新时间
     */
    private Date updateTime;

    /**
     * 数据更新人ID
     */
    private Integer updateId;

    /**
     * 主键
     * @return id 主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * 主键
     * @param id 主键
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * ICCID号
     * @return iccid ICCID号
     */
    public String getIccid() {
        return iccid;
    }

    /**
     * ICCID号
     * @param iccid ICCID号
     */
    public void setIccid(String iccid) {
        this.iccid = iccid == null ? null : iccid.trim();
    }

    /**
     * 运营商(0:未知  1:联通-麦谷  2:电信-翼车云  3:移动-鎏信)
     * @return operator 运营商(0:未知  1:联通-麦谷  2:电信-翼车云  3:移动-鎏信)
     */
    public Integer getOperator() {
        return operator;
    }

    /**
     * 运营商(0:未知  1:联通-麦谷  2:电信-翼车云  3:移动-鎏信)
     * @param operator 运营商(0:未知  1:联通-麦谷  2:电信-翼车云  3:移动-鎏信)
     */
    public void setOperator(Integer operator) {
        this.operator = operator;
    }

    /**
     * SIM卡号
     * @return sim SIM卡号
     */
    public String getSim() {
        return sim;
    }

    /**
     * SIM卡号
     * @param sim SIM卡号
     */
    public void setSim(String sim) {
        this.sim = sim == null ? null : sim.trim();
    }

    /**
     * 设备IMSI号
     * @return imsi 设备IMSI号
     */
    public String getImsi() {
        return imsi;
    }

    /**
     * 设备IMSI号
     * @param imsi 设备IMSI号
     */
    public void setImsi(String imsi) {
        this.imsi = imsi == null ? null : imsi.trim();
    }

    /**
     * 设备IMEI号
     * @return imei 设备IMEI号
     */
    public String getImei() {
        return imei;
    }

    /**
     * 设备IMEI号
     * @param imei 设备IMEI号
     */
    public void setImei(String imei) {
        this.imei = imei == null ? null : imei.trim();
    }

    /**
     * 状态（0：库存 1：可测试 2：可激活 3：已激活 4：已停用 5：已失效 6：已注销）
     * @return status 状态（0：库存 1：可测试 2：可激活 3：已激活 4：已停用 5：已失效 6：已注销）
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 状态（0：库存 1：可测试 2：可激活 3：已激活 4：已停用 5：已失效 6：已注销）
     * @param status 状态（0：库存 1：可测试 2：可激活 3：已激活 4：已停用 5：已失效 6：已注销）
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 套餐编号
     * @return package_sn 套餐编号
     */
    public String getPackageSn() {
        return packageSn;
    }

    /**
     * 套餐编号
     * @param packageSn 套餐编号
     */
    public void setPackageSn(String packageSn) {
        this.packageSn = packageSn == null ? null : packageSn.trim();
    }

    /**
     * 套餐名称
     * @return package_name 套餐名称
     */
    public String getPackageName() {
        return packageName;
    }

    /**
     * 套餐名称
     * @param packageName 套餐名称
     */
    public void setPackageName(String packageName) {
        this.packageName = packageName == null ? null : packageName.trim();
    }

    /**
     * 套餐描述
     * @return package_info 套餐描述
     */
    public String getPackageInfo() {
        return packageInfo;
    }

    /**
     * 套餐描述
     * @param packageInfo 套餐描述
     */
    public void setPackageInfo(String packageInfo) {
        this.packageInfo = packageInfo == null ? null : packageInfo.trim();
    }

    /**
     * 套餐周期（单位：天）
     * @return package_period 套餐周期（单位：天）
     */
    public Integer getPackagePeriod() {
        return packagePeriod;
    }

    /**
     * 套餐周期（单位：天）
     * @param packagePeriod 套餐周期（单位：天）
     */
    public void setPackagePeriod(Integer packagePeriod) {
        this.packagePeriod = packagePeriod;
    }

    /**
     * 当前周期内总量（单位：kb）
     * @return amount_usage 当前周期内总量（单位：kb）
     */
    public Double getAmountUsage() {
        return amountUsage;
    }

    /**
     * 当前周期内总量（单位：kb）
     * @param amountUsage 当前周期内总量（单位：kb）
     */
    public void setAmountUsage(Double amountUsage) {
        this.amountUsage = amountUsage;
    }

    /**
     * 当月用量（单位：kb）
     * @return month_usage 当月用量（单位：kb）
     */
    public Double getMonthUsage() {
        return monthUsage;
    }

    /**
     * 当月用量（单位：kb）
     * @param monthUsage 当月用量（单位：kb）
     */
    public void setMonthUsage(Double monthUsage) {
        this.monthUsage = monthUsage;
    }

    /**
     * 当前周期剩余用量（单位：kb）
     * @return surplus_usage 当前周期剩余用量（单位：kb）
     */
    public Double getSurplusUsage() {
        return surplusUsage;
    }

    /**
     * 当前周期剩余用量（单位：kb）
     * @param surplusUsage 当前周期剩余用量（单位：kb）
     */
    public void setSurplusUsage(Double surplusUsage) {
        this.surplusUsage = surplusUsage;
    }

    /**
     * 卡剩余服务周期（单位：天）
     * @return surplus_period 卡剩余服务周期（单位：天）
     */
    public Integer getSurplusPeriod() {
        return surplusPeriod;
    }

    /**
     * 卡剩余服务周期（单位：天）
     * @param surplusPeriod 卡剩余服务周期（单位：天）
     */
    public void setSurplusPeriod(Integer surplusPeriod) {
        this.surplusPeriod = surplusPeriod;
    }

    /**
     * 卡到期日期
     * @return expire_date 卡到期日期
     */
    public Date getExpireDate() {
        return expireDate;
    }

    /**
     * 卡到期日期
     * @param expireDate 卡到期日期
     */
    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    /**
     * 卡首次激活日期
     * @return first_active 卡首次激活日期
     */
    public Date getFirstActive() {
        return firstActive;
    }

    /**
     * 卡首次激活日期
     * @param firstActive 卡首次激活日期
     */
    public void setFirstActive(Date firstActive) {
        this.firstActive = firstActive;
    }

    /**
     * 卡最近激活日期
     * @return last_active 卡最近激活日期
     */
    public Date getLastActive() {
        return lastActive;
    }

    /**
     * 卡最近激活日期
     * @param lastActive 卡最近激活日期
     */
    public void setLastActive(Date lastActive) {
        this.lastActive = lastActive;
    }

    /**
     * 当月流量是否清零（1：是 0：否）
     * @return is_reset 当月流量是否清零（1：是 0：否）
     */
    public Integer getIsReset() {
        return isReset;
    }

    /**
     * 当月流量是否清零（1：是 0：否）
     * @param isReset 当月流量是否清零（1：是 0：否）
     */
    public void setIsReset(Integer isReset) {
        this.isReset = isReset;
    }

    /**
     * 实名认证状态（0：未实名 1：已实名）
     * @return real_name_status 实名认证状态（0：未实名 1：已实名）
     */
    public Integer getRealNameStatus() {
        return realNameStatus;
    }

    /**
     * 实名认证状态（0：未实名 1：已实名）
     * @param realNameStatus 实名认证状态（0：未实名 1：已实名）
     */
    public void setRealNameStatus(Integer realNameStatus) {
        this.realNameStatus = realNameStatus;
    }

    /**
     * 更新时间
     * @return fresh_date 更新时间
     */
    public Date getFreshDate() {
        return freshDate;
    }

    /**
     * 更新时间
     * @param freshDate 更新时间
     */
    public void setFreshDate(Date freshDate) {
        this.freshDate = freshDate;
    }

    /**
     * 是否已删除（1:是，0:否）
     * @return is_del 是否已删除（1:是，0:否）
     */
    public Integer getIsDel() {
        return isDel;
    }

    /**
     * 是否已删除（1:是，0:否）
     * @param isDel 是否已删除（1:是，0:否）
     */
    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    /**
     * 数据创建时间
     * @return create_time 数据创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 数据创建时间
     * @param createTime 数据创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 数据创建人ID
     * @return create_id 数据创建人ID
     */
    public Integer getCreateId() {
        return createId;
    }

    /**
     * 数据创建人ID
     * @param createId 数据创建人ID
     */
    public void setCreateId(Integer createId) {
        this.createId = createId;
    }

    /**
     * 数据更新时间
     * @return update_time 数据更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 数据更新时间
     * @param updateTime 数据更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 数据更新人ID
     * @return update_id 数据更新人ID
     */
    public Integer getUpdateId() {
        return updateId;
    }

    /**
     * 数据更新人ID
     * @param updateId 数据更新人ID
     */
    public void setUpdateId(Integer updateId) {
        this.updateId = updateId;
    }
}