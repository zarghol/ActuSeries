package actuseries.android.com.actuseries.event;

import android.os.AsyncTask;
import android.util.Log;

import com.squareup.otto.Bus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by charly on 14/01/2015.
 */
public class TaskManager {
    private static TaskManager taskManager;
    private List<AsyncTask> tasks;
    private final Bus bus = new Bus();

    private static TaskManager getInstance() {
        if (TaskManager.taskManager == null) {
            TaskManager.taskManager = new TaskManager();
        }
        return TaskManager.taskManager;
    }

    private TaskManager() {
        this.tasks = new ArrayList<>();
    }

    public static void register(Object object) {
        TaskManager.getInstance().bus.register(object);
    }

    public static void unregister(Object object) {
        TaskManager.getInstance().bus.unregister(object);
    }

    public static void post(Object result) {
        TaskManager.getInstance().bus.post(result);
    }

    public static <T> void launchTask(Class<?> typeTask, T[] params) {
        try {
            AsyncTask task = (AsyncTask) typeTask.newInstance();
            TaskManager.getInstance().tasks.add(task);
            task.execute(params);
        } catch (Exception e) {
            Log.e("actuseries", "erreur instantiation de tasks", e);
        }
    }

    public static void cancelAllTasks() {
        for (AsyncTask task : TaskManager.getInstance().tasks) {
            task.cancel(true);
        }

        TaskManager.getInstance().tasks.clear();
    }

    public static void cancelTask(Class<?> taskClass) {
        int location = -1;
        for (AsyncTask task : TaskManager.getInstance().tasks) {
            if (task.getClass().equals(taskClass)) {
                location = TaskManager.getInstance().tasks.indexOf(task);
                task.cancel(true);
                break;
            }
        }
        if (location != -1) {
            TaskManager.getInstance().tasks.remove(location);
        }
    }
}
