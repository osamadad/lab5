package com.tuwaiq.lab5_studentsystem.Controller;

import Api.ApiResponse;
import com.tuwaiq.lab5_studentsystem.Model.Student;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/student-system")
public class StudentController {

    ArrayList<Student> students=new ArrayList<>();

    @PostMapping("/add")
    public ApiResponse addStudent(@RequestBody Student student){
        this.students.add(student);
        return new ApiResponse("The student have been added successfully");
    }

    @GetMapping("/get")
    public ArrayList<Student> getStudent(){
        return this.students;
    }

    @PutMapping("/update/{index}")
    public ApiResponse updateStudent(@PathVariable int index, @RequestBody Student student){
        if (students.size()-1<index){
            return new ApiResponse("No student found");
        }
        students.set(index,student);
        return new ApiResponse("The student have been updated successfully");
    }

    @DeleteMapping("/delete/{index}")
    public ApiResponse deleteStudent(@PathVariable int index){
        students.remove(index);
        return new ApiResponse("The student have been deleted successfully");
    }

    @GetMapping("/get/degree/{id}")                             /* added as extra */
    public ApiResponse getStudentDegree(@PathVariable String id){
        for (Student student :students) {
            if (student.getId().equalsIgnoreCase(id)) {
                return new ApiResponse("The student have a "+ student.getDegree()+" degree.");
            }
        }
        return new ApiResponse("No student found");
    }

    @GetMapping("/get/honor-category/{id}")
    public ApiResponse honorCategoriesOfStudent(@PathVariable String id){
        for (Student student :students){
            if (student.getId().equalsIgnoreCase(id)){
                double studentGpa=student.getGpa();
                if (studentGpa>=4.5){
                    return new ApiResponse("You are a great honorary student");
                } else if (studentGpa>=3.75) {
                    return new ApiResponse("You are a very good honorary student");
                } else if (studentGpa>=2.75) {
                    return new ApiResponse("You are a good honorary student");
                }else {
                    return new ApiResponse("You are a acceptable honorary student");
                }
            }
        }
        return new ApiResponse("No student found");
    }

    @GetMapping("/get/above-average-student")
    public ArrayList<Student> getAboveAverageStudent(){
        ArrayList<Student> aboveAverageStudent=new ArrayList<>();
        double averageGpa=0;
        for (Student student:students){
            averageGpa+=student.getGpa();
        }
        averageGpa/=students.size();
        for (Student student:students){
            if (student.getGpa()>averageGpa){
                aboveAverageStudent.add(student);
            }
        }
        return aboveAverageStudent;
    }
}
