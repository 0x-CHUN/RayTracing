public abstract class Material {
    public abstract boolean scatter(final Ray r_in, final HitRecord rec, Vec3 attenuation, Ray scattered);
}
