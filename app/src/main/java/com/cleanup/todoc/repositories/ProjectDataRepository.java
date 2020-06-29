package com.cleanup.todoc.repositories;

import android.arch.lifecycle.LiveData;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.database.dao.ProjectDao;

import java.util.List;

public class ProjectDataRepository {
    //-- PROPERTIES
    private final ProjectDao projectDao;

    //-- INIT
    public ProjectDataRepository(ProjectDao projectDao) { this.projectDao = projectDao; }


    //-- METHODS
    // GET PROJECT
    public LiveData<Project> getProject(long projectId) { return this.projectDao.getProject(projectId); }

    // GET PROJECTS
    public LiveData<List<Project>> getProjects() {
        return this.projectDao.getProjects();
    }

    // CREATE PROJECT
    public void createProject(Project project) {
        projectDao.createProject(project);
    }
}
