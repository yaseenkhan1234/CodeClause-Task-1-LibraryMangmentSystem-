package Codeclause;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudentBooksAddRepo extends JpaRepository<StudentBooksAdd, Integer>{
	
		List<StudentBooksAdd> findBystudId(String stuid);
		
		@Modifying
		@Query(value = "update StudentBooksAdd set id=:id, name=:name, isbn=:isbn,company=:company, price=:price ,quality=:quality where studId=:dbStud")
		void UpdateBystudId(@Param("id") int id,@Param("name") String name,@Param("isbn") String isbn
				,@Param("company") String company,@Param("price") Long price,@Param("quality") String quality,
				 @Param("dbStud") String dbStud);
		
		
		
	
}
