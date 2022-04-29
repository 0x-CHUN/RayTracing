public class Lambertian extends Material {
    public Vec3 albedo;

    public Lambertian(final Vec3 albedo) {
        this.albedo = albedo;
    }

    public Lambertian(double e1, double e2, double e3) {
        this.albedo = new Vec3(e1, e2, e3);
    }

    @Override
    public boolean scatter(Ray r_in, HitRecord rec, Vec3 attenuation, Ray scattered) {
        Vec3 scatter_direction = rec.normal.Add(Vec3.random_unit_vector());
        if (scatter_direction.near_zero()) {
            scatter_direction = rec.normal;
        }
        scattered.set(new Ray(rec.point, scatter_direction));
        attenuation.set(albedo);
        return true;
    }
}
