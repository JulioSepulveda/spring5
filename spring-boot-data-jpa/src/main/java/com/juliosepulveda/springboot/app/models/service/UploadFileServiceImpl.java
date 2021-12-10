package com.juliosepulveda.springboot.app.models.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadFileServiceImpl implements IUploadFileService{

	/*
	 * Log
	 */
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	private final String UPLOADS_FOLDER = "uploads";
	
	@Override
	public Resource load(String filename) throws MalformedURLException {

		Path pathFoto = getPath(filename);
		log.info("pathFoto: " + pathFoto);
		Resource recurso = null;
		
		recurso = new UrlResource(pathFoto.toUri());
		if(!recurso.exists() && !recurso.isReadable()) {
			throw new RuntimeException("Error: no se puede cargar la imagen: " + pathFoto.toString());
		}
		
		return recurso;
	}

	@Override
	public String copy(MultipartFile file) throws IOException {
		
		/*
		 * Nombre único para que no se machaquen archivos con el mismo nombre
		 */
		String uniqueFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
		
		/*
		 * De esta manera estamos guardando las imagenes que se cargan directamente en el proyecto.
		 * No es una buena práctica
		 */
//		Path directorioRecursos = Paths.get("src//main//resources//static/uploads");
//		String rootPath = directorioRecursos.toFile().getAbsolutePath();
		
		/*
		 * Forma para guardar los archivos de las imagenes en una ruta externa al proyecto
		 */
//		String rootPath = "C://Temp//uploads";
		
		/*
		 * Forma para guardar los archivos de las imagenes en un directorio absoluto y externo en raiz del proyecto
		 */
		Path rootPath = getPath(uniqueFileName);
		
		log.info("rootPath: " + rootPath);
		
		
		/*
		 * Guardamos el archivo en la ruta especificada
		 */
//			byte[] bytes = foto.getBytes();
//			Path rutaCompleta = Paths.get(rootPath + "//" + foto.getOriginalFilename());
//			Files.write(rutaCompleta, bytes);
		
		/*
		 * Otra forma de copiar el archivo más sencilla
		 */
		Files.copy(file.getInputStream(), rootPath);
		
		return uniqueFileName;
	}

	@Override
	public boolean delete(String filename) {

		/*
		 * Para eliminar el archivo de la imagen
		 */
		Path rootPath = getPath(filename);
		File archivo = rootPath.toFile();
		
		if(archivo.exists() && archivo.canRead()) {
			if(archivo.delete()) {
				return true;
			}
		}
		return false;
	}

	public Path getPath(String filename) {
		return Paths.get(UPLOADS_FOLDER).resolve(filename).toAbsolutePath();
	}

	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(Paths.get(UPLOADS_FOLDER).toFile());
		
	}

	@Override
	public void init() throws IOException {
		Files.createDirectory(Paths.get(UPLOADS_FOLDER));
		
	}
}
