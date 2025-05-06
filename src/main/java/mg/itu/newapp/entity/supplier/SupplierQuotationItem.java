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
    String uom;
    double conversion_factor;
    double base_rate;
    double base_amount;
    String parent;



    public void AjustRateUpdate(double newrate) {
        setRate(newrate);
        setBase_rate(newrate);
        setBase_amount(newrate * getQty());
        setAmount(newrate * getQty());
    }
}
