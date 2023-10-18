package com.cst438;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cst438.domain.Course;
import com.cst438.domain.CourseRepository;
import com.cst438.domain.Enrollment;
import com.cst438.domain.EnrollmentRepository;

/*
 * This example shows how to use selenium testing using the web driver 
 * with Chrome browser.
 * 
 *  - Buttons, input, and anchor elements are located using XPATH expression.
 *  - onClick( ) method is used with buttons and anchor tags.
 *  - Input fields are located and sendKeys( ) method is used to enter test data.
 *  - Spring Boot JPA is used to initialize, verify and reset the database before
 *      and after testing.
 *      
 *    Make sure that TEST_COURSE_ID is a valid course for TEST_SEMESTER.
 *    
 *    URL is the server on which Node.js is running.
 */

@SpringBootTest
public class EndToEndScheduleTest {

	public static final String CHROME_DRIVER_FILE_LOCATION = "C:/chromedriver_win32/chromedriver.exe";

	public static final String URL = "http://localhost:3000";

	public static final String TEST_USER_EMAIL = "test@csumb.edu";

	public static final int TEST_COURSE_ID = 40442;

	public static final String TEST_SEMESTER = "2021 Fall";
	
	public static final int TEST_STUDENT_ID = 12345;

	public static final int SLEEP_DURATION = 1000; // 1 second.


	@Test
	public void addStudentTest() throws Exception {
		System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_FILE_LOCATION);
		WebDriver driver = new ChromeDriver();
		// Puts an Implicit wait for 10 seconds before throwing exception
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		try {

			driver.get(URL);
			Thread.sleep(SLEEP_DURATION);

			// select the last of the radio buttons on the list of semesters page.
			List<WebElement> weList = driver.findElements(By.xpath("//input"));
			
			// should be 3 elements in list. click on last one for 2021 Fall
			weList.get(2).click();

			// Locate and click "View Students" button
			driver.findElement(By.id("viewStudents")).click();
			Thread.sleep(SLEEP_DURATION);

			// Locate and click "Add Students" button which is the first and only button on the page.
			driver.findElement(By.id("addStudents")).click();
			Thread.sleep(SLEEP_DURATION);

			// enter student id and click Add button
			driver.findElement(By.id("studentId")).sendKeys(Integer.toString(TEST_STUDENT_ID));
			driver.findElement(By.id("add")).click();
			Thread.sleep(SLEEP_DURATION);

			/*
			 * verify that new student shows in schedule. search for the id of the student
			 * in the updated list.
			 */
			WebElement we = driver.findElement(By.xpath("//tr[td='" + TEST_STUDENT_ID + "']"));
			assertNotNull(we, "Test student id not found in list.");

			// drop the student
			WebElement dropButton = we.findElement(By.xpath("//button"));
			assertNotNull(dropButton);
			dropButton.click();

			// the drop student action causes an alert to occur.
			WebDriverWait wait = new WebDriverWait(driver, 1);
			wait.until(ExpectedConditions.alertIsPresent());

			Alert simpleAlert = driver.switchTo().alert();
			simpleAlert.accept();

			// check that student is no longer in the list
			Thread.sleep(SLEEP_DURATION);
			assertThrows(NoSuchElementException.class, () -> {
				driver.findElement(By.xpath("//tr[td='" + TEST_STUDENT_ID + "']"));
			});

		} catch (Exception ex) {
			throw ex;
		} finally {
			driver.quit();
		}
	}
	
	@Test
	public void updateStudentTest() throws Exception {
		System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_FILE_LOCATION);
		WebDriver driver = new ChromeDriver();
		// Puts an Implicit wait for 10 seconds before throwing exception
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		try {

			driver.get(URL);
			Thread.sleep(SLEEP_DURATION);

			// select the last of the radio buttons on the list of semesters page.
			List<WebElement> weList = driver.findElements(By.xpath("//input"));
			
			// should be 3 elements in list. click on last one for 2021 Fall
			weList.get(2).click();

			// Locate and click "View Students" button
			driver.findElement(By.id("viewStudents")).click();
			Thread.sleep(SLEEP_DURATION);
			
			// creates the edit button to be clicked
			WebElement EB = driver.findElement(By.xpath("//button[text()='Edit']"));
			assertNotNull(EB);
			EB.click();
			
			//Updates the students name
			driver.findElement(By.id("studentName")).clear();
			driver.findElement(By.id("studentName")).sendKeys("update student name");

			// Updates the students email
			driver.findElement(By.id("studentEmail")).clear();
			driver.findElement(By.id("studentEmail")).sendKeys("update student email");
			
			// Creates the save button to save information
			driver.findElement(By.id("save")).click();
			Thread.sleep(SLEEP_DURATION);
			
			/*
			 * verify that new student information has been saved
			 */
			WebElement we = driver.findElement(By.xpath("//tr[td='update student name']"));
			assertNotNull(we, "Test student name not updated in list.");
		} catch (Exception ex) {
			throw ex;
		} finally {
			driver.quit();
		}
	}
	
	@Test
	public void deleteStudentTest() throws Exception {
		System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_FILE_LOCATION);
		WebDriver driver = new ChromeDriver();
		// Puts an Implicit wait for 10 seconds before throwing exception
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		try {

			driver.get(URL);
			Thread.sleep(SLEEP_DURATION);

			// select the last of the radio buttons on the list of semesters page.
			List<WebElement> weList = driver.findElements(By.xpath("//input"));
			
			// should be 3 elements in list. click on last one for 2021 Fall
			weList.get(2).click();

			// Locate and click "View Students" button
			driver.findElement(By.id("viewStudents")).click();
			Thread.sleep(SLEEP_DURATION);

			// creates the delete button to be clicked
			WebElement DB = driver.findElement(By.xpath("//button[text()='Delete']"));
			assertNotNull(DB);
			DB.click();
			
			// the delete student action causes an alert to occur.
			WebDriverWait wait = new WebDriverWait(driver, 1);
			wait.until(ExpectedConditions.alertIsPresent());
			
			Alert simpleAlert = driver.switchTo().alert();
			simpleAlert.accept();

			// check that student is no longer in the list
			Thread.sleep(SLEEP_DURATION);
			assertThrows(NoSuchElementException.class, () -> {
				driver.findElement(By.xpath("//tr[td='update student name']"));
			});
		} catch (Exception ex) {
			throw ex;
		} finally {
			driver.quit();
		}
	}

}