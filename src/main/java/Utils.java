import java.io.IOException;
import java.io.Writer;
import java.util.Random;

public class Utils {
    private static final Random random = new Random();

    public static double RandomDouble() {
        return random.nextDouble();
    }

    public static double RandomDouble(double min, double max) {
        return min + (max - min) * RandomDouble();
    }

    /**
     * 将x限制在 min 到 max 之间
     */
    public static double clamp(double x, double min, double max) {
        if (x < min) {
            return min;
        }
        return Math.min(x, max);
    }

    public static double degreeToRadian(double degrees) {
        return degrees * Math.PI / 180.0;
    }

    public static void WriteColor(Writer writer, Vec3 color, int samples_per_pixel) throws IOException {
        double scale = 1.0 / (double) samples_per_pixel;
        double r = Math.sqrt(color.x() * scale);
        double g = Math.sqrt(color.y() * scale);
        double b = Math.sqrt(color.z() * scale);
        int ir = (int) (256 * clamp(r, 0.0, 0.999));
        int ig = (int) (256 * clamp(g, 0.0, 0.999));
        int ib = (int) (256 * clamp(b, 0.0, 0.999));
        writer.write(String.format("%d %d %d\n", ir, ig, ib));
    }
}
