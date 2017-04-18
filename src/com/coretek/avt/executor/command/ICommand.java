package com.coretek.avt.executor.command;

public interface ICommand
{
	public int getIndex();
	
	public String getCommand();
	
	public String[] getParams();
	
	public String getResult();
}