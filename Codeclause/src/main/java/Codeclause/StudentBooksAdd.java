package Codeclause;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "StudentBooksAdd")
@Data
public class StudentBooksAdd {

	
	@Id
	private int id1;
	private String studName;
	private String studId;
	private int id;
	private String name;
	private String isbn;
	private String company;
	private Long price;
	private String quality;
	private String pass;

}
