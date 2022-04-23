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
}
