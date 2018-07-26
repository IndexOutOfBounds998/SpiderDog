package pre.cyy.request;

/**
 * @author yang
 * @date 2018/7/26 15:16
 * @description Interface for identifying different tasks
 * task
 */
public interface Task {

    /**
     * unique id for a task.
     *
     * @return uuid
     */
    String getUUID();

    /**
     * site of a task
     *
     * @return site
     */
    Site getSite();

}
