package com.coretek.avt.executor.message;

import java.util.ArrayList;
import java.util.List;

public class Period
{
	private int		index;	// 周期下标

	private List<Field>	fields = new ArrayList<Field>();	// 字段

	public int getIndex()
	{
		return index;
	}

	public void setIndex(int index)
	{
		this.index = index;
	}

	public List<Field> getFields()
	{
		return fields;
	}

	public void setFields(List<Field> fields)
	{
		this.fields = fields;
	}

}