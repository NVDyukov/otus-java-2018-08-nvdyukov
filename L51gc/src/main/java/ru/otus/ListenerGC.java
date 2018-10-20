package ru.otus;

import com.sun.management.GarbageCollectionNotificationInfo;

import javax.management.ListenerNotFoundException;
import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ListenerGC {
    private final List<GcSpecification> specificationList;
    private final List<Runnable> registration;

    public ListenerGC() {
        List<GarbageCollectorMXBean> garbageCollectorMXBeans = ManagementFactory.getGarbageCollectorMXBeans();
        int size = garbageCollectorMXBeans.size();
        specificationList = new ArrayList<>(size);
        registration = new ArrayList<>(size);
        for (GarbageCollectorMXBean e : garbageCollectorMXBeans) {
            specificationList.add(new GcSpecification(e.getName()));
            NotificationEmitter emitter = (NotificationEmitter) e;
            NotificationListener listener = (notification, handback) -> {

                GarbageCollectionNotificationInfo notificationInfo =
                        GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());
                Optional<GcSpecification> any = specificationList.stream()
                        .filter(el -> el.getGcName().equals(notificationInfo.getGcName()))
                        .findAny();
                GcSpecification gcSpecification = any.orElseThrow();
                gcSpecification.setCountOfGc(gcSpecification.getCountOfGc() + 1);
                gcSpecification.setAmountOfTime(gcSpecification.getAmountOfTime()
                        + notificationInfo.getGcInfo().getDuration());
            };

            emitter.addNotificationListener(listener, null, null);
            registration.add(() -> {
                try {
                    emitter.removeNotificationListener(listener);
                } catch (ListenerNotFoundException e1) {
                    e1.printStackTrace();
                }
            });
        }
    }

    public List<GcSpecification> getSpecificationList() {
        return Collections.unmodifiableList(specificationList);
    }

    public void specificationListZero() {
        specificationList.parallelStream()
                .forEach(e -> e.toZero());
    }

    public void writeToLog() {
        specificationList.stream()
                .forEach(e -> System.out.println(e));
        specificationListZero();
    }

    public void removeListeners() {
        registration.stream()
                .forEach(Runnable::run);
    }
}
