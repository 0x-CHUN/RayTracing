/**
 * HitTable接口，返回能否和Ray相交
 */
public interface HitTable {
    /**
     * 返回能否和光线相交
     *
     * @param ray  光线
     * @param tMin 最小的距离
     * @param tMax 最大的距离
     * @param rec  记录Hit状况的类
     * @return
     */
    public boolean hit(final Ray ray, double tMin, double tMax, HitRecord rec);
}
