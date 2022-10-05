package com.projectsolution.hierarchicalstruct;

import de.saxsys.javafx.test.JfxRunner;
import javafx.embed.swing.JFXPanel;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

@RunWith(JfxRunner.class)
class HierarchyElementTest {

    @Test
    void copy() {
        new JFXPanel();
        HierarchyElement hierarchyElement = new HierarchyElement("d",2,5);
        HierarchyElement copy=hierarchyElement.copy();
        Assert.assertNotEquals(copy,hierarchyElement);
        Assert.assertTrue(hierarchyElement.equals(copy));
    }

    @Test
    void testEquals() {
        new JFXPanel();
        HierarchyElement hierarchyElement = new HierarchyElement("d",2,5);
        HierarchyElement copy=new HierarchyElement("d",2,5);
        Assert.assertTrue(hierarchyElement.equals(copy));
    }
}