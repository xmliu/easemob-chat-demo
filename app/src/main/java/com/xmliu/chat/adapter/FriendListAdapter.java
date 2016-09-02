/*************************************************************************
 *  
 *  Copyright (C) 2015 Suzhou Raipeng Information Technology co., LTD.
 * 
 *                       All rights reserved.
 *
 *************************************************************************/
package com.xmliu.chat.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xmliu.chat.R;

/**
 * @date: 2015年4月30日 下午4:34:38
 * 
 * @email: xmliu@raipeng.com
 * 
 * @version: V1.0
 * 
 * @description: 
 * 
 * @author: diyangxia
 *
 */
public class FriendListAdapter extends BaseAdapter {

	Context mContext;
	List<String> mListData;

	public FriendListAdapter(Context mContext, List<String> mListData) {
		super();
		this.mContext = mContext;
		this.mListData = mListData;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mListData.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int index, View cView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		FHolder holder;
		if (cView == null) {
			holder = new FHolder();
			cView = LayoutInflater.from(mContext).inflate(
					R.layout.friend_listview_item, null);
			holder.name = (TextView) cView
					.findViewById(R.id.friend_listview_name);
			cView.setTag(holder);

		} else {
			holder = (FHolder) cView.getTag();
		}

		holder.name.setText(mListData.get(index));

		return cView;
	}

	class FHolder {
		TextView name;
	}
}

