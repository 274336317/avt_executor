package com.coretek.avt.executor.rawmessage;

/**
 * 当所有的消息执行完毕后，会通过此接口发出通知
 * @author David
 *
 */
public interface IAllMessageDoneListener
{
	public void onAllMessageDone();
}
