package com.cst438.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.cst438.domain.Course;
import com.cst438.domain.StudentDTO;
import com.cst438.domain.CourseRepository;
import com.cst438.domain.Enrollment;
import com.cst438.domain.EnrollmentRepository;
import com.cst438.domain.ScheduleDTO;
import com.cst438.domain.Student;
import com.cst438.domain.StudentRepository;
import com.cst438.service.GradebookService;

@RestController
@CrossOrigin
public class StudentController {
	@Autowired
	CourseRepository courseRepository;
	
	@Autowired
	StudentRepository studentRepository;
	
	@Autowired
	EnrollmentRepository enrollmentRepository;
	
	@Autowired
	GradebookService gradebookService;
	// get by primary key
//	REST api for a resource named "course" 
	// get by primary key
    // get by primary key
    @GetMapping("/student/{student_id}")
    public StudentDTO getStudent(@PathVariable("student_id") int student_id) {
        Optional<Student> studentOptional = studentRepository.findById(student_id);
        
        if (studentOptional.isPresent()) {
            // Convert the Student object to a StudentDTO (You need to create StudentDTO)
            Student student = studentOptional.get();
            StudentDTO studentDTO = new StudentDTO(
                    student.getStudent_id(), 
                    student.getName(), 
                    student.getEmail(), 
                    student.getStatus(), 
                    student.getStatusCode());

            return studentDTO;
        } else {
            // If the student is not found, return a 404 Not Found status
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found");
        }
    }

	// create a new course and return the system generated course_id
	@PostMapping("/student")
	@Transactional	
	public int createStudent(@RequestBody StudentDTO studentDTO) {
		Student student = new Student();
		student.setName(studentDTO.name());
		student.setEmail(studentDTO.email());
		student.setStatus(studentDTO.status());
		student.setStatusCode(studentDTO.status_code());
		studentRepository.save(student);
		return student.getStudent_id();
		
	}


    @DeleteMapping("/student/{student_id}")
    public void deleteStudent(@PathVariable("student_id") int student_id,
            @RequestParam("force") Optional<String> force) {
        Student student = studentRepository.findById(student_id).orElseThrow(() -> 
            new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found"));
        boolean e = enrollmentRepository.findById(student_id).isEmpty();
        if (e && force.isEmpty()) {
            // Implement logic for force delete if needed
        	throw  new ResponseStatusException( HttpStatus.BAD_REQUEST, "Student_id invalid.");
        }else {
        	studentRepository.delete(student);
        }
        
    }



	// update course
    @PutMapping("/student/{student_id}")
    public void updateStudent(@RequestBody StudentDTO studentDTO, @PathVariable("student_id") int student_id) {
        Student student = studentRepository.findById(student_id).orElseThrow(() -> 
        new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found"));
        student.setName(studentDTO.name());
        student.setEmail(studentDTO.email());
        student.setStatus(studentDTO.status());
        student.setStatusCode(studentDTO.status_code());
        studentRepository.findByEmail(studentDTO.email());
        studentRepository.save(student);
    }

	@GetMapping("/student")
	public StudentDTO[] getAllStudents() {
		List<Student> students = studentRepository.findAll();
		StudentDTO[] result = new StudentDTO[students.size()];
		for(int i =0; i <students.size();i++) {
			Student s = students.get(i);
			StudentDTO dto = new StudentDTO(s.getStudent_id(),
					s.getName(),
					s.getEmail(),
					s.getStatus(),
					s.getStatusCode());
			result[i] = dto;
		}
		return result;
		
	}
	
}