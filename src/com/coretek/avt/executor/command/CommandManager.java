package com.coretek.avt.executor.command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 管理SPTE客户端发送的所有控制命令
 * 
 * @author David
 *
 */
public class CommandManager implements Runnable
{
	private static CommandManager	INSTANCE	= new CommandManager();

	private List<ICommand>			commands	= new ArrayList<ICommand>();

	private List<ICommandListener>	listeners	= new ArrayList<ICommandListener>();

	public static CommandManager GetInstance()
	{
		return INSTANCE;
	}

	public void addCommand(ICommand command)
	{
		this.commands.add(command);
		this.fireCommandEvent(command);
	}

	public void addCommandListener(ICommandListener listener)
	{
		this.listeners.add(listener);
	}
	
	/**
	 * 向控制命令发送端返回命令执行结果
	 * @param cmd
	 */
	public void writeCommandResult(ICommand cmd)
	{
		System.out.println(cmd.getResult());
		System.out.flush();
	}
	
	public ICommand getRunCommand()
	{
		ICommand cmd = null;
		for(ICommand c: commands)
		{
			if(c instanceof RunCommand)
			{
				cmd = (RunCommand)c;
				break;
			}
		}
		return cmd;
	}

	private void fireCommandEvent(ICommand command)
	{
		for (ICommandListener listener : listeners)
		{
			listener.onCommand(command);
		}
	}

	private class CommandReader implements Runnable
	{
		private CommandParser	commandParser	= new CommandParser();

		public CommandReader()
		{

		}

		@Override
		public void run()
		{
			InputStreamReader isr = new InputStreamReader(System.in);
			BufferedReader br = new BufferedReader(isr);
			String str = null;
			try
			{
				while ((str = br.readLine()) != null)
				{
					if (commandParser.isCommand(str))
					{
						ICommand cmd = commandParser.parse(str);
						if (cmd == null)
						{// 未知命令
							// TODO
						}
						else
						{
							addCommand(cmd);
							fireCommandEvent(cmd);
						}
					}
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	@Override
	public void run()
	{
		new Thread(new CommandReader()).start();
	}
}