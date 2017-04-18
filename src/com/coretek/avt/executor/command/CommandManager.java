package com.coretek.avt.executor.command;

import java.util.ArrayList;
import java.util.List;

public class CommandManager
{
	private List<ICommand> commands = new ArrayList<ICommand>();
	
	private List<ICommandListener> listeners = new ArrayList<ICommandListener>();
	
	public void addCommand(ICommand command)
	{
		this.commands.add(command);
		this.fireCommandEvent(command);
	}
	
	public void addCommandListener(ICommandListener listener)
	{
		this.listeners.add(listener);
	}
	
	public void fireCommandEvent(ICommand command)
	{
		for(ICommandListener listener: listeners)
		{
			listener.onCommand(command);
		}
	}
}