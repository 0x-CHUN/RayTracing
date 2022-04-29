public class Ray {
    /**
     * 光线发射点
     */
    public Vec3 orig;

    /**
     * 光线方向
     */
    public Vec3 dir;

    public Ray() {
        this.orig = new Vec3();
        this.dir = new Vec3();
    }

    public Ray(Vec3 orig, Vec3 dir) {
        this.orig = orig;
        this.dir = dir;
    }


    public Vec3 origin() {
        return this.orig;
    }

    public Vec3 direction() {
        return this.dir;
    }

    public Vec3 at(double t) {
        return orig.Add(dir.Scale(t));
    }

    public void set(Ray r) {
        this.orig = r.orig;
        this.dir = r.dir;
    }
}
