package pe.edu.idat.demoticketrecaptcha.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class TicketPurchaseForm {
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 50, message = "El nombre debe tener entre 3 y 50 caracteres")
    private String fullName;

    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "Debe ser un correo electrónico válido")
    private String email;

    @NotNull(message = "Debes indicar la cantidad de tickets")
    @Min(value = 1, message = "Debes comprar al menos 1 ticket")
    @Max(value = 4, message = "El límite es de 4 tickets por persona")
    private Integer quantity;
}
