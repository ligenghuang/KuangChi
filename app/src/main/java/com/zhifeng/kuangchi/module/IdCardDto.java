package com.zhifeng.kuangchi.module;

public class IdCardDto {


    /**
     * status : 200
     * msg : success
     * data : {"chargeStatus":1,"message":"成功","data":{"orderNo":"1567498166226","handleTime":"2019-09-03 16:09:26","result":"03","remark":"无记录","province":"广东省","city":"佛山市","country":"南海区","birthday":"19960226","age":"24","gender":"1"},"code":"200000"}
     */

    private int status;
    private String msg;
    private DataBeanX data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public static class DataBeanX {
        /**
         * chargeStatus : 1
         * message : 成功
         * data : {"orderNo":"1567498166226","handleTime":"2019-09-03 16:09:26","result":"03","remark":"无记录","province":"广东省","city":"佛山市","country":"南海区","birthday":"19960226","age":"24","gender":"1"}
         * code : 200000
         */

        private int chargeStatus;
        private String message;
        private DataBean data;
        private String code;

        public int getChargeStatus() {
            return chargeStatus;
        }

        public void setChargeStatus(int chargeStatus) {
            this.chargeStatus = chargeStatus;
        }

        public String getMessage() {
            return message == null ? "" : message;
        }

        public void setMessage(String message) {
            this.message = message == null ? "" : message;
        }

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public String getCode() {
            return code == null ? "" : code;
        }

        public void setCode(String code) {
            this.code = code == null ? "" : code;
        }

        public static class DataBean {
            /**
             * orderNo : 1567498166226
             * handleTime : 2019-09-03 16:09:26
             * result : 03
             * remark : 无记录
             * province : 广东省
             * city : 佛山市
             * country : 南海区
             * birthday : 19960226
             * age : 24
             * gender : 1
             */

            private String orderNo;
            private String handleTime;
            private String result;
            private String remark;
            private String province;
            private String city;
            private String country;
            private String birthday;
            private String age;
            private String gender;

            public String getOrderNo() {
                return orderNo == null ? "" : orderNo;
            }

            public void setOrderNo(String orderNo) {
                this.orderNo = orderNo == null ? "" : orderNo;
            }

            public String getHandleTime() {
                return handleTime == null ? "" : handleTime;
            }

            public void setHandleTime(String handleTime) {
                this.handleTime = handleTime == null ? "" : handleTime;
            }

            public String getResult() {
                return result == null ? "" : result;
            }

            public void setResult(String result) {
                this.result = result == null ? "" : result;
            }

            public String getRemark() {
                return remark == null ? "" : remark;
            }

            public void setRemark(String remark) {
                this.remark = remark == null ? "" : remark;
            }

            public String getProvince() {
                return province == null ? "" : province;
            }

            public void setProvince(String province) {
                this.province = province == null ? "" : province;
            }

            public String getCity() {
                return city == null ? "" : city;
            }

            public void setCity(String city) {
                this.city = city == null ? "" : city;
            }

            public String getCountry() {
                return country == null ? "" : country;
            }

            public void setCountry(String country) {
                this.country = country == null ? "" : country;
            }

            public String getBirthday() {
                return birthday == null ? "" : birthday;
            }

            public void setBirthday(String birthday) {
                this.birthday = birthday == null ? "" : birthday;
            }

            public String getAge() {
                return age == null ? "" : age;
            }

            public void setAge(String age) {
                this.age = age == null ? "" : age;
            }

            public String getGender() {
                return gender == null ? "" : gender;
            }

            public void setGender(String gender) {
                this.gender = gender == null ? "" : gender;
            }
        }
    }
}
