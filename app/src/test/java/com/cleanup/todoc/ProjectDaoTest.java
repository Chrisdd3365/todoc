package com.cleanup.todoc;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.cleanup.todoc.database.ToDocDatabase;
import com.cleanup.todoc.model.Project;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class ProjectDaoTest {
    //-- PROPERTIES
    // DATA
    private ToDocDatabase database;
    // DATA SET FOR TEST
    private static long PROJECT_ID = 2L;
    private static Project PROJECT_DEMO = new Project(PROJECT_ID, "Projet Lucidia", 0xFFB4CDBA);

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

    @Test
    public void insertAndGetProjects() throws InterruptedException {
        // BEFORE : init projects
        List<Project> projects = LiveDataTestUtil.getValue(database.projectDao().getProjects());
        assertEquals(0, projects.size());
        // adding a new project
        database.projectDao().createProject(PROJECT_DEMO);
        // refresh database
        projects = LiveDataTestUtil.getValue(database.projectDao().getProjects());
        // TEST
        assertEquals(1, projects.size());
    }

}
