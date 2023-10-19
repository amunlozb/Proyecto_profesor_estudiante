package com.example.estudiante.controlador;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.estudiante.modelo.Estudiante;
import com.example.estudiante.servicio.ServicioEstudiante;



@Controller
public class ControladorEstudiante {
	
	@Autowired
    private ServicioEstudiante servicioEstudiante;
	/**
	 * Maneja la solicitud GET para la ruta de inicio ("/").
	 * 
	 * Este método se encargará de preparar y proporcionar la lista de estudiantes
	 * para la vista inicial, permitiendo que se muestre en la página de inicio.
	 * 
	 * @param model  el objeto Model proporcionado por Spring MVC, utilizado para pasar 
	 *               atributos a la vista. 
	 * @return       el nombre de la vista que se va a renderizar (en este caso, "index" 
	 *               para index.html).
	 */
    // Nota: Aquí estamos mapeando a la "raíz" del contexto de "/estudiantes"
    @GetMapping("/")
    public String mostrarInicioLista(Model model) {
        List<Estudiante> listaEstudiantes = servicioEstudiante.obtenerTodos();
        double promedioEdad = servicioEstudiante.promedioEdad();
        List<String> cursos = servicioEstudiante.obtenerCursos();
        
        model.addAttribute("estudiantes", listaEstudiantes);
        model.addAttribute("promedioEdad",promedioEdad);
        model.addAttribute("cursos",cursos);
 
        return "index";  // nombre de la plantilla HTML a utilizar (index.html)
    }
    
    @GetMapping("/buscar")
    public String buscarEstudiante(@RequestParam String nombre, Model model) {
        // Buscar estudiantes por nombre.
        List<Estudiante> estudiantes = servicioEstudiante.buscarPorNombre(nombre);

        // Imprimir los estudiantes encontrados en la consola.
        if (estudiantes.isEmpty()) {
            System.out.println("No se encontraron estudiantes con el nombre: " + nombre);
        } else {
            System.out.println("Estudiantes encontrados: ");
            for (Estudiante estudiante : estudiantes) {
                System.out.println(estudiante);
            }
        }

        // Agregar estudiantes al modelo para usarlos en la vista.
        model.addAttribute("estudiantes", estudiantes);

        return "index"; // Este es el nombre de la plantilla HTML que muestra los resultados (resultadosBusqueda.html).
    }
    
    @GetMapping("/filtrar")
    public String filtrarEstudiantesPorCurso(@RequestParam String curso, Model model) {
        List<Estudiante> estudiantes = servicioEstudiante.filtrarPorCurso(curso);
        model.addAttribute("estudiantes", estudiantes); // Se pasa la lista filtrada a la vista
        return "index"; // nombre de la plantilla HTML que muestra los estudiantes
    }
    
    
    /**
     * Prepara y proporciona los atributos necesarios para la vista, usualmente representada por una página HTML en aplicaciones web.
     * Este método puede, por ejemplo, agregar atributos al modelo que serán utilizados para renderizar la vista y pasar datos entre el controlador y la vista.
     * 
     * @param model Representa la estructura de datos que el controlador devuelve a la vista. Puede contener datos que la vista utilizará para renderizar contenido,
     *              como objetos, configuraciones, mensajes, y más. Se espera que este parámetro sea un tipo de objeto compatible con el sistema de vistas, 
     *              como un Model, un Map, etc., dependiendo de la tecnología específica que se esté utilizando.
     * @return Una cadena que representa el nombre de la vista que se renderizará, permitiendo al framework de aplicaciones web saber qué vista usar para 
     *         la respuesta. Esto podría ser el nombre de un archivo HTML, un identificador de plantilla, entre otros, según cómo esté configurado el framework.
     */
    @GetMapping("/agregar")
    public String mostrarFormulario(Model model) {
        Estudiante estudiante = new Estudiante();
        model.addAttribute("estudiante", estudiante);

        // También aquí puedes calcular y agregar el promedio de edad
        double promedioEdad = servicioEstudiante.promedioEdad();
        model.addAttribute("promedioEdad", promedioEdad);

        return "formulario_estudiante"; // este es el nombre de tu archivo HTML
    }

    /**
     * Maneja la recepción de datos de un formulario para agregar un nuevo Estudiante.
     * Si los datos validados no tienen errores, el estudiante se agrega mediante el servicio correspondiente.
     * 
     * @param estudiante Un objeto Estudiante que contiene los datos ingresados en el formulario. 
     *                   Este objeto se espera que sea mapeado por los campos del formulario, permitiendo su fácil acceso y manipulación.
     * @param result Un objeto BindingResult que contiene los resultados de la validación de los datos ingresados.
     *                      Esto incluye información sobre si hubo errores en los datos proporcionados, permitiendo que el método actúe en consecuencia.
     * @return Una cadena que representa la vista que se renderizará como respuesta.
     *         Si hay errores en los datos, generalmente regresará al formulario. Si los datos son correctos, redirige a la página deseada, 
     *         que en este contexto es una lista de todos los estudiantes.
     */
    @PostMapping("/agregar")
    public String agregarEstudiante(@ModelAttribute("estudiante") Estudiante estudiante, BindingResult result) {
    	
    	// Validación manual de los campos
        if (estudiante.getNombre().trim().isEmpty()) {
            result.rejectValue("nombre", "error.nombre", "El campo nombre no puede estar vacío");
        }
        if (estudiante.getEdad() == null || estudiante.getEdad() <= 0) {
            result.rejectValue("edad", "error.edad", "La edad debe ser un número positivo");
        }
        if (estudiante.getCurso().trim().isEmpty()) {
            result.rejectValue("curso", "error.curso", "El campo curso no puede estar vacío");
        }

        // Si hay errores, volver al formulario
        if (result.hasErrors()) {
            return "formulario_estudiante";
        }
   
        servicioEstudiante.agregarEstudiante(estudiante);
        return "redirect:/"; // redirigir a la página principal
    }
    

}


