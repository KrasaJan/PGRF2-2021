package rasterize;

import java.util.Optional;

public interface Raster<T> {

    /**
     * Checks whether the element is within the bounds of canvas
     *
     * @param x x coordinate
     * @param y y coordinate
     * @return true, if element is within canvas
     */
    default boolean checkBounds(int x, int y) {
        return (x >= 0 && y >= 0) && (x < getWidth() && y < getHeight());
    }

    /**
     * Clear canvas
     */
    void clear();

    /**
     * Set clear value
     *
     * @param clearValue clear value
     */
    void setClearValue(T clearValue);

    /**
     * Get horizontal size
     *
     * @return width
     */
    int getWidth();

    /**
     * Get vertical size
     *
     * @return height
     */
    int getHeight();

    /**
     * Get value at [x,y] position
     *
     * @param x horizontal coordinate
     * @param y vertical coordinate
     * @return value
     */
    Optional<T> getElement(int x, int y);

    /**
     * Set value at [x,y] position
     *
     * @param x     horizontal coordinate
     * @param y     vertical coordinate
     * @param value value
     */
    void setElement(int x, int y, T value);

}
