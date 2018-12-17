package com.wangyd.dingding.core.utils;

import com.google.gson.reflect.TypeToken;
import com.tianxiabuyi.txutils.TxUserManager;
import com.tianxiabuyi.txutils.TxUtils;
import com.tianxiabuyi.txutils.util.GsonUtils;
import com.tianxiabuyi.txutils.util.SpUtils;
import com.tianxiabuyi.villagedoctor.module.chart.model.DeviceType;
import com.tianxiabuyi.villagedoctor.module.device.model.DeviceBean;
import com.tianxiabuyi.villagedoctor.module.login.model.User;
import com.tianxiabuyi.villagedoctor.module.search.model.SearchBean;
import com.tianxiabuyi.villagedoctor.module.team.model.TeamMemberBean;
import com.tianxiabuyi.villagedoctor.module.team.model.TeamPackageBean;
import com.tianxiabuyi.villagedoctor.module.team.model.TeamPackageLevel1;
import com.tianxiabuyi.villagedoctor.module.team.model.TeamPackageLevel2;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wangyd
 * @date 2018/5/9
 * @description 用户数据缓存
 */
public class UserSpUtils {
    // 文件名
    private static final String FILE_NAME = "tx_user";
    private static UserSpUtils instance;
    private SpUtils spUtils;

    private UserSpUtils() {
        spUtils = new SpUtils(TxUtils.getInstance().getContext(), FILE_NAME);
    }

    public static UserSpUtils getInstance() {
        if (instance == null) {
            synchronized (UserSpUtils.class) {
                if (instance == null) {
                    instance = new UserSpUtils();
                }
            }
        }
        return instance;
    }

    public void clear() {
        spUtils.clear();
    }

    /**
     * 乡镇ID
     */
    public String getCountyId() {
        User currentUser = TxUserManager.getInstance().getCurrentUser(User.class);
        return currentUser == null ? "" : currentUser.getCounty();
    }

    /**
     * 获取团队列表
     */
    public List<TeamMemberBean> getTeams() {
        User currentUser = TxUserManager.getInstance().getCurrentUser(User.class);
        if (currentUser != null) {
            List<TeamMemberBean> list = currentUser.getList();
            if (list != null) {
                return list;
            }
        }
        return new ArrayList<>();
    }

    /**
     * 是否是团队负责人
     */
    public boolean isTeamLeader() {
        List<TeamMemberBean> list = getTeams();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if (1 == list.get(i).getLeader()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获取团队ID字符串
     */
    public String getTeamIdString() {
        List<TeamMemberBean> teams = getTeams();

        StringBuilder sb = new StringBuilder();
        int size = teams.size();
        for (int i = 0; i < size; i++) {
            if (i == size - 1) {
                sb.append(teams.get(i).getTeamId());
            } else {
                sb.append(teams.get(i).getTeamId()).append(",");
            }
        }
        return sb.toString();
    }

    /**
     * 获取团队名称字符串
     */
    public String getTeamNameString() {
        List<TeamMemberBean> teams = getTeams();

        StringBuilder sb = new StringBuilder();
        int size = teams.size();
        for (int i = 0; i < size; i++) {
            if (i == size - 1) {
                sb.append(teams.get(i).getTeamName());
            } else {
                sb.append(teams.get(i).getTeamName()).append("、");
            }
        }
        return sb.toString();
    }

    /**
     * 保存团队辖区
     */
    public void setTeamArea(List<SearchBean> data) {
        spUtils.put("team_area", GsonUtils.toJson(data));
    }

    /**
     * 获取团队辖区列表
     */
    public List<SearchBean> getTeamArea() {
        String teamArea = (String) spUtils.get("team_area", "");
        List<SearchBean> list = GsonUtils.fromJson(teamArea, new TypeToken<List<SearchBean>>() {
        });

        return list == null ? new ArrayList<>() : list;
    }

    /**
     * 获取团队辖区名称，顿号隔开
     */
    public String getTeamAreaName() {
        List<SearchBean> data = getTeamArea();

        StringBuilder sb = new StringBuilder();
        int size = data.size();
        for (int i = 0; i < size; i++) {
            if (i == size - 1) {
                sb.append(data.get(i).getName());
            } else {
                sb.append(data.get(i).getName()).append("、");
            }
        }
        return sb.toString();
    }

    /**
     * 随访类型
     */
    public void setFollowupType(String type) {
        spUtils.put("followup_type", type);
    }

    public String getFollowupType() {
        return (String) spUtils.get("followup_type", "");
    }


    //////////////////////////////////// 服务包 ////////////////////////////////////////////////////

    /**
     * 保存选中的可执行项目
     */
    public void saveServiceChecked(List<TeamPackageLevel1> data) {
        spUtils.put("team_service_checked", GsonUtils.toJson(data));
    }

    public ArrayList<TeamPackageLevel1> getServiceChecked() {
        String json = (String) spUtils.get("team_service_checked", "");
        return GsonUtils.fromJson(json, new TypeToken<ArrayList<TeamPackageLevel1>>() {
        });
    }

    /**
     * 保存可执行项目
     */
    public void saveServiceExecutable(List<TeamPackageBean> data) {
        spUtils.put("team_service_executable", GsonUtils.toJson(data));

        if (data != null) {
            Map<String, String> templateMap = new HashMap<>(16);
            for (TeamPackageBean packageBean : data) {
                List<TeamPackageLevel1> list = packageBean.getList();
                for (int i = 0; list != null && i < list.size(); i++) {
                    List<TeamPackageLevel2> list2 = list.get(i).getList();
                    for (int j = 0; list2 != null && j < list2.size(); j++) {
                        TeamPackageLevel2 level2 = list2.get(j);
                        templateMap.put(level2.getTemplateName(), String.valueOf(level2.getTemplateId()));
                    }
                }
            }
            // 保存服务包模板
            saveFollowUpTemplate(templateMap);
        }
    }

    public ArrayList<TeamPackageBean> getServiceExecutable() {
        String json = (String) spUtils.get("team_service_executable", "");
        return GsonUtils.fromJson(json, new TypeToken<ArrayList<TeamPackageBean>>() {
        });
    }

    /**
     * 保存随访模板数据
     */
    public void saveFollowUpTemplate(Map<String, String> data) {
        spUtils.put("followup_template", GsonUtils.toJson(data));
    }

    public Map<String, String> getFollowUpTemplate() {
        String templateStr = (String) spUtils.get("followup_template", "");
        Map<String, String> data = GsonUtils.fromJson(templateStr, new TypeToken<Map<String, String>>() {
        });
        return data == null ? new HashMap<>(0) : data;
    }


    //////////////////////////////////// 蓝牙设备 //////////////////////////////////////////////////

    /**
     * 是否是首次绑定设备
     */
    public boolean isFirstBindDevice() {
        return (boolean) spUtils.get("is_first_bind_device", true);
    }

    public void setFirstBindDevice(boolean isFirstBind) {
        spUtils.put("is_first_bind_device", isFirstBind);
    }

    /**
     * 保存设备类型
     */
    public void saveDeviceTypes(List<DeviceType> data) {
        spUtils.put("device_type_all", GsonUtils.toJson(data));
    }

    public List<DeviceType> getDeviceTypes() {
        String json = (String) spUtils.get("device_type_all", "");
        return GsonUtils.fromJson(json, new TypeToken<List<DeviceType>>() {
        });
    }

    /***
     * 保存绑定设备
     */
    public void saveBindDevices(List<DeviceBean> data) {
        spUtils.put("device_bind_all", GsonUtils.toJson(data));
    }

    public List<DeviceBean> getBindDevices() {
        String json = (String) spUtils.get("device_bind_all", "");
        return GsonUtils.fromJson(json, new TypeToken<List<DeviceBean>>() {
        });
    }


    //////////////////////////////////// 离线缓存 //////////////////////////////////////////////////

    /**
     * 是否离线模式
     */
    public boolean isOfflineMode() {
        return (boolean) spUtils.get("is_offline_mode", false);
    }

    public void setOfflineMode(boolean isOffline) {
        spUtils.put("is_offline_mode", isOffline);
    }

    /**
     * 上次更新时间
     */
    public String getLastUpdateTime() {
        return (String) spUtils.get("offline_update_time", "");
    }

    public void setLastUpdateTime(String time) {
        spUtils.put("offline_update_time", time);
    }

    /**
     * 是否提示网络连接
     */
    public boolean isShowConnectTip() {
        String date = (String) spUtils.get("is_show_conn_dialog", "");
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date current = new Date();
            Date old = sdf.parse(date);
            if (current.before(old)) {
                // 小于保存日期不显示
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 今日不再提示
     */
    public void setShowDialogTomorrow() {
        // 日期天数加1 保存
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 1);
        String format = sdf.format(c.getTime());

        spUtils.put("is_show_conn_dialog", format);
    }
}
