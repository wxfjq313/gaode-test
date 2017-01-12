package com.yajol.gaode;

public class Point {
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double x;
    public double y;

    @Override
    public String toString() {
//		return String.format("(%4d,%4d)", x, y);
        return "x:" + x + ",y:" + y;
    }
}