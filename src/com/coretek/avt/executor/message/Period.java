package com.coretek.avt.executor.message;

import java.util.ArrayList;
import java.util.List;

public class Period
{
	private int		index;	// �����±�

	private List<Field>	fields = new ArrayList<Field>();	// �ֶ�

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