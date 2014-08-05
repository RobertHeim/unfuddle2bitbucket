package de.robert_heim.unfuddle2bitbucket.model.unfuddle;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class IntegerConverter extends XmlAdapter<String, Integer> {
	@Override
	public String marshal(Integer value) throws Exception {
		return value.toString();
	}

	@Override
	public Integer unmarshal(String storedValue) throws Exception {
		if (storedValue.trim().equalsIgnoreCase(""))
			return null;
		return Integer.valueOf(storedValue);
	}
}
