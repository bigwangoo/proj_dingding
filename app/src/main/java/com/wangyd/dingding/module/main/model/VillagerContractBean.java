package com.wangyd.dingding.module.main.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.tianxiabuyi.txutils.db.annotation.Column;
import com.tianxiabuyi.txutils.db.annotation.Table;
import com.tianxiabuyi.villagedoctor.common.db.util.DbUtils;
import com.wangyd.dingding.core.utils.BitmapUtils;
import com.tianxiabuyi.villagedoctor.module.label.model.LabelSubBean;

import java.util.List;

/**
 * @author wangyd
 * @date 2018/6/6
 * @description 签约村民
 */
@Table(name = DbUtils.TABLE_CONTRACT)
public class VillagerContractBean implements Parcelable {

    /*获取首字符*/
    public String getFirstChar() {
        if (firstChar == null) {
            firstChar = BitmapUtils.getFirstChar(name);
        }
        return firstChar;
    }

    // 唯一ID
    @Column(name = "_id", isId = true, autoGen = true)
    private long _id;

    @Column(name = "code")
    private String code;                    //所属地区编码
    @Column(name = "id")
    private int id;                         //签约id
    @Column(name = "number")
    private String number;                  //签约编号
    @Column(name = "residentId")
    private String residentId;              //居民档案id
    @Column(name = "residentNumber")
    private String residentNumber;          //居民档案编号
    @Column(name = "name")
    private String name;                    //签约人姓名
    @Column(name = "initial")
    private String initial;                 //签约人姓名首字母
    @Column(name = "firstChar")
    private String firstChar;               //签约人姓名首字符，头像用
    @Column(name = "age")
    private String age;                     //年龄
    @Column(name = "gender")
    private int gender;                     //性别 1男 2女
    @Column(name = "labelIdStr")
    private String labelIdStr;              //标签id
    @Column(name = "labelNameStr")
    private String labelNameStr;            //标签name

    private int householder;                //是否户主
    private String avatar;                  //个人头像
    private String birthday;                //出生日期
    private String phone;                   //联系方式
    private String address;                 //地址
    private String cardNum;                 //身份证号
    private String situation;               //基本情况
    private String tab;                     //标签
    private String familyMember;            //家庭成员
    private String operationType;           //操作类型
    private String focusGroupsType;         //重点人群类型
    private String registerFamilyType;      //签约家庭类型
    private String parentId;                //服务包父节点
    private int yearCount;                  //本年体检次数
    private int allCount;                   //体检总次数
    private String spId;                    //服务包id
    private String spIdStr;                 //服务包id
    private String spName;                  //服务包名称
    private String servicePeriod;           //服务年限
    private int teamId;                     //所属团队id
    private String teamName;                //团队名称
    private String contractTime;            //签约时间
    private List<VillagerPackageBean> list; //服务包列表
    private List<LabelSubBean> list2;       //标签列表

    public VillagerContractBean() {
        //db
    }

