package  com.flynas.worker.request;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
@Schema(requiredProperties = { "name", "age" })
@JsonPropertyOrder({ "name", "age"})
public class SampleCreateRequest {

	@Schema(description = "User Name")
	@NotBlank(message = "410")
	@Length(message = "414", max = 100)
	private String name;

	@Schema(description = "User Age")
	@NotBlank(message = "410")
	@Length(message = "414", max = 36)
	@JsonProperty("age")
	private String age;

	 
}
