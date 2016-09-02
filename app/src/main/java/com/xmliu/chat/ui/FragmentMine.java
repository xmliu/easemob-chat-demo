package com.xmliu.chat.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.easemob.EMCallBack;
import com.easemob.chat.EMChatManager;
import com.xmliu.chat.BaseFragment;
import com.xmliu.chat.R;

/**
 * Date: 2016/8/31 18:43
 * Email: diyangxia@163.com
 * Author: diyangxia
 * Description: TODO
 */
public class FragmentMine extends BaseFragment {

    private LinearLayout logoutLayout;


    private TextView mUserTV;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);

        mUserTV = (TextView) view.findViewById(R.id.current_user);
        logoutLayout = (LinearLayout) view.findViewById(R.id.id_user_center_logout_layout);

        mUserTV.setText(EMChatManager.getInstance().getCurrentUser());


        logoutLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                showLogoutDialog();

            }
        });

        return view;
    }



    private void showLogoutDialog() {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(mContext)
                .title("应用提示")
                .theme(Theme.LIGHT)
                .content( "确定要注销" + EMChatManager.getInstance().getCurrentUser()
                        + "用户吗？")
                .positiveText("确定").onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        dialog.dismiss();
                        // EMChatManager.getInstance().logout();
                        logout(new EMCallBack() {

                            @Override
                            public void onSuccess() {
                                // TODO Auto-generated method stub
                                startActivity(new Intent(mContext,
                                        LoginActivity.class));
                            }

                            @Override
                            public void onProgress(int arg0, String arg1) {
                                // TODO Auto-generated method stub

                            }

                            @Override
                            public void onError(int arg0, String arg1) {
                                // TODO Auto-generated method stub

                            }
                        });

                    }
                })
                .negativeText("取消")
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        dialog.dismiss();
                    }
                });

        MaterialDialog dialog = builder.build();
        dialog.show();

    }

    public void logout(final EMCallBack callback) {
        // setPassword(null);
        EMChatManager.getInstance().logout(new EMCallBack() {

            @Override
            public void onSuccess() {
                // TODO Auto-generated method stub
                if (callback != null) {
                    callback.onSuccess();
                }
            }

            @Override
            public void onError(int code, String message) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onProgress(int progress, String status) {
                // TODO Auto-generated method stub
                if (callback != null) {
                    callback.onProgress(progress, status);
                }
            }

        });
    }


}
