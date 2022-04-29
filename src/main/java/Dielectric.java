public class Dielectric extends Material {
    public double ir;

    public Dielectric(double ir) {
        this.ir = ir;
    }

    @Override
    public boolean scatter(final Ray r_in, final HitRecord rec, Vec3 attenuation, Ray scattered) {
        attenuation.set(new Vec3(1.0, 1.0, 1.0));
        double refraction_ratio = rec.front_face ? (1.0 / ir) : ir;
        Vec3 unit_direction = r_in.direction().normalize();
        double cos_theta = Math.min(unit_direction.Neg().Dot(rec.normal), 1.0);
        double sin_theta = Math.sqrt(1.0 - cos_theta * cos_theta);
        boolean cannot_refract = refraction_ratio * sin_theta > 1.0;
        Vec3 direction = (cannot_refract || reflectance(cos_theta, refraction_ratio) > Utils.RandomDouble()) ?
                Vec3.reflect(unit_direction, rec.normal) : Vec3.refract(unit_direction, rec.normal, refraction_ratio);
        scattered.set(new Ray(rec.point, direction));
        return true;
    }

    private static double reflectance(double cosine, double ref_idx) {
        double r0 = (1.0 - ref_idx) / (1.0 + ref_idx);
        r0 = r0 * r0;
        return r0 + (1 - r0) * Math.pow((1 - cosine), 5);
    }
}
