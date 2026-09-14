package glowstone.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.List;

class WorkspaceTest {
    @Test
    void testWorkspaceCreation() {
        Workspace workspace = new Workspace("Test name");

        assertEquals("Test name", workspace.getName());
    }

    @Test
    void workspaceStartsWithNOCategories() {
        Workspace workspace = new Workspace("Test name");

        assertEquals(0, workspace.getCategories().size());
    }

    @Test
    void testSetName() {
        Workspace workspace = new Workspace("Initial name");

        workspace.setName("Updated name");

        assertEquals("Updated name", workspace.getName());
    }

    @Test
    void testGetName() {
        Workspace workspace1 = new Workspace("Test name1");
        Workspace workspace2 = new Workspace("Test name2");

        assertEquals("Test name1", workspace1.getName());
        assertEquals("Test name2", workspace2.getName());
    }

    @Test
    void testGetId() {
        Workspace workspace1 = new Workspace("Test name1");
        Workspace workspace2 = new Workspace("Test name2");

        assertNotEquals(workspace1.getId(), workspace2.getId());
    }

    @Test
    void createCategorySetsParentIdAndAddsCategory() {
        Workspace workspace = new Workspace("Test name");
        Category category = new Category("Test category");

        workspace.createCategory(category);

        assertEquals(workspace.getId(), category.getParentId());
        assertEquals(List.of(category), workspace.getCategories());
    }

    @Test
    void newWorkspaceStartsWithNoCategories() {
        Workspace workspace = new Workspace("Test name");

        assertEquals(0, workspace.getCategories().size());
    }

    @Test
    void removeCategoryRemovesCategory() {
        Workspace workspace = new Workspace("Test name");
        Category category = new Category("Test name");

        workspace.createCategory(category);

        assertEquals(1, workspace.getCategories().size());

        workspace.removeCategory(category);

        assertEquals(0, workspace.getCategories().size());
    }

    @Test
    void removeMissingCategoryDoesNothing() {
        Workspace workspace = new Workspace("Test name");
        Category realCategory = new Category("Real");
        Category fakeCategory = new Category("Fake");

        workspace.createCategory(realCategory);
        workspace.removeCategory(fakeCategory);

        assertEquals(1, workspace.getCategories().size());
        assertEquals(realCategory, workspace.getCategories().get(0));
    }

    @Test
    void categoriesPreserveInsertionOrder() {
        Workspace workspace = new Workspace("Test name");
        Category category1 = new Category("category1");
        Category category2 = new Category("category2");

        workspace.createCategory(category1);
        workspace.createCategory(category2);

         assertEquals(category1, workspace.getCategories().get(0));
        assertEquals(category2, workspace.getCategories().get(1));
    }
}
