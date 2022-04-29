/**
 * Hit的记录
 */
public class HitRecord {

    /**
     * 相交点
     */
    public Vec3 point;

    /**
     * 相交法向量
     */
    public Vec3 normal;

    public double t;

    /**
     * 光线在球面内还是外
     */
    public boolean front_face;

    /**
     * Hit中的材料
     */
    public Material material;

    public HitRecord() {
        this.point = new Vec3();
        this.t = 0;
        this.normal = new Vec3();
    }

    public void set_face_normal(Ray r, Vec3 outward_normal) {
        front_face = r.direction().Dot(outward_normal) < 0;
        normal.set(front_face ? outward_normal : outward_normal.Neg());
    }

    public void set(HitRecord rec) {
        this.point.set(rec.point);
        this.normal.set(rec.normal);
        this.t = rec.t;
        this.front_face = rec.front_face;
        this.material = rec.material;
    }
}
