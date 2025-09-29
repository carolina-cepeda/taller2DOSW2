package edu.dosw.controller;

import edu.dosw.dto.UserDTO;
import edu.dosw.exception.ResponseHandler;
import edu.dosw.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Controlador REST para la gestión de usuarios.
 * Proporciona endpoints para operaciones de creación y gestión de usuarios.
 */
@Tag(name = "Users", description = "API para la gestión de usuarios")

@RestController
@RequestMapping(
    value = "/api/user",
    produces = MediaType.APPLICATION_JSON_VALUE
)
public class UserController {

    private final UserService userService;

    /**
     * Constructor para la inyección de dependencias.
     *
     * @param userService Servicio para la lógica de negocio de usuarios
     */
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Crea un nuevo usuario en el sistema.
     *
     * @param user Datos del usuario a crear
     * @return ResponseEntity con el usuario creado o mensaje de error
     */
    @PostMapping(
        value = "/create",
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
        summary = "Crea un nuevo usuario",
        description = "Permite registrar un nuevo usuario en el sistema"
    )
    @ApiResponse(responseCode = "200", description = "Usuario creado exitosamente")
    @ApiResponse(responseCode = "400", description = "Datos de usuario inválidos")
    @ApiResponse(responseCode = "409", description = "El nombre de usuario ya existe")
    public ResponseEntity<?> createUser(@RequestBody(required = false) UserDTO user) {
        if(user == null){
            return ResponseHandler.generateErrorResponse("User is null", HttpStatus.BAD_REQUEST);
        }
        return ResponseHandler.generateResponse("User created", HttpStatus.OK, userService.createUser(user));
    }

    /**
     * Obtiene un usuario por su ID.
     *
     * @param userId ID del usuario a buscar
     * @return ResponseEntity con los datos del usuario o mensaje de error
     */
    @GetMapping("/{userId}")
    @Operation(
        summary = "Obtiene un usuario por ID",
        description = "Permite obtener los detalles de un usuario específico"
    )
    @ApiResponse(responseCode = "200", description = "Usuario encontrado")
    @ApiResponse(responseCode = "400", description = "ID de usuario inválido")
    @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    public ResponseEntity<?> getUserById(@PathVariable String userId) {
        if (userId == null || userId.trim().isEmpty()) {
            return ResponseHandler.generateErrorResponse("User id cannot be null or empty", HttpStatus.BAD_REQUEST);
        }
        return ResponseHandler.generateResponse("User found", HttpStatus.OK, userService.getUserById(userId));
    }
}
