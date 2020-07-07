package com.cleanup.todoc.database;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.utils.LiveDataTestUtil;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class TaskDaoTest {
    //-- PROPERTIES
    // DATA
    private ToDocDatabase database;
    // DATA SET FOR TEST
    private static long PROJECT_ID = 3L;
    private static Project PROJECT_DEMO = new Project(PROJECT_ID, "Projet Circus", 0xFFA3CED2);
    private static Task TASK_DEMO = new Task(PROJECT_ID, "Unit testing task demo", new Date().getTime());

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDb() {
        this.database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                ToDocDatabase.class)
                .allowMainThreadQueries()
                .build();
    }

    @After
    public void closeDb() {
        database.close();
    }

    //-- UNIT TESTS
    @Test
    public void getTasksWhenNoTaskInserted() throws InterruptedException {
        // TEST
        List<Task> tasks = LiveDataTestUtil.getValue(database.taskDao().getTasks());
        assertTrue(tasks.isEmpty());
    }

    @Test
    public void insertAndGetTasks() throws InterruptedException {
        // BEFORE: Adding a new project
        database.projectDao().createProject(PROJECT_DEMO);
        // Create task
        database.taskDao().insertTask(TASK_DEMO);

        // TEST
        List<Task> tasks = LiveDataTestUtil.getValue(this.database.taskDao().getTasks());
        assertEquals(1, tasks.size());
    }

    @Test
    public void insertAndDeleteTask() throws InterruptedException {
        // BEFORE: Adding a new project
        database.projectDao().createProject(PROJECT_DEMO);
        // Create task
        database.taskDao().insertTask(TASK_DEMO);
        List<Task> tasks = LiveDataTestUtil.getValue(this.database.taskDao().getTasks());
        Task taskAdded = tasks.get(0);
        // Delete task
        database.taskDao().deleteTask(taskAdded.getId());

        // TEST
        tasks = LiveDataTestUtil.getValue(this.database.taskDao().getTasks());
        assertTrue(tasks.isEmpty());
    }
}
