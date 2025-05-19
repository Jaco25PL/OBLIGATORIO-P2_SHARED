package model;

import java.util.Random;

public class FireworkInstance {
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
    private FireworksAnimation parentAnimation; 

    // Crea instancia de fuego artificial.
    public FireworkInstance(FireworksAnimation parentAnimation) {
        this.parentAnimation = parentAnimation;
        Random random = parentAnimation.getRandom(); 

        this.currentX = (int) (parentAnimation.getWidth() * 0.15 +
                random.nextDouble() * parentAnimation.getWidth() * 0.7);
        this.maxHeight = (int) (parentAnimation.getHeight() * 0.1 +
                random.nextDouble() * parentAnimation.getHeight() * 0.35);
        this.currentY = parentAnimation.getHeight() - 1;
        this.exploded = false;
        this.explosionStep = 0;
        this.maxExplosionSteps = 5 + random.nextInt(3);
        this.color = parentAnimation.getRandomColor();
        this.rocketChar = (random.nextBoolean()) ? '^' : '|';
        this.creationTimeMillis = System.currentTimeMillis();
        this.timeToExplodeMillis = 500 + random.nextInt(400);
    }

    // Actualiza y dibuja fuego.
    void updateAndDraw() { 
        if (!exploded) {
            if (currentY > maxHeight && (System.currentTimeMillis() - creationTimeMillis < timeToExplodeMillis)) {
                for (int i = 1; i <= trailLength; i++) {
                    if (currentY + i < parentAnimation.getHeight()) {
                        parentAnimation.setPixel(currentX, currentY + i, this.color + "." + parentAnimation.getAnsiReset());
                    }
                }
                parentAnimation.setPixel(currentX, currentY, this.color + rocketChar + parentAnimation.getAnsiReset());
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
            Random random = parentAnimation.getRandom(); 
            for (int i = 0; i < particlesThisStep; i++) {
                int offsetX = random.nextInt(2 * currentRadius + 1) - currentRadius;
                int offsetY = random.nextInt(2 * currentRadius + 1) - currentRadius;
                offsetY += explosionStep / 2;

                int px = explosionX + offsetX;
                int py = explosionY + offsetY;

                if (random.nextDouble() > 0.2) {
                    parentAnimation.setPixel(px, py, parentAnimation.getRandomColor() + parentAnimation.getRandomParticleChar() + parentAnimation.getAnsiReset());
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
