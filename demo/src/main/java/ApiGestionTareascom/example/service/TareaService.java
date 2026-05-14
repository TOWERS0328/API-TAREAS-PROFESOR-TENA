package ApiGestionTareascom.example.service;

import ApiGestionTareascom.example.dto.TareaDTO;
import ApiGestionTareascom.example.entity.Tarea;
import ApiGestionTareascom.example.entity.Tarea.EstadoTarea;
import ApiGestionTareascom.example.repository.TareaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TareaService {

    @Autowired
    private TareaRepository tareaRepository;

    private TareaDTO convertirADTO(Tarea tarea) {
        TareaDTO dto = new TareaDTO();
        dto.setId(tarea.getId());
        dto.setTitulo(tarea.getTitulo());
        dto.setDescripcion(tarea.getDescripcion());
        dto.setEstado(tarea.getEstado());
        return dto;
    }

    private Tarea convertirAEntidad(TareaDTO dto) {
        Tarea tarea = new Tarea();
        tarea.setTitulo(dto.getTitulo());
        tarea.setDescripcion(dto.getDescripcion());
        tarea.setEstado(dto.getEstado());
        return tarea;
    }

    public List<TareaDTO> listarTodas() {
        return tareaRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public TareaDTO obtenerPorId(Long id) {
        Tarea tarea = tareaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada con id: " + id));
        return convertirADTO(tarea);
    }

    public TareaDTO crear(TareaDTO dto) {
        // Validación de negocio: título obligatorio (ya cubierto por @Valid, pero doble seguridad)
        if (dto.getTitulo() == null || dto.getTitulo().trim().isEmpty()) {
            throw new RuntimeException("El título de la tarea es obligatorio");
        }
        
        // Si no se envía estado, por defecto PENDIENTE
        if (dto.getEstado() == null) {
            dto.setEstado(EstadoTarea.PENDIENTE);
        }
        
        Tarea tarea = convertirAEntidad(dto);
        Tarea guardada = tareaRepository.save(tarea);
        return convertirADTO(guardada);
    }

    public TareaDTO actualizar(Long id, TareaDTO dto) {
        Tarea existente = tareaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada con id: " + id));

        // 🔴 REGLA DE NEGOCIO: No permitir cambiar a COMPLETADA si está en PENDIENTE
        EstadoTarea estadoActual = existente.getEstado();
        EstadoTarea nuevoEstado = dto.getEstado();
        
        if (estadoActual == EstadoTarea.PENDIENTE && nuevoEstado == EstadoTarea.COMPLETADA) {
            throw new RuntimeException("No se puede cambiar el estado de PENDIENTE a COMPLETADA directamente. Debe pasar por EN_PROCESO");
        }

        existente.setTitulo(dto.getTitulo());
        existente.setDescripcion(dto.getDescripcion());
        existente.setEstado(dto.getEstado());

        return convertirADTO(tareaRepository.save(existente));
    }

    public void eliminar(Long id) {
        if (!tareaRepository.existsById(id)) {
            throw new RuntimeException("Tarea no encontrada con id: " + id);
        }
        tareaRepository.deleteById(id);
    }

    public List<TareaDTO> listarPorEstado(EstadoTarea estado) {
        return tareaRepository.findByEstado(estado)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }
}