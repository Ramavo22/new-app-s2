package mg.itu.newapp.entity.supplier;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SupplierQuotationItem {
    String name;
    String item_code;
    String item_name;
    double qty;
    double rate;
    double amount;
}
