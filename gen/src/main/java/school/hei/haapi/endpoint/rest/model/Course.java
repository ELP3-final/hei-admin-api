/*
 * HEI Admin API
 * _Programmatically connect to a computer programming [school](https://hei.school)._ After [joining us](mailto:contact@hei.school), you can get an identification token from our [dev](https://dev-hei-admin.auth.eu-west-3.amazoncognito.com/oauth2/authorize?client_id=5s8cg50doahmu855rlc8fr6qmp&response_type=token&scope=email+openid&redirect_uri=https%3A%2F%2Fapi-dev.hei.school%2Fwhoami) or [prod](https://prod-hei-admin.auth.eu-west-3.amazoncognito.com/oauth2/authorize?client_id=i8bg538jpfu6mqmqb61m26trd&response_type=token&scope=email+openid&redirect_uri=https%3A%2F%2Fapi-prod.hei.school%2Fwhoami) authentication service. Then, start playing with our system!  The implementation of our API is [publicly disclosed](https://github.com/hei-school/hei-admin-api). You are welcome to try and compromise it. Happy hacking! 
 *
 * The version of the OpenAPI document: 1.0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package school.hei.haapi.endpoint.rest.model;

import java.util.Objects;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * Course
 */
@JsonPropertyOrder({
  Course.JSON_PROPERTY_ID,
  Course.JSON_PROPERTY_REF,
  Course.JSON_PROPERTY_NAME,
  Course.JSON_PROPERTY_CREDITS,
  Course.JSON_PROPERTY_TOTAL_HOURS
})
@javax.annotation.processing.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2022-09-10T14:13:25.782434+03:00[Africa/Nairobi]")
public class Course {
  public static final String JSON_PROPERTY_ID = "id";
  private String id;

  public static final String JSON_PROPERTY_REF = "ref";
  private String ref;

  public static final String JSON_PROPERTY_NAME = "name";
  private String name;

  public static final String JSON_PROPERTY_CREDITS = "credits";
  private Integer credits;

  public static final String JSON_PROPERTY_TOTAL_HOURS = "total_hours";
  private Integer totalHours;


  public Course id(String id) {
    this.id = id;
    return this;
  }

   /**
   * Get id
   * @return id
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")
  @JsonProperty(JSON_PROPERTY_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getId() {
    return id;
  }


  @JsonProperty(JSON_PROPERTY_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setId(String id) {
    this.id = id;
  }


  public Course ref(String ref) {
    this.ref = ref;
    return this;
  }

   /**
   * Get ref
   * @return ref
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(example = "PROG1", value = "")
  @JsonProperty(JSON_PROPERTY_REF)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getRef() {
    return ref;
  }


  @JsonProperty(JSON_PROPERTY_REF)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setRef(String ref) {
    this.ref = ref;
  }


  public Course name(String name) {
    this.name = name;
    return this;
  }

   /**
   * Get name
   * @return name
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(example = "Algorithmics", value = "")
  @JsonProperty(JSON_PROPERTY_NAME)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getName() {
    return name;
  }


  @JsonProperty(JSON_PROPERTY_NAME)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setName(String name) {
    this.name = name;
  }


  public Course credits(Integer credits) {
    this.credits = credits;
    return this;
  }

   /**
   * Get credits
   * @return credits
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")
  @JsonProperty(JSON_PROPERTY_CREDITS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Integer getCredits() {
    return credits;
  }


  @JsonProperty(JSON_PROPERTY_CREDITS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setCredits(Integer credits) {
    this.credits = credits;
  }


  public Course totalHours(Integer totalHours) {
    this.totalHours = totalHours;
    return this;
  }

   /**
   * Get totalHours
   * @return totalHours
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")
  @JsonProperty(JSON_PROPERTY_TOTAL_HOURS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Integer getTotalHours() {
    return totalHours;
  }


  @JsonProperty(JSON_PROPERTY_TOTAL_HOURS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setTotalHours(Integer totalHours) {
    this.totalHours = totalHours;
  }


  /**
   * Return true if this Course object is equal to o.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Course course = (Course) o;
    return Objects.equals(this.id, course.id) &&
        Objects.equals(this.ref, course.ref) &&
        Objects.equals(this.name, course.name) &&
        Objects.equals(this.credits, course.credits) &&
        Objects.equals(this.totalHours, course.totalHours);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, ref, name, credits, totalHours);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Course {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    ref: ").append(toIndentedString(ref)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    credits: ").append(toIndentedString(credits)).append("\n");
    sb.append("    totalHours: ").append(toIndentedString(totalHours)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}

