package mg.itu.newapp.utils.frappe;

import lombok.Data;

@Data
public class FrappeResponse<T> {
    String message;
    T data;
    public FrappeResponse() {}
    public FrappeResponse(String message, T data) {
        this.message = message;
        this.data = data;
    }

}
