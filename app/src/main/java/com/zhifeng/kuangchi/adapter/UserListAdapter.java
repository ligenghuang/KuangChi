package com.zhifeng.kuangchi.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lgh.huanglib.util.config.GlideUtil;
import com.zhifeng.kuangchi.R;
import com.zhifeng.kuangchi.module.UserDto;
import com.zhifeng.kuangchi.util.data.MySp;

/**
  *
  * @ClassName:     用户列表
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/9/6 11:17
  * @Version:        1.0
 */

public class UserListAdapter extends BaseRecyclerAdapter<UserDto>{
    Context context;

    OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public UserListAdapter(Context context) {
        super(R.layout.layout_item_user);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(SmartViewHolder holder, UserDto model, int position) {
        holder.setIsRecyclable(false);
        holder.text(R.id.tv_name,model.getName());
        TextView tvIsLogin = holder.itemView.findViewById(R.id.tv_item_login);
        boolean b = model.getToken().equals(MySp.getAccessToken(context));
        tvIsLogin.setVisibility(b? View.VISIBLE:View.GONE);

        ImageView imageView = holder.itemView.findViewById(R.id.iv_avatar);
        GlideUtil.setImageCircle(context,model.getImg(),imageView,R.mipmap.icon_avatar);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListener.OnClick(model);
            }
        });
    }

    public interface OnClickListener{
        void OnClick(UserDto dto);
    }

}
