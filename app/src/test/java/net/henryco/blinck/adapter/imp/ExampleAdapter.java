package net.henryco.blinck.adapter.imp;

import net.henryco.blinck.adapter.dto.ExampleDTO_One;
import net.henryco.blinck.adapter.dto.ExampleDTO_two;
import net.henryco.blinck.util.adapter.BlinckDataAdapter;

/**
 * Created by HenryCo on 19/08/17.
 */

public class ExampleAdapter implements BlinckDataAdapter<ExampleDTO_One, ExampleDTO_two> {

	@Override
	public ExampleDTO_One adapt1(ExampleDTO_two data) {
		ExampleDTO_One one = new ExampleDTO_One();
		one.setSomeName(data.getName());
		one.setSomeText(data.getText());
		one.setSomeValue(data.getValue());
		one.setSomeObject(data.getObject());
		return one;
	}

	@Override
	public ExampleDTO_two adapt2(ExampleDTO_One data) {
		ExampleDTO_two two = new ExampleDTO_two();
		two.setName(data.getSomeName());
		two.setText(data.getSomeText());
		two.setValue(data.getSomeValue());
		two.setObject(data.getSomeObject());
		return two;
	}
}
