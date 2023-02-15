package Codeclause;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ControllerClass {
	
	//add student repo
	
	@Autowired
	AddStudentRepo repo1;

	//add Book repo
	@Autowired
	AddBookRepo repo2;
		
	//Student Books Add Repo
	
	@Autowired
	StudentBooksAddRepo repo3;
	
	
	//handler method for Home Page
	
	@RequestMapping("/home")
	public String Home() {
		
		return "home";
	}
	//redirect to sign in page
	@RequestMapping("/signin")
	public String LoginPage() {
		
		return "Login";
	}
	
	
	//redirect to add.html 
	@RequestMapping("/add")
	public String add() {
		
		
		return "add";
	}
	
	
	
	
	
	@RequestMapping("/addStudent")
	public String addStudent(@ModelAttribute AddStudent add,@RequestParam("studId") String studId,HttpServletResponse res) throws Exception{
		
		List<AddStudent> add1=repo1.findByStudId(studId);
		for(AddStudent stud:add1) {
			
			String dbstudId=stud.getStudId();
			if(studId.equals(dbstudId)) {
				PrintWriter out=res.getWriter();
				out.println("<h1>"+"<center>"+"yout have already an account"+"</center"+"</h1>");
				return "add";
			}
		}
		repo1.save(add);
		return "main";
		
		
		
		
	}
	
	@RequestMapping("/addbook")
	public String addbookpage() {
		
		
		
		return "addbook";
	}
	
	
	@RequestMapping("/bookadd")
	public String bookadd(@ModelAttribute AddBookEntity addbook) {
		
		repo2.save(addbook);
		return "addbook";
	}
	
	
	  
	@RequestMapping("/search") 
	  public String bookadd(@RequestParam("book") String booname,Model model) {
	 
	 System.out.println("bookname="+booname);
	 if(booname.startsWith("j") || booname.startsWith("J")) {
	//List<AddBookEntity> li=repo2.findAll();
		 
	 List<AddBookEntity> li=repo2.getBookByName(booname);
	 
	 model.addAttribute("allbooks",li);
	 
	 return "details-of-books"; 
	 }
	 
	 else if(booname.equals("python") || booname.equals("Python") ||
	 booname.equals("python programming") || booname.equals("p")){
		 //List<AddBookEntity> li=repo2.findAll();
		 List<AddBookEntity> li=repo2.getBookByName(booname);
		
		 model.addAttribute("allbooks",li);
		 return "details-of-books"; 
	 }
	
	 else if(booname.equals("code clause")){
				 //List<AddBookEntity> li=repo2.findAll();
				 List<AddBookEntity> li=repo2.getBookByName(booname);
				
				 model.addAttribute("allbooks",li);
				 return "details-of-books"; 
			 }
			
	 
	 return "main";
	 
	  }
	//Redirect BookIssue.html page
	@RequestMapping("/book-issue")
	public String IssueBook() {
		
		
		
		return "BookIssue";
	}
	//Book Issue Controller
	@RequestMapping("/IssueBook")
	public String IssueBook(@ModelAttribute StudentBooksAdd confirmBook,@RequestParam("studId") String StudId,@RequestParam("pass") String pass,
	
			@RequestParam("bid") int Id,@RequestParam("rn") int rn,Model model,HttpServletResponse res)throws Exception{
		
		PrintWriter out=res.getWriter();
		
		List<AddStudent> li=repo1.findByStudId(StudId);
		List<AddBookEntity> l1=repo2.findByid(Id);

		for(AddBookEntity abc:l1) {
		for(AddStudent add:li) {
			String studname=add.getFullname();
			String dbstudId=add.getStudId();
			String dbpass=add.getPass();
			int id=abc.getId();
			System.out.println("id="+id);
			String company=abc.getCompany();
			String isbn=abc.getIsbn();
			String name=abc.getName();
			Long price=abc.getPrice();
			String quality=abc.getQuality();
			
			System.out.println("studId="+dbstudId+"dbpass"+dbpass+"id"+id+"company"+company+"isbn"+isbn);
			
			if(StudId.equals(dbstudId) && pass.equals(dbpass)) {
				
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3307/LibraryMangementSystem","root","humtum123@");
					PreparedStatement pst=con.prepareStatement("insert into Student_Books_Add values(?,?,?,?,?,?,?,?,?,?)");
					System.out.println("connection");
					pst.setInt(1, rn);
					pst.setString(2, company);
					pst.setInt(3, id);
					pst.setString(4, isbn);
					pst.setString(5, name);
					pst.setString(6, dbpass);
					pst.setLong(7, price);
					pst.setString(8, quality);
					pst.setString(9, dbstudId);
					pst.setString(9, dbstudId);
					pst.setString(10, studname);
					
					System.out.println("connection");

					int a=pst.executeUpdate();
					System.out.println("a="+a);
					if(a>=1) {
						System.out.println("Record Inserted");
						return "bookissuesuccess";
					}else {
						System.out.println("!not");
					}
					
					
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			else if(StudId!=dbstudId) {
				try {
					out.print("<h1><center>"+"invalid student Id"+"</center></h1>");
				}catch(Exception e) {
					
				}
			}
			
			else if(pass != dbpass) {
				try {
					out.print("<h1><center>"+"invalid Password"+"</center></h1>");
				}catch(Exception e) {
					
				}
			}
			
		}}
		return "BookIssue";
	}
	
	//Login Controller
	@RequestMapping("/Login")
	public String LoginController(@RequestParam("studId") String stuid,@RequestParam("pass") String pass,Model model,HttpServletResponse res,HttpSession session) {
			
		List<StudentBooksAdd> list=repo3.findBystudId(stuid);
		List<AddStudent> addStud=repo1.findByStudId(stuid);
		
		
		for(AddStudent as:addStud) {
		for(StudentBooksAdd add:list) {
			
			
		
			String dbstuid=add.getStudId();
			String dbpass=add.getPass();
			String name=as.getFullname();
			
			if(stuid.equals(dbstuid) && pass.equals(dbpass)) {
				session.setAttribute("name",name);
					model.addAttribute("list",list);
		
					return "studentDetails";
			}
			else {
				try {
				PrintWriter out=res.getWriter();
				out.print("<h1><center>"+"invalid student Id or password"+"</center></h1>");
				}catch (Exception e) {
				}
			}
			
			
		}}
		return "Login";
	}

	//Admin Controller
	@RequestMapping("/admin")
	public String admin(@RequestParam("email") String email,@RequestParam("pass") String pass,Model model) {
		//hard corded
		if(email.equals("admin@imsec.ac.in") && pass.equals("adminpass")) {
			List<StudentBooksAdd> list=repo3.findAll();
			model.addAttribute("list",list);
			return "admin";
		}
	
		return "Login";
		
	}
	
	//redirecr to return books
	@RequestMapping("/redirect/to/Admin")
	public String Return() {
		return "return";
	}
	
	
	@RequestMapping("/delete")
	@Transactional
	public String ReturnBook(@RequestParam("studentId") String studentId,HttpServletResponse res) throws Exception{
		//find student in database
		List<StudentBooksAdd> list=repo3.findBystudId(studentId);
		
		for(StudentBooksAdd abc:list) {
			String studId=abc.getStudId();
			
			if(studentId.equals(studId)) {
				
				repo3.UpdateBystudId(0,null,null,null,null,null,studId);
				return "confirm";
			}
			else if(studentId!=studId) {
				PrintWriter out=res.getWriter();
				out.println("<h1>"+"<center>"+"Wrong Student Id"+"</center"+"</h1>");
			}
		}
		
		return "return";
	}
	
	
}



