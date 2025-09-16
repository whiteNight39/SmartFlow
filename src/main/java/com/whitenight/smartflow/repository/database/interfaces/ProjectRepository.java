package com.whitenight.smartflow.repository.database.interfaces;

import com.whitenight.smartflow.model.entity.Project;

import java.util.UUID;

public interface ProjectRepository {

    void createProject(Project project);
    Project getProjectById(UUID projectId);
    void updateProject(Project project);
    void deleteProject(UUID projectId);

//    void assignProjectToStaff(UUID projectId, UUID staffId);
//    void approveProject(String projectId, String projectSmApprovalNotes, Boolean projectSmApproved);
//    void authoriseProject(String projectId, Boolean projectFinalAuthorised, String projectFinalApprovalNotes);
}
