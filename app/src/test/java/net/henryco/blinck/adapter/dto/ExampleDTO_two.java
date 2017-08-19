package net.henryco.blinck.adapter.dto;

/**
 * Created by HenryCo on 19/08/17.
 */

public class ExampleDTO_two {

	private String name;
	private String text;
	private float value;
	private Object object;


	public ExampleDTO_two() {}
	public ExampleDTO_two(String name, String text, float value, Object object) {
		this.name = name;
		this.text = text;
		this.value = value;
		this.object = object;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public float getValue() {
		return value;
	}
	public void setValue(float value) {
		this.value = value;
	}
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}


	@Override
	public String toString() {
		return "ExampleDTO_two{" +
				"name='" + name + '\'' +
				", text='" + text + '\'' +
				", value=" + value +
				", object=" + object +
				'}';
	}
}
