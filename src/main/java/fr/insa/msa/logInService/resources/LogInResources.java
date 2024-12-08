package fr.insa.msa.logInService.resources;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.insa.msa.logInService.models.User;

@RestController
@CrossOrigin(origins = {"http://127.0.0.1:9090", "http://localhost:9090"})
@RequestMapping("/login")
public class LogInResources {
	
	private String dbUrl = "jdbc:mysql://srv-bdens.insa-toulouse.fr:3306/projet_gei_067";
	private String dbUsername = "projet_gei_067";
	private String dbPassword = "Cho7ugai";
	
	@GetMapping("/{email}+{password}")
	public User getUser(@PathVariable("email") String email, @PathVariable("password") String pwd) {
		
		// Get the user from the database based on email and password
		try {
			Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
			Statement stmt = connection.createStatement(); 
			ResultSet rs = stmt.executeQuery("SELECT * FROM Users;");
			
			while(rs.next()) {
				if (email.equals(rs.getString(5)) && pwd.equals(rs.getString(7))) {
					return new User(rs.getInt(1), pwd, rs.getString(2), email, rs.getString(6), rs.getString(3), rs.getString(4));
				}
			}
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
}