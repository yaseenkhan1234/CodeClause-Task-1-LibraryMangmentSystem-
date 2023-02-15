package Codeclause;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AddStudentRepo extends JpaRepository<AddStudent, String>{
	
	
	/*
	 * @Query("from AddStudent where stud_id=:id") public String
	 * findBystudId(@Param("id") String id);
	 * 
	 * String findByStudId(String studId);
	 */
	List<AddStudent> findByStudId(String studId);
	
	
}
