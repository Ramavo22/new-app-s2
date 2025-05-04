package mg.itu.newapp.entity.supplier;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SupplierQuotation {
    String name;
    String supplier;
    String transaction_date;
    double total_qty;
    double total;
    double net_total;
    String status;
    String valid_till;
    List<SupplierQuotationItem> items;
}



