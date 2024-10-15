package com.kingmang.imggen.generators.linear;

import com.kingmang.imggen.generators.Generator;
import com.kingmang.imggen.utils.ColorValue;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static com.kingmang.imggen.generators.linear.Linear.*;

public class LinearGenerator implements Generator {
    @Override
    public void generate() throws IOException {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                image.setRGB(x, y, ColorValue.BACKGROUND_COLOR.getRGB());
            }
        }

        int x = WIDTH / 2;
        int y = HEIGHT / 2;
        drawSide(x, y, randomChoice(new int[]{1, 12, 13, 14, 15}));

        int iters = 0;

        while (!workingStack.isEmpty()) {
            int[] pos = workingStack.remove(random.nextInt(workingStack.size()));
            x = pos[0];
            y = pos[1];

            List<Integer> sides = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15));

            excludeSides(x, y, sides);

            if (iters > MAX_ITERATIONS || x <= 12 || x >= (WIDTH - 12) || y <= 12 || y >= (HEIGHT - 12)) {
                for (int side = 4; side <= 7; side++) {
                    if (sides.contains(side)) {
                        sides = Arrays.asList(side);
                        break;
                    }
                }
            }

            int side = randomChoice(sides.stream().mapToInt(Integer::intValue).toArray());
            drawSide(x, y, side);

            iters++;
        }

        System.out.println("Generation done (" + iters + ")");

        BufferedImage rotatedImage = rotateImage90Degrees(image);
        BufferedImage resizedImage = resizeImage(rotatedImage, RESIZE_TO[0], RESIZE_TO[1]);
        Random randomm = new Random();
        String generatedString = randomm.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        ImageIO.write(resizedImage, "png", new File("GeneratedImageF".concat(generatedString).concat(".png")));
    }

}

