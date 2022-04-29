import java.util.ArrayList;

public class HitTableList implements HitTable {
    public ArrayList<HitTable> objects;

    public HitTableList() {
        this.objects = new ArrayList<>();
    }

    public void clear() {
        this.objects.clear();
    }

    public void add(HitTable obj) {
        this.objects.add(obj);
    }

    @Override
    public boolean hit(Ray ray, double tMin, double tMax, HitRecord rec) {
        HitRecord temp_rec = new HitRecord();
        boolean hit_anything = false;
        double closest_so_far = tMax;
        for (HitTable obj : objects) {
            if (obj.hit(ray, tMin, closest_so_far, temp_rec)) {
                hit_anything = true;
                closest_so_far = temp_rec.t;
                rec.set(temp_rec);
            }
        }
        return hit_anything;
    }
}
