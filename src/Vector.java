public class Vector {
    double x, y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector sub(Vector v) {
        return new Vector(x-v.x, y-v.y);
    }
    public Vector add(Vector v) {
        return new Vector(x+v.x, y+v.y);
    }
    public Vector div(double d) {
        return new Vector(x/d, y/d);
    }
    public Vector mult(double d) {
        return new Vector(x*d, y*d);
    }
    public double len() {
        return Math.sqrt(x*x + y*y);
    }
    public Vector normalize() {
        if (len() != 0) {
            return new Vector(x/len(), y/len());
        }
        else {
            return this;
        }
    }
    public double dot(Vector v) {
        return x*v.x + y*v.y;
    }
    public double dist(Vector v) {
        return Math.sqrt((x-v.x)*(x-v.x) + (y-v.y)*(y-v.y));
    }
}
