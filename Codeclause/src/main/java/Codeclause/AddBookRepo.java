package Codeclause;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AddBookRepo extends JpaRepository<AddBookEntity,Integer>{
	
	List<AddBookEntity> getBookByName(String bookname);
	List<AddBookEntity> findByid(int id);

}
