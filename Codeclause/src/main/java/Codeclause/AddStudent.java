package Codeclause;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import lombok.Data;

@Entity
@Table
@Data
public class AddStudent {

	
	private String fullname;
	private String email;
	@Id
	private String studId;
	private String contact;
	private String pass;
	private String gender;
	private String semester;
	
}
