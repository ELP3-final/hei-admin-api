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
import school.hei.haapi.endpoint.rest.model.CreatePayment;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * Payment
 */
@JsonPropertyOrder({
  Payment.JSON_PROPERTY_ID,
  Payment.JSON_PROPERTY_FEE_ID,
  Payment.JSON_PROPERTY_CREATION_DATETIME,
  Payment.JSON_PROPERTY_TYPE,
  Payment.JSON_PROPERTY_AMOUNT,
  Payment.JSON_PROPERTY_COMMENT
})
@javax.annotation.processing.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2022-09-10T14:13:25.782434+03:00[Africa/Nairobi]")
public class Payment {
  public static final String JSON_PROPERTY_ID = "id";
  private String id;

  public static final String JSON_PROPERTY_FEE_ID = "fee_id";
  private String feeId;

  public static final String JSON_PROPERTY_CREATION_DATETIME = "creation_datetime";
  private java.time.Instant creationDatetime;

  /**
   * Gets or Sets type
   */
  public enum TypeEnum {
    CASH("CASH"),
    
    MOBILE_MONEY("MOBILE_MONEY"),
    
    SCHOLARSHIP("SCHOLARSHIP"),
    
    FIX("FIX");

    private String value;

    TypeEnum(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static TypeEnum fromValue(String value) {
      for (TypeEnum b : TypeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  public static final String JSON_PROPERTY_TYPE = "type";
  private TypeEnum type;

  public static final String JSON_PROPERTY_AMOUNT = "amount";
  private Integer amount;

  public static final String JSON_PROPERTY_COMMENT = "comment";
  private String comment;


  public Payment id(String id) {
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


  public Payment feeId(String feeId) {
    this.feeId = feeId;
    return this;
  }

   /**
   * Get feeId
   * @return feeId
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")
  @JsonProperty(JSON_PROPERTY_FEE_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getFeeId() {
    return feeId;
  }


  @JsonProperty(JSON_PROPERTY_FEE_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setFeeId(String feeId) {
    this.feeId = feeId;
  }


  public Payment creationDatetime(java.time.Instant creationDatetime) {
    this.creationDatetime = creationDatetime;
    return this;
  }

   /**
   * Get creationDatetime
   * @return creationDatetime
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")
  @JsonProperty(JSON_PROPERTY_CREATION_DATETIME)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public java.time.Instant getCreationDatetime() {
    return creationDatetime;
  }


  @JsonProperty(JSON_PROPERTY_CREATION_DATETIME)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setCreationDatetime(java.time.Instant creationDatetime) {
    this.creationDatetime = creationDatetime;
  }


  public Payment type(TypeEnum type) {
    this.type = type;
    return this;
  }

   /**
   * Get type
   * @return type
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")
  @JsonProperty(JSON_PROPERTY_TYPE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public TypeEnum getType() {
    return type;
  }


  @JsonProperty(JSON_PROPERTY_TYPE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setType(TypeEnum type) {
    this.type = type;
  }


  public Payment amount(Integer amount) {
    this.amount = amount;
    return this;
  }

   /**
   * Get amount
   * @return amount
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")
  @JsonProperty(JSON_PROPERTY_AMOUNT)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Integer getAmount() {
    return amount;
  }


  @JsonProperty(JSON_PROPERTY_AMOUNT)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setAmount(Integer amount) {
    this.amount = amount;
  }


  public Payment comment(String comment) {
    this.comment = comment;
    return this;
  }

   /**
   * Get comment
   * @return comment
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")
  @JsonProperty(JSON_PROPERTY_COMMENT)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getComment() {
    return comment;
  }


  @JsonProperty(JSON_PROPERTY_COMMENT)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setComment(String comment) {
    this.comment = comment;
  }


  /**
   * Return true if this Payment object is equal to o.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Payment payment = (Payment) o;
    return Objects.equals(this.id, payment.id) &&
        Objects.equals(this.feeId, payment.feeId) &&
        Objects.equals(this.creationDatetime, payment.creationDatetime) &&
        Objects.equals(this.type, payment.type) &&
        Objects.equals(this.amount, payment.amount) &&
        Objects.equals(this.comment, payment.comment);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, feeId, creationDatetime, type, amount, comment);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Payment {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    feeId: ").append(toIndentedString(feeId)).append("\n");
    sb.append("    creationDatetime: ").append(toIndentedString(creationDatetime)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
    sb.append("    comment: ").append(toIndentedString(comment)).append("\n");
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

