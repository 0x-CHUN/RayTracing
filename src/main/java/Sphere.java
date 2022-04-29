/**
 * 球面
 */
public class Sphere implements HitTable {
    /**
     * 球面中心位置
     */
    public Vec3 center;

    /**
     * 球面半径
     */
    public double radius;

    /**
     * 球面材料
     */
    public Material material;

    public Sphere(Vec3 center, double radius, Material material) {
        this.center = center;
        this.radius = radius;
        this.material = material;
    }

    @Override
    public boolean hit(Ray r, double tMin, double tMax, HitRecord rec) {
        Vec3 oc = r.origin().Sub(center);
        double a = r.direction().length_squared();
        double half_b = oc.Dot(r.direction());
        double c = oc.length_squared() - radius * radius;
        double discriminant = half_b * half_b - a * c;
        if (discriminant < 0) {
            return false;
        }
        double sqrtd = Math.sqrt(discriminant);
        double root = (-half_b - sqrtd) / a;
        if (root < tMin || tMax < root) {
            root = (-half_b + sqrtd) / a;
            if (root < tMin || tMax < root) {
                return false;
            }
        }
        rec.t = root;
        rec.point = r.at(rec.t);
        Vec3 outward_normal = (rec.point.Sub(center)).Div(radius);
        rec.set_face_normal(r, outward_normal);
        rec.material = material;
        return true;
    }
}
