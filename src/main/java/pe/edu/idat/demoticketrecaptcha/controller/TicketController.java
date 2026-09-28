package pe.edu.idat.demoticketrecaptcha.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pe.edu.idat.demoticketrecaptcha.dto.TicketPurchaseForm;
import pe.edu.idat.demoticketrecaptcha.service.CaptchaService;

@Controller
@RequestMapping("/tickets")
@RequiredArgsConstructor // Lombok inyecta las dependencias automáticamente
public class TicketController {
    private final CaptchaService captchaService;

    @Value("${recaptcha.site.key}")
    private String siteKey;

    @GetMapping("/comprar")
    public String mostrarFormulario(Model model) {
        model.addAttribute("ticketForm", new TicketPurchaseForm());
        model.addAttribute("siteKey", siteKey);
        return "checkout";
    }

    @PostMapping("/comprar")
    public String procesarCompra(
            @Valid @ModelAttribute("ticketForm") TicketPurchaseForm ticketForm,
            BindingResult bindingResult,
            @RequestParam(name = "g-recaptcha-response", required = false) String captchaResponse,
            Model model) {

        boolean isCaptchaValid = captchaService.isCaptchaValid(captchaResponse);
        if (!isCaptchaValid) {
            model.addAttribute("captchaError", "Por favor, verifica que no eres un robot.");
        }

        // Si hay errores, regresamos al formulario
        if (bindingResult.hasErrors() || !isCaptchaValid) {
            model.addAttribute("siteKey", siteKey);
            return "checkout";
        }

        // Si todo está correcto, pasamos los datos del formulario a la vista de éxito
        model.addAttribute("ticketConfirmado", ticketForm);

        return "exito";
    }
}