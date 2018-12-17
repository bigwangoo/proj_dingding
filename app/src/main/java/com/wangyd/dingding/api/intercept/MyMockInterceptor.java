package com.wangyd.dingding.api.intercept;

import android.content.Context;
import android.content.res.AssetManager;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.tianxiabuyi.txutils.TxUtils;
import com.tianxiabuyi.txutils.db.util.IOUtil;
import com.tianxiabuyi.txutils.network.util.TxLog;
import com.tianxiabuyi.txutils.util.GsonUtils;
import com.wangyd.dingding.api.API;
import com.wangyd.dingding.api.model.MyHttpResult;
import com.tianxiabuyi.villagedoctor.common.db.CacheDB;
import com.tianxiabuyi.villagedoctor.common.db.OfflineDB;
import com.wangyd.dingding.core.utils.UserSpUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @author wangyd
 * @date 2018/5/22
 * @description 模拟数据拦截器
 */
public class MyMockInterceptor implements Interceptor {

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        //TxLog.e("intercept: Thread=" + Thread.currentThread().getName());
        Response response = null;
        Request request = chain.request();

        // 离线模式
        boolean offlineMode = UserSpUtils.getInstance().isOfflineMode();
        if (offlineMode) {
            response = interceptRequestWhenDebug(request);
        }

        // 默认不处理
        if (response == null) {
            response = chain.proceed(request);
        }
        return response;
    }

    /**
     * 拦截指定地址
     */
    private Response interceptRequestWhenDebug(Request request) {
        String path = request.url().uri().getPath();
        TxLog.e("intercept: path=" + path);

        String result;
        switch (getExactlyPath(path)) {
            // 获取设备类型列表
            case API.DEVICE_TYPE_LIST:
                result = getCacheResponse(UserSpUtils.getInstance().getDeviceTypes());
                break;
            // 获取绑定设备列表
            case API.DEVICE_LIST:
                result = getCacheResponse(UserSpUtils.getInstance().getBindDevices());
                break;
            // 获取标签
            case API.LABEL_LIST:
                result = getCacheResponse(CacheDB.getCacheCacheLabel());
                break;
            // 获取服务项目
            case API.SERVICE_PACK:
                result = getCacheResponse(CacheDB.getCacheSP());
                break;
            // 获取可执行服务项目
            case API.SERVICE_PACK_APP:
                result = getCacheResponse(UserSpUtils.getInstance().getServiceExecutable());
                break;

            // 获取团队日程
            case API.TEAM_DAILY_WORK:
                result = getCacheResponse(CacheDB.getTown());
                break;
            // 签约村民列表
            case API.CONTRACT_LIST_SEARCH:
                result = getCacheResponse(CacheDB.getCacheContractVillager(request));
                break;
            // 居民档案列表
            case API.RESIDENT_LIST_SEARCH:
                result = getCacheResponse(CacheDB.getCacheResidentList(request));
                break;
            // 随访居民列表
            case API.NEWBORN_LIST:
                result = getCacheResponse(CacheDB.getCacheMarkVillager(API.NEWBORN_LIST, request));
                break;
            case API.CHILD_LIST:
                result = getCacheResponse(CacheDB.getCacheMarkVillager(API.CHILD_LIST, request));
                break;
            case API.PREGNANT_LIST:
                result = getCacheResponse(CacheDB.getCacheMarkVillager(API.PREGNANT_LIST, request));
                break;
            case API.MEDICINE_CHILD_LIST:
                result = getCacheResponse(CacheDB.getCacheMarkVillager(API.MEDICINE_CHILD_LIST, request));
                break;
            case API.MEDICINE_TZ_LIST:
                result = getCacheResponse(CacheDB.getCacheMarkVillager(API.MEDICINE_TZ_LIST, request));
                break;
            case API.BS_LIST:
                result = getCacheResponse(CacheDB.getCacheMarkVillager(API.BS_LIST, request));
                break;
            case API.BP_LIST:
                result = getCacheResponse(CacheDB.getCacheMarkVillager(API.BP_LIST, request));
                break;
            case API.MENTAL_LIST:
                result = getCacheResponse(CacheDB.getCacheMarkVillager(API.MENTAL_LIST, request));
                break;
            case API.TUBER_LIST:
                result = getCacheResponse(CacheDB.getCacheMarkVillager(API.TUBER_LIST, request));
                break;

            // 新增签约
            case API.CONTRACT_ADD:
                boolean v1 = OfflineDB.saveContractData(getParameterMap(request));
                result = v1 ? getCacheResponse(null) : null;
                break;
            // 新增居民
            case API.RESIDENT_INSERT:
                boolean insert = OfflineDB.saveResidentData(getParameterMap(request));
                result = insert ? getCacheResponse(null) : null;
                break;
            // 修改居民
            case API.RESIDENT_MODIFY_OFFLINE:
                boolean modify = OfflineDB.saveResidentModifyData(getParameterMap(request));
                result = modify ? getCacheResponse(null) : null;
                break;

            // 上传工作签到
            case API.TEAM_SIGN_IN:
                boolean sign = OfflineDB.saveSignInData(getParameterMap(request));
                result = sign ? getCacheResponse(null) : null;
                break;
            // 上传血糖血压
            case API.HEATH_FOLLOWUP:
                boolean follow = OfflineDB.saveFollowUpData(getParameterMap(request));
                result = follow ? getCacheResponse(null) : null;
                break;

            // 新生儿
            case API.NEWBORN_ADD:
                boolean b1 = OfflineDB.saveFollowupData(getParameterMap(request));
                result = b1 ? getCacheResponse(null) : null;
                break;
            // 儿童 1、2、3、4、5
            case API.CHILD_ADD:
                boolean b2 = OfflineDB.saveFollowupData(getParameterMap(request));
                result = b2 ? getCacheResponse(null) : null;
                break;
            // 孕妇
            case API.GRAVIDA_FIRST_ADD:
                boolean b3 = OfflineDB.saveFollowupData(getParameterMap(request));
                result = b3 ? getCacheResponse(null) : null;
                break;
            // 孕妇 2、3、4、5
            case API.GRAVIDA_ADD:
                boolean b4 = OfflineDB.saveFollowupData(getParameterMap(request));
                result = b4 ? getCacheResponse(null) : null;
                break;
            // 孕妇 产后、42
            case API.POSTPARTUM_ADD:
                boolean b5 = OfflineDB.saveFollowupData(getParameterMap(request));
                result = b5 ? getCacheResponse(null) : null;
                break;
            // 儿童中医药
            case API.MEDICINE_CHILD_ADD:
                boolean b6 = OfflineDB.saveFollowupData(getParameterMap(request));
                result = b6 ? getCacheResponse(null) : null;
                break;
            // 血糖
            case API.BS_ADD:
                boolean b7 = OfflineDB.saveFollowupData(getParameterMap(request));
                result = b7 ? getCacheResponse(null) : null;
                break;
            // 血压
            case API.BP_ADD:
                boolean b8 = OfflineDB.saveFollowupData(getParameterMap(request));
                result = b8 ? getCacheResponse(null) : null;
                break;
            // 精神
            case API.MENTAL_ADD:
                boolean b9 = OfflineDB.saveFollowupData(getParameterMap(request));
                result = b9 ? getCacheResponse(null) : null;
                break;
            // 精神补充
            case API.MENTAL_SUPPLY_ADD:
                boolean b10 = OfflineDB.saveFollowupData(getParameterMap(request));
                result = b10 ? getCacheResponse(null) : null;
                break;
            // 肺结核第一次入户
            case API.FIRST_TUBER_ADD:
                boolean b11 = OfflineDB.saveFollowupData(getParameterMap(request));
                result = b11 ? getCacheResponse(null) : null;
                break;
            // 肺结核
            case API.TUBER_ADD:
                boolean b12 = OfflineDB.saveFollowupData(getParameterMap(request));
                result = b12 ? getCacheResponse(null) : null;
                break;

            // 获取模拟数据
            case "api/likeThis":
                result = getMockResponse("mock/getContactList.json");
                break;
            default:
                result = getDefaultResponse();
//                result = null;
                break;
        }
//        if (result == null) {
//            return null;
//        }

        if (TextUtils.isEmpty(result)) {
            return new Response.Builder().code(500).protocol(Protocol.HTTP_1_0).request(request)
                    .addHeader("Content-Type", "application/json")
                    .message("")
                    .body(ResponseBody.create(MediaType.parse("application/json"), result))
                    .build();
        } else {
            return new Response.Builder().code(200).protocol(Protocol.HTTP_1_0).request(request)
                    .addHeader("Content-Type", "application/json")
                    .message("")
                    .body(ResponseBody.create(MediaType.parse("application/json"), result))
                    .build();
        }
    }

    /**
     * 默认请求响应
     */
    private String getDefaultResponse() {
        MyHttpResult result = new MyHttpResult();
        result.setMsg("离线模式，无法访问网络");
        result.setStatus(40000);
        return GsonUtils.toJson(result);
    }

    /**
     * 从本地Json文件 获取请求响应
     */
    private String getMockResponse(String jsonPath) {
        try {
            Context context = TxUtils.getInstance().getContext();
            InputStream inputStream = context.getAssets().open(jsonPath, AssetManager.ACCESS_BUFFER);
            return IOUtil.readStr(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 从本地数据库 获取请求响应
     */
    private <T> String getCacheResponse(T data) {
        MyHttpResult<T> result = new MyHttpResult<>();
        result.setStatus(MyHttpResult.SUCCESS);
        result.setMsg("success");
        result.setData(data);
        return GsonUtils.toJson(result);
    }

    /**
     * 读取请求中参数
     */
    @NonNull
    private Map<String, String> getParameterMap(Request request) {
        HttpUrl url = request.url();
        Map<String, String> params = new HashMap<>(16);
        Set<String> parameterNames = url.queryParameterNames();
        if (parameterNames.size() > 0) {
            for (String name : parameterNames) {
                String value = url.queryParameter(name);
                params.put(name, value);
            }
        }
        return params;
    }

    /**
     * 获取匹配路径
     */
    private String getExactlyPath(String path) {
        if (!TextUtils.isEmpty(path)) {
            if (path.startsWith("/")) {
                return path.substring(1, path.length());
            }
        }
        return path;
    }
}
