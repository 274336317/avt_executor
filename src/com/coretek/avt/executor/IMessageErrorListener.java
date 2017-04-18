package com.coretek.avt.executor;

import com.coretek.avt.executor.model.Message;

/**
 * 当执行消息出错时会通过此接口通知感兴趣的对象
 * 
 * @author David
 *
 */
public interface IMessageErrorListener
{
	// 发送消息失败
	public final static int	ERROR_SEND_FAILED		= 1;

	// 接收消息失败
	public final static int	ERROR_RECV_FAILED		= 2;

	// 预期值与实际值不匹配
	public final static int	ERROR_VALUE_NOT_MATCHED	= 3;

	/**
	 * 当执行消息出错时会调用此接口。
	 * 
	 * @param msg
	 *            出错的消息
	 * @param period
	 *            在哪个周期出错
	 * @param errorCode
	 *            出错的原因
	 */
	public void onMessageError(Message msg, int period, int errorCode);
}