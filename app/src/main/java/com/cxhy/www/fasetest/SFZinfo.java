package com.cxhy.www.fasetest;

/**
 * Created by Administrator on 2018/3/10/010.
 */

public class SFZinfo {

    /**
     * reason : 成功
     * result : {"realname":"董佳伟","sex":"男","nation":"满","born":"19900618","address":"河北省秦皇岛市青龙满族自治县木头凳镇木头凳村161255号","idcard":"130321199006184233","side":"front","orderid":"2018031023290333471"}
     * error_code : 0
     */

    private String reason;
    private ResultBean result;
    private int error_code;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public static class ResultBean {
        /**
         * realname : 董佳伟
         * sex : 男
         * nation : 满
         * born : 19900618
         * address : 河北省秦皇岛市青龙满族自治县木头凳镇木头凳村161255号
         * idcard : 130321199006184233
         * side : front
         * orderid : 2018031023290333471
         */

        private String realname;
        private String sex;
        private String nation;
        private String born;
        private String address;
        private String idcard;
        private String side;
        private String orderid;

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getNation() {
            return nation;
        }

        public void setNation(String nation) {
            this.nation = nation;
        }

        public String getBorn() {
            return born;
        }

        public void setBorn(String born) {
            this.born = born;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getIdcard() {
            return idcard;
        }

        public void setIdcard(String idcard) {
            this.idcard = idcard;
        }

        public String getSide() {
            return side;
        }

        public void setSide(String side) {
            this.side = side;
        }

        public String getOrderid() {
            return orderid;
        }

        public void setOrderid(String orderid) {
            this.orderid = orderid;
        }
    }
}
