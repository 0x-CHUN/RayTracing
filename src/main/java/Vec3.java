import java.util.Arrays;

/**
 * 三维向量或者是RGB颜色
 */
public class Vec3 {
    public double[] e;

    public Vec3(double e0, double e1, double e2) {
        this.e = new double[]{e0, e1, e2};
    }

    public Vec3() {
        this.e = new double[3];
    }

    double x() {
        return e[0];
    }

    double y() {
        return e[1];
    }

    double z() {
        return e[2];
    }

    /**
     * 获取方向相反的向量
     */
    public Vec3 Neg() {
        return new Vec3(-e[0], -e[1], -e[2]);
    }

    /**
     * 与另外一个向量相加
     *
     * @param v 另外一个向量
     * @return 相加结果
     */
    public Vec3 Add(Vec3 v) {
        return new Vec3(e[0] + v.e[0], e[1] + v.e[1], e[2] + v.e[2]);
    }

    /**
     * 与另外一个向量相减
     *
     * @param v 另外一个向量
     * @return 相减结果
     */
    public Vec3 Sub(Vec3 v) {
        return new Vec3(e[0] - v.e[0], e[1] - v.e[1], e[2] - v.e[2]);
    }

    /**
     * 向量放缩
     *
     * @param c 放缩比例
     * @return 放缩后向量
     */
    public Vec3 Scale(double c) {
        return new Vec3(e[0] * c, e[1] * c, e[2] * c);
    }

    /**
     * 向量缩小
     *
     * @param c 缩小比例
     * @return 缩小后向量
     */
    public Vec3 Div(double c) {
        return new Vec3(e[0] / c, e[1] / c, e[2] / c);
    }

    /**
     * 向量长度
     */
    public double length() {
        return Math.sqrt(Math.pow(e[0], 2.0) + Math.pow(e[1], 2) + Math.pow(e[2], 2));
    }

    /**
     * 向量长度的平方
     */
    public double length_squared() {
        return Math.pow(e[0], 2.0) + Math.pow(e[1], 2) + Math.pow(e[2], 2);
    }

    /**
     * 向量相乘
     *
     * @param v 另外一个向量
     * @return 相乘结果
     */
    public Vec3 Multi(Vec3 v) {
        return new Vec3(e[0] * v.e[0], e[1] * v.e[1], e[2] * v.e[2]);
    }

    /**
     * 该向量的单位向量
     */
    public Vec3 normalize() {
        double length = this.length();
        return this.Div(length);
    }

    /**
     * 向量点乘
     */
    public double Dot(Vec3 v) {
        return (e[0] * v.e[0] + e[1] * v.e[1] + e[2] * v.e[2]);
    }

    /**
     * 向量叉乘
     */
    public Vec3 Cross(Vec3 v) {
        return new Vec3(e[1] * v.e[2] - e[2] * v.e[1], e[2] * v.e[0] - e[0] * v.e[2], e[0] * v.e[1] - e[1] * v.e[0]);
    }

    /**
     * 反射的向量
     *
     * @param v 入射的向量
     * @param n 法向量
     * @return 反射的向量
     */
    public static Vec3 reflect(final Vec3 v, final Vec3 n) {
        return v.Sub(n.Scale(2 * v.Dot(n)));
    }

    /**
     * 折射向量
     *
     * @param uv             入射向量
     * @param n              法向量
     * @param etai_over_etat 折射率
     * @return
     */
    public static Vec3 refract(final Vec3 uv, final Vec3 n, double etai_over_etat) {
        double cos_theta = Math.min(uv.Neg().Dot(n), 1.0);
        Vec3 r_out_perp = uv.Add(n.Scale(cos_theta)).Scale(etai_over_etat);
        Vec3 r_out_parallel = n.Scale(-Math.sqrt(Math.abs(1.0 - r_out_perp.length_squared())));
        return r_out_perp.Add(r_out_parallel);
    }

    public void set(final Vec3 v) {
        System.arraycopy(v.e, 0, this.e, 0, 3);
    }

    /**
     * 判断是否接近0
     */
    public boolean near_zero() {
        double s = 1e-8;
        return (Math.abs(e[0]) < s) && (Math.abs(e[1]) < s) && (Math.abs(e[2]) < s);
    }

    /**
     * 返回一个随机的三维向量
     */
    public static Vec3 random() {
        return new Vec3(Utils.RandomDouble(), Utils.RandomDouble(), Utils.RandomDouble());
    }

    /**
     * 返回范围内的随机的三维向量
     *
     * @param min 最小值
     * @param max 最大值
     */
    public static Vec3 random(double min, double max) {
        return new Vec3(Utils.RandomDouble(min, max), Utils.RandomDouble(min, max), Utils.RandomDouble(min, max));
    }

    public static Vec3 random_in_unit_sphere() {
        while (true) {
            Vec3 p = Vec3.random(-1, 1);
            if (p.length_squared() < 1) {
                return p;
            }
        }
    }

    public static Vec3 random_unit_vector() {
        double a = Utils.RandomDouble(0, 2 * Math.PI);
        double z = Utils.RandomDouble(-1, 1);
        double r = Math.sqrt(1.0 - z * z);
        return new Vec3(r * Math.cos(a), r * Math.sin(a), z);
    }

    public static Vec3 random_in_hemisphere(final Vec3 normal) {
        Vec3 in_unit_sphere = random_in_unit_sphere();
        if (in_unit_sphere.Dot(normal) > 0.0) {
            return in_unit_sphere;
        } else {
            return in_unit_sphere.Neg();
        }
    }

    public static Vec3 random_in_unit_disk() {
        while (true) {
            Vec3 p = new Vec3(Utils.RandomDouble(-1, 1), Utils.RandomDouble(-1, 1), 0);
            if (p.length_squared() < 1) {
                return p;
            }
        }
    }

    @Override
    public String toString() {
        return "Vec3{" + Arrays.toString(e) + "}";
    }

}
