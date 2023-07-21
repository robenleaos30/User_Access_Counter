import java.io.*;
import java.util.*;

public class DataStreams {

	private static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {

		try {

			LoginBusiness business = new LoginBusiness();
			SecurityInfo info = null;

			// Show Message
			System.out.println("------------------------------");
			System.out.println("Welcome to the Access Counter!");
			System.out.println("------------------------------");
			System.out.println();

			// check login or signUp
			if (business.isEmpty()) {

				// registration
				System.out.println("You need to registration!");

				// sign Up
				info = business.signUp(
					read("User Name"),
					read("Login ID"),
					read("Password"));

			} else {

				// login message
				System.out.println("You need to Login!");

				// login
				info = business.login(
					read("Login ID"),
					read("Password"));
			}

			System.out.println("------------------------------");
			System.out.printf("User Name    : %s%n", info.getName());
			System.out.printf("Access Count : %d%n", info.getAccessCount());
			System.out.println("------------------------------");

		} catch (RuntimeException e) {
			System.out.println("------------------------------");
			System.out.println("Error : " + e.getMessage());
			System.out.println("------------------------------");
		}
	}

	private static String read(String message) {
		System.out.printf("%s-10:", message);
		return sc.nextLine();
	}
}

class LoginBusiness {

	private SecurityManager security;
	private SecurityInfo info;

	LoginBusiness() {
		security = new SecurityManager();
		info = security.read();
	}

	boolean isEmpty() {
		return null == info;
	}

	SecurityInfo login(String loginId, String password) {

		if (loginId.isEmpty()) {
			throw new RuntimeException("Please enter the loginId!");
		}

		if (password.isEmpty()) {
			throw new RuntimeException("Please enter the password!");
		}

		if (!info.getLoginId().equals(loginId)) {
			throw new RuntimeException("Please check your loginId!");
		}

		if (!info.getPassword().equals(password)) {
			throw new RuntimeException("Please check your password!");
		}

		info.access();
		security.save(info);

		return info;
	}

	SecurityInfo signUp(String name, String loginId, String password) {

		if (name.isEmpty()) {
			throw new RuntimeException("Please enter the name!");
		}

		if (loginId.isEmpty()) {
			throw new RuntimeException("Please enter the loginId!");
		}

		if (password.isEmpty()) {
			throw new RuntimeException("Please enter the password!");
		}

		info = new SecurityInfo(name, loginId, password);

		info.access();
		security.save(info);

		return info;
	}
}

class SecurityManager {

	private static final String FILE = "login.txt";

	private SecurityInfo info = null;

	public SecurityInfo read() {

		try (var dataInput = new DataInputStream(new FileInputStream(FILE))) {

			info = new SecurityInfo();

			info.setLoginId(dataInput.readUTF());
			info.setPassword(dataInput.readUTF());
			info.setName(dataInput.readUTF());
			info.setAccessCount(dataInput.readInt());


		} catch (FileNotFoundException e) {
			// doesn't login
		} catch (IOException e) {
			// Fata Error
			e.printStackTrace();
		}

		return info;
	}

	public void save(SecurityInfo info) {

		try (var dataOutput = new DataOutputStream(new FileOutputStream(FILE))) {

			dataOutput.writeUTF(info.getLoginId());
			dataOutput.writeUTF(info.getPassword());
			dataOutput.writeUTF(info.getName());
			dataOutput.writeInt(info.getAccessCount());

		} catch (FileNotFoundException e) {
			// doesn't login
		} catch (IOException e) {
			// Fata Error
			e.printStackTrace();
		}
	}
}

class SecurityInfo {

	private String loginId;
	private String password;
	private String name;
	private int accessCount;

	SecurityInfo() {}

	public void access() {
		accessCount ++;
	}

	SecurityInfo(String name, String loginId, String password) {
		this.name = name;
		this.loginId = loginId;
		this.password = password;
	}

	public String getLoginId() {
		return loginId;
	}

	public String getPassword() {
		return password;
	}

	public String getName() {
		return name;
	}

	public int getAccessCount() {
		return accessCount;
	}

	public void setLoginId(String data) {
		this.loginId = data;
	}

	public void setPassword(String data) {
		this.password = data;
	}

	public void setName(String data) {
		this.name = data;
	}

	public void setAccessCount(Integer data) {
		this.accessCount = data;
	}
}