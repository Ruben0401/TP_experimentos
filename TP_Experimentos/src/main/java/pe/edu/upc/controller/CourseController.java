package pe.edu.upc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import pe.edu.upc.entity.Course;
import pe.edu.upc.serviceinterface.ICourseService;

@Controller
@RequestMapping("/courses")
public class CourseController {
	@Autowired
	private ICourseService cS;

	@GetMapping("/new")
	public String newCourse(Model model) {
		model.addAttribute("course", new Course());
		return "course/course";
	}

	@PostMapping("/save")
	public String saveCourse(@Validated Course course, BindingResult result, Model model) throws Exception {
		if (result.hasErrors()) {
			return "course/course";
		} else {
			int rpta = cS.insert(course);
			if (rpta > 0) {
				model.addAttribute("mensaje", "Ya existe un curso con ese nombre");
				return "course/course";
			} else {
				cS.insert(course);
				model.addAttribute("listCourses", cS.list());
				return "course/listCourses";
			}
		}

	}

	@GetMapping("/list")
	public String listCourses(Model model) {
		try {
			model.addAttribute("listCourses", cS.list());
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "course/listCourses";
	}

	@RequestMapping("/delete/{id}")
	public String deleteCourse(Model model, @PathVariable(value = "id") int id) {
		try {
			if (id > 0) {
				cS.delete(id);
			}
			model.addAttribute("mensaje", "Se eliminó correctamente");
		} catch (Exception e) {
			model.addAttribute("mensaje", "Ocurrió un error, no se pudo eliminar");
		}
		model.addAttribute("listCourses", cS.list());
		return "course/listCourses";

	}
}
