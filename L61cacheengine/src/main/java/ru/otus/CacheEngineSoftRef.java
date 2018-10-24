package ru.otus;


import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.*;
import java.util.function.Function;

public class CacheEngineSoftRef<K, V> implements CacheEngine<K, V> {
    private static final int TIME_THRESHOLD_MS = 5;
    private static final int TIME_THRESHOLD_CLEAR_REF = 6_000;

    private final int maxElements;
    private final long lifeTimeMs;
    private final long idleTimeMs;
    private final boolean isEternal;

    private final Map<K, SoftReference<MyElement<K, V>>> elements = new LinkedHashMap<>();
    private final ReferenceQueue<MyElement<K, V>> referenceQueue = new ReferenceQueue<>();
    private final ReferenceCounter referenceCounter;
    private final Timer timer = new Timer();

    private int hit = 0;
    private int miss = 0;

    public CacheEngineSoftRef(int maxElements, long lifeTimeMs, long idleTimeMs, boolean isEternal) {
        this.maxElements = maxElements;
        this.lifeTimeMs = lifeTimeMs > 0 ? lifeTimeMs : 0;
        this.idleTimeMs = idleTimeMs > 0 ? idleTimeMs : 0;
        this.isEternal = lifeTimeMs == 0 && idleTimeMs == 0 || isEternal;
        referenceCounter = new ReferenceCounter();
        timer.schedule(getTimerTaskClearReferences(), TIME_THRESHOLD_CLEAR_REF, TIME_THRESHOLD_CLEAR_REF);
    }

    @Override
    public void put(MyElement<K, V> element) {
        if (elements.size() == maxElements) {
            K firstKey = elements.keySet().iterator().next();
            elements.remove(firstKey);
        }

        K key = element.getKey();
        SoftReference<MyElement<K, V>> softReference = new SoftReference<>(element, referenceQueue);
        elements.put(key, softReference);

        if (!isEternal) {
            if (lifeTimeMs != 0) {
                TimerTask lifeTimerTask = getTimerTask(key, lifeElement -> lifeElement.getCreationTime() + lifeTimeMs);
                timer.schedule(lifeTimerTask, lifeTimeMs);
            }
            if (idleTimeMs != 0) {
                TimerTask idleTimerTask = getTimerTask(key, idleElement -> idleElement.getLastAccessTime() + idleTimeMs);
                timer.schedule(idleTimerTask, idleTimeMs, idleTimeMs);
            }
        }
    }


    @Override
    public MyElement<K, V> get(K key) {
        SoftReference<MyElement<K, V>> softReference = elements.get(key);
        MyElement<K, V> element = null;
        if (Objects.nonNull(softReference) && Objects.nonNull(element = softReference.get())) {
            hit++;
            element.setAccessed();
        } else {
            miss++;
        }
        return element;
    }

    @Override
    public int getHitCount() {
        return hit;
    }

    @Override
    public int getMissCount() {
        return miss;
    }

    private TimerTask getTimerTask(final K key, Function<MyElement<K, V>, Long> timeFunction) {
        return new TimerTask() {
            @Override
            public void run() {
                SoftReference<MyElement<K, V>> softReference = elements.get(key);
                MyElement<K, V> element = null;

                if (Objects.isNull(softReference) || (Objects.nonNull(element = softReference.get())
                        && isT1BeforeT2(timeFunction.apply(element), System.currentTimeMillis()))) {
                    elements.remove(key);
                    this.cancel();
                }
            }
        };
    }

    private TimerTask getTimerTaskClearReferences() {
        return new TimerTask() {
            @Override
            public void run() {
                referenceCounter.getReferences();
                referenceCounter.clear();
            }
        };
    }

    private boolean isT1BeforeT2(long t1, long t2) {
        return t1 < t2 + TIME_THRESHOLD_MS;
    }

    @Override
    public void close() {
        timer.cancel();
    }

    private class ReferenceCounter<K, V> {
        private final LinkedList<Reference<?>> listMyElement = new LinkedList<>();

        public void getReferences() {
            Reference<?> el;
            while ((el = CacheEngineSoftRef.this.referenceQueue.poll()) != null) {
                listMyElement.add(el);
            }
            System.out.println("Кол-во элементов в referenceQueue: " + listMyElement.size());
        }

        public void clearReferences() {
            for (Reference<?> e : listMyElement) {
                CacheEngineSoftRef.this.elements.values().remove(e);
            }
            listMyElement.clear();
        }

        public void clear() {
            if (listMyElement.size() > 20) {//CacheEngineSoftRef.this.elements.size() >> 1) {
                clearReferences();
                System.out.println("Кеш очищен от мягких ссылок");
            }
        }
    }
}
