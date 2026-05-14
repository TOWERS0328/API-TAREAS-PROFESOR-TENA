package ApiGestionTareascom.example.dto;

import ApiGestionTareascom.example.entity.Tarea.EstadoTarea;
import jakarta.validation.constraints.*;

public class TareaDTO {

    private Long id;

    @NotBlank(message = "El título es obligatorio")
    @Size(min = 1, max = 100, message = "El título debe tener entre 1 y 100 caracteres")
    private String titulo;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(min = 5, max = 500, message = "La descripción debe tener mínimo 5 caracteres")
    private String descripcion;

    @NotNull(message = "El estado es obligatorio")
    private EstadoTarea estado;

    public TareaDTO() {}

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public EstadoTarea getEstado() { return estado; }
    public void setEstado(EstadoTarea estado) { this.estado = estado; }
}