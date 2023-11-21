package com.example.galacticMessengerClient;

import java.util.Dictionary;
import java.util.Hashtable;

public class Session {

    private static Dictionary<String, Object> _data = new Hashtable<>();

    public static void setData(String key, Object data) {
        _data.put(key, data);
    }

    public static Object getData(String key) {
        return _data.get(key);
    }

    public static void deleteData(String key) {
        _data.remove(key);
    }

    public static boolean isEmpty() {
        return _data == null;
    }
}