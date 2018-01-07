package com.accolite.java_assignment;

import java.math.BigInteger;
import java.time.LocalDate;

public interface User_Interface {
	public boolean add_user(String new_name,String addr,String email,LocalDate birthday);
	boolean delete_user(user USER);
	boolean edit_user();
	user search_user(String name);
}

interface Contact_Interface{
	public boolean add_contact(String name,String email,String phoneno);
	boolean delete_contact();
	//boolean edit_user();
	contact search_contact(String name);
}
interface Telephone_Interface{
	public void dial();
	public void call(String name);
}