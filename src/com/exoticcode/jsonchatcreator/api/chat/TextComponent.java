package com.exoticcode.jsonchatcreator.api.chat;

public class TextComponent implements Cloneable {

	private String text;
	private JsonColor color;
	private boolean bold = false;
	private boolean italic = false;
	private boolean underlined = false;
	private boolean strikethrough = false;
	private boolean obfuscated = false;
	private ClickEvent clickEvent = null;
	private HoverEvent hoverEvent = null;

	public TextComponent() {
		this(null, null);
	}

	public TextComponent(String string) {
		this(string, null);
	}

	public TextComponent(String string, JsonColor color) {
		this.text = string;
		this.color = color;
	}

	public ClickEvent getClickEvent() {
		return clickEvent;
	}

	public HoverEvent getHoverEvent() {
		return hoverEvent;
	}

	public JsonColor getColor() {
		return color;
	}

	public String getText() {
		return text;
	}

	public boolean isBold() {
		return bold;
	}

	public boolean isItalic() {
		return italic;
	}

	public boolean isObfuscated() {
		return obfuscated;
	}

	public boolean isStrikethrough() {
		return strikethrough;
	}

	public boolean isUnderlined() {
		return underlined;
	}

	public TextComponent setBold(boolean bold) {
		this.bold = bold;
		return this;
	}

	public TextComponent setClickEvent(ClickEvent clickEvent) {
		this.clickEvent = clickEvent;
		return this;
	}

	public TextComponent setColor(JsonColor color) {
		this.color = color;
		return this;
	}

	public TextComponent setHoverEvent(HoverEvent hoverEvent) {
		this.hoverEvent = hoverEvent;
		return this;
	}

	public TextComponent setItalic(boolean italic) {
		this.italic = italic;
		return this;
	}

	public TextComponent setObfuscated(boolean obfuscated) {
		this.obfuscated = obfuscated;
		return this;
	}

	public TextComponent setStrikethrough(boolean strikethrough) {
		this.strikethrough = strikethrough;
		return this;
	}

	public TextComponent setText(String text) {
		this.text = text;
		return this;
	}

	public TextComponent setUnderlined(boolean underlined) {
		this.underlined = underlined;
		return this;
	}

	public String toConsoleString() {
		if (text == null)
			return "null";
		if (color != null)
			return color.color().toString() + text;
		return text;
	}

	@Override
	public String toString() {
		if (text == null || text.isEmpty())
			return "{\"text\":\"null\"}";
		StringBuilder builder = new StringBuilder("{\"text\":\"" + text.replace('&', '§') + "\"");
		if (color != null)
			builder.append(",\"color\":\"" + color.toString() + "\"");
		if (bold)
			builder.append(",\"bold\":\"true\"");
		if (italic)
			builder.append(",\"italic\":\"true\"");
		if (underlined)
			builder.append(",\"underlined\":\"true\"");
		if (strikethrough)
			builder.append(",\"strikethrough\":\"true\"");
		if (obfuscated)
			builder.append(",\"obfuscated\":\"true\"");
		if (clickEvent != null)
			builder.append(",\"clickEvent\":" + clickEvent.toString());
		if (hoverEvent != null)
			builder.append(",\"hoverEvent\":" + hoverEvent.toString());
		return builder.append("}").toString();
	}

	public TextComponent clone() {
		TextComponent clone = new TextComponent(text, color);
		clone.setBold(bold);
		clone.setClickEvent(clickEvent);
		clone.setHoverEvent(hoverEvent);
		clone.setItalic(italic);
		clone.setObfuscated(obfuscated);
		clone.setStrikethrough(strikethrough);
		clone.setUnderlined(underlined);
		return clone;
	}

}
