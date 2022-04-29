public class Camera {
    public Vec3 origin;
    public Vec3 lower_left_corner;
    public Vec3 horizontal;
    public Vec3 vertical;
    public Vec3 u, v, w;
    public double lens_radius;

    public Camera(Vec3 lookFrom, Vec3 lookAt, Vec3 vup, double vfov, double aspect_ratio, double aperture, double focus_dist) {
        double theta = Utils.degreeToRadian(vfov);
        double h = Math.tan(theta / 2.0);
        double viewport_height = 2.0 * h;
        double viewport_width = aspect_ratio * viewport_height;

        w = lookFrom.Sub(lookAt).normalize();
        u = vup.Cross(w).normalize();
        v = w.Cross(u);

        origin = lookFrom;
        horizontal = u.Scale(focus_dist * viewport_width);
        vertical = v.Scale(focus_dist * viewport_height);
        lower_left_corner = origin.Sub(horizontal.Div(2)).Sub(vertical.Div(2)).Sub(w.Scale(focus_dist));
        lens_radius = aperture / 2;
    }

    public Ray get_ray(double s, double t) {
        Vec3 rd = Vec3.random_in_unit_disk().Scale(lens_radius);
        Vec3 offset = u.Scale(rd.x()).Add(v.Scale(rd.y()));
        return new Ray(origin.Add(offset),
                lower_left_corner.Add(horizontal.Scale(s)).Add(vertical.Scale(t)).Sub(origin).Sub(offset));
    }
}
