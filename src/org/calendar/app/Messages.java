package org.calendar.app;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Messages class for I18N
 * 
 * @author gv
 * 
 */
public class Messages {
	// The bundle name.
	private static final String BUNDLE_NAME = "org.calendar.app.messages"; //$NON-NLS-1$
	// The resource bundle.
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	// Constructs the messages handler.
	private Messages() {
		// Do nothing.
	}

	/**
	 * Gets the string from the specified key.
	 * 
	 * @param key_p
	 *            The key.
	 * @return The string result.
	 */
	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
