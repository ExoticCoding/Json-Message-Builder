package com.exoticcode.jsonchatcreator.api.anvilgui;

public abstract class AnvilRunnable implements Runnable {
	
	private String text = null;

	public void setText(String text) {
		this.text = text;
	}
	
	public String getText() {
		return text;
	}
	
}
