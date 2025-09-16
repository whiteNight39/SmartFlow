package com.whitenight.smartflow.repository.database.implementation;

import com.whitenight.smartflow.mapper.ProjectMapper;
import com.whitenight.smartflow.model.entity.Project;
import com.whitenight.smartflow.repository.database.interfaces.ProjectRepository;
import com.whitenight.smartflow.repository.database.query.ProjectQuery;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class ProjectRepositoryImpl implements ProjectRepository{

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ProjectRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void createProject(Project project) {

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("projectCompanyId", project.getProjectCompanyId())
                .addValue("projectType", project.getProjectType())
                .addValue("projectCreatedById", project.getProjectCreatedById())
                .addValue("projectSupplierId", project.getProjectSupplierId())
                .addValue("projectAssignedSmId", project.getProjectAssignedSmId())
                .addValue("projectSmApproved", project.isProjectSmApproved())
                .addValue("projectSmApprovalNotes", project.getProjectSmApprovalNotes())
                .addValue("projectDeptHeadId", project.getProjectDeptHeadId())
                .addValue("projectFinalAuthorised", project.isProjectFinalAuthorised())
                .addValue("projectFinalApprovalNotes", project.getProjectFinalApprovalNotes())
                .addValue("projectContractStartDate", project.getProjectContractStartDate())
                .addValue("projectContractEndDate", project.getProjectContractEndDate())
                .addValue("projectContractCurrency", project.getProjectContractCurrency())
                .addValue("projectContractTotalAmount", project.getProjectContractTotalAmount())
                .addValue("projectRfxCurrency", project.getProjectRfxCurrency())
                .addValue("projectRfxDeadline", project.getProjectRfxDeadline())
                .addValue("projectRfxPaymentSplit", project.getProjectRfxPaymentSplit());

        jdbcTemplate.update(ProjectQuery.CREATE_PROJECT, params);
    }

    @Override
    public Project getProjectById(UUID projectId) {

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("projectId", projectId);

        List<Project> projects = jdbcTemplate.query(ProjectQuery.GET_PROJECT_BY_ID, params,
                new ProjectMapper());

        return projects.isEmpty() ? null : projects.getFirst();
    }

    @Override
    public void updateProject(Project project) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("projectId", project.getProjectId())
                .addValue("projectType", project.getProjectType())
                .addValue("projectSupplierId", project.getProjectSupplierId())
                .addValue("projectAssignedSmId", project.getProjectAssignedSmId())
                .addValue("projectSmApproved", project.isProjectSmApproved())
                .addValue("projectSmApprovalNotes", project.getProjectSmApprovalNotes())
                .addValue("projectDeptHeadId", project.getProjectDeptHeadId())
                .addValue("projectFinalAuthorised", project.isProjectFinalAuthorised())
                .addValue("projectFinalApprovalNotes", project.getProjectFinalApprovalNotes())
                .addValue("projectContractStartDate", project.getProjectContractStartDate())
                .addValue("projectContractEndDate", project.getProjectContractEndDate())
                .addValue("projectContractCurrency", project.getProjectContractCurrency())
                .addValue("projectContractTotalAmount", project.getProjectContractTotalAmount())
                .addValue("projectRfxCurrency", project.getProjectRfxCurrency())
                .addValue("projectRfxDeadline", project.getProjectRfxDeadline())
                .addValue("projectRfxPaymentSplit", project.getProjectRfxPaymentSplit())
                .addValue("projectStatus", project.getProjectStatus());

        jdbcTemplate.update(ProjectQuery.UPDATE_PROJECT, params);
    }


    @Override
    public void deleteProject(UUID projectId) {

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("projectId", projectId);

        jdbcTemplate.update(ProjectQuery.DELETE_PROJECT, params);
    }
}
