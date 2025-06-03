package org.example.basketballshop.DTO.Forms;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class GiftCertificateForm {
    @NotNull(message = "Сумма сертификата обязательна")
    @DecimalMin(value = "500.00", message = "Минимальная сумма сертификата 500 рублей")
    @DecimalMax(value = "100000.00", message = "Максимальная сумма сертификата 100000 рублей")
    private BigDecimal amount;
} 