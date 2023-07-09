package ninemanmorris.screen.screencontroller;

import java.util.HashMap;

/**
 * Utility class for intents used alongside scene switching to pass
 * information between scenes
 */
public class Intent {
    
    // Item Storage
    private HashMap<String, Object> items;

    /**
     * Intent constructor for creating an Intent
     */
    public Intent() {
        items = new HashMap<>();
    }

    /**
     * Add items to the intent object
     * @param key - string which represents key mapped to value
     * @param value - Object to be mapped to string key
     */
    public void addItems(String key, Object value) {
        items.put(key, value);
    }

    /**
     * Get item value from an intent object with a particular key
     * @param <T> - The type of the item
     * @param key - string which represents key in intent
     * @return value mapped to the key in the intent object
     */
    @SuppressWarnings("unchecked")
    public <T>T getItem(String key) {
        return (T)items.get(key);
    }
}
