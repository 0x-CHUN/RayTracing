public class Metal extends Material {
    public Vec3 albedo;
    public double fuzz;

    public Metal(Vec3 albedo, double fuzz) {
        this.albedo = albedo;
        this.fuzz = fuzz < 1 ? fuzz : 1;
    }

    public Metal(double e1, double e2, double e3, double fuzz) {
        this.albedo = new Vec3(e1, e2, e3);
        this.fuzz = fuzz < 1 ? fuzz : 1;
    }

    @Override
    public boolean scatter(Ray r_in, HitRecord rec, Vec3 attenuation, Ray scattered) {
        Vec3 reflected = Vec3.reflect(r_in.direction().normalize(), rec.normal);
        scattered.set(new Ray(rec.point, reflected.Add(Vec3.random_in_unit_sphere().Scale(fuzz))));
        attenuation.set(albedo);
        return (scattered.dir.Dot(rec.normal) > 0);
    }
}
