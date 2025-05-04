package mg.itu.newapp.entity.supplier;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Supplier {
    String name;
    String supplier_name;
    String supplier_type;
    String country;
}
