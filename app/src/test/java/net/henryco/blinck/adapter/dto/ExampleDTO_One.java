package net.henryco.blinck.adapter.dto;

import net.henryco.blinck.adapter.imp.ExampleAdapter;
import net.henryco.blinck.util.adapter.Adapter;

/**
 * Created by HenryCo on 19/08/17.
 */
@Adapter({ExampleAdapter.class})
public class ExampleDTO_One {

	private String someName;
	private String someText;
	private float someValue;
	private Object someObject;

	public ExampleDTO_One() {}
	public ExampleDTO_One(String someName, String someText, float someValue, Object someObject) {
		this.someName = someName;
		this.someText = someText;
		this.someValue = someValue;
		this.someObject = someObject;
	}


	@Override
	public String toString() {
		return "ExampleDTO_One{" +
				"someName='" + someName + '\'' +
				", someText='" + someText + '\'' +
				", someValue=" + someValue +
				", someObject=" + someObject +
				'}';
	}

	public String getSomeName() {
		return someName;
	}
	public void setSomeName(String someName) {
		this.someName = someName;
	}
	public String getSomeText() {
		return someText;
	}
	public void setSomeText(String someText) {
		this.someText = someText;
	}
	public float getSomeValue() {
		return someValue;
	}
	public void setSomeValue(float someValue) {
		this.someValue = someValue;
	}
	public Object getSomeObject() {
		return someObject;
	}
	public void setSomeObject(Object someObject) {
		this.someObject = someObject;
	}

}
