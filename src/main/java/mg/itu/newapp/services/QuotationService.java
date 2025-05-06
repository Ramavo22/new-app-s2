package mg.itu.newapp.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import mg.itu.newapp.dto.updaterate.UpdateRate;
import mg.itu.newapp.dto.ressources.FrappeRessource;
import mg.itu.newapp.dto.updaterate.UpdateRateWrapper;
import mg.itu.newapp.entity.supplier.SupplierQuotationItem;
import mg.itu.newapp.utils.ApiUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QuotationService {

    private ApiUtils apiUtils;
    private ObjectMapper objectMapper;

    public QuotationService(ApiUtils apiUtils, ObjectMapper objectMapper) {
        this.apiUtils = apiUtils;
        this.objectMapper = objectMapper;
    }


    public SupplierQuotationItem getItem(String ref,String sid)throws Exception {
        SupplierQuotationItem item = null;

        String endPoint = "/api/resource/Supplier Quotation Item/"+ref;
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        headers.put("Cookie",sid);

        ResponseEntity<String> response = apiUtils.call(
                endPoint,
                HttpMethod.GET,
                null,
                String.class,
                headers
        );
        TypeReference<FrappeRessource<SupplierQuotationItem>> typeRef = new TypeReference<>() {};
        item = objectMapper.readValue(response.getBody(), typeRef).getData();
        System.out.println(item);
        return item;
    }

    public void SubmitSupplierQuotation(UpdateRateWrapper updateRateWrapper, String quotationName, String sid) throws Exception {
        List<UpdateRate> updateRateList = updateRateWrapper.getItems();
        for(UpdateRate updateRate : updateRateList){
            updateSupplierQuotationItem(updateRate.getItem_code(),updateRate.getRate(),sid);
        }
        String endPoint = "/api/resource/Supplier Quotation/"+quotationName+"?run_method=submit";
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        headers.put("Cookie", sid);
        ResponseEntity<String> response = apiUtils.call(
                    endPoint,
                    HttpMethod.POST,
                   null,
                    String.class,
                    headers
                );
        System.out.println(response.getBody());
    }

    public void updateSupplierQuotationItem(String ref,double newrate, String sid) throws Exception {
        String endPoint = "/api/resource/Supplier Quotation Item/"+ref;
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        headers.put("Cookie", sid);
        Map<String,Object> body = new HashMap<>();
        body.put("rate",newrate);
        String json = objectMapper.writeValueAsString(body);
        System.out.println(json);
        ResponseEntity<String> response = apiUtils.call(
                endPoint,
                HttpMethod.PUT,
                json,
                String.class,
                headers
        );
    }

//    public void SubmitSupplierQuotation(UpdateRateWrapper updateRateWrapper, String quotationName, String sid) throws Exception {
//        List<UpdateRate> updateRateList = updateRateWrapper.getItems();
//        for(UpdateRate updateRate : updateRateList){
//            updateSupplierQuotationItem(updateRate.getItem_code(),updateRate.getRate(),sid);
//        }
//        String endPoint = "/api/method/frappe.client.submit";
//        Map<String, String> headers = new HashMap<>();
//        headers.put("Accept", "application/json");
//        headers.put("Cookie", sid);
//        Map<String, String> doc = new HashMap<>();
//        doc.put("doctype","Supplier Quotation");
//        doc.put("name",quotationName);
//        String body = objectMapper.writeValueAsString(doc);
//        System.out.println(body);
//        ResponseEntity<String> response = apiUtils.call(
//                endPoint,
//                HttpMethod.PUT,
//                body,
//                String.class,
//                headers
//        );
//        System.out.println(response.getBody());
//    }

}
