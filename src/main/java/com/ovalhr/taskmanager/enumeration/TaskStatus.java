package com.ovalhr.taskmanager.enumeration;

/**
 * Created by rana on 8/27/21.
 */
public enum TaskStatus {
	OPEN("OPEN"),
	IN_PROGRESS("IN_PROGRESS"),
	CLOSED("CLOSED");
	private String text;

	TaskStatus(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
