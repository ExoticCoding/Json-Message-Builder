package com.exoticcode.jsonchatcreator.api.chat;

public class ClickEvent {

	private ClickAction action;
	private String value;

	public ClickEvent(ClickAction action) {
		this.action = action;
	}

	public ClickEvent(ClickAction action, String value) {
		this.action = action;
		this.value = value;
	}

	public ClickEvent setValue(String value) {
		this.value = value;
		return this;
	}

	public ClickEvent setAction(ClickAction action) {
		this.action = action;
		return this;
	}

	public ClickAction getAction() {
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

	public enum ClickAction {

		OPEN_URL, OPEN_FILE, RUN_COMMAND, SUGGEST_COMMAND;

		@Override
		public String toString() {
			return super.toString().toLowerCase();
		}

	}

}
