package com.accolite.java_assignment;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Telephone {
	static List<user> user_arr = new ArrayList<user>();
	List<contact> contact_arr = new ArrayList<contact>();
	List<Telephone_Working> Tele_arr = new ArrayList<Telephone_Working>();
	final private BigInteger My_Phone_No = new BigInteger("9876598765");


	static void create_user(contact contact_ref) {
		Scanner sc = new Scanner(System.in);
	
		System.out.println("Enter your name : ");
		String user_name = sc.nextLine();
		String email_id = null;
		System.out.println("Enter your Address : ");
		String address = sc.nextLine();
		do{
			System.out.println("Enter your Email : ");
			email_id = sc.nextLine();
		}
		while(!(contact_ref.isValidEmailAddress(email_id)));
		user_arr.add(new user(user_name,address,email_id, null));
		System.out.println("Created Admin User : " + user_name);
	}
	public static void main(String[] args) {

		user user_ref = new user(); 
		contact contact_ref = new contact();
		Telephone_Working tele_ref = new Telephone_Working();
		System.out.println("--------Telephone-------");
		Scanner sc = new Scanner(System.in);
		create_user(contact_ref);
		
		while(true) {
			System.out.println("  ");

			System.out.println("Select any of the following choices");
			System.out.println("1 : dial a number  "
					+ "2 : make a call" +"\n" +  "3 : get callhistory" + 
					" 4 . get all contacts" +"\n"+ "5 . delete a contact" +
					" 6 . add contact"+  "\n" + "7 . find a contact" +  " 8.exit");
			switch(sc.next()) {

			case "1":	tele_ref.dial();
			break;
			case "2":	System.out.println("Enter contact name : ");
			tele_ref.call(sc.next());
			break;
			case "3":	tele_ref.call_hisory();
			break;
			case "4":	contact_ref.get_all();
			break;
			case "5":
						contact_ref.delete_contact();
			break;
			case "6":	System.out.println("Enter contact name : ");
			String name = sc.next();
			System.out.println("Enter contact email : ");
			String email = sc.next();
			System.out.println("Enter Phone Number : ");
			String phn = sc.next();
			if(contact_ref.add_contact(name,email,phn)) {
				System.out.println("Added "+ name);
			}
			else {
				System.out.println("Failed to add " + name);
			}
			break;
			case "7":	
				sc = new Scanner(System.in);
				System.out.println("Enter contact name : ");
				name = sc.nextLine();
				contact_ref.search_contact(name);
				break;
			case "8":	return;
			default : System.out.println("Choose a valid Option");
			break;
			}
		}

	}



}

class Telephone_Working implements Telephone_Interface{

	String dailed_name = "";
	LocalDate Date = null;
	LocalTime Time = null;
	long Call_Duration = 0;
	
	Telephone T = new Telephone();
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	contact C = new contact();
	Telephone_Working(){

	}

	Telephone_Working(String name, long duration){
		this.dailed_name = name;
		this.Date = LocalDate.now();
		this.Time = LocalTime.now();
		this.Call_Duration = duration;
	}

	public void dial(){
		try {
			System.out.println("Enter Phone Number: ");
			String temp = br.readLine();
			if(temp.length() != 10) {
				System.out.println("Please enter a 10 digit phone number");
			}
			else {
				BigInteger phn_no = new BigInteger(temp);
			}
		} catch (Exception e) {
			System.out.println("Enter Valid Phone Number");
			e.printStackTrace();
		}
	}

	public void call(String name) {
		int temp = 1;
		if(C.search_contact(name) == null){
			System.out.println("This contact is not available");
			Scanner sc = new Scanner(System.in);
			System.out.println("Enter Mobile number : ");
			String phoneno = sc.nextLine();
			try {
				if(phoneno.length() != 10) {
					System.out.println("Enter a Valid Phone Number");
					return ;
				}
				BigInteger phn_no = new BigInteger(phoneno);
			}
			catch(Exception e) {
				System.out.println("Enter a Valid Phone Number");
				return;
			}
		}
		System.out.println("Calling....Calling.....");
		System.out.println("Connected");
		long startTime = System.nanoTime();
		while(temp == 1) {
			System.out.println("Enter 0 to disconnect");
			try {
				temp =	Integer.parseInt(br.readLine());
			} catch (NumberFormatException | IOException e) {
				System.out.println("Error occured");
				break;
			}
		}
		long endTime = System.nanoTime();
		System.out.println("time " + (endTime-startTime)/1000000000 + "seconds");
		System.out.println("Disconnected");
		T.Tele_arr.add(new Telephone_Working(name,(endTime-startTime)/1000000000 ));
	}


