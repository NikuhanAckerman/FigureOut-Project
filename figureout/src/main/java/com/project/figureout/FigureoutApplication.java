package com.project.figureout;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.sql.init.SqlInitializationAutoConfiguration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;

// Workaround
@SpringBootApplication
public class FigureoutApplication {

	public static void main(String[] args) throws ClassNotFoundException {
		SpringApplication.run(FigureoutApplication.class, args);

	}
}
