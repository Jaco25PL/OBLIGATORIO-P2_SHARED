/*
 * Autores: [Matías Piedra 354007], [Joaquin Piedra 304804] 
 */
package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FireworksAnimation {

    // ANSI escape codes for colors
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_MAGENTA = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    private static final String[] FIREWORK_COLORS = {
            ANSI_RED, ANSI_GREEN, ANSI_YELLOW, ANSI_BLUE, ANSI_MAGENTA, ANSI_CYAN
    };

    private static final char[] PARTICLE_CHARS = {'*', 'o', '+'};

    private final int width;
    private final int height;
    private final String[][] frameBuffer;
    private final List<FireworkInstance> activeFireworks;
    private final Random random;

    public FireworksAnimation(int width, int height) {
        this.width = width;
        this.height = height;
        this.frameBuffer = new String[height][width];
        this.activeFireworks = new ArrayList<>();
        this.random = new Random();
    }

    private void clearFrameBuffer() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                frameBuffer[i][j] = " ";
            }
        }
    }

    private void printFrame(boolean firstFrame) {
        StringBuilder sb = new StringBuilder();
        if (!firstFrame) {
            // Move cursor up by 'height' lines and clear screen from cursor down
            System.out.print(String.format("\033[%dA\033[0J", this.height));
        }

        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < width; j++) {
                sb.append(frameBuffer[i][j]);
            }
            sb.append("\n");
        }
        System.out.print(sb.toString());
        System.out.flush();
    }

    private String getRandomColor() {
        return FIREWORK_COLORS[random.nextInt(FIREWORK_COLORS.length)];
    }

    private char getRandomParticleChar() {
        return PARTICLE_CHARS[random.nextInt(PARTICLE_CHARS.length)];
    }

    public void play(int numberOfFireworks, int totalDurationMillis) {
        long frameDelayMillis = 120; // Controls speed of animation
        long startTime = System.currentTimeMillis();
        long nextFireworkLaunchTime = System.currentTimeMillis();
        int fireworksLaunched = 0;

        activeFireworks.clear();

        // Initial message and space for animation
        String winnerMessage = " ".repeat(Math.max(0,this.width/2 - 5)) + ANSI_YELLOW + "¡GANADOR!" + ANSI_RESET;
        System.out.println(winnerMessage);
        for(int i=0; i<this.height; i++) {
            System.out.println(); 
        }

        boolean firstFrame = true;
        while (System.currentTimeMillis() - startTime < totalDurationMillis) {
            long loopStartTime = System.currentTimeMillis();
            clearFrameBuffer();

            // Launch new fireworks
            if (fireworksLaunched < numberOfFireworks && loopStartTime >= nextFireworkLaunchTime) {
                activeFireworks.add(new FireworkInstance());
                fireworksLaunched++;
                if (fireworksLaunched < numberOfFireworks) {
                    long remainingTime = totalDurationMillis - (loopStartTime - startTime);
                    int fireworksRemaining = numberOfFireworks - fireworksLaunched;
                    if (fireworksRemaining > 0 && remainingTime > frameDelayMillis * 2) {
                        long averageDelay = remainingTime / fireworksRemaining;
                        // Stagger launch times
                        nextFireworkLaunchTime = loopStartTime + Math.max(frameDelayMillis, (long) (averageDelay * (0.6 + random.nextDouble() * 0.8)));
                    } else {
                        nextFireworkLaunchTime = Long.MAX_VALUE; // No more launches if time is short
                    }
                } else {
                    nextFireworkLaunchTime = Long.MAX_VALUE; // All fireworks launched
                }
            }

            // Update and draw active fireworks
            for (FireworkInstance fw : activeFireworks) {
                fw.updateAndDraw(this);
            }
            activeFireworks.removeIf(FireworkInstance::isFaded);

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

            // Exit condition: all fireworks launched and faded, and a good portion of duration passed
            if (fireworksLaunched >= numberOfFireworks && activeFireworks.isEmpty() &&
                (System.currentTimeMillis() - startTime) > totalDurationMillis * 0.75) {
                break;
            }
        }
        // Cleanup: Clear the animation area and reprint the winner message
        if (firstFrame) { // If animation didn't even start (e.g., duration too short)
             System.out.println(ANSI_RESET); // Just reset color
        } else {
            System.out.print(String.format("\033[%dA\033[0J", this.height)); // Move up and clear
            System.out.println(winnerMessage); // Reprint winner message
            for(int i=0; i<3;i++) System.out.println(); // Some spacing after
        }
        System.out.print(ANSI_RESET); // Ensure color is reset
    }

    // METHOD MOVED HERE
    // Helper to set a character in the frame buffer
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

        FireworkInstance() {
            this.currentX = (int) (width * 0.15 + random.nextDouble() * width * 0.7); // Launch from a range
            this.currentY = height - 1; // Start from bottom
            this.maxHeight = (int) (height * 0.1 + random.nextDouble() * height * 0.35); // Explode in upper part
            this.exploded = false;
            this.explosionStep = 0;
            this.maxExplosionSteps = 5 + random.nextInt(3); // Duration of explosion
            this.color = getRandomColor(); 
            this.rocketChar = (random.nextBoolean()) ? '^' : '|';
            this.creationTimeMillis = System.currentTimeMillis();
            this.timeToExplodeMillis = 500 + random.nextInt(400); // Time rocket ascends
        }

        void updateAndDraw(FireworksAnimation parent) {
            if (!exploded) {
                // Rocket ascending
                if (currentY > maxHeight && (System.currentTimeMillis() - creationTimeMillis < timeToExplodeMillis)) {
                    // Draw trail
                    for (int i = 1; i <= trailLength; i++) {
                        if (currentY + i < parent.height) {
                            parent.setPixel(currentX, currentY + i, this.color + "." + ANSI_RESET);
                        }
                    }
                    parent.setPixel(currentX, currentY, this.color + rocketChar + ANSI_RESET);
                    currentY--;
                } else {
                    // Explode
                    exploded = true;
                    explosionX = currentX;
                    explosionY = currentY;
                    explosionStep = 1;
                }
            }

            if (exploded && explosionStep <= maxExplosionSteps) {
                // Draw explosion particles
                int particlesThisStep = 5 + explosionStep * 2; 
                for (int i = 0; i < particlesThisStep; i++) {
                    double angle = (2 * Math.PI / particlesThisStep) * i + (random.nextDouble() * 0.5 - 0.25); // Add some randomness to angle
                    double radius = explosionStep * (1.0 + random.nextDouble() * 0.6); 
                    double gravityEffect = 0.1 * explosionStep * explosionStep; // Particles fall a bit
                    
                    int px = explosionX + (int) Math.round(Math.cos(angle) * radius * 1.8); // Horizontal spread (1.8 for wider look)
                    int py = explosionY + (int) Math.round(Math.sin(angle) * radius + gravityEffect);

                    if (random.nextDouble() > 0.1) { // Don't draw every potential particle for a sparser look
                        parent.setPixel(px, py, parent.getRandomColor() + parent.getRandomParticleChar() + ANSI_RESET); // This call is now correct
                    }
                }
                explosionStep++;
            }
        }

        boolean isFaded() {
            // Firework is done after explosion finishes + a couple of steps for particles to clear
            return exploded && explosionStep > maxExplosionSteps + 2;
        }
    }
}
