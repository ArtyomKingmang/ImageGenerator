package com.kingmang.imggen;

import com.kingmang.imggen.generators.Generator;
import com.kingmang.imggen.generators.linear.LinearGenerator;

import java.io.IOException;

public class Main {
    static Generator lingen = new LinearGenerator();
    static final int COUNT = 1;


    public static void main(String[] args) throws IOException {
        linearMapGenerate();
    }

    private static void linearMapGenerate() throws IOException {
        for(int i = 0; i < COUNT; i++)
            lingen.generate();
    }
}

