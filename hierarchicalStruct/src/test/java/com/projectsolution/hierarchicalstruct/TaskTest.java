package com.projectsolution.hierarchicalstruct;

import de.saxsys.javafx.test.JfxRunner;
import javafx.embed.swing.JFXPanel;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

@RunWith(JfxRunner.class)
class TaskTest {

    @Test
    void copy() {
        new JFXPanel();
        Task task = new Task.TaskBuilder("d",2,5).build();
        Task copy= task.copy();
        Assert.assertNotEquals(copy, task);
        Assert.assertTrue(task.equals(copy));
    }

    @Test
    void testEquals() {
        new JFXPanel();
        Task task = new Task.TaskBuilder("d",2,5).build();
        Task copy=new Task.TaskBuilder("d",2,5).build();
        Assert.assertTrue(task.equals(copy));
    }
}