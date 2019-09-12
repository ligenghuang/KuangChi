package com.zhifeng.kuangchi.net;

import com.zhifeng.kuangchi.BuildConfig;

public class WebUrlUtil {

    static {
        //配合retrofit，需要以/结尾
        if (BuildConfig.DEBUG) {
//            BASE_URL = "http://orepool.zhifengwangluo.com/api/";
//            BASE_URL = "http://www.dxzh.net/api/";
            BASE_URL = " http://www.imnebula.com/api/";
            BASE_URL2 = "https://api.huobi.pro/market/";
        } else {
//            BASE_URL = "http://orepool.zhifengwangluo.com/api/";
//            BASE_URL = "http://www.dxzh.net/api/";
            BASE_URL = " http://www.imnebula.com/api/";
            BASE_URL2 = "https://api.huobi.pro/market/";
        }
    }

    public static String BASE_URL;
    public static String BASE_URL2;

    /**
     * 登录或注册
     */
    public static final String POST_LOGIN = "user/doLogin";

    /**
     * 获取验证码
     */
    public static final String POST_GET_CODE = "user/sendVerifyCode";

    /**
     * 用户协议
     */
    public static final String POST_GET_AGREEMENT = "user/consult";

    /**
     * 首页
     */
    public static final String POST_GET_HOME_INDEX = "index/index";

    /**
     * 资讯详情页
     */
    public static final String POST_GET_INFO_DETAIL = "banner/info_detail";

    /**
     * 公告详情页
     */
    public static final String POST_GET_ANNOUNCE_DETAIL ="banner/announce_detail";

    /**
     * 我的信息
     */
    public static final String POST_MY_INFO = "safe/my_info";

    /**
     * 邀请链接
     */
    public static final String POST_USER_WEM = "user/ewm";

    /**
     * 消息中心
     */
    public static final String POST_MESSAGE_LIST ="safe/message_center";

    /**
     * 安全中心
     */
    public static final String POST_SAFE_INDEX = "safe/index";

    /**
     * 校验手机号
     */
    public static final String POST_CHECK_NEW_PHONE = "safe/check_new_phone";

    /**
     * 换绑手机号
     */
    public static final String POST_SAFE_CHANGE_PHONE = "safe/change_phone";

    /**
     * 委托明细
     */
    public static final String POST_MINERS_ENTRUST_LIST = "Miners/Entrust_list";

    /**
     * 代理明细
     */
    public static final String POST_MINERS_AGENT_LIST = "miners/agent_users";

    /**
     * 代理等级人数详情
     */
    public static final String POST_AGENT_NUM_LIST = "Miners/agent_nums_list";

    /**
     * 下级代理列表
     */
    public static final String POST_MINERESAGENT_LOWER_LISET = "Miners/agent_users_list";

    /**
     * 收益明细
     */
    public static final String POST_ENTRUST_LOG = "Miners/entrust_log";

    /**
     * 提币明细
     */
    public static final String POST_MINERS_CARRY = "Miners/carry";

    /**
     * 提币详情
     */
    public static final String POST_MINERS_CARRY_DETAIL = "miners/carry_list";

    /**
     * 我的余额
     */
    public static final String POST_BALANCEE = "user/get_coin_list";

    /**
     * 我的订单
     */
    public static final String POST_ORDER_LIST = "miners/order_list_log";

    /**
     * 商品详情
     */
    public static final String POST_GOODS_DETAIL = "goods/goodsDetail";

    /**
     *存储服务器群组
     */
    public static final String POST_MINERS_INDEX = "Miners/index";

    /**
     * 设置支付密码
     */
    public static final String POST_SET_PAY_PWD ="safe/set_pay_password";

    /**
     * 忘记支付密码
     */
    public static final String POST_FORGET_PAY_PWD ="safe/forget_pay_password";

    /**
     * 修改支付密码
     */
    public static final String POST_EDIT_PAY_PWD = "safe/edit_pay_password";

    /**
     * 客服对话列表
     */
    public static final String POST_CUSTOMER_INDEX = "customer/index";

    /**
     * 发送消息
     */
    public static final String POST_CUSTOMER_SEND = "customer/service";

    /**
     * 提币
     */
    public static final String POST_GET_COIN = "user/get_coin";

    /**
     * 充币
     */
    public static final String POST_PUT_COIN = "user/put_coin";

    /**
     * 转换率
     */
    public static final String POST_COIN_RATE = "user/coin_rate";

    /**
     * 身份认证
     */
    public static final String POST_USER_API = "user/user_api";

    /**
     * 委托支付
     */
    public static final String POST_ORDER_ENTRUST = "order/entrust";

    /**
     * 修改头像
     */
    public static final String POST_SHANG_AVATAR = "safe/change_avatar";

    /**
     * 修改昵称
     */
    public static final String POST_CHANGE_NAME = "safe/change_realname";

    /**
     *
     * 修改登录密码
     */
    public static final String POST_MOBLIE_LOGIN_PWD = "safe/reset_password";

    /**
     *行情
     */
    public static final String POST_HISTORY_KLINE = "user/lamb_hangqing";

    /**
     * 充值明细
     */
    public static final String POST_PUT_LIST ="user/put_lst";

    /**
     * 充值详情
     */
    public static final String POST_USER_PUT_DETAIL = "user/put_detail";
}
