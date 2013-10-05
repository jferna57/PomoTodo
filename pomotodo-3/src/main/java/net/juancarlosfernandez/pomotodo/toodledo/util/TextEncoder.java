package net.juancarlosfernandez.pomotodo.toodledo.util;

public class TextEncoder {
    public static String encode(String original) {
        return original.replace("&", "%26").replace(";", "%3B");
    }
}
