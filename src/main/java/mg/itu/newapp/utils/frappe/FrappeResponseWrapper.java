package mg.itu.newapp.utils.frappe;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FrappeResponseWrapper<T> {
    private FrappeResponse<T> message;
}