	public void call_hisory() {
		int i;
		for(i=T.Tele_arr.size()-1;i >= 0;i--) {
			System.out.print(T.Tele_arr.get(i).dailed_name);
			System.out.print(" " +T.Tele_arr.get(i).Date);
			System.out.println(" " +T.Tele_arr.get(i).Time);
			System.out.println(" " +T.Tele_arr.get(i).Call_Duration);
		}
	}
}

class user implements User_Interface{
	protected String user_name = "";
	protected String address = "";
	protected String email_id = "";
	protected LocalDate dob = LocalDate.now();
	Telephone T = new Telephone();
	user(){

	}
	user(String name, String addr,String email,LocalDate birthday){
		this.user_name = name;
		this.address = addr;
		this.email_id = email;
		this.dob = birthday;
	}

	@Override
	public boolean add_user(String new_name, String addr, String email, LocalDate birthday) {
		// TODO Auto-generated method stub
		user obj = new user(new_name,addr,email,birthday);
		if(T.user_arr.add(obj))
			return true;
		return false;
	}

	@Override
	public boolean delete_user(user obj1) {
		// TODO Auto-generated method stub
		for(user obj : T.user_arr) {
			if(obj.equals(obj1)) {
				T.user_arr.remove(obj);
				return true;
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((dob == null) ? 0 : dob.hashCode());
		result = prime * result + ((email_id == null) ? 0 : email_id.hashCode());
		result = prime * result + ((user_name == null) ? 0 : user_name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		user other = (user) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (dob == null) {
			if (other.dob != null)
				return false;
		} else if (!dob.equals(other.dob))
			return false;
		if (email_id == null) {
			if (other.email_id != null)
				return false;
		} else if (!email_id.equals(other.email_id))
			return false;
		if (user_name == null) {
			if (other.user_name != null)
				return false;
		} else if (!user_name.equals(other.user_name))
			return false;
		return true;
	}

	@Override
	public boolean edit_user() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public user search_user(String name) {
		// TODO Auto-generated method stub
		for(user obj : T.user_arr) {
			if(obj.user_name.equals(name)) {
				return obj;
			}
		}
		return null;
	}

}


class contact implements Contact_Interface{
	protected String contact_name = "";
	protected String email = "";
	protected BigInteger phone = null;
	Telephone T = new Telephone();
	contact(){

	}
	contact(String name,String email,BigInteger phoneno){
		this.contact_name = name;
		this.email = email;
		this.phone = phoneno;
	}


	void get_all() {
		for(contact obj : T.contact_arr) {
			System.out.println("Name : " + obj.contact_name);
			System.out.println("Phone Number : " + obj.phone);
			System.out.println("Email id : " + obj.email);
		}
	}

	@Override
	public contact search_contact(String name) {
		// TODO Auto-generated method stub
		for(contact obj : T.contact_arr) {
			if(obj.contact_name.equals(name)) {
				System.out.println("Name : " + obj.contact_name);
				System.out.println("Phone Number : " + obj.phone);
				System.out.println("Email id : " + obj.email);
				return obj;
			}
		}
		System.out.println("No Contact Found");
		return null;
	}

	@Override
	public boolean add_contact(String name,String email,String phoneno){

		// TODO Auto-generated method stub
		BigInteger phn_no = null;
		contact obj = null;
		if(!(search_contact(name) == null)) {
			System.out.println("Contact Already Exists");
			return false;
		}
		if(!isValidEmailAddress(email)) {
			System.out.println("Enter a Valid Email-id");
			return false;
		}
		try {
			if(phoneno.length() != 10) {
				System.out.println("Enter a Valid Phone Number");
			}
			phn_no = new BigInteger(phoneno);
			obj = new contact(name,email,phn_no);
		}
		catch(Exception e) {
			System.out.println("Enter a Valid Phone Number");
			e.printStackTrace();
			return false;
		}

		if(obj!= null && T.contact_arr.add(obj))
			return true;
		return false;
	}

	@Override
	public boolean delete_contact() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter Contact name : ");
		String name = sc.nextLine();
		for(contact obj : T.contact_arr) {
			if(obj.contact_name.equals(name)) {
				T.contact_arr.remove(obj);
				System.out.println("Deleted Contact" + obj.contact_name);
				return true;
			}
		}
		System.out.println("Contact not found");
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((contact_name == null) ? 0 : contact_name.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		contact other = (contact) obj;
		if (contact_name == null) {
			if (other.contact_name != null)
				return false;
		} else if (!contact_name.equals(other.contact_name))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		return true;
	}

	public boolean isValidEmailAddress(String email) {
		String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
		java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
		java.util.regex.Matcher m = p.matcher(email);
		return m.matches();
	}
}