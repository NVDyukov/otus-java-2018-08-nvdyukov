import ru.otus.CacheEngine;
import ru.otus.CacheEngineSoftRef;
import ru.otus.MyElement;

public class Test1 {
    public static void main(String[] args) {
        int size = 98;
        try (CacheEngine<Integer, String> cache = new CacheEngineSoftRef<>(size, 0, 0, true)) {
            for (int i = 0; i < 100; i++) {
                cache.put(new MyElement<>(i, "String: " + i));
            }
            byte[] bytes = new byte[250_000];
            Thread.sleep(11_000);
            byte[] bytes1 = new byte[250_000];
            Thread.sleep(11_000);
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
