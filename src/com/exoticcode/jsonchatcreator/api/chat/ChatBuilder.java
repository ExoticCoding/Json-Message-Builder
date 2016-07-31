package com.exoticcode.jsonchatcreator.api.chat;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ChatBuilder {

	private List<TextComponent> components;
	private TextComponent current;

	public ChatBuilder() {
		this(new TextComponent());
	}

	public ChatBuilder(TextComponent component) {
		components = new ArrayList<>();
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

	public List<TextComponent> getComponents() {
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
		return builder.toString();
	}

}
