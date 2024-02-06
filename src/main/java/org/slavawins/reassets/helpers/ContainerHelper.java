package org.slavawins.reassets.helpers;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class ContainerHelper {

    public static void Set(ItemMeta meta, String name, String val) {
        meta.getPersistentDataContainer().set(NamespacedKey.fromString(name), PersistentDataType.STRING, val);
    }


    public static void Set(ItemMeta meta, String name, int val) {
        meta.getPersistentDataContainer().set(NamespacedKey.fromString(name), PersistentDataType.INTEGER, val);
    }

    public static int GetInt(ItemMeta meta, String name) {
        if (!meta.getPersistentDataContainer().has(NamespacedKey.fromString(name), PersistentDataType.INTEGER))
            return 0;
        return meta.getPersistentDataContainer().get(NamespacedKey.fromString(name), PersistentDataType.INTEGER);
    }

    public static double GetDouble(ItemMeta meta, String name) {
        if (!meta.getPersistentDataContainer().has(NamespacedKey.fromString(name), PersistentDataType.DOUBLE)) return 0;

        return meta.getPersistentDataContainer().get(NamespacedKey.fromString(name), PersistentDataType.DOUBLE);
    }

    public static String GetString(ItemMeta meta, String name) {
        if(meta==null)return  "";
        if (!meta.getPersistentDataContainer().has(NamespacedKey.fromString(name), PersistentDataType.STRING))
            return "";
        return meta.getPersistentDataContainer().get(NamespacedKey.fromString(name), PersistentDataType.STRING);
    }

    public static void Set(ItemMeta meta, String name, double val) {
        meta.getPersistentDataContainer().set(NamespacedKey.fromString(name), PersistentDataType.DOUBLE, val);
    }
}
