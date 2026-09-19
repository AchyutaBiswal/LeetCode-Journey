class Solution {
    public boolean checkOverlap(int radius, int xCenter, int yCenter,
                                int x1, int y1, int x2, int y2) {

        // Get the closest point of the rectangle to the circle's center
        int closestX = Math.max(x1, Math.min(xCenter, x2));
        int closestY = Math.max(y1, Math.min(yCenter, y2));

        // Calculate the distance from the center to that point
        int dx = xCenter - closestX;
        int dy = yCenter - closestY;

        // If the distance is within the radius, they overlap
        return dx * dx + dy * dy <= radius * radius;
    }
}
