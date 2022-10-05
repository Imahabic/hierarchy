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
    private final ArrayList<HierarchyElement> arrayList = new ArrayList<>();
    /**
     * list of connections
     */
    private final ArrayList<Connection> connections = new ArrayList<Connection>();

    /**
     * @param text on block
     * @param x on pane
     * @param y on pane
     * @return new block
     */
    public HierarchyElement add(String text, double x, double y)
    {
        HierarchyElement element = new HierarchyElement(text,x,y);
        arrayList.add(element);
        return element;
    }

    /**
     * @param element for adding
     * @return new block
     */
    public HierarchyElement add(HierarchyElement element)
    {
        arrayList.add(element);
        return element;
    }

    /**
     * @param element foe deleting
     */
    public void delete(HierarchyElement element)
    {
        arrayList.remove(element);
    }

    /**
     * @return list of blocks
     */
    public ArrayList<HierarchyElement> getAll()
    {
        return arrayList;
    }

    /**
     * @param top block
     * @param bottom block
     * @return new connection
     */
    public Connection createConnection(HierarchyElement top, HierarchyElement bottom)
    {
        Connection connection = new Connection(top,bottom);
        connections.add(connection);
        return connection;
    }

    /**
     * @param connection for deleting
     * @param connection for deleting
     */
    public void deleteConnection(Connection connection)
    {
        connections.remove(connection);
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
