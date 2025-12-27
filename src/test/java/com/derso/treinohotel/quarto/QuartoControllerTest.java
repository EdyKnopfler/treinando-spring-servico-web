package com.derso.treinohotel.quarto;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import jakarta.transaction.Transactional;
import tools.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class QuartoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    private String jsonQuarto;

    @BeforeEach
    void criaJsonQuarto() {
        QuartoDTO quartoExemplo = new QuartoDTO(
            null,
            101,
            new BigDecimal("150.00"),
            "Quarto simples"
        );

        jsonQuarto = mapper.writeValueAsString(quartoExemplo);
    }

    @Test
    void deveRetornar401SemToken() throws Exception {
        mockMvc.perform(post("/quartos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonQuarto))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "user@test.com", roles = {"USER"})
    void deveRetornar403SemRoleAdmin() throws Exception {
        mockMvc.perform(post("/quartos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonQuarto))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "user@test.com", roles = {"ADMIN"})
    void naoDevePermitirNumeroDuplicado() throws Exception {
        mockMvc.perform(post("/quartos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonQuarto))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/quartos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonQuarto))
                .andExpect(status().isConflict());
    }
    
}
