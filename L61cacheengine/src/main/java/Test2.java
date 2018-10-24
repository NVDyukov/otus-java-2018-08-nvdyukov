import ru.otus.CacheEngine;
import ru.otus.CacheEngineSoftRef;
import ru.otus.MyElement;

public class Test2 {
    public static void main(String[] args) {
        int size = 99;
        try (CacheEngine<Integer, String> cache = new CacheEngineSoftRef<>(size, 100_000, 0, false)) {
            for (int i = 0; i < 100; i++) {
                cache.put(new MyElement<>(i, "String: " + i));
            }
            byte[] bytes = new byte[500_000];
            Thread.sleep(15_000);
            byte[] bytes1 = new byte[410_000];
            Thread.sleep(18_000);

            for (int i = 0; i < 100; i++) {
                MyElement<Integer, String> element = cache.get(i);
                System.out.println("String for " + i + ": " + (element != null ? element.getValue() : "null"));
            }


            System.out.println("Cache hits: " + cache.getHitCount());
            System.out.println("Cache misses: " + cache.getMissCount());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
