package com.kingmang.imggen.utils;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ColorValue {
    public static final Map<String, Color> rgb = new HashMap<>() {{
        put("red", new Color(255, 99, 132));
        put("orange", new Color(255, 159, 64));
        put("yellow", new Color(255, 205, 86));
        put("green", new Color(75, 192, 192));
        put("blue", new Color(54, 162, 235));
        put("purple", new Color(153, 102, 255));
        put("grey", new Color(201, 203, 207));
    }};

    public static final Color COLOR = rgb.get("purple");
    public static final Color BACKGROUND_COLOR = new Color(21, 23, 32);
}
