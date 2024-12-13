/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.common.libs;

import me.common.libs.objs.TaskWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class TaskScheduler<T extends TaskWorker> {

    private static final Logger LOG = LoggerFactory.getLogger(TaskScheduler.class);

    private final String name;
    private final T appWork;
    //cai dat lenh chay hang ngay
    private final int targetHour;
    private final int targetMin;
    private final int targetSec;
    //cat dat lenh chay sau xxx giay
    private final int delaySeconds;
    private final AtomicInteger completedTasks = new AtomicInteger(0);
    ////
    private ScheduledExecutorService _executorService = Executors.newScheduledThreadPool(1);
    private volatile boolean isBusy = false;
    private volatile ScheduledFuture<?> scheduledTask = null;

    public TaskScheduler(T appWork, int delaySeconds) {
        this.name = "Executor [" + MiscHelper.getSimpleClass(appWork.getClass()) + "]";
        this.appWork = appWork;

        this.delaySeconds = delaySeconds;
        this.targetHour = -1;
        this.targetMin = -1;
        this.targetSec = -1;
    }

    public TaskScheduler(T appWork, int targetHour, int targetMin, int targetSec) {
        this.name = "Executor [" + MiscHelper.getSimpleClass(appWork.getClass()) + "]";
        this.appWork = appWork;

        this.targetHour = targetHour;
        this.targetMin = targetMin;
        this.targetSec = targetSec;
        this.delaySeconds = -1;
    }

    private static long computeNextDelay(int targetHour, int targetMin, int targetSec) {
        ZonedDateTime zonedNow = minskDateTime();
        ZonedDateTime zonedNextTarget = zonedNow.withHour(targetHour).withMinute(targetMin).withSecond(targetSec).withNano(0);

        if (zonedNow.compareTo(zonedNextTarget) > 0) {
            zonedNextTarget = zonedNextTarget.plusDays(1);
        }

        Duration duration = Duration.between(zonedNow, zonedNextTarget);
        return duration.getSeconds();
    }

    public static ZonedDateTime minskDateTime() {
        return ZonedDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"));
    }

    public boolean serviceIsStopped() {
        return this._executorService.isTerminated();
    }

    public void start() {
        if (_executorService.isShutdown()) {
            _executorService = new ScheduledThreadPoolExecutor(1);
        }
        scheduleNextTask(doTaskWork());
    }

    public void stop() {
        LOG.info(name + " is stopping.");
        if (scheduledTask != null) {
            scheduledTask.cancel(false);
        }
        _executorService.shutdown();
        LOG.info(name + " stopped.");
        try {
            LOG.info(name + " awaitTermination, start: isBusy [ " + isBusy + "]");
            // wait one minute to termination if busy
            if (isBusy) {
                _executorService.awaitTermination(1, TimeUnit.MINUTES);
            }
        } catch (InterruptedException ex) {
            LOG.error(name + " awaitTermination exception", ex);
        } finally {
            LOG.info(name + " awaitTermination, finish");
        }
    }

    private Runnable doTaskWork() {
        return () -> {
            LOG.info(name + " [" + completedTasks.get() + "] start: " + minskDateTime());
            try {
                isBusy = true;
                appWork.doWork();
                LOG.info(name + " finish work in " + minskDateTime());
            } catch (Exception ex) {
                LOG.error(name + " throw exception in " + minskDateTime(), ex);
            } finally {
                isBusy = false;
            }

            scheduleNextTask(doTaskWork());
            LOG.info(name + " [" + completedTasks.get() + "] finish: " + minskDateTime());
            LOG.info(name + " completed tasks: " + completedTasks.incrementAndGet());
        };
    }

    private void scheduleNextTask(Runnable task) {
        LOG.info(name + " make schedule in " + minskDateTime());
        long delay = delaySeconds;
        if (delay <= 0) {
            delay = computeNextDelay(targetHour, targetMin, targetSec);
        }
        LOG.info(name + " has delay in " + delay);

        scheduledTask = _executorService.schedule(task, delay, TimeUnit.SECONDS);
    }

    public String getName() {
        return name;
    }

    public AtomicInteger getCompletedTasks() {
        return completedTasks;
    }

    public ScheduledFuture<?> getScheduledTask() {
        return scheduledTask;
    }
}
