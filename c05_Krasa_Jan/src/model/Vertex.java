package model;

import transforms.*;

import java.util.Optional;

public class Vertex {

    private final Point3D point;
    private final Col color;

    private final Vec2D texCoord;

    public Vertex(Point3D point, Col color) {
        this.point = point;
        this.color = color;
        // To ensure texture will apply only at triangles where all 3 textureCoordinates were defined.
        this.texCoord = new Vec2D(Long.MIN_VALUE,Long.MIN_VALUE);
    }

    public Vertex(Point3D point, Col color, Vec2D texCoord) {
        this.point = point;
        this.color = color;
        this.texCoord = texCoord;
    }

    public Vertex mul(double t) {
        return new Vertex(point.mul(t), color.mul(t), texCoord.mul(t));
    }

    public Vertex add(Vertex v) {
        return new Vertex(point.add(v.getPoint()), color.add(v.getColor()), texCoord.add(v.getTexCoord()));
    }

    public Optional<Vertex> dehomog() {
        Optional<Vec3D> dehomog = point.dehomog();
        if (dehomog.isPresent()) {
            return Optional.of(new Vertex(new Point3D(dehomog.get()), color, texCoord));
        } else {
            return Optional.empty();
        }
    }

    public Point3D getPoint() {
        return point;
    }

    public Col getColor() {
        return color;
    }

    public Vec2D getTexCoord() {
        return texCoord;
    }

    public double getX() {
        return point.getX();
    }

    public double getY() {
        return point.getY();
    }

    public double getZ() {
        return point.getZ();
    }

    public double getW() {
        return point.getW();
    }

}
