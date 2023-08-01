package org.javaboy.tcrpc.util;

/**
 * @author:majin.wj
 */
public class ReflectUtil {


    public static String typesToString(Class<?>[] types) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < types.length; i++) {
            builder.append(types[i].getName());
            if (i != types.length - 1) {
                builder.append(",");
            }
        }
        return builder.toString();
    }

}
