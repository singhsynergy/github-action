package  com.flynas.worker.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder({ "createdByUuid", "name", "href", "date" })
public class CreatedDetailResponse {
	
	@JsonProperty("id")
	public String createdByUuid;

	public String name;

	public String href;
 
	public String date;
}
