package edu.dosw.controller;

import edu.dosw.dto.TaskDTO;
import edu.dosw.dto.UpdateTaskDTO;
import edu.dosw.exception.ResponseHandler;
import edu.dosw.services.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST para la gestión de tareas.
 * Proporciona endpoints para operaciones CRUD en tareas, con diferentes niveles de acceso
 * según el rol del usuario.
 */
@Tag(name = "Tasks", description = "API para la gestión de tareas")

@RestController
@RequestMapping(
    value = "/api/task",
    produces = MediaType.APPLICATION_JSON_VALUE
)
public class TaskController {
    private final TaskService taskService;

    /**
     * Constructor para la inyección de dependencias.
     *
     * @param taskService Servicio para la lógica de negocio de tareas
     */
    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * Crea una nueva tarea en el sistema.
     *
     * @param userId ID del usuario que realiza la operación
     * @param task Datos de la tarea a crear
     * @return ResponseEntity con la tarea creada o mensaje de error
     */
    @PostMapping(
        value = "/{userId}/tasks",
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
        summary = "Crea una nueva tarea",
        description = "Permite a usuarios ADMIN y MEMBER crear nuevas tareas"
    )
    @ApiResponse(responseCode = "200", description = "Tarea creada exitosamente")
    @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    @ApiResponse(responseCode = "403", description = "No autorizado")
    public ResponseEntity<?> createTask(
            @PathVariable String userId,
            @RequestBody TaskDTO task) {
        if (task == null){
            return ResponseHandler.generateErrorResponse("Task is null", HttpStatus.BAD_REQUEST);
        }
        return ResponseHandler.generateResponse("Task created", HttpStatus.OK, taskService.createTask(task, userId));
    }

    /**
     * Obtiene todas las tareas del sistema.
     * Accesible para todos los tipos de usuarios.
     *
     * @param userId ID del usuario que realiza la consulta
     * @return ResponseEntity con la lista de tareas o mensaje de error
     */
    @GetMapping("/{userId}/tasks")
    @Operation(
        summary = "Obtiene todas las tareas",
        description = "Permite a cualquier usuario autenticado ver todas las tareas"
    )
    @ApiResponse(responseCode = "200", description = "Lista de tareas obtenida exitosamente")
    @ApiResponse(responseCode = "400", description = "ID de usuario inválido")
    public ResponseEntity<?> getTasks(@PathVariable String userId) {
        if (userId == null) {
            return ResponseHandler.generateErrorResponse("User id is null", HttpStatus.BAD_REQUEST);
        }
        return ResponseHandler.generateResponse("Tasks", HttpStatus.OK, taskService.getTasks());
    }

    /**
     * Filtra tareas según los criterios especificados.
     * Solo accesible para usuarios ADMIN.
     *
     * @param userId ID del usuario que realiza la consulta
     * @param filter Tipo de filtro a aplicar (ej. 'status', 'date', 'keyword')
     * @param extra Valor del filtro a aplicar
     * @return ResponseEntity con la lista de tareas filtradas o mensaje de error
     */
    @GetMapping("/{userId}/tasks/{filter}/{extra}")
    @Operation(
        summary = "Filtra tareas",
        description = "Permite a usuarios ADMIN filtrar tareas por diferentes criterios"
    )
    @ApiResponse(responseCode = "200", description = "Tareas filtradas exitosamente")
    @ApiResponse(responseCode = "400", description = "Parámetros de filtrado inválidos")
    @ApiResponse(responseCode = "403", description = "No autorizado")
    public ResponseEntity<?> getTasksByFilter(
            @PathVariable String userId,
            @PathVariable String filter,
            @PathVariable String extra) {
        if (filter == null || extra == null) {
            return ResponseHandler.generateErrorResponse("Filter or extra is null", HttpStatus.BAD_REQUEST);
        }
        return ResponseHandler.generateResponse("Tasks", HttpStatus.OK, taskService.getTasksByFilter(filter, extra));
    }

    /**
     * Actualiza una tarea existente.
     * Solo accesible para usuarios ADMIN y MEMBER.
     *
     * @param userId ID del usuario que realiza la operación
     * @param task Nuevos datos de la tarea
     * @return ResponseEntity con la tarea actualizada o mensaje de error
     */
    @PutMapping(
            value = "/{userId}/tasks/{taskId}",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Actualiza una tarea existente",
            description = "Permite a usuarios ADMIN y MEMBER actualizar tareas"
    )
    @ApiResponse(responseCode = "200", description = "Tarea actualizada exitosamente")
    @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    @ApiResponse(responseCode = "403", description = "No autorizado")
    @ApiResponse(responseCode = "404", description = "Tarea no encontrada")
    public ResponseEntity updateTask(@PathVariable String userId, @PathVariable String taskId, @RequestBody UpdateTaskDTO updatedTask) {
        if (taskId == null) {
            return ResponseHandler.generateErrorResponse("Task id is null", HttpStatus.BAD_REQUEST);
        }
        return ResponseHandler.generateResponse("Task updated", HttpStatus.OK, taskService.updateTask(updatedTask , taskId, userId));
    }

    /**
     * Elimina una tarea del sistema.
     * Solo accesible para usuarios ADMIN.
     *
     * @param userId ID del usuario que realiza la operación
     * @param id ID de la tarea a eliminar
     * @return ResponseEntity con mensaje de éxito o error
     */
    @DeleteMapping("/{userId}/tasks/{id}")
    @Operation(
            summary = "Elimina una tarea",
            description = "Permite a usuarios ADMIN eliminar tareas"
    )
    @ApiResponse(responseCode = "200", description = "Tarea eliminada exitosamente")
    @ApiResponse(responseCode = "400", description = "ID de tarea inválido")
    @ApiResponse(responseCode = "403", description = "No autorizado")
    @ApiResponse(responseCode = "404", description = "Tarea no encontrada")
    public ResponseEntity<?> deleteTask(
            @PathVariable String userId,
            @PathVariable String taskId) {
        if (taskId == null) {
            return ResponseHandler.generateErrorResponse("Task id is null", HttpStatus.BAD_REQUEST);
        }
        return ResponseHandler.generateResponse("Task deleted", HttpStatus.OK, taskService.deleteTask(userId, taskId));
    }
}
