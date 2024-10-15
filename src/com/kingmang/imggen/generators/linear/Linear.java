package com.kingmang.imggen.generators.linear;

import com.kingmang.imggen.utils.ColorValue;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.*;

public class Linear {

    protected static final int WIDTH = 256;
    protected static final int HEIGHT = 256;


    protected static final List<Color> SWITCH_COLORS = new ArrayList<>(ColorValue.rgb.values());

    protected static final int MAX_ITERATIONS = 512;
    protected static final int[] RESIZE_TO = {WIDTH * 2, HEIGHT * 2};

    protected static final BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    protected static final Random random = new Random();
    protected static Color currentColor = ColorValue.COLOR;

    protected static List<int[]> workingStack = new ArrayList<>();

    static final int leftLimit = 97;
    static final int rightLimit = 122;
    static final int targetStringLength = 10;



    protected static void drawSide(int x, int y, int side) {
        if (random.nextInt(100) == 41) {
            currentColor = SWITCH_COLORS.get(random.nextInt(SWITCH_COLORS.size()));
        }

        if (side == 1 || side == 2 || side == 4 || side == 8 || side == 11 || side == 12 || side == 13 || side == 15) {
            rightEnter(x, y);
            if (x + 6 < WIDTH && isBackgroundColor(x + 6, y) && !containsPosition(workingStack, x + 6, y)) {
                workingStack.add(new int[]{x + 6, y});
            }
        }

        if (side == 1 || side == 2 || side == 6 || side == 9 || side == 10 || side == 13 || side == 14 || side == 15) {
            leftEnter(x, y);
            if (x - 6 >= 0 && isBackgroundColor(x - 6, y) && !containsPosition(workingStack, x - 6, y)) {
                workingStack.add(new int[]{x - 6, y});
            }
        }

        if (side == 1 || side == 3 || side == 5 || side == 8 || side == 9 || side == 12 || side == 13 || side == 14) {
            upEnter(x, y);
            if (y + 6 < HEIGHT && isBackgroundColor(x, y + 6) && !containsPosition(workingStack, x, y + 6)) {
                workingStack.add(new int[]{x, y + 6});
            }
        }

        if (side == 1 || side == 3 || side == 7 || side == 10 || side == 11 || side == 12 || side == 14 || side == 15) {
            downEnter(x, y);
            if (y - 6 >= 0 && isBackgroundColor(x, y - 6) && !containsPosition(workingStack, x, y - 6)) {
                workingStack.add(new int[]{x, y - 6});
            }
        }
    }

    private static void rightEnter(int x, int y) {
        for (int i = 0; i < 3; i++) {
            if (x + i < WIDTH) {
                image.setRGB(x + i, y, currentColor.getRGB());
            }
        }
    }

    private static void leftEnter(int x, int y) {
        for (int i = -2; i <= 0; i++) {
            if (x + i >= 0) {
                image.setRGB(x + i, y, currentColor.getRGB());
            }
        }
    }

    private static void upEnter(int x, int y) {
        for (int i = 0; i < 3; i++) {
            if (y + i < HEIGHT) {
                image.setRGB(x, y + i, currentColor.getRGB());
            }
        }
    }

    private static void downEnter(int x, int y) {
        for (int i = -2; i <= 0; i++) {
            if (y + i >= 0) {
                image.setRGB(x, y + i, currentColor.getRGB());
            }
        }
    }

    private static boolean isBackgroundColor(int x, int y) {
        return new Color(image.getRGB(x, y)).equals(ColorValue.BACKGROUND_COLOR);
    }

    private static boolean containsPosition(List<int[]> list, int x, int y) {
        for (int[] pos : list) {
            if (pos[0] == x && pos[1] == y) {
                return true;
            }
        }
        return false;
    }

    protected static void excludeSides(int x, int y, List<Integer> sides) {
        if (x + 6 < WIDTH && !isBackgroundColor(x + 6, y) && isBackgroundColor(x + 4, y)) {
            sides.removeAll(Arrays.asList(1, 2, 4, 8, 11, 12, 13, 15));
        }
    }

    protected static int randomChoice(int[] array) {
        return array[random.nextInt(array.length)];
    }

    protected static BufferedImage rotateImage90Degrees(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage rotated = new BufferedImage(height, width, image.getType());
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                rotated.setRGB(height - 1 - y, x, image.getRGB(x, y));
            }
        }
        return rotated;
    }

    protected static BufferedImage resizeImage(BufferedImage image, int newWidth, int newHeight) {
        BufferedImage resized = new BufferedImage(newWidth, newHeight, image.getType());
        for (int x = 0; x < newWidth; x++) {
            for (int y = 0; y < newHeight; y++) {
                int srcX = x * image.getWidth() / newWidth;
                int srcY = y * image.getHeight() / newHeight;
                resized.setRGB(x, y, image.getRGB(srcX, srcY));
            }
        }
        return resized;
    }
}