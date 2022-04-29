import java.io.FileWriter;
import java.io.IOException;

public class Render {
    public static void main(String[] args) throws IOException {
        // Image
        double aspect_ratio = 3.0 / 2.0;
        int width = 400;
        int height = (int) (width / aspect_ratio);
        int samples_per_pixel = 500;
        int max_depth = 50;
        // world
        HitTableList world = randomScene();

        // camera
        Vec3 lookFrom = new Vec3(13, 2, 3);
        Vec3 lookAt = new Vec3(0, 0, 0);
        Vec3 vup = new Vec3(0, 1, 0);
        double dist_to_focus = 10.0;
        double aperture = 0.1;

        Camera camera = new Camera(lookFrom, lookAt, vup, 20.0, aspect_ratio, aperture, dist_to_focus);
        // render
        FileWriter writer = new FileWriter("image.ppm");
        writer.write(String.format("P3\n%d %d\n255\n", width, height));
        ProcessBar processBar = new ProcessBar(height - 1);
        for (int j = height - 1; j >= 0; j--) {
            processBar.step(1);
            for (int i = 0; i < width; i++) {
                Vec3 pixel_color = new Vec3();
                for (int s = 0; s < samples_per_pixel; s++) {
                    double u = (i + Utils.RandomDouble()) / (width - 1);
                    double v = (j + Utils.RandomDouble()) / (height - 1);
                    Ray ray = camera.get_ray(u, v);
                    pixel_color = pixel_color.Add(ray_color(ray, world, max_depth));
                }
                Utils.WriteColor(writer, pixel_color, samples_per_pixel);
            }
        }
    }

    public static Vec3 ray_color(Ray ray, HitTable world, int depth) {
        HitRecord rec = new HitRecord();
        if (depth <= 0) {
            return new Vec3(0, 0, 0);
        }
        if (world.hit(ray, 0.001, Double.POSITIVE_INFINITY, rec)) {
            Ray scattered = new Ray();
            Vec3 attenuation = new Vec3();
            if (rec.material.scatter(ray, rec, attenuation, scattered)) {
                return attenuation.Multi(ray_color(scattered, world, depth - 1));
            }
            return new Vec3(0, 0, 0);
//            Vec3 target = rec.point.Add(rec.normal).Add(Vec3.random_unit_vector());
//            Vec3 target = rec.point.Add(rec.normal).Add(Vec3.random_in_unit_sphere());
//            Vec3 target = rec.point.Add(rec.normal).Add(Vec3.random_in_hemisphere(rec.normal));
//            return ray_color(new Ray(rec.point, target.Sub(rec.point)), world, depth - 1).Scale(0.5);
        }
        Vec3 unit_dir = ray.dir.normalize();
        double t = 0.5 * (unit_dir.y() + 1.0);
        return new Vec3(1.0, 1.0, 1.0).Scale(1.0 - t).Add(new Vec3(0.5, 0.7, 1.0).Scale(t));
    }

    public static HitTableList randomScene() {
        // World
        HitTableList world = new HitTableList();

        Material ground_material = new Lambertian(0.5, 0.5, 0.5);
        world.add(new Sphere(new Vec3(0, -1000, 0), 1000, ground_material));
        for (int a = -11; a < 11; a++) {
            for (int b = -11; b < 11; b++) {
                double choose_mat = Utils.RandomDouble();
                Vec3 center = new Vec3(a + 0.9 * Utils.RandomDouble(), 0.2, b + 0.9 * Utils.RandomDouble());
                if (center.Sub(new Vec3(4, 0.3, 0)).length() > 0.9) {
                    Material sphere_material;
                    if (choose_mat < 0.8) {
                        // diffuse
                        Vec3 albedo = Vec3.random().Multi(Vec3.random());
                        sphere_material = new Lambertian(albedo);
                        world.add(new Sphere(center, 0.2f, sphere_material));
                    } else if (choose_mat < 0.95) {
                        // metal
                        Vec3 albedo = Vec3.random(0.5, 1);
                        double fuzz = Utils.RandomDouble(0, 0.5);
                        sphere_material = new Metal(albedo, fuzz);
                        world.add(new Sphere(center, 0.2, sphere_material));
                    } else {
                        // glass
                        sphere_material = new Dielectric(1.5);
                        world.add(new Sphere(center, 0.2, sphere_material));
                    }
                }
            }
        }

        Material material1 = new Dielectric(1.5);
        world.add(new Sphere(new Vec3(0.0, 1.0, 0.0), 1.0, material1));

        Material material2 = new Lambertian(new Vec3(0.4, 0.2, 0.1));
        world.add(new Sphere(new Vec3(-4.0, 1.0, 0.0), 1.0, material2));

        Material material3 = new Metal(new Vec3(0.7, 0.6, 0.5), 0.0);
        world.add(new Sphere(new Vec3(4.0, 1.0, 0.0), 1.0, material3));

        return world;
    }

}
