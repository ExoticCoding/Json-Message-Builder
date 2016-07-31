package com.exoticcode.jsonchatcreator.api.chat;

public class HoverEvent {

	private HoverAction action;
	private String value;

	public HoverEvent(HoverAction action) {
		this.action = action;
	}

	public HoverEvent(HoverAction action, String value) {
		this.action = action;
		this.value = value;
	}

	public HoverEvent setValue(String value) {
		this.value = value;
		return this;
	}

	public HoverEvent setAction(HoverAction action) {
		this.action = action;
		return this;
	}

	public HoverAction getAction() {
		return action;
	}

	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		if (value == null || value.isEmpty() || action == null)
			return "{}";
		return "{\"action\":\"" + action.toString() + "\",\"value\":\"" + value + "\"}";
	}

	public enum HoverAction {

		SHOW_TEXT;

		@Override
		public String toString() {
			return super.toString().toLowerCase();
		}

	}

}
