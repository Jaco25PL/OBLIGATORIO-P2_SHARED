/*
 * Autores: [Matías Piedra 354007], [Joaquin Piedra 304804] 
 */
package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FireworksAnimation {

    private String ansiReset;
    private String ansiRed;
    private String ansiGreen;
    private String ansiYellow;
    private String ansiBlue;
    private String ansiMagenta;
    private String ansiCyan;

    private String[] fireworkColors;
    private char[] particleChars;

    private int width;
    private int height;
    private String[][] frameBuffer;
    private List<FireworkInstance> activeFireworks;
    private Random random;

    // Crea nueva animación fuegos.
    public FireworksAnimation(int width, int height) {
        this.width = width;
        this.height = height;
        this.frameBuffer = new String[height][width];
        this.activeFireworks = new ArrayList<>();
        this.random = new Random();

        this.ansiReset = "\u001B[0m";
        this.ansiRed = "\u001B[31m";
        this.ansiGreen = "\u001B[32m";
        this.ansiYellow = "\u001B[33m";
        this.ansiBlue = "\u001B[34m";
        this.ansiMagenta = "\u001B[35m";
        this.ansiCyan = "\u001B[36m";

        this.fireworkColors = new String[]{
                this.ansiRed, this.ansiGreen, this.ansiYellow, this.ansiBlue, this.ansiMagenta, this.ansiCyan
        };
        this.particleChars = new char[]{'*', 'o', '+'};
    }

    // Limpia el buffer de fotogramas.
    private void clearFrameBuffer() {
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                this.frameBuffer[i][j] = " ";
            }
        }
    }

    // Imprime fotograma en consola.
    private void printFrame(boolean firstFrame) {
        String frameString = "";
        if (!firstFrame) {
            System.out.print(String.format("\033[%dA\033[0J", this.height));
        }

        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                frameString += this.frameBuffer[i][j];
            }
            frameString += "\n";
        }
        System.out.print(frameString);
        System.out.flush();
    }

    // Obtiene color aleatorio.
    String getRandomColor() {
        return this.fireworkColors[this.random.nextInt(this.fireworkColors.length)];
    }

    // Obtiene partícula aleatoria.
    char getRandomParticleChar() {
        return this.particleChars[this.random.nextInt(this.particleChars.length)];
    }

    Random getRandom() {
        return this.random;
    }

    int getWidth() {
        return this.width;
    }

    int getHeight() {
        return this.height;
    }

    String getAnsiReset() {
        return this.ansiReset;
    }

    // Inicia la animación de fuegos.
    public void play(int numberOfFireworks, int totalDurationMillis) {
        long frameDelayMillis = 120;
        long startTime = System.currentTimeMillis();
        int fireworksLaunched = 0;

        this.activeFireworks.clear();
        System.out.println(); 

        boolean firstFrame = true;
        boolean animationShouldContinue = true; 

        while (animationShouldContinue) {
            if (System.currentTimeMillis() - startTime >= totalDurationMillis) {
                animationShouldContinue = false;
                continue; 
            }

            long loopStartTime = System.currentTimeMillis();
            clearFrameBuffer();

            if (fireworksLaunched < numberOfFireworks) {
                long expectedLaunchTime = startTime + (fireworksLaunched * (totalDurationMillis / Math.max(1, numberOfFireworks)));
                if (loopStartTime >= expectedLaunchTime) {
                    this.activeFireworks.add(new FireworkInstance(this));
                    fireworksLaunched++;
                }
            }

            for (FireworkInstance fw : this.activeFireworks) {
                fw.updateAndDraw();
            }
            this.activeFireworks.removeIf(FireworkInstance::isFaded);

            printFrame(firstFrame);
            firstFrame = false;

            long loopEndTime = System.currentTimeMillis();
            long sleepTime = frameDelayMillis - (loopEndTime - loopStartTime);

            if (sleepTime > 0) {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.err.println("Animación interrumpida.");
                    animationShouldContinue = false; 
                }
            }

            if (!animationShouldContinue) {
                continue;
            }

            if (fireworksLaunched >= numberOfFireworks &&
                this.activeFireworks.isEmpty() &&
                (System.currentTimeMillis() - startTime) > totalDurationMillis * 0.75) {
                animationShouldContinue = false; 
            }
        }

        if (firstFrame) {
             System.out.println(this.ansiReset);
        } else {
            System.out.print(String.format("\033[%dA\033[0J", this.height));
            System.out.println();
        }
        System.out.print(this.ansiReset);
    }

    // Establece pixel en buffer.
    void setPixel(int x, int y, String coloredChar) {
        if (x >= 0 && x < this.width && y >= 0 && y < this.height) {
            this.frameBuffer[y][x] = coloredChar;
        }
    }
}
