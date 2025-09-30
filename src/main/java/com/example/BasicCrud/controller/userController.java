package com.example.BasicCrud.controller;

import com.example.BasicCrud.dto.updateUser;
import com.example.BasicCrud.dto.userDto;
import com.example.BasicCrud.model.User;
import com.example.BasicCrud.repository.UserRepository;
import com.example.BasicCrud.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user/")
@Tag(name = "User Controller  " , description = "Controller for User")
public class userController {
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private  UserService userService;




    @GetMapping("list/")
    @PreAuthorize("hasRole('Admin')")
    @Operation(
            summary = "Listar usuarios",
            description = "Obtiene un listado con todos los usuarios registrados solo para admin",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Listado de usuarios obtenido correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = User.class))
                            )
                    )
            }
    )
    public ResponseEntity<List<User>> getAllUser(){
        return ResponseEntity.ok(userService.findAll());
    }
    @GetMapping("{id}/")
    @PreAuthorize("hasAnyRole('Admin','User')")
    @Operation(
            summary = "Obtener usuario por ID",
            description = "Devuelve los detalles de un usuario específico según su ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Usuario encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = User.class))
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Usuario no encontrado")

            }
    )
    public ResponseEntity<User> getUserById(@PathVariable ("id") Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @DeleteMapping("delete/{id}/")
    @PreAuthorize("hasRole('Admin')")
    @Operation(
            summary = "eliminar un  usuario por ID solo admin",
            description = "Elimina un  usuario  en específico según su ID",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Usuario eliminado"

                    ),
                    @ApiResponse(responseCode = "404", description = "Usuario no encontrado")

            }
    )
    public ResponseEntity<Boolean> deleteUser(@PathVariable ("id") Long id) {
        try {
            userService.deleteById(id);
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }

      @PutMapping("update/{id}")
       @PreAuthorize("hasAnyRole('Admin','User')")
      @Operation(
              summary = "Actualizar usuario",
              description = "Permite a un **Admin** actualizar cualquier usuario, o a un **User** actualizar únicamente su propio perfil",
              requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                      description = "Datos a actualizar del usuario",
                      required = true,
                      content = @Content(mediaType = "application/json",
                              schema = @Schema(implementation = updateUser.class))
              ),
              responses = {
                      @ApiResponse(
                              responseCode = "200",
                              description = "Usuario actualizado correctamente",
                              content = @Content(mediaType = "application/json",
                                      schema = @Schema(implementation = updateUser.class))
                      ),
                      @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
              }
      )
       public   ResponseEntity<userDto> updateUsers(@PathVariable("id") Long id,
                                                    @RequestBody  updateUser update, Authentication authentication){
       User existeUser= userService.findById(id);
          if (existeUser == null) {
              return ResponseEntity.status(HttpStatus.NOT_FOUND)
                      .body(null); }

       if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_User"))) {
          String usernameAuth = authentication.getName();
           if (!existeUser.getUsername().equals(usernameAuth)) {
             return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
          }
       }
       existeUser.setName(update.name());
       existeUser.setEmail(update.email());
       existeUser.setUsername(update.username());

          if (update.newpassword() != null && !update.newpassword().isEmpty()) {
              existeUser.setPassword(passwordEncoder.encode(update.newpassword()));
          }

       User response =userService.save(existeUser);
          userDto dto = new userDto(
                  response.getName(),
                  response.getEmail(),
                  response.getUsername(),
                  response.getTypeUser().getType()
          );
     return  ResponseEntity.ok(dto);
      }

}
