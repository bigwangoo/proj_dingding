package com.wangyd.dingding.module.main.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.tianxiabuyi.txutils.db.annotation.Column;
import com.tianxiabuyi.txutils.db.annotation.Table;
import com.tianxiabuyi.villagedoctor.common.db.util.DbUtils;
import com.wangyd.dingding.module.main.adapter.VillageAdapter;

import java.util.List;

/**
 * @author wangyd
 * @date 2018/6/19
 * @description 村庄
 */
@Table(name = DbUtils.TABLE_VILLAGE)
public class VillageBean implements Parcelable, MultiItemEntity {

    // 唯一ID
    @Column(name = "_id", isId = true, autoGen = true)
    private long _id;

    @Column(name = "id")
    private int id;
    @Column(name = "code")
    private String code;
    @Column(name = "name")
    private String name;
    @Column(name = "parentCode")
    private String parentCode;
    @Column(name = "parentName")
    private String parentName;
    @Column(name = "residentCount")
    private String residentCount;           // 居民人数
    @Column(name = "count")
    private String count;                   // 签约人数
    @Column(name = "isSignIn")
    private int isSignIn;                   // 0 未签到 1 已签到
    // 居民列表
    private List<VillagerContractBean> residentList;

    public VillageBean() {
        //db
    }

    protected VillageBean(Parcel in) {
        _id = in.readLong();
        id = in.readInt();
        code = in.readString();
        name = in.readString();
        parentCode = in.readString();
        parentName = in.readString();
        residentCount = in.readString();
        count = in.readString();
        isSignIn = in.readInt();
        residentList = in.createTypedArrayList(VillagerContractBean.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(_id);
        dest.writeInt(id);
        dest.writeString(code);
        dest.writeString(name);
        dest.writeString(parentCode);
        dest.writeString(parentName);
        dest.writeString(residentCount);
        dest.writeString(count);
        dest.writeInt(isSignIn);
        dest.writeTypedList(residentList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<VillageBean> CREATOR = new Creator<VillageBean>() {
        @Override
        public VillageBean createFromParcel(Parcel in) {
            return new VillageBean(in);
        }

        @Override
        public VillageBean[] newArray(int size) {
            return new VillageBean[size];
        }
    };

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getResidentCount() {
        return residentCount;
    }

    public void setResidentCount(String residentCount) {
        this.residentCount = residentCount;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public int getIsSignIn() {
        return isSignIn;
    }

    public void setIsSignIn(int isSignIn) {
        this.isSignIn = isSignIn;
    }

    public List<VillagerContractBean> getResidentList() {
        return residentList;
    }

    public void setResidentList(List<VillagerContractBean> residentList) {
        this.residentList = residentList;
    }

    /**
     * Item 类型
     */
    @Override
    public int getItemType() {
        // ExpandableItem
        return VillageAdapter.TYPE_ITEM;
    }
}
