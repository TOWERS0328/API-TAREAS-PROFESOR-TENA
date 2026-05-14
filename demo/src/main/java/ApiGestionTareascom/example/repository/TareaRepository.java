package ApiGestionTareascom.example.repository;

import ApiGestionTareascom.example.entity.Tarea;
import ApiGestionTareascom.example.entity.Tarea.EstadoTarea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TareaRepository extends JpaRepository<Tarea, Long> {
    List<Tarea> findByEstado(EstadoTarea estado);
}