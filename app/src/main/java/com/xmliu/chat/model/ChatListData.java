package com.xmliu.chat.model;

public class ChatListData {

	String receiveAvatar;
	String receiveContent;
	String chatTime;
	String sendAvatar;
	String sendContent;
	/**
	 * 1 发送； 2接收
	 */
	int type;
	/**
	 * 1 发送； 2接收
	 */
	public int getType() {
		return type;
	}
	/**
	 * 1 发送； 2接收
	 */
	public void setType(int type) {
		this.type = type;
	}

	public String getReceiveAvatar() {
		return receiveAvatar;
	}

	public void setReceiveAvatar(String receiveAvatar) {
		this.receiveAvatar = receiveAvatar;
	}

	public String getReceiveContent() {
		return receiveContent;
	}

	public void setReceiveContent(String receiveContent) {
		this.receiveContent = receiveContent;
	}

	public String getChatTime() {
		return chatTime;
	}

	public void setChatTime(String chatTime) {
		this.chatTime = chatTime;
	}

	public String getSendAvatar() {
		return sendAvatar;
	}

	public void setSendAvatar(String sendAvatar) {
		this.sendAvatar = sendAvatar;
	}

	public String getSendContent() {
		return sendContent;
	}

	public void setSendContent(String sendContent) {
		this.sendContent = sendContent;
	}

}
