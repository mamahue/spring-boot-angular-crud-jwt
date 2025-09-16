package com.example.BasicCrud.controller;

import com.example.BasicCrud.dto.autResponse;
import com.example.BasicCrud.dto.logindto;
import com.example.BasicCrud.dto.registerDto;
import com.example.BasicCrud.model.User;
import com.example.BasicCrud.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;

@RestController
@RequestMapping("/auth/")
@Tag(name = "Authentification" , description = "Controller for Authentification")
public class authController {

    @Autowired
    private AuthService authService;

    @PostMapping("register/")
    @Operation(
            summary = "Registrar Usuario",
            description = "Registra un usuario en el sistema y devuelve el token JWT junto con los detalles del usuario",
            tags = {"Auth"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del usuario a registrar (name, email, username, password, role)",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = registerDto.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Usuario registrado correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = autResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Datos inválidos en la petición"
                    )
            }
    )
    private ResponseEntity<autResponse> registerUser (@RequestBody registerDto dto) throws URISyntaxException {
        try {
            return  ResponseEntity.ok(authService.register(dto));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    @Operation(
            summary = "Logeo de usuario",
            description = "ingresa un usuario  al  sistema y devuelve el token JWT junto con los detalles del usuario",
            tags = {"Login"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del usuario a registrar ( username, password)",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation =logindto.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Usuario logeado correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = autResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Datos inválidos en la petición"
                    )
            }
    )

    @PostMapping("login/")
    private  ResponseEntity<autResponse> loginUser(@RequestBody logindto dto) throws URISyntaxException {

        try {

            autResponse loginUser = authService.login(dto);
            return ResponseEntity.ok(loginUser);
        }catch ( Exception e){
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }


}