    protected VillagerContractBean(Parcel in) {
        _id = in.readLong();
        code = in.readString();
        id = in.readInt();
        number = in.readString();
        residentId = in.readString();
        residentNumber = in.readString();
        name = in.readString();
        initial = in.readString();
        firstChar = in.readString();
        age = in.readString();
        gender = in.readInt();
        labelIdStr = in.readString();
        labelNameStr = in.readString();
        householder = in.readInt();
        avatar = in.readString();
        birthday = in.readString();
        phone = in.readString();
        address = in.readString();
        cardNum = in.readString();
        situation = in.readString();
        tab = in.readString();
        familyMember = in.readString();
        operationType = in.readString();
        focusGroupsType = in.readString();
        registerFamilyType = in.readString();
        parentId = in.readString();
        yearCount = in.readInt();
        allCount = in.readInt();
        spId = in.readString();
        spIdStr = in.readString();
        spName = in.readString();
        servicePeriod = in.readString();
        teamId = in.readInt();
        teamName = in.readString();
        contractTime = in.readString();
        list2 = in.createTypedArrayList(LabelSubBean.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(_id);
        dest.writeString(code);
        dest.writeInt(id);
        dest.writeString(number);
        dest.writeString(residentId);
        dest.writeString(residentNumber);
        dest.writeString(name);
        dest.writeString(initial);
        dest.writeString(firstChar);
        dest.writeString(age);
        dest.writeInt(gender);
        dest.writeString(labelIdStr);
        dest.writeString(labelNameStr);
        dest.writeInt(householder);
        dest.writeString(avatar);
        dest.writeString(birthday);
        dest.writeString(phone);
        dest.writeString(address);
        dest.writeString(cardNum);
        dest.writeString(situation);
        dest.writeString(tab);
        dest.writeString(familyMember);
        dest.writeString(operationType);
        dest.writeString(focusGroupsType);
        dest.writeString(registerFamilyType);
        dest.writeString(parentId);
        dest.writeInt(yearCount);
        dest.writeInt(allCount);
        dest.writeString(spId);
        dest.writeString(spIdStr);
        dest.writeString(spName);
        dest.writeString(servicePeriod);
        dest.writeInt(teamId);
        dest.writeString(teamName);
        dest.writeString(contractTime);
        dest.writeTypedList(list2);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<VillagerContractBean> CREATOR = new Creator<VillagerContractBean>() {
        @Override
        public VillagerContractBean createFromParcel(Parcel in) {
            return new VillagerContractBean(in);
        }

        @Override
        public VillagerContractBean[] newArray(int size) {
            return new VillagerContractBean[size];
        }
    };

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getResidentId() {
        return residentId;
    }

    public void setResidentId(String residentId) {
        this.residentId = residentId;
    }

    public String getResidentNumber() {
        return residentNumber;
    }

    public void setResidentNumber(String residentNumber) {
        this.residentNumber = residentNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInitial() {
        return initial;
    }

    public void setInitial(String initial) {
        this.initial = initial;
    }

    public void setFirstChar(String firstChar) {
        this.firstChar = firstChar;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getLabelIdStr() {
        return labelIdStr;
    }

    public void setLabelIdStr(String labelIdStr) {
        this.labelIdStr = labelIdStr;
    }

    public String getLabelNameStr() {
        return labelNameStr;
    }

    public void setLabelNameStr(String labelNameStr) {
        this.labelNameStr = labelNameStr;
    }

    public int getHouseholder() {
        return householder;
    }

    public void setHouseholder(int householder) {
        this.householder = householder;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public String getSituation() {
        return situation;
    }

    public void setSituation(String situation) {
        this.situation = situation;
    }

    public String getTab() {
        return tab;
    }

    public void setTab(String tab) {
        this.tab = tab;
    }

    public String getFamilyMember() {
        return familyMember;
    }

    public void setFamilyMember(String familyMember) {
        this.familyMember = familyMember;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public String getFocusGroupsType() {
        return focusGroupsType;
    }

    public void setFocusGroupsType(String focusGroupsType) {
        this.focusGroupsType = focusGroupsType;
    }

    public String getRegisterFamilyType() {
        return registerFamilyType;
    }

    public void setRegisterFamilyType(String registerFamilyType) {
        this.registerFamilyType = registerFamilyType;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public int getYearCount() {
        return yearCount;
    }

    public void setYearCount(int yearCount) {
        this.yearCount = yearCount;
    }

    public int getAllCount() {
        return allCount;
    }

    public void setAllCount(int allCount) {
        this.allCount = allCount;
    }

    public String getSpId() {
        return spId;
    }

    public void setSpId(String spId) {
        this.spId = spId;
    }

    public String getSpIdStr() {
        return spIdStr;
    }

    public void setSpIdStr(String spIdStr) {
        this.spIdStr = spIdStr;
    }

    public String getSpName() {
        return spName;
    }

    public void setSpName(String spName) {
        this.spName = spName;
    }

    public String getServicePeriod() {
        return servicePeriod;
    }

    public void setServicePeriod(String servicePeriod) {
        this.servicePeriod = servicePeriod;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getContractTime() {
        return contractTime;
    }

    public void setContractTime(String contractTime) {
        this.contractTime = contractTime;
    }

    public List<VillagerPackageBean> getList() {
        return list;
    }

    public void setList(List<VillagerPackageBean> list) {
        this.list = list;
    }

    public List<LabelSubBean> getList2() {
        return list2;
    }

    public void setList2(List<LabelSubBean> list2) {
        this.list2 = list2;
    }
}
