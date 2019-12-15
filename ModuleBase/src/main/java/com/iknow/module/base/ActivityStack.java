package com.iknow.module.base;

import android.app.Activity;
import android.app.Application;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.Stack;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * use {@link Application#registerActivityLifecycleCallbacks(Application.ActivityLifecycleCallbacks)}
 * to listen all Action's life cycle method
 *
 * @author xiaojinzi
 */
public class ActivityStack {

    /**
     * flag the {@link Activity} is not allowed to finish
     * but you can call the method {@link #forcePopAllActivity()}
     */
    @Target(ElementType.TYPE)
    @Retention(RUNTIME)
    public @interface UnFinishActivity {

        /**
         * flag the Activity with a flag,empty string means Activity will be finish when
         * the method {@link #forcePopAllActivity()} invoked in any case
         * <p>
         * if the string is not empty,
         *
         * @return
         */
        String value();
    }

    /**
     * you can use Annotation {@link ActivityAction} to flag your Activity {@link Activity}
     * and write your custom action.
     * 你可以利用这个注解标记的Activity,一个完整的业务流程用同一个标记,
     * 这样子可以让你在这个流程结束的时候,
     * 可以准确无误的杀死这个流程上的所有Activity
     */
    @Target(ElementType.TYPE)
    @Retention(RUNTIME)
    public @interface ActivityAction {

        /**
         * the value of custom action
         *
         * @return
         */
        String[] value();
    }

    /**
     * flag a Activity you can open more than one
     */
    @Target(ElementType.TYPE)
    @Retention(RUNTIME)
    public @interface ActivityCanRepeat {

    }

    /**
     * the stack will be save all reference of Activity
     */
    private Stack<Activity> activityStack = new Stack<>();
    /**
     * 当前在前台的 Activity
     */
    private Activity mCurrentActivity;

    private ActivityStack() {
    }

    private static class Holder {

        private static ActivityStack INSTANCE = new ActivityStack();
    }

    @MainThread
    public static ActivityStack getInstance() {
        return Holder.INSTANCE;
    }

    private String[] canNotDestoryActivities = {
            "com.jdpaysdk.author.AuthorActivity"
    };

    /**
     * 进入栈
     */
    public synchronized void pushActivity(Activity activity) {

        if (activity == null) {
            return;
        }
        if (activityStack.contains(activity)) {
            return;
        }

        // 检查顶层的这个是不是和当前要进入的这个是一个Class,这是一个折中的方式,下一步,让每一个视图都继承baseView中的父类,在父类中对点击事件进行处理

        boolean canOpenMore = activity.getClass().isAnnotationPresent(ActivityCanRepeat.class);

        if (activityStack.size() > 0 && !canOpenMore) {

            boolean isFinish = false;

            Activity preAct = activityStack.get(activityStack.size() - 1);

            if (preAct.getClass() == activity.getClass()) {
                isFinish = true;
            }

            // 如果匹配到名称就放过,不要销毁掉
            for (String className : canNotDestoryActivities) {
                if (className.equals(preAct.getClass().getName())) {
                    isFinish = false;
                    break;
                }
            }

            // 如果需要销毁前一个
            if (isFinish) {
                if (!preAct.isFinishing()) {
                    preAct.finish();
                }
                removeActivity(preAct);
            }
        }

        activityStack.add(activity);
    }

    /**
     * you can use Annotation {@link ActivityAction} to flag your Activity {@link Activity}
     * and write your custom action.
     * and then you can call this method delivery a action,such as "selfOrderAction" .
     * this method will finish all Activity which was marked with Annotation {@link ActivityAction}
     * and the Annotation's value() is equals to "selfOrderAction"
     *
     * @param action the custom action
     */
    public synchronized void popActivityWithAction(@NonNull String action) {
        outter:
        for (int i = activityStack.size() - 1; i >= 0; i--) {
            Activity currentAct = activityStack.get(i);
            if (currentAct == null) {
                continue;
            }
            ActivityAction activityAction = currentAct.getClass().getAnnotation(ActivityAction.class);
            if (activityAction == null) {
                continue;
            }
            String[] values = activityAction.value();
            if (values == null) {
                continue;
            }
            inner:
            for (String value : values) {
                if (value.equals(action)) {
                    currentAct.finish();
                    removeActivity(currentAct);
                    break inner;
                }
            }
        }
    }

    /**
     * will destory all Activity except the activity is masked with Annotation {@link UnFinishActivity}
     * see {@link #forcePopAllActivity()}
     *
     * @author cxj
     * @time 26/10/2017  10:52 AM
     */
    public synchronized void popAllActivity() {
        popAllActivityExcept((Activity) null);
    }

   /**
     * 弹出所有的activity，除了指定的activity
     */
    public synchronized void popAllActivityExcept(@Nullable Activity exceptActivity) {
        for (int i = activityStack.size() - 1; i >= 0; i--) {
            final Activity activity = activityStack.get(i);
            if (activity == null) {
                continue;
            }
            if (exceptActivity != null && activity == exceptActivity) {
                continue;
            }
            if (activity.getClass().isAnnotationPresent(UnFinishActivity.class)) {
                continue;
            }
            removeActivity(activity);
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }

    /**
     * will finish all Activity
     *
     * @author cxj
     * @time 26/10/2017  1:27 PM
     */
    public synchronized void forcePopAllActivity() {
        forcePopAllActivityWithFalg();
    }

    /**
     * will finish all Activity except the Activities
     * which UnFinishActivity's value is equal to parameter 'flag'
     *
     * @param flags
     */
    public synchronized void forcePopAllActivityWithFalg(@Nullable String... flags) {

        for (int i = activityStack.size() - 1; i >= 0; i--) {

            Activity activity = activityStack.get(i);

            if (activity == null) {
                continue;
            }

            boolean isFinish = true;

            UnFinishActivity unFinishActivity = activity.getClass().getAnnotation(UnFinishActivity.class);
            if (unFinishActivity != null && flags != null) {
                // if the flag string is the same as value,the Activity will not be finish
                String value = unFinishActivity.value();
                for (int j = 0; j < flags.length; j++) {
                    if (value.equals(flags[j])) {
                        isFinish = false;
                        break;
                    }
                }
            }

            if (isFinish) {
                if (!activity.isFinishing()) {
                    activity.finish();
                }
                activityStack.remove(i);
            }
        }
    }

    /**
     * remove the reference of Activity
     *
     * @author cxj
     * @time 26/10/2017  10:52 AM
     */
    public synchronized void removeActivity(Activity activity) {
        activityStack.remove(activity);
    }

    /**
     * @return whether the the size of stack of Activity is zero or not
     */
    public synchronized boolean isEmpty() {
        if (activityStack == null || activityStack.size() == 0) {
            return true;
        }
        return false;
    }

    /**
     * 主界面是否存在
     *
     * @return
     */
    public synchronized boolean isActivityExit(Class<?> clazz) {
        if (activityStack == null || activityStack.size() == 0) {
            return false;
        }

        boolean result = false;

        for (Activity act : activityStack) {
            if (act.getClass() == clazz) {
                result = true;
                break;
            }
        }

        return result;
    }

    /**
     * @return the size of stack of Activity
     */
    public synchronized int getSize() {
        if (activityStack == null) {
            return 0;
        }
        return activityStack.size();
    }

    /**
     * @return the Activity On the top
     */
    @Nullable
    public synchronized Activity getTopActivity() {
        return isEmpty() || activityStack.size() < 1 ? null : activityStack.get(activityStack.size() - 1);
    }

    public synchronized Activity getBottomActivity() {
        return isEmpty() || activityStack.size() < 1 ? null : activityStack.get(0);
    }

}
