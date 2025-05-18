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
        StringBuilder sb = new StringBuilder();
        if (!firstFrame) {
            System.out.print(String.format("\033[%dA\033[0J", this.height));
        }

        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                sb.append(this.frameBuffer[i][j]);
            }
            sb.append("\n");
        }
        System.out.print(sb.toString());
        System.out.flush();
    }

    // Obtiene color aleatorio.
    private String getRandomColor() {
        return this.fireworkColors[this.random.nextInt(this.fireworkColors.length)];
    }

    // Obtiene partícula aleatoria.
    private char getRandomParticleChar() {
        return this.particleChars[this.random.nextInt(this.particleChars.length)];
    }

    // Inicia la animación de fuegos.
    public void play(int numberOfFireworks, int totalDurationMillis) {
        long frameDelayMillis = 120;
        long startTime = System.currentTimeMillis();
        int fireworksLaunched = 0;

        this.activeFireworks.clear();
        System.out.println();

        boolean firstFrame = true;
        while (System.currentTimeMillis() - startTime < totalDurationMillis) {
            long loopStartTime = System.currentTimeMillis();
            clearFrameBuffer();

            if (fireworksLaunched < numberOfFireworks) {
                long expectedLaunchTime = startTime + (fireworksLaunched * (totalDurationMillis / Math.max(1, numberOfFireworks)));
                if (loopStartTime >= expectedLaunchTime) {
                    this.activeFireworks.add(new FireworkInstance());
                    fireworksLaunched++;
                }
            }

            for (FireworkInstance fw : this.activeFireworks) {
                fw.updateAndDraw(this);
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
                    break;
                }
            }

            if (fireworksLaunched >= numberOfFireworks && this.activeFireworks.isEmpty() &&
                (System.currentTimeMillis() - startTime) > totalDurationMillis * 0.75) {
                break;
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
    private void setPixel(int x, int y, String coloredChar) {
        if (x >= 0 && x < this.width && y >= 0 && y < this.height) {
            this.frameBuffer[y][x] = coloredChar;
        }
    }

    private class FireworkInstance {
        int currentX, currentY;
        int explosionX, explosionY;
        int maxHeight;
        boolean exploded;
        int explosionStep;
        int maxExplosionSteps;
        int trailLength = 2;
        String color;
        char rocketChar;
        long creationTimeMillis;
        long timeToExplodeMillis;

        // Crea instancia de fuego artificial.
        FireworkInstance() {
            this.currentX = (int) (FireworksAnimation.this.width * 0.15 + FireworksAnimation.this.random.nextDouble() * FireworksAnimation.this.width * 0.7);
            this.currentY = FireworksAnimation.this.height - 1;
            this.maxHeight = (int) (FireworksAnimation.this.height * 0.1 + FireworksAnimation.this.random.nextDouble() * FireworksAnimation.this.height * 0.35);
            this.exploded = false;
            this.explosionStep = 0;
            this.maxExplosionSteps = 5 + FireworksAnimation.this.random.nextInt(3);
            this.color = FireworksAnimation.this.getRandomColor();
            this.rocketChar = (FireworksAnimation.this.random.nextBoolean()) ? '^' : '|';
            this.creationTimeMillis = System.currentTimeMillis();
            this.timeToExplodeMillis = 500 + FireworksAnimation.this.random.nextInt(400);
        }

        // Actualiza y dibuja fuego.
        void updateAndDraw(FireworksAnimation parent) {
            if (!exploded) {
                if (currentY > maxHeight && (System.currentTimeMillis() - creationTimeMillis < timeToExplodeMillis)) {
                    for (int i = 1; i <= trailLength; i++) {
                        if (currentY + i < parent.height) {
                            parent.setPixel(currentX, currentY + i, this.color + "." + parent.ansiReset);
                        }
                    }
                    parent.setPixel(currentX, currentY, this.color + rocketChar + parent.ansiReset);
                    currentY--;
                } else {
                    exploded = true;
                    explosionX = currentX;
                    explosionY = currentY;
                    explosionStep = 1;
                }
            }

            if (exploded && explosionStep <= maxExplosionSteps) {
                int particlesThisStep = 5 + explosionStep;
                int currentRadius = explosionStep * 2;
                for (int i = 0; i < particlesThisStep; i++) {
                    int offsetX = parent.random.nextInt(2 * currentRadius + 1) - currentRadius;
                    int offsetY = parent.random.nextInt(2 * currentRadius + 1) - currentRadius;
                    offsetY += explosionStep / 2; 

                    int px = explosionX + offsetX;
                    int py = explosionY + offsetY;

                    if (parent.random.nextDouble() > 0.2) {
                        parent.setPixel(px, py, parent.getRandomColor() + parent.getRandomParticleChar() + parent.ansiReset);
                    }
                }
                explosionStep++;
            }
        }

        // Verifica si fuego se desvaneció.
        boolean isFaded() {
            return exploded && explosionStep > maxExplosionSteps + 2;
        }
    }
}
