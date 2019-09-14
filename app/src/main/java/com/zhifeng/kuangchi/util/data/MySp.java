package com.zhifeng.kuangchi.util.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.lgh.huanglib.util.L;
import com.lgh.huanglib.util.data.MySharedPreferencesUtil;
/**     
  * 
  * @ClassName:     
  * @Description:    
  * @Author:         lgh
  * @CreateDate:     2019/8/30 14:01
  * @Version:        1.0
 */

public class MySp extends MySharedPreferencesUtil {
    /**
     * 清空缓存
     * @param context
     */
    public static void clearAllSP(Context context) {
        setAccessToken(context,null);
        setUserPhone(context,null);
        setUserName(context,null);
        setUserId(context,null);
        setUserImg(context,null);
        setUserVip(context,0);
        setUserNameapi(context,0);
        setBouns(context,"0");
        setBounsDay(context,"0");
        setFound(context,0);
    }

    /**
     * 判断是否登录
     * @param context
     * @return
     */
    public static boolean iSLoginLive(Context context) {

            String accessToken = getAccessToken(context);
            if (accessToken != null) {
                L.e("MySharedPreferencesUtil", " 登陆了");
                return true;
            } else {
                L.e("MySharedPreferencesUtil", " 没有 登陆");
                return false;
            }
    }


    /**
     * 获取 签名
     *
     * @param context
     * @return
     */
    public static String getAccessToken(Context context) {
        SharedPreferences sp = getProjectSP(context);
        return sp.getString("token", null);
    }

    /**
     * 设置 签名
     *
     * @param context
     * @param token
     */
    public static boolean setAccessToken(Context context, String token) {
        SharedPreferences sp = getProjectSP(context);
        SharedPreferences.Editor editor = sp.edit();
        return editor.putString("token", token).commit();
    }

    /**
     * 保存用户列表
     * @param context
     * @param json
     * @return
     */
    public static boolean setUserList(Context context,String json){
        SharedPreferences sp = getProjectSP(context);
        SharedPreferences.Editor editor = sp.edit();
        return editor.putString("UserJson", json).commit();
    }

    /**
     * 获取用户列表
     * @param context
     * @return
     */
    public static String getUserList(Context context){
        SharedPreferences sp = getProjectSP(context);
        return sp.getString("UserJson", null);
    }


    /**
     * 获取用户昵称
     * @param context
     * @return
     */
    public static String getUserName(Context context){
        SharedPreferences sp = getProjectSP(context);
        return sp.getString("UserName", null);
    }

    /**
     * 设置 用户昵称
     *
     * @param context
     * @param UserName
     */
    public static boolean setUserName(Context context, String UserName) {
        SharedPreferences sp = getProjectSP(context);
        SharedPreferences.Editor editor = sp.edit();
        return editor.putString("UserName", UserName).commit();
    }

    /**
     * 获取用户手机号
     * @param context
     * @return
     */
    public static String getUserPhone(Context context){
        SharedPreferences sp = getProjectSP(context);
        return sp.getString("UserPhone", null);
    }

    /**
     * 设置 用户手机号
     *
     * @param context
     * @param UserPhone
     */
    public static boolean setUserPhone(Context context, String UserPhone) {
        SharedPreferences sp = getProjectSP(context);
        SharedPreferences.Editor editor = sp.edit();
        return editor.putString("UserPhone", UserPhone).commit();
    }

    /**
     * 获取用户id
     * @param context
     * @return
     */
    public static String getUserId(Context context){
        SharedPreferences sp = getProjectSP(context);
        return sp.getString("UserId", null);
    }

    /**
     * 设置 用户id
     *
     * @param context
     * @param UserPhone
     */
    public static boolean setUserId(Context context, String UserPhone) {
        SharedPreferences sp = getProjectSP(context);
        SharedPreferences.Editor editor = sp.edit();
        return editor.putString("UserId", UserPhone).commit();
    }

    /**
     * 获取用户头像
     * @param context
     * @return
     */
    public static String getUserImg(Context context){
        SharedPreferences sp = getProjectSP(context);
        return sp.getString("UserImg", null);
    }

    /**
     * 设置 用户头像
     *
     * @param context
     * @param UserPhone
     */
    public static boolean setUserImg(Context context, String UserPhone) {
        SharedPreferences sp = getProjectSP(context);
        SharedPreferences.Editor editor = sp.edit();
        return editor.putString("UserImg", UserPhone).commit();
    }


    /**
     * 获取用户是否激活
     * @param context
     * @return
     */
    public static int getUserVip(Context context){
        SharedPreferences sp = getProjectSP(context);
        return sp.getInt("UserIs_vip", 0);
    }

    /**
     * 设置 用户是否激活
     *
     * @param context
     * @param is_vip
     */
    public static boolean setUserVip(Context context, int is_vip) {
        SharedPreferences sp = getProjectSP(context);
        SharedPreferences.Editor editor = sp.edit();
        return editor.putInt("UserIs_vip", is_vip).commit();
    }

    /**
     * 获取用户是否实名认证
     * @param context
     * @return
     */
    public static int getUserNameapi(Context context){
        SharedPreferences sp = getProjectSP(context);
        return sp.getInt("UserIs_nameapi", 0);
    }

    /**
     * 设置 用户是否实名认证
     *
     * @param context
     * @param is_nameapi
     */
    public static boolean setUserNameapi(Context context, int is_nameapi) {
        SharedPreferences sp = getProjectSP(context);
        SharedPreferences.Editor editor = sp.edit();
        return editor.putInt("UserIs_nameapi", is_nameapi).commit();
    }


    /**
     * 获取 日收益
     *
     * @param context
     * @return
     */
    public static String getBounsDay(Context context) {
        SharedPreferences sp = getProjectSP(context);
        return sp.getString("BounsDay", "0");
    }

    /**
     * 设置 日收益
     *
     * @param context
     * @param BounsDay
     */
    public static boolean setBounsDay(Context context, String BounsDay) {
        SharedPreferences sp = getProjectSP(context);
        SharedPreferences.Editor editor = sp.edit();
        return editor.putString("BounsDay", BounsDay).commit();
    }


    /**
     * 获取 总收益
     *
     * @param context
     * @return
     */
    public static String getBouns(Context context) {
        SharedPreferences sp = getProjectSP(context);
        return sp.getString("Bouns", "0");
    }

    /**
     * 设置 累计收益
     *
     * @param context
     * @param Bouns
     */
    public static boolean setBouns(Context context, String Bouns) {
        SharedPreferences sp = getProjectSP(context);
        SharedPreferences.Editor editor = sp.edit();
        return editor.putString("Bouns", Bouns).commit();
    }

    /**
     * 获取状态值  检验资产页是否需要验证密码 0需要验证 1不需要
     * @param context
     * @return
     */
    public static int getFound(Context context){
        SharedPreferences sp = getProjectSP(context);
        return sp.getInt("isFound", 0);
    }

    /**
     * 设置状态值
     * @param context
     * @param isFound
     * @return
     */
    public static boolean setFound(Context context,int isFound){
        SharedPreferences sp = getProjectSP(context);
        SharedPreferences.Editor editor = sp.edit();
        return editor.putInt("isFound", isFound).commit();
    }

}
