package com.tianxiabuyi.txutils.network.service;

import com.tianxiabuyi.txutils.config.TxConstants;
import com.tianxiabuyi.txutils.network.TxCall;
import com.tianxiabuyi.txutils.network.model.TxFileResult;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

/**
 * @author xjh1994
 * @date 2016/8/19
 * @description 文件上传接口
 */
public interface TxFileService {

    /**
     * 单文件上传
     *
     * @param name            文件名
     * @param type            与服务器保持一致
     * @param fileRequestBody MultipartBody
     * @return
     */
    @Multipart
    @POST(TxConstants.UPLOAD_FILE_URL)
    TxCall<TxFileResult> uploadImage(@Query("name") String name,
                                     @Query("type") String type,
                                     @Part MultipartBody.Part fileRequestBody);

    /**
     * 多文件上传
     *
     * @param act
     * @param params
     * @return
     */
    @Multipart
    @POST(TxConstants.UPLOAD_FILE_URL)
    TxCall<ResponseBody> uploadMultiImage(@Part("act") String act,
                                          @PartMap Map<String, RequestBody> params);
}
