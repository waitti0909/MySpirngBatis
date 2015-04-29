package com.foya.noms.util;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

public class EncryptUtil {
	public static String encodeMD5(String password,String salt) {
		Md5PasswordEncoder md5 = new Md5PasswordEncoder();
		return md5.encodePassword(password, salt);
	}
	
	public static String encodeMD5(String password) {
		Md5PasswordEncoder md5 = new Md5PasswordEncoder();
		return md5.encodePassword(password, null);
	}
	
	public static String encodeSHA(String password,String salt) {
		ShaPasswordEncoder sha = new ShaPasswordEncoder();
		return sha.encodePassword(password, salt);
	}
	
	public static String encodeSHA(String password) {
		ShaPasswordEncoder sha = new ShaPasswordEncoder();
		return sha.encodePassword(password, null);
	}
	
	
	public static void main(String args[]){
		
		String a = "admin";
		
		System.out.println(EncryptUtil.encodeMD5(a, null));
		
		//SELECT HASHBYTES('SHA1','admin')
		//7C87541FD3F3EF5016E12D411900C87A6046A8E8
		String b ="d033e22ae348aeb5660fc2140aec35850c4da997";
		ShaPasswordEncoder sha = new ShaPasswordEncoder();
		
		System.out.println(sha.encodePassword(a, null));
	
		System.out.println(sha.isPasswordValid(b, a, null));
		
		
	}
}
