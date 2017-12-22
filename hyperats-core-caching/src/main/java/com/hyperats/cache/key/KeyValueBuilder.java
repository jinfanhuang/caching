package com.hyperats.cache.key;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class KeyValueBuilder {
	private static final Logger logger = LogManager.getLogger(KeyValueBuilder.class);
	
	private static final String DEFAULT_ELEMENT_SEPERATOR = "/";

	private final String elementSeperator;
	private final StringBuffer keyValue;

	public KeyValueBuilder() {
		keyValue = new StringBuffer();
		this.elementSeperator = DEFAULT_ELEMENT_SEPERATOR;
	}

	public KeyValueBuilder(final String elementSeperator) {
		keyValue = new StringBuffer();
		this.elementSeperator = elementSeperator;
	}

	public KeyValueBuilder addElement(final Boolean element) {
		if (keyValue.length() > 0) {
			keyValue.append(elementSeperator);
		}

		keyValue.append(Boolean.toString(element));

		return this;
	}

	public KeyValueBuilder addElement(final Enum<?> element) {
		if (keyValue.length() > 0) {
			keyValue.append(elementSeperator);
		}

		keyValue.append(element.name());

		return this;
	}

	public KeyValueBuilder addElement(final Integer element) {
		if (keyValue.length() > 0) {
			keyValue.append(elementSeperator);
		}

		keyValue.append(Integer.toString(element));

		return this;
	}

	public KeyValueBuilder addElement(final String element) {
		if (keyValue.length() > 0) {
			keyValue.append(elementSeperator);
		}

		keyValue.append(element);

		return this;
	}

	public String getKeyValue() {
		String k = keyValue.toString();
		if (logger.isDebugEnabled()) {
			logger.debug("Key value: " + k);
		}
		return k;
	}
}