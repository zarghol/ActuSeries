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
    private final Bus bus = new Bus();
    private List<AsyncTask> tasks;

    private TaskManager() {
        this.tasks = new ArrayList<>();
    }

    private static TaskManager getInstance() {
        if(TaskManager.taskManager == null) {
            TaskManager.taskManager = new TaskManager();
        }
        return TaskManager.taskManager;
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

    public static <S> void launchTask(Class<?> typeTask, S[] params) {
        try {
            AsyncTask task = (AsyncTask) typeTask.newInstance();
            TaskManager.getInstance().tasks.add(task);
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
        } catch(Exception e) {
            Log.e("actuseries", "erreur instantiation de tasks", e);
        }
    }

    public static void cancelAllTasks() {
        for(AsyncTask task : TaskManager.getInstance().tasks) {
            task.cancel(true);
        }

        TaskManager.getInstance().tasks.clear();
    }

    public static boolean cancelTask(Class<?> taskClass) {
        int location = -1;
        for(AsyncTask task : TaskManager.getInstance().tasks) {
            if(task.getClass().equals(taskClass)) {
                location = TaskManager.getInstance().tasks.indexOf(task);
                task.cancel(true);
                break;
            }
        }
        if(location != -1) {
            TaskManager.getInstance().tasks.remove(location);
            return true;
        }
        return false;
    }
}
