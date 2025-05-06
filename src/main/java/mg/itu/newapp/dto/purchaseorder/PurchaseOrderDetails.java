package mg.itu.newapp.dto.purchaseorder;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseOrderDetails {

    private String name;
    private String supplier;

    @JsonProperty("transaction_date")
    private String transactionDate;

    @JsonProperty("grand_total")
    private double grandTotal;

    private String status;

    @JsonProperty("per_billed")
    private double perBilled;

    @JsonProperty("payment_status")
    private String paymentStatus;

    private List<Invoice> invoices;


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Invoice {
        private String invoice;

        @JsonProperty("posting_date")
        private String postingDate;

        @JsonProperty("grand_total")
        private double grandTotal;

        @JsonProperty("outstanding_amount")
        private double outstandingAmount;

        private String status;
    }
}
