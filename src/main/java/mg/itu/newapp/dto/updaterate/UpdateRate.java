package mg.itu.newapp.dto.updaterate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRate {
    String item_code;
    double rate;
}
