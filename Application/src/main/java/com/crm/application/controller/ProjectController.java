package com.crm.application.controller;

import com.crm.application.dto.ProjectDTO;
import com.crm.application.dto.UserDTO;
import com.crm.application.service.ProjectService;
import com.crm.application.session.SessionManager;
import com.crm.domain.entity.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Optional;
import java.util.stream.Stream;

@Controller
public class ProjectController {
    private final ProjectService projectService;
    private final SessionManager sessionManager;


    @Autowired
    public ProjectController(
            ProjectService projectService,
            SessionManager sessionManager) {
        this.projectService = projectService;
        this.sessionManager = sessionManager;
    }

    public Optional<Project> createProject(String managerUsername , String name, String description) {
        return this.projectService.createNewProject(managerUsername, name, description);
    }

    public void updateProject(ProjectDTO projectDTO) {
        this.projectService.updateProject(projectDTO);
    }

    public Stream<ProjectDTO> getProjectNamesByUsername(String username) {
        return this.projectService.getProjectDTOByProjectName(username);
    }

    public Stream<UserDTO> getUsersByProjectName(String projectName) {
        String manager = this.sessionManager.getCurrentUser().getUsername();
        return this.projectService.getUsersByUsername(projectName, manager);
    }
}
