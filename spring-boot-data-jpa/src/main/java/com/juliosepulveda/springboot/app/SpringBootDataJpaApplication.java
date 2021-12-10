package com.juliosepulveda.springboot.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.juliosepulveda.springboot.app.models.service.IUploadFileService;

@SpringBootApplication
public class SpringBootDataJpaApplication implements CommandLineRunner {

	@Autowired
	IUploadFileService uploadFileService;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootDataJpaApplication.class, args);
	}

	/*
	 * Método para eliminarr y crear automáticamente la carpeta uploads cada vez que se arranca la App
	 */
	@Override
	public void run(String... args) throws Exception {
		
		uploadFileService.deleteAll();
		uploadFileService.init();
	}

}
