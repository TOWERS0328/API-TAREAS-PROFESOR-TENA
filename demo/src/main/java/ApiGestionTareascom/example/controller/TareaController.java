package ApiGestionTareascom.example.controller;

import ApiGestionTareascom.example.dto.TareaDTO;
import ApiGestionTareascom.example.entity.Tarea.EstadoTarea;
import ApiGestionTareascom.example.service.TareaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tareas")
@CrossOrigin(origins = "*")
public class TareaController {

    @Autowired
    private TareaService tareaService;

    // GET /tareas → listar todas
    @GetMapping
    public ResponseEntity<List<TareaDTO>> listarTodas() {
        return ResponseEntity.ok(tareaService.listarTodas());
    }

    // GET /tareas/{id} → obtener por id
    @GetMapping("/{id}")
    public ResponseEntity<TareaDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(tareaService.obtenerPorId(id));
    }

    // POST /tareas → crear
    @PostMapping
    public ResponseEntity<TareaDTO> crear(@Valid @RequestBody TareaDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tareaService.crear(dto));
    }

    // PUT /tareas/{id} → actualizar
    @PutMapping("/{id}")
    public ResponseEntity<TareaDTO> actualizar(@PathVariable Long id, @Valid @RequestBody TareaDTO dto) {
        return ResponseEntity.ok(tareaService.actualizar(id, dto));
    }

    // DELETE /tareas/{id} → eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        tareaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // EXTRA: GET /tareas/estado/{estado} → filtrar por estado
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<TareaDTO>> listarPorEstado(@PathVariable EstadoTarea estado) {
        return ResponseEntity.ok(tareaService.listarPorEstado(estado));
    }
}