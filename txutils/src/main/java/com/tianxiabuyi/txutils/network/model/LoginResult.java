package com.tianxiabuyi.txutils.network.model;

/**
 * @author xjh1994
 * @date 2016/7/18
 */
public class LoginResult<T extends TxUser> extends HttpResult {

    /**
     * uid : 1873
     * user_name : test
     * name : 王浩然
     * avatar : http://image.eeesys.com/default/user_m.png
     */
    private T user;
    private AuthBean auth;

    public T getUser() {
        return user;
    }

    public void setUser(T user) {
        this.user = user;
    }

    public AuthBean getAuth() {
        return auth;
    }

    public void setAuth(AuthBean auth) {
        this.auth = auth;
    }

    public static class AuthBean {

        /**
         * token : 6af25391ed920e5cf3a4366004ebeb37
         * expires_in : 3600
         */
        private String token;
        private int expires_in;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public int getExpires_in() {
            return expires_in;
        }

        public void setExpires_in(int expires_in) {
            this.expires_in = expires_in;
        }
    }
}
