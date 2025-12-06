package com.RusGruz.JobAggregator.Controller;

import com.github.dockerjava.api.command.RestartContainerCmd;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Generating_CSRF {
@GetMapping("/csrf-token")
    public CsrfToken generateCsrfToken(HttpServletRequest request) {
return (CsrfToken)request.getAttribute("_csrf");
    }
}
