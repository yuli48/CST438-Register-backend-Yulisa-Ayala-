package com.cst438.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.cst438.domain.FinalGradeDTO;
import com.cst438.domain.Enrollment;
import com.cst438.domain.EnrollmentDTO;
import com.cst438.domain.EnrollmentRepository;

@Service
@ConditionalOnProperty(prefix = "gradebook", name = "service", havingValue = "rest")
@RestController
public class GradebookServiceREST implements GradebookService {

	private RestTemplate restTemplate = new RestTemplate();

	@Value("${gradebook.url}")
	private static String gradebook_url;
	
	public GradebookServiceREST() {
		System.out.println("gradebook service rest " );
	}

	@Override
	public void enrollStudent(String student_email, String student_name, int course_id) {
		System.out.println("Start Message "+ student_email +" " + course_id); 
		EnrollmentDTO dto = new EnrollmentDTO(0, student_email, student_name, course_id);
		EnrollmentDTO result = restTemplate.postForObject(gradebook_url + "/enrollment/", dto, EnrollmentDTO.class);
		System.out.println("POST result " + result);
	}
	
	@Autowired
	EnrollmentRepository enrollmentRepository;
	/*
	 * endpoint for final course grades
	 */
	@PutMapping("/course/{course_id}")
	@Transactional
	public void updateCourseGrades( @RequestBody FinalGradeDTO[] grades, @PathVariable("course_id") int course_id) {
		System.out.println("Grades received "+grades.length);
		for (FinalGradeDTO dto: grades) {
			Enrollment enrollment = enrollmentRepository.findByEmailAndCourseId(dto.studentEmail(), dto.courseId());
			if (enrollment !=null) {
				enrollment.setCourseGrade(dto.grade());
				enrollmentRepository.save(enrollment);
			} else {
				System.out.println("Error. Received final grade but could not find enrollment. "+dto.toString());
			}
		}
	}
}