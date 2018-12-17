package com.tianxiabuyi.txutils.network.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author xjh1994
 * @date 2016/8/31
 * @description 软件更新
 */
public class TxUpdateResult extends HttpResult {

    private AppBean app;

    public AppBean getApp() {
        return app;
    }

    public void setApp(AppBean app) {
        this.app = app;
    }

    public static class AppBean implements Parcelable {

        /**
         * hospital : 1044
         * app_type : hospital
         * app_name : 家庭医生端
         * desc : 产品优化
         * version_code : 2
         * version : 2.0
         * url : http://file.eeesys.com/hospital/1044/platform_doctor_v0.9_txby(1).apk
         */

        private int hospital;
        private String app_type;
        private String app_name;
        private String desc;
        private int version_code;
        private String version;
        private String url;

        public AppBean() {
        }

        protected AppBean(Parcel in) {
            hospital = in.readInt();
            app_type = in.readString();
            app_name = in.readString();
            desc = in.readString();
            version_code = in.readInt();
            version = in.readString();
            url = in.readString();
        }

        public static final Creator<AppBean> CREATOR = new Creator<AppBean>() {
            @Override
            public AppBean createFromParcel(Parcel in) {
                return new AppBean(in);
            }

            @Override
            public AppBean[] newArray(int size) {
                return new AppBean[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(hospital);
            dest.writeString(app_type);
            dest.writeString(app_name);
            dest.writeString(desc);
            dest.writeInt(version_code);
            dest.writeString(version);
            dest.writeString(url);
        }

        public int getHospital() {
            return hospital;
        }

        public void setHospital(int hospital) {
            this.hospital = hospital;
        }

        public String getApp_type() {
            return app_type;
        }

        public void setApp_type(String app_type) {
            this.app_type = app_type;
        }

        public String getApp_name() {
            return app_name;
        }

        public void setApp_name(String app_name) {
            this.app_name = app_name;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public int getVersion_code() {
            return version_code;
        }

        public void setVersion_code(int version_code) {
            this.version_code = version_code;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
