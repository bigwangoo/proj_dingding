package com.wangyd.dingding.module.main.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.tianxiabuyi.txutils.db.annotation.Column;
import com.tianxiabuyi.txutils.db.annotation.Table;
import com.tianxiabuyi.villagedoctor.common.db.util.DbUtils;
import com.wangyd.dingding.module.main.adapter.VillageAdapter;

import java.util.List;

/**
 * @author wangyd
 * @date 2018/6/20
 * @description 乡镇
 */
@Table(name = DbUtils.TABLE_TOWN)
public class TownBean extends AbstractExpandableItem<VillageBean> implements Parcelable, MultiItemEntity {

    // 唯一ID
    @Column(name = "_id", isId = true, autoGen = true)
    private long _id;

    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "code")
    private String code;
    @Column(name = "parentCode")
    private String parentCode;
    @Column(name = "residentPopulation")
    private String residentPopulation;
    @Column(name = "householdPopulation")
    private String householdPopulation;
    // 村庄列表
    private List<VillageBean> list;

    public TownBean() {
        //db
    }

    protected TownBean(Parcel in) {
        _id = in.readLong();
        id = in.readInt();
        name = in.readString();
        code = in.readString();
        parentCode = in.readString();
        residentPopulation = in.readString();
        householdPopulation = in.readString();
        list = in.createTypedArrayList(VillageBean.CREATOR);
    }

    public static final Creator<TownBean> CREATOR = new Creator<TownBean>() {
        @Override
        public TownBean createFromParcel(Parcel in) {
            return new TownBean(in);
        }

        @Override
        public TownBean[] newArray(int size) {
            return new TownBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(_id);
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(code);
        dest.writeString(parentCode);
        dest.writeString(residentPopulation);
        dest.writeString(householdPopulation);
        dest.writeTypedList(list);
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getResidentPopulation() {
        return residentPopulation;
    }

    public void setResidentPopulation(String residentPopulation) {
        this.residentPopulation = residentPopulation;
    }

    public String getHouseholdPopulation() {
        return householdPopulation;
    }

    public void setHouseholdPopulation(String householdPopulation) {
        this.householdPopulation = householdPopulation;
    }

    public List<VillageBean> getList() {
        return list;
    }

    public void setList(List<VillageBean> list) {
        this.list = list;
    }

    /**
     * ExpandableItem 层级
     */
    @Override
    public int getLevel() {
        return 0;
    }

    /**
     * Item 类型
     */
    @Override
    public int getItemType() {
        return VillageAdapter.TYPE_LEVEL_0;
    }
}
