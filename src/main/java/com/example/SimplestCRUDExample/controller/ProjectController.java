package com.example.SimplestCRUDExample.controller;

import com.example.SimplestCRUDExample.model.Project;
import com.example.SimplestCRUDExample.repo.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ProjectController {

    @Autowired
    ProjectRepository projectRepository;

    @GetMapping("/getAllProjects")
    public ResponseEntity<List<Project>> getAllProjects() {
        try {
            List<Project> projectList = new ArrayList<>();
            projectRepository.findAll().forEach(projectList::add);

            if (projectList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(projectList, HttpStatus.OK);
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getProjectById/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable Long id) {
        Optional<Project> projectObj = projectRepository.findById(id);
        if (projectObj.isPresent()) {
            return new ResponseEntity<>(projectObj.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/addProject")
    public ResponseEntity<Project> addBook(@RequestBody Project project) {
        try {
            Project projectObj = projectRepository.save(project);
            return new ResponseEntity<>(projectObj, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/updateProject/{id}")
    public ResponseEntity<Project> updateBook(@PathVariable Long id, @RequestBody Project project) {
        try {
            Optional<Project> projectData = projectRepository.findById(id);
            if (projectData.isPresent()) {
                Project updatedProjectData = projectData.get();
                updatedProjectData.setTitle(project.getTitle());
                updatedProjectData.setDescription(project.getDescription());

                Project projectObj = projectRepository.save(updatedProjectData);
                return new ResponseEntity<>(projectObj, HttpStatus.CREATED);
            }

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteProjectById/{id}")
    public ResponseEntity<HttpStatus> deleteBook(@PathVariable Long id) {
        try {
            projectRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/deleteAllProjects")
    public ResponseEntity<HttpStatus> deleteAllBooks() {
        try {
            projectRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
