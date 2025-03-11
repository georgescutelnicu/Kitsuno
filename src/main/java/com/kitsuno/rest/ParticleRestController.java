package com.kitsuno.rest;

import com.kitsuno.entity.Particle;
import com.kitsuno.service.ParticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "Particles", description = "APIs for retrieving and working with Japanese particles.")
public class ParticleRestController {

    private final ParticleService particleService;

    @Autowired
    public ParticleRestController(ParticleService particleService) {
        this.particleService = particleService;
    }

    @Operation(summary = "Get all Japanese particles",
            description = "Retrieve a list of all available Japanese particles.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved particle list",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Particle.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - API key is missing or invalid",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    @GetMapping("/particles")
    public List<Particle> getAllParticles(@RequestHeader("API-KEY") String apiKey) {
        return particleService.findAll();
    }
}
