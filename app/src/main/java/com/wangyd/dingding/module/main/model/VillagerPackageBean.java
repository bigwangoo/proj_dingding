package com.wangyd.dingding.module.main.model;

import com.tianxiabuyi.txutils.network.model.BaseBean;

/**
 * @author wangyd
 * @date 2018/6/8
 * @description 村民签约服务包
 */
public class VillagerPackageBean extends BaseBean {

    private int id;
    private String parentId;
    private String name;
    private String type;
    private String medicalProjectCode;
    private String medicalProjectName;
    private String suitableUser;
    private String feeScale;
    private String normalFeeScale;
    private String packageFeeScale;
    private String averageTime;
    private String chargeUnit;
    private String money;
    private String serviceLife;
    private String executable;
    private String templates;
    private String templateId;
    private String templateName;
    private String remark;
    private String level;
    private String count;           // 已随访次数
    private String startTime;       // 开始时间
    private String endTime;         // 结束时间

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMedicalProjectCode() {
        return medicalProjectCode;
    }

    public void setMedicalProjectCode(String medicalProjectCode) {
        this.medicalProjectCode = medicalProjectCode;
    }

    public String getMedicalProjectName() {
        return medicalProjectName;
    }

    public void setMedicalProjectName(String medicalProjectName) {
        this.medicalProjectName = medicalProjectName;
    }

    public String getSuitableUser() {
        return suitableUser;
    }

    public void setSuitableUser(String suitableUser) {
        this.suitableUser = suitableUser;
    }

    public String getFeeScale() {
        return feeScale;
    }

    public void setFeeScale(String feeScale) {
        this.feeScale = feeScale;
    }

    public String getNormalFeeScale() {
        return normalFeeScale;
    }

    public void setNormalFeeScale(String normalFeeScale) {
        this.normalFeeScale = normalFeeScale;
    }

    public String getPackageFeeScale() {
        return packageFeeScale;
    }

    public void setPackageFeeScale(String packageFeeScale) {
        this.packageFeeScale = packageFeeScale;
    }

    public String getAverageTime() {
        return averageTime;
    }

    public void setAverageTime(String averageTime) {
        this.averageTime = averageTime;
    }

    public String getChargeUnit() {
        return chargeUnit;
    }

    public void setChargeUnit(String chargeUnit) {
        this.chargeUnit = chargeUnit;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getServiceLife() {
        return serviceLife;
    }

    public void setServiceLife(String serviceLife) {
        this.serviceLife = serviceLife;
    }

    public String getExecutable() {
        return executable;
    }

    public void setExecutable(String executable) {
        this.executable = executable;
    }

    public String getTemplates() {
        return templates;
    }

    public void setTemplates(String templates) {
        this.templates = templates;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
