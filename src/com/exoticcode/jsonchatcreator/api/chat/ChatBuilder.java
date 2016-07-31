package com.exoticcode.jsonchatcreator.api.chat;

import java.util.LinkedList;

public class ChatBuilder {

	private LinkedList<TextComponent> components;
	private TextComponent current;

	public ChatBuilder() {
		this(new TextComponent());
	}

	public ChatBuilder(TextComponent component) {
		components = new LinkedList<>();
		current = component;
	}

	public void next(TextComponent component) {
		TextComponent next = current;
		components.add(next);
		current = component;
	}

	public void next() {
		TextComponent next = current;
		components.add(next);
		current = new TextComponent();
	}

	public void setComponents(LinkedList<TextComponent> components) {
		this.components = components;
	}

	public LinkedList<TextComponent> getComponents() {
		return components;
	}

	public void setCurrent(TextComponent current) {
		this.current = current;
	}

	public TextComponent getCurrent() {
		return current;
	}

	public String build() {
		if (current.getText() != null)
			next();
		StringBuilder builder = new StringBuilder("[");
		for (TextComponent component : components)
			builder.append(component.toString() + ",");
		builder.deleteCharAt(builder.length() - 1);
		builder.append("]");
		components.removeLast();
		return builder.toString();
	}

}
