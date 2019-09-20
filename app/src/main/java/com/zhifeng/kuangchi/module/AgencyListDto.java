package com.zhifeng.kuangchi.module;

import java.util.ArrayList;
import java.util.List;

public class AgencyListDto {


    /**
     * status : 200
     * msg : success
     * data : {"level_count_1":0,"level_count_2":0,"level_count_3":0,"level_count_4":0,"user_first":{"total":1,"per_page":20,"current_page":1,"last_page":1,"data":[{"id":31051,"level":0,"realname":"默认昵称","first_leader":31033,"is_vip":0,"mobile":"15219781117","createtime":1568189768,"is_nameapi":0}]},"team_count":1}
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
         * level_count_1 : 0
         * level_count_2 : 0
         * level_count_3 : 0
         * level_count_4 : 0
         * user_first : {"total":1,"per_page":20,"current_page":1,"last_page":1,"data":[{"id":31051,"level":0,"realname":"默认昵称","first_leader":31033,"is_vip":0,"mobile":"15219781117","createtime":1568189768,"is_nameapi":0}]}
         * team_count : 1
         */

        private int level_count_1;
        private int level_count_2;
        private int level_count_3;
        private int level_count_4;
        private UserFirstBean user_first;
        private int team_count;
        private String T_num;

        public String getT_num() {
            return T_num == null ? "0" : T_num;
        }

        public void setT_num(String t_num) {
            T_num = t_num == null ? "0" : t_num;
        }

        public int getLevel_count_1() {
            return level_count_1;
        }

        public void setLevel_count_1(int level_count_1) {
            this.level_count_1 = level_count_1;
        }

        public int getLevel_count_2() {
            return level_count_2;
        }

        public void setLevel_count_2(int level_count_2) {
            this.level_count_2 = level_count_2;
        }

        public int getLevel_count_3() {
            return level_count_3;
        }

        public void setLevel_count_3(int level_count_3) {
            this.level_count_3 = level_count_3;
        }

        public int getLevel_count_4() {
            return level_count_4;
        }

        public void setLevel_count_4(int level_count_4) {
            this.level_count_4 = level_count_4;
        }

        public UserFirstBean getUser_first() {
            return user_first;
        }

        public void setUser_first(UserFirstBean user_first) {
            this.user_first = user_first;
        }

        public int getTeam_count() {
            return team_count;
        }

        public void setTeam_count(int team_count) {
            this.team_count = team_count;
        }

        public static class UserFirstBean {
            /**
             * total : 1
             * per_page : 20
             * current_page : 1
             * last_page : 1
             * data : [{"id":31051,"level":0,"realname":"默认昵称","first_leader":31033,"is_vip":0,"mobile":"15219781117","createtime":1568189768,"is_nameapi":0}]
             */

            private int total;
            private int per_page;
            private int current_page;
            private int last_page;
            private List<DataBean> data;

            public int getTotal() {
                return total;
            }

            public void setTotal(int total) {
                this.total = total;
            }

            public int getPer_page() {
                return per_page;
            }

            public void setPer_page(int per_page) {
                this.per_page = per_page;
            }

            public int getCurrent_page() {
                return current_page;
            }

            public void setCurrent_page(int current_page) {
                this.current_page = current_page;
            }

            public int getLast_page() {
                return last_page;
            }

            public void setLast_page(int last_page) {
                this.last_page = last_page;
            }

            public List<DataBean> getData() {
                if (data == null) {
                    return new ArrayList<>();
                }
                return data;
            }

            public void setData(List<DataBean> data) {
                this.data = data;
            }

            public static class DataBean {
                /**
                 * id : 31051
                 * level : 0
                 * realname : 默认昵称
                 * first_leader : 31033
                 * is_vip : 0
                 * mobile : 15219781117
                 * createtime : 1568189768
                 * is_nameapi : 0
                 */

                private int id;
                private int level;
                private String realname;
                private int first_leader;
                private int is_vip;
                private String mobile;
                private int createtime;
                private int is_nameapi;
                private int T_time;

                public int getT_time() {
                    return T_time;
                }

                public void setT_time(int t_time) {
                    T_time = t_time;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public int getLevel() {
                    return level;
                }

                public void setLevel(int level) {
                    this.level = level;
                }

                public String getRealname() {
                    return realname == null ? "" : realname;
                }

                public void setRealname(String realname) {
                    this.realname = realname;
                }

                public int getFirst_leader() {
                    return first_leader;
                }

                public void setFirst_leader(int first_leader) {
                    this.first_leader = first_leader;
                }

                public int getIs_vip() {
                    return is_vip;
                }

                public void setIs_vip(int is_vip) {
                    this.is_vip = is_vip;
                }

                public String getMobile() {
                    return mobile == null ? "" : mobile;
                }

                public void setMobile(String mobile) {
                    this.mobile = mobile;
                }

                public int getCreatetime() {
                    return createtime;
                }

                public void setCreatetime(int createtime) {
                    this.createtime = createtime;
                }

                public int getIs_nameapi() {
                    return is_nameapi;
                }

                public void setIs_nameapi(int is_nameapi) {
                    this.is_nameapi = is_nameapi;
                }
            }
        }
    }
}
