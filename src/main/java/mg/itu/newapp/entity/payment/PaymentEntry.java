package mg.itu.newapp.entity.payment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mg.itu.newapp.entity.purchaseInvoice.PurchaseInvoice;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data

public class PaymentEntry {
    String name;
    String payment_type;
    String party_type;
    String party;
    String posting_date;
    String paid_from;
    String paid_to;
    double paid_amount;
    double received_amount;
    String reference_no;
    String reference_date;
    String company;
    List<References> references;

    public PaymentEntry(PurchaseInvoice purchaseInvoice) {
        setPayment_type("Pay");
        setParty_type("Supplier");
        setParty(purchaseInvoice.getSupplier());
        setPosting_date(LocalDate.now().toString());
        setPaid_from("Bank Account - FE");
        setPaid_to("Creditors - FE");
        setPaid_amount(purchaseInvoice.getNet_total());
        setReceived_amount(purchaseInvoice.getNet_total());
        setReference_no("Fanah's ERP");
        setReference_date(getPosting_date());
        setCompany("Fanah's ERP");
        setReferences(new ArrayList<>());

        References ref = new References();
        ref.setReference_doctype("Purchase Invoice");
        ref.setReference_name(purchaseInvoice.getName());
        ref.setTotal_amount(purchaseInvoice.getNet_total());
        ref.setOutstanding_amount(purchaseInvoice.getNet_total());
        ref.setAllocated_amount(purchaseInvoice.getNet_total());

        getReferences().add(ref);
    }



    @Data
    public static class References{
        String reference_doctype;
        String reference_name;
        double total_amount;
        double outstanding_amount;
        double allocated_amount;
    }
}
