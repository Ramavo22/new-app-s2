package mg.itu.newapp.entity.purchaseOrder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrder {
    String name;
    String supplier;
    String transaction_date;
    String schedule_date;
    double total_qty;
    double net_total;
    String status;
}
