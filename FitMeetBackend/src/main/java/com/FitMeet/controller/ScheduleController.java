package com.FitMeet.controller;

import com.FitMeet.dto.ScheduleDto;
import com.FitMeet.model.Coache;
import com.FitMeet.model.Schedule;
import com.FitMeet.repository.ScheduleRepository;
import com.FitMeet.service.serviceImpl.ScheduleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ScheduleController {
@Autowired
    private ScheduleServiceImpl scheduleServiceImpl;
@Autowired
private ScheduleRepository scheduleRepository;
//    public ResponseEntity<Schedule> addproject(@RequestBody Schedule schedule
//            , @RequestParam("userId") Long userId) {
    @PostMapping("/createdschedule")
    public ResponseEntity<Schedule> addproject(@RequestBody ScheduleDto schedule) {
System.out.println("đã vô");
        Schedule projectsaved = scheduleServiceImpl.save(schedule);


        return ResponseEntity.ok(projectsaved);
    }

//    @GetMapping("/searchproject")
//    public ResponseEntity<List<Project>> searchproject(@RequestParam("data") String data) {
//        return ResponseEntity.ok(projectRepository.searchproject(data));
//    }

    @GetMapping("/getallschedule")
    public ResponseEntity<List<Schedule>> getallproject() {
        return ResponseEntity.ok(scheduleServiceImpl.findAll());
    }

//    @GetMapping("/getlistcoachchat")
//    public ResponseEntity<List<Coache>> getListCoachChat(@RequestParam("student_id")  Long student_id) {
//        System.out.println(student_id);
//        return ResponseEntity.ok(scheduleRepository.findListCoachByStudentId(student_id));
//    }

//    @GetMapping("/deleteproject")
//    public ResponseEntity<Void> deleteproject(@RequestParam("projectid") Long projectid) {
//        projectServiceImpl.deteleById(projectid);
//        return ResponseEntity.ok().build();
//    }
//
//    @GetMapping("/getprjectbyuserId")
//    public ResponseEntity<List<Project>> getprjectbyuserId(@RequestParam("userId") Long userId) {
//        List<Project> projectList= projectRepository.findProjectByUser(userId);
//        for(Project pro:projectList){
//            pro.setUserProjectList(null);
//            pro.setMessageList(null);
//        }
//        return ResponseEntity.ok(projectList);
//    }
//
//    @GetMapping("/findprojectbydate")
//    public ResponseEntity<List<Project>> findprojectbydate(@RequestParam("user_id") Long user_id
//            , @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
//        List<Project> project = projectRepository.findProjectdate(user_id, date);
//        if (project != null) {
//            List<Project> taskList=  projectRepository.findProjectdate(user_id, date);
//
//            for (Project note:taskList){
//                note.setUserProjectList(null);
//                note.setMessageList(null);
//            }
//
//            return ResponseEntity.ok(taskList);
//        } else {
//            throw new ResourceNotFoundException("Hôm nay rảnh.Không có việc");
//        }
//
//    }
//
//    @PostMapping("/updateproject")
//    public ResponseEntity<Project> updateproject(@RequestBody Project project
//            , @RequestParam("projectId") String projectId) {
//
//        project.setProjectStatus(ProjectStatus.TODO.toString());
//
//        Project projectsaved = projectServiceImpl.update(project,projectId);
//
//        return ResponseEntity.ok(projectsaved);
//    }


}
