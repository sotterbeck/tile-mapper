package de.simbuildings.tilemapper.image;

public class SquareImageResolution extends ImageResolution {

    public SquareImageResolution(int squareResolution) {
        super(squareResolution, squareResolution);
    }

    @Override
    public boolean isPowerOfTwo() {
        if (isSquare()) {
            return (getWidth() & (getWidth() - 1)) == 0;
        }
        return false;
    }

    @Override
    public boolean isSquare() {
        return true;
    }

    /**
     * Returns the string representation of a square image resolution.
     * The string contains the square resolution followed by a "x" symbol.
     * A square resolution of 64 would output as "64x".
     */
    @Override
    public String toString() {
        return getHeight() + "x";
    }
}
