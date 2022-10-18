package com.projectsolution.hierarchicalstruct;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * list of connections and blocks
 */
public class HierarchyElementsList implements Serializable {
    /**
     * list of blocks
     */
    private final ArrayList<Task> arrayList = new ArrayList<>();
    /**
     * list of connections
     */
    private final ArrayList<Connection> connections = new ArrayList<>();

    /**
     * @param text on block
     * @param x on pane
     * @param y on pane
     * @return new block
     */
    public Task add(String text, double x, double y)
    {
        Task element = new Task.TaskBuilder(text,x,y).build();
        arrayList.add(element);
        return element;
    }

    /**
     * @param element for adding
     * @return new block
     */
    public Task add(Task element)
    {
        arrayList.add(element);
        return element;
    }

    /**
     * @param element foe deleting
     */
    public void delete(Task element)
    {
        arrayList.remove(element);
    }

    /**
     * @return list of blocks
     */
    public ArrayList<Task> getAll()
    {
        return arrayList;
    }

    /**
     * @param top block
     * @param bottom block
     * @return new connection
     */
    public Connection createConnection(Task top, Task bottom)
    {
        Connection connection = new Connection(top,bottom);
        connections.add(connection);
        return connection;
    }

    /**
     * @param connection for deleting
     */
    public void deleteConnection(Connection connection)
    {
        connections.remove(connection);
    }
    public void deleteChild(Task elem)
    {
        for(Task task:arrayList)
            task.childrenTasks.remove(elem);
    }
    /**
     * @return all connections
     */
    public ArrayList<Connection> getAllConnections()
    {
        return connections;
    }

    /**
     * remove all elements
     */
    public void removeAll()
    {
        connections.clear();
        arrayList.clear();
    }
}
