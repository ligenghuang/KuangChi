package com.zhifeng.kuangchi.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lgh.huanglib.util.L;
import com.lgh.huanglib.util.config.GlideUtil;
import com.zhifeng.kuangchi.R;
import com.zhifeng.kuangchi.module.CustomerServiceListDto;
import com.zhifeng.kuangchi.util.data.DynamicTimeFormat;
import com.zhifeng.kuangchi.util.data.MySp;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
  *
  * @ClassName:     客服对话列表适配器
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/9/2 11:16
  * @Version:        1.0
 */

public class CustomerServiceListAdapter extends BaseRecyclerAdapter<CustomerServiceListDto.DataBean> {
    Context context;

    public CustomerServiceListAdapter(Context context) {
        super(R.layout.layout_item_customer_service);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(SmartViewHolder holder, CustomerServiceListDto.DataBean model, int position) {
        holder.setIsRecyclable(false);
        RelativeLayout llLeft = holder.itemView.findViewById(R.id.ll_left);
        LinearLayout llRight = holder.itemView.findViewById(R.id.ll_right);

        llLeft.setVisibility(View.GONE);
        llRight.setVisibility(View.GONE);
        //时间
        TextView timeTv = holder.itemView.findViewById(R.id.tv_item_time);
        long time = (long)model.getCreate_time()*(long)1000;
        String date = getTimeString(time);
        timeTv.setText(date);
        if (position != 0){
            long lastTime = (long)getAllData().get(position -1).getCreate_time()*(long)1000;
            String lastDate = getTimeString(lastTime);
            timeTv.setVisibility(lastDate.equals(date)?View.GONE:View.VISIBLE);
        }

        if (model.getSend_type() == 0){
            //todo 客服发送
            llLeft.setVisibility(View.VISIBLE);
            holder.text(R.id.tv_left_name,model.getSend_name());//客服名称
            holder.text(R.id.tv_left_message,model.getContent());//客服发送的消息
            ImageView ivLeft = holder.itemView.findViewById(R.id.iv_left_avatar);
            GlideUtil.setImageCircle(context,model.getSend_avatar(),ivLeft,R.mipmap.icon_avatar);//todo 2019/09/02 客服头像
        }else if (model.getSend_type() == 1){
            //todo 用户发送
            llRight.setVisibility(View.VISIBLE);
            holder.text(R.id.tv_right_message,model.getContent());
            ImageView ivRight = holder.itemView.findViewById(R.id.iv_right_avatar);
            GlideUtil.setImageCircle(context, MySp.getUserImg(context),ivRight,R.mipmap.icon_avatar);//todo 2019/09/02 用户头像
        }

    }

    public static String getTimeString(Long timestamp) {
        String result = "";
        String[] weekNames = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        String hourTimeFormat = "HH:mm";
        String monthTimeFormat = "M月d日 HH:mm";
        String yearTimeFormat = "yyyy年M月d日 HH:mm";
        try {
            Calendar todayCalendar = Calendar.getInstance();
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(timestamp);

            if (todayCalendar.get(Calendar.YEAR) == calendar.get(Calendar.YEAR)) {//当年
                if (todayCalendar.get(Calendar.MONTH) == calendar.get(Calendar.MONTH)) {//当月
                    int temp = todayCalendar.get(Calendar.DAY_OF_MONTH) - calendar.get(Calendar.DAY_OF_MONTH);
                    switch (temp) {
                        case 0://今天
                            result = getTime(timestamp, hourTimeFormat);
                            break;
                        case 1://昨天
                            result = "昨天 " + getTime(timestamp, hourTimeFormat);
                            break;
                        case 2:
                        case 3:
                        case 4:
                        case 5:
                        case 6:
                            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
                            result = weekNames[dayOfWeek - 1] + " " + getTime(timestamp, hourTimeFormat);
                            break;
                        default:
                            result = getTime(timestamp, monthTimeFormat);
                            break;
                    }
                } else {
                    result = getTime(timestamp, monthTimeFormat);
                }
            } else {
                result = getTime(timestamp, yearTimeFormat);
            }
            return result;
        } catch (Exception e) {
            Log.e("getTimeString", e.getMessage());
            return "";
        }
    }

    public static String getTime(long time, String pattern) {
        Date date = new Date(time);
        return dateFormat(date, pattern);
    }

    public static String dateFormat(Date date, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }
}
