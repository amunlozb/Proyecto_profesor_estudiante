package com.example.estudiante.servicio;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.estudiante.modelo.Estudiante;

@Service
public class ServicioEstudiante {
		// Lista que almacena los estudiantes.
	   private List<Estudiante> estudiantes = new ArrayList<>(List.of(
			   new Estudiante("Paco", 13, "3ESO"), 
			   new Estudiante("Juan", 14, "4ESO"), 
			   new Estudiante("Juan", 13, "2ESO")
	    ));
	   
	   /**
	    * Agrega un nuevo estudiante a la lista.
	    *
	    * @param estudiante El objeto Estudiante que será añadido a la lista.
	    */
	   public void agregarEstudiante(Estudiante estudiante) {
	        estudiantes.add(estudiante);
	    }

	   /**
	    * Recupera la lista completa de estudiantes.
	    *
	    * @return Una lista de objetos Estudiante.
	    */
	    public List<Estudiante> obtenerTodos() {
	    	
	        return estudiantes;
	    }

	   /**
	    * Busca estudiantes que coincidan con el nombre proporcionado.
	    *
	    * @param nombre El nombre de los estudiantes a buscar.
	    * @return Una lista de estudiantes que tienen coincidencias con el nombre proporcionado.
	    */
	    public List<Estudiante> buscarPorNombre(String nombre) {
	        return estudiantes.stream()
	                .filter(e -> e.getNombre().equalsIgnoreCase(nombre))
	                .collect(Collectors.toList());
	    }

	    /**
	     * Filtra la lista de estudiantes por curso.
	     *
	     * @param curso El curso específico para filtrar los estudiantes.
	     * @return Una lista de estudiantes que están inscritos en el curso especificado.
	     */
	    public List<Estudiante> filtrarPorCurso(String curso) {
	        return estudiantes.stream()
	                .filter(e -> e.getCurso().equalsIgnoreCase(curso))
	                .collect(Collectors.toList());
	    }

	    /**
	     * Calcula el promedio de edad de los estudiantes.
	     *
	     * @return El promedio de edad. Si no hay estudiantes, retorna 0.
	     */
	    public double promedioEdad() {
	        return estudiantes.stream()
	                .mapToInt(Estudiante::getEdad)
	                .average()
	                .orElse(0);
	    }
	    
	    /**
	     * Recupera una lista de todos los cursos únicos en los que están inscritos los estudiantes.
	     *
	     * @return Una lista de strings representando los nombres de los cursos.
	     */
	    public List<String> obtenerCursos() {
	        return estudiantes.stream().map(e -> e.getCurso()).distinct().collect(Collectors.toList());
	              
	    }
}
