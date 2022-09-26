package ua.com.alevel.hw17.factory;

import java.util.concurrent.atomic.AtomicInteger;

public final class RobotsFactory {
    private static RobotsFactory instance;
    private AtomicInteger fuelCount;
    private AtomicInteger detailInProgress;
    private AtomicInteger programmingOfSchema;
    private AtomicInteger detail;

    private RobotsFactory() {
        fuelCount = new AtomicInteger(0);
        detailInProgress = new AtomicInteger(0);
        programmingOfSchema = new AtomicInteger(0);
        detail = new AtomicInteger(0);
    }

    public static RobotsFactory getInstance() {
        if (instance == null) {
            synchronized (RobotsFactory.class) {
                if (instance == null) {
                    instance = new RobotsFactory();
                }
            }
        }

        return instance;
    }

    public synchronized AtomicInteger getFuelCount() {
        return fuelCount;
    }

    public synchronized void setFuelCount(int count) {
        fuelCount.set(count);
    }

    public synchronized AtomicInteger getProcessOfCreatingDetail() {
        return detailInProgress;
    }

    public synchronized void setProcessOfCreatingDetail(int detail) {
        detailInProgress.set(detail);
    }

    public synchronized AtomicInteger getProcessOfProgrammingSchema() {
        return programmingOfSchema;
    }

    public synchronized void setProcessOfProgrammingSchema(int schema) {
        programmingOfSchema.set(schema);
    }

    public synchronized AtomicInteger getFinalDetail() {
        return detail;
    }

    public synchronized void setFinalDetail(int finalDetail) {
        detail.set(finalDetail);
    }
}
