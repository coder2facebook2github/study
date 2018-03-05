package com.utils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author ripflowers
 */
public class FileMonitor {
    private static class FileMonitorTask extends TimerTask {
        private FileChangeListener listener;
        private String filename;
        private File monitoredFile;
        private long lastModified;

        public FileMonitorTask(FileChangeListener listener, String filename) {
            this.listener = listener;
            this.filename = filename;

            monitoredFile = new File(filename);
            if (!monitoredFile.exists()) {
                return;
            }

            lastModified = monitoredFile.lastModified();
        }

        @Override
        public void run() {
            long latestChange = monitoredFile.lastModified();
            if (lastModified != latestChange) {
                lastModified = latestChange;
                listener.fileChanged(filename);
            }
        }
    }

    private static final FileMonitor instance = new FileMonitor();

    public static FileMonitor getInstance() {
        return instance;
    }

    private Timer timer;

    private Map<String, FileMonitorTask> timerEntries;

    private FileMonitor() {
        timerEntries = new HashMap<String, FileMonitorTask>();
        timer = new Timer();
    }

    public void addFileChangeListener(FileChangeListener listener, String filename, long period) {
        removeFileChangeListener(filename);

        FileMonitorTask task = new FileMonitorTask(listener, filename);

        timerEntries.put(filename, task);
        timer.schedule(task, period, period);
    }

    public void removeFileChangeListener(String filename) {
        FileMonitorTask task = timerEntries.remove(filename);

        if (task != null) {
            task.cancel();
        }
    }
}
