package com.tuwaiq.lab5_projecttrackersystem.Controller;

import Api.ApiResponse;
import com.tuwaiq.lab5_projecttrackersystem.Model.Project;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/project-tracker-system")
public class ProjectTrackerController {

    ArrayList<Project> projects= new ArrayList<>();

    @PostMapping("/add")
    public ApiResponse addProject(@RequestBody Project project){
        projects.add(project);
        return new ApiResponse("The project have been added successfully");
    }

    @GetMapping("/get")
    public ArrayList<Project> getProjects(){
        return projects;
    }

    @PutMapping("/update/{index}")
    public ApiResponse updateProject(@PathVariable int index, @RequestBody Project project){
        if (projects.size()-1<index){
            return new ApiResponse("No project found");
        }
        projects.set(index,project);
        return new ApiResponse("The project have been updated successfully");
    }

    @DeleteMapping("/delete/{index}")
    public ApiResponse deleteProject(@PathVariable int index){
        projects.remove(index);
        return new ApiResponse("The project have been deleted successfully");
    }

    @PutMapping("/update-status/{id}")
    public ApiResponse changeStatus(@PathVariable String id){
        for (Project project :projects){
            if (project.getId().equalsIgnoreCase(id)){
                if (project.getStatus().equalsIgnoreCase("not done")){
                    project.setStatus("done");
                }
                else {
                    project.setStatus("not done");
                }
                return new ApiResponse("The project status have been changed to "+project.getStatus());
            }
        }
        return new ApiResponse("No project found");
    }

    @GetMapping("/get/status/{id}")                             /* added as extra */
    public ApiResponse getStatus(@PathVariable String id){
        for (Project project :projects){
            if (project.getId().equalsIgnoreCase(id)){
                return new ApiResponse("The project status is "+project.getStatus());
            }
        }
        return new ApiResponse("No project found");
    }

    @GetMapping("/get/project/{title}")
    public Project searchByTitle(@PathVariable String title){
        for (Project project:projects){
            if (project.getTitle().equalsIgnoreCase(title)){
                return project;
            }
        }
        return null;
    }

    @GetMapping("/get/projects/{companyName}")
    public ArrayList<Project> getProjectsByCompany(@PathVariable String companyName){
        ArrayList<Project> companyProjects=new ArrayList<>();
        for (Project project:projects){
            if (project.getCompanyName().equalsIgnoreCase(companyName)){
                companyProjects.add(project);
            }
        }
        return companyProjects;
    }


}
