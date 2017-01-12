package com.yajol.gaode;

/**
 * Created by YJZX-LCH on 2016-11-15.
 */
public class Test {
    public void test() {


        testSimplePolygon();
//
//        testPolygonWithHoles();

//        testLatLngPolygon();
    }


    public static void testLatLngPolygon() {
        Polygon polygon = Polygon.Builder()
                .addVertex(new Point(116.38295, 40.09416))
                .addVertex(new Point(116.44037, 40.095898))
                .addVertex(new Point(116.448275, 40.083313))
                .addVertex(new Point(116.448455, 40.050818))
                .addVertex(new Point(116.448275, 40.038307))

                .addVertex(new Point(116.441448, 40.038418))

                .addVertex(new Point(116.436058, 40.038804))
                .addVertex(new Point(116.417302, 40.039136))
                .addVertex(new Point(116.414822, 40.039384))
                .addVertex(new Point(116.412738, 40.039329))
                .addVertex(new Point(116.407672, 40.085162))
                .addVertex(new Point(116.388628, 40.085162))
                .addVertex(new Point(116.383633, 40.084997))
                .build();

        // Point is inside
        isInside(polygon, new Point(116.414822, 40.039384));

        // Point isn't inside
        isInside(polygon, new Point(116.33269544, 10.26599516));
    }


    /**
     * Create simple polygon and test that the point is inside
     */
    public static void testSimplePolygon() {
        Polygon polygon = Polygon.Builder()
//                .addVertex(new Point(1, 3))
//                .addVertex(new Point(2, 8))
//                .addVertex(new Point(5, 4))
//                .addVertex(new Point(5, 9))
//                .addVertex(new Point(7, 5))
//                .addVertex(new Point(6, 1))
//                .addVertex(new Point(3, 1))
                .addVertex(new Point(3, 1))
                .addVertex(new Point(1, 3))
                .addVertex(new Point(3, 6))
                .addVertex(new Point(6, 6))
                .addVertex(new Point(4, 4))
//                .addVertex(new Point(3, 1))
//                .addVertex(new Point(3, 1))
                .build();

        // Point is inside
        isInside(polygon, new Point(5.5f, 7));

        // Point isn't inside
        isInside(polygon, new Point(1, 1));
    }

    /**
     * Create polygon two holes and test that the point is inside
     */
    public static void testPolygonWithHoles() {
        Polygon polygon = Polygon.Builder()
                .addVertex(new Point(1, 2)) // polygon
                .addVertex(new Point(1, 6))
                .addVertex(new Point(8, 7))
                .addVertex(new Point(8, 1))
                .close()
                .addVertex(new Point(2, 3)) // hole one
                .addVertex(new Point(5, 5))
                .addVertex(new Point(6, 2))
                .close()
                .addVertex(new Point(6, 6)) // hole two
                .addVertex(new Point(7, 6))
                .addVertex(new Point(7, 5))
                .build();

        // Point is inside
        isInside(polygon, new Point(6, 5));

        // Point isn't inside
        isInside(polygon, new Point(4, 3));

        // Point isn't inside
        isInside(polygon, new Point(6.5f, 5.8f));
    }

    /**
     * Check if point inside the polygon
     *
     * @param polygon
     * @param point
     */
    private static void isInside(Polygon polygon, Point point) {
        boolean contains = polygon.contains(point);
        System.out.println("The point:" + point.toString() + " is " + (contains ? "" : "not ") + "inside the polygon");
    }
}
