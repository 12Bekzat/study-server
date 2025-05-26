package com.study.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.study.entities.Course;
import com.study.entities.CourseDto;
import com.study.entities.Lesson;
import com.study.entities.LessonDto;
import com.study.entities.Test;
import com.study.repos.ICourseRepo;
import com.study.repos.ILessonRepo;
import com.study.repos.IRoleRepository;
import com.study.repos.ITestRepo;
import com.study.repos.IUserRepo;

import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
public class MainController {
    @Autowired
    private IUserRepo userRepo;
    @Autowired
    private IRoleRepository roleRepository;
    @Autowired
    private ICourseRepo courseRepo;
    @Autowired
    private ILessonRepo lessonRepo;
    @Autowired
    private ITestRepo testRepo;

    @GetMapping("/api/course/getPaged")
    public ResponseEntity<List<Course>> getCourses() {
        List<Course> all = courseRepo.findAll();
        
        return ResponseEntity.ok(all);
    }

    @GetMapping("/api/course/getById/{id}")
    public ResponseEntity<Course> getByIdCourse(@PathVariable Long id) {
        Course course = courseRepo.findById(id).get();
        
        return ResponseEntity.ok(course);
    }

    @GetMapping("/api/lesson/getById/{id}")
    public ResponseEntity<Lesson> getByIdLesson(@PathVariable Long id) {
        Lesson lesson = lessonRepo.findById(id).get();
        
        return ResponseEntity.ok(lesson);
    }

    @GetMapping("/api/test/getById/{id}")
    public ResponseEntity<Test> getByIdTest(@PathVariable Long id) {
        Test course = testRepo.findById(id).get();
        
        return ResponseEntity.ok(course);
    }
    
    @PostMapping("/api/course/remove/{id}")
    public ResponseEntity<Course> removeCourse(@PathVariable Long id) {
        courseRepo.deleteById(id);
        
        return ResponseEntity.ok(new Course());
    }

    @GetMapping("/api/lesson/remove/{id}")
    public ResponseEntity<Lesson> removeLesson(@PathVariable Long id) {
        lessonRepo.deleteById(id);
        
        return ResponseEntity.ok(new Lesson());
    }

    @GetMapping("/api/test/remove/{id}")
    public ResponseEntity<Test> removeTest(@PathVariable Long id) {
        testRepo.deleteById(id);
        
        return ResponseEntity.ok(new Test());
    }

    @GetMapping("/api/lesson/getPaged")
    public ResponseEntity<List<Lesson>> getLessons() {
        List<Lesson> all = lessonRepo.findAll();
        
        return ResponseEntity.ok(all);
    }

    @GetMapping("/api/test/getPaged")
    public ResponseEntity<List<Test>> getTests() {
        List<Test> all = testRepo.findAll();
        
        return ResponseEntity.ok(all);
    }

    @PostMapping("/api/course/create")
    public ResponseEntity<Course> createCourse(@RequestBody CourseDto courseDto) {
        Course course = new Course();
        course.setTitle(courseDto.getTitle());
        course.setPreview(courseDto.getPreview());
        course.setDescription(courseDto.getDescription());

        Course savedCourse = courseRepo.save(course);

        if(!courseDto.getLessons().isEmpty()) {
            for (LessonDto lessonDto : courseDto.getLessons()) {
                Lesson lesson = new Lesson();
                lesson.setCourseId(savedCourse.getId());
                lesson.setVideoUrl(lessonDto.getVideoUrl());
                lesson.setTitle(lessonDto.getTitle());
                lesson.setDescription(lessonDto.getDescription());
                lesson.setReview(false);

                Lesson savedLesson = lessonRepo.save(lesson);

                if(lessonDto.getTest() != null) {
                    Test newTest = new Test();
                    newTest.setTitle(lessonDto.getTest().getTitle());
                    newTest.setQuestions(lessonDto.getTest().getQuestions());
                    newTest.setLessonId(savedLesson.getId());

                    testRepo.save(newTest);
                }
            }
        }
        
        return ResponseEntity.ok(course);
    }

    @PostMapping("/api/test/create")
    public ResponseEntity<Test> createTest(@RequestBody Test test) {
        Test savedTest = testRepo.save(test);
        
        return ResponseEntity.ok(savedTest);
    }
    
    @PostMapping("/api/lesson/create")
    public ResponseEntity<Lesson> createLesson(@RequestBody LessonDto test) {
        Lesson lesson = new Lesson();
        lesson.setCourseId(test.getCourseId());
        lesson.setVideoUrl(test.getVideoUrl());
        lesson.setTitle(test.getTitle());
        lesson.setDescription(test.getDescription());
        Lesson savedTest = lessonRepo.save(lesson);

        Test newTest = new Test();
        newTest.setTitle(test.getTest().getTitle());
        newTest.setQuestions(test.getTest().getQuestions());
        newTest.setLessonId(savedTest.getId());
        
        return ResponseEntity.ok(savedTest);
    }
}
