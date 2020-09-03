package pe.edu.upc.serviceinterface;

import java.util.List;
import java.util.Optional;

import pe.edu.upc.entity.Enrollment;

public interface IEnrollmentService {

	public void insert(Enrollment enrollment);

	List<Enrollment> list();

	public void delete(int idEnrollment);

	Optional<Enrollment> searchId(int idEnrollment);
}