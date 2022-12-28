package account;

public class Account {
	private String email, password, name, address, phone;
	private String message ="";
	private int role;
	
	public Account(String email, String password) {
		this.email = email;
		this.password = password;
	}
	
	public Account(String email, String password, String name, String address, String phone) {
		this.email = email;
		this.password = password;
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.role = 1;
	}
	
	public boolean validate() {
		// regex email and password
		String regexEmail = "^\\w+@[A-Z0-9\\.a-z]+\\.[A-Za-z]{2,6}";
		String regexPassword = "[a-zA-Z0-9_!@#$%^&*]{6,}";
		
		// return false if email is invalid
		if(email.equals("") || password.equals("")) {
			password="";
			message = "";
			return false;
		}
		
		if(!email.matches(regexEmail) || !password.matches(regexPassword)) {
			message = "Invalid email or password";
			return false;
		}
		
		message = "";
		return true;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getRole() {
		return role;
	}
	public void setRole(int role) {
		this.role = role;
	}

	public String getMessage() {
		return this.message;
	}
}
