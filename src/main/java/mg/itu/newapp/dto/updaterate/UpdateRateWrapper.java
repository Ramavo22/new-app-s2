package mg.itu.newapp.dto.updaterate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRateWrapper {
    List<UpdateRate> items;
}
