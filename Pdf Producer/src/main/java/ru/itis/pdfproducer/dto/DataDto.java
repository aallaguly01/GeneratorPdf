package ru.itis.pdfproducer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DataDto {
    private String firstName;
    private String lastName;

    public String toString(){
        JSONObject jsonInfo = new JSONObject();

        jsonInfo.put("firstName", this.firstName);
        jsonInfo.put("lastName", this.lastName);

        return jsonInfo.toString();
    }
}
