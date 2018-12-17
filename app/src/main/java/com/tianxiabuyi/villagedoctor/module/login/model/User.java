package com.tianxiabuyi.villagedoctor.module.login.model;

import com.tianxiabuyi.txutils.network.model.TxUser;
import com.tianxiabuyi.villagedoctor.module.team.model.TeamMemberBean;

import java.util.List;

/**
 * @author wangyd
 * @date 2018/5/9
 * @description 用户信息 （父类参数暂未用到）
 */
public class User extends TxUser {

    // 院长
    public static final int TYPE_PRESIDENT = 1;
    // 1男 2女
    public static final int GENDER_MAN = 1;
    public static final int GENDER_WOMAN = 2;

    /**
     * county : 140221000000
     * id : 805
     * userName : 18344684233
     * name : 王耀东
     * avatar : http://thirdqq.qlogo.cn/qqapp/1106305715/D90948BFCE8E23182396C1809CEED725/100
     * gender : 1
     * birthday :
     * phone : 18344684233
     * tel : 6666555
     * orgName : 阳高县人民医院
     * deptId : 393
     * deptName : 测试科室
     * title : 主任医师
     * identity : 扶贫成员
     * city :
     * address :
     * type : 1
     * content :
     * position :
     * isAdmin : null
     * password :
     * repassword : null
     * wechatUnionId :
     * qqUnionId : D90948BFCE8E23182396C1809CEED725
     * list : []
     * json : {"ip":" ","login_time":"2018-06-29","token":"1251ba498958a8483ac7ca11d4ce2f23"}
     * <p>
     * signature : null
     * strTime : null
     * createTime : null
     * orderColumn : create_time
     * category : null
     * status : 1
     * version :
     */
    private int isPresident;    // 是否为院长  1院长
    private int id;
    private String userName;
    private String name;
    private String avatar;
    private int gender;
    private String phone;
    private String birthday;
    private String city;
    private String county;
    private String address;
    private String orgName;
    private int deptId;
    private String deptName;
    private String title;
    private String identity;
    private String type;
    private String tel;
    private String content;
    private String position;
    private String password;
    private String repassword;
    private String qqUnionId;
    private String wechatUnionId;
    private String json;
    private String isAdmin;
    private String createTime;
    private String strTime;
    private String orderColumn;
    private String category;
    private String signature;
    private String version;
    private int status;
    // 团队信息
    private List<TeamMemberBean> list;

    public int getIsPresident() {
        return isPresident;
    }

    public void setIsPresident(int isPresident) {
        this.isPresident = isPresident;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public int getDeptId() {
        return deptId;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepassword() {
        return repassword;
    }

    public void setRepassword(String repassword) {
        this.repassword = repassword;
    }

    public String getQqUnionId() {
        return qqUnionId;
    }

    public void setQqUnionId(String qqUnionId) {
        this.qqUnionId = qqUnionId;
    }

    public String getWechatUnionId() {
        return wechatUnionId;
    }

    public void setWechatUnionId(String wechatUnionId) {
        this.wechatUnionId = wechatUnionId;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public String getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(String isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getStrTime() {
        return strTime;
    }

    public void setStrTime(String strTime) {
        this.strTime = strTime;
    }

    public String getOrderColumn() {
        return orderColumn;
    }

    public void setOrderColumn(String orderColumn) {
        this.orderColumn = orderColumn;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<TeamMemberBean> getList() {
        return list;
    }

    public void setList(List<TeamMemberBean> list) {
        this.list = list;
    }
}
