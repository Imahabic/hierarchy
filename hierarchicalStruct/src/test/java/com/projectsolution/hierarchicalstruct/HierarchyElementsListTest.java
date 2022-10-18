package com.projectsolution.hierarchicalstruct;

import de.saxsys.javafx.test.JfxRunner;
import javafx.embed.swing.JFXPanel;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

@RunWith(JfxRunner.class)
class HierarchyElementsListTest {

    @Test
    void add() {
        new JFXPanel();
        HierarchyElementsList list = new HierarchyElementsList();
        Task element=list.add("ds",3,5);
        Assert.assertEquals(list.getAll().get(0),element);
    }

    @Test
    void deleteConnection() {
        new JFXPanel();
        HierarchyElementsList list = new HierarchyElementsList();
        Connection connection = list.createConnection(new Task.TaskBuilder("",4,4).build(),new Task.TaskBuilder("d",4,5).build());
        Assert.assertEquals(list.getAllConnections().size(),1);
        list.deleteConnection(connection);
        Assert.assertEquals(list.getAllConnections().size(),0);
    }

    @Test
    void removeAll() {
        new JFXPanel();
        HierarchyElementsList list = new HierarchyElementsList();
        list.add("ds",3,5);
        list.add("ds",3,5);
        list.createConnection(new Task.TaskBuilder("",4,4).build(),new Task.TaskBuilder("d",4,5).build());
        Assert.assertEquals(list.getAllConnections().size(),1);
        Assert.assertEquals(list.getAll().size(),2);
        list.removeAll();
        Assert.assertEquals(list.getAllConnections().size(),0);
        Assert.assertEquals(list.getAll().size(),0);
    }
}