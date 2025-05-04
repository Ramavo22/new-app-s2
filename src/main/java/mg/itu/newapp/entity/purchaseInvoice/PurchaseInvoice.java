package mg.itu.newapp.entity.purchaseInvoice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseInvoice {
    String name;
    String supplier;
    String due_date;
    double total_qty;
    double total;
    double net_total;
    String status;
}
