package com.cst438.service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoder {

 public static void main(String[] args) {
     BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
     String password = "tom";
     String encrpted_password = encoder.encode(password);
     System.out.println(encrpted_password);
 }
}