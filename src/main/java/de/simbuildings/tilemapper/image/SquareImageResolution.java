package de.simbuildings.tilemapper.image;

/**
 * Created by SimBuildings on 11.10.21 at 16:55
 */
public class SquareImageResolution extends ImageResolution {

    public SquareImageResolution(int squareResolution) {
        super(squareResolution, squareResolution);
    }

    @Override
    public boolean isPowerOfTwo() {
        if (isSquare()) {
            // width and hight are the same
            return (getWidth() & (getWidth() - 1)) == 0;
        }
        return false;
    }

    @Override
    public boolean isSquare() {
        return true;
    }
}
