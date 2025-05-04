package mg.itu.newapp.entity.supplier;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplierQuotationItem {
    String name;
    String item_code;
    String item_name;
    double qty;
    double rate;
    double amount;
}
