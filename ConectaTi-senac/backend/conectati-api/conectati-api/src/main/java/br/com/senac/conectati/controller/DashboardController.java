package br.com.senac.conectati.controller;

import br.com.senac.conectati.dto.response.DashboardResponse;
import br.com.senac.conectati.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService service;

    @GetMapping
    public ResponseEntity<DashboardResponse> obterDashboard() {
        return ResponseEntity.ok(service.obterDashboard());
    }
}
