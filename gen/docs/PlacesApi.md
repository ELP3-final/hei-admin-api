# PlacesApi

All URIs are relative to *https://api-dev.hei.school*

Method | HTTP request | Description
------------- | ------------- | -------------
[**createOrUpdateEventParticipants**](PlacesApi.md#createOrUpdateEventParticipants) | **PUT** /events/{event_id}/event_participants | Create or update event participants
[**createOrUpdateEventParticipantsWithHttpInfo**](PlacesApi.md#createOrUpdateEventParticipantsWithHttpInfo) | **PUT** /events/{event_id}/event_participants | Create or update event participants
[**getEventParticipants**](PlacesApi.md#getEventParticipants) | **GET** /events/{event_id}/event_participants | Get all the event participants.
[**getEventParticipantsWithHttpInfo**](PlacesApi.md#getEventParticipantsWithHttpInfo) | **GET** /events/{event_id}/event_participants | Get all the event participants.



## createOrUpdateEventParticipants

> List<EventParticipant> createOrUpdateEventParticipants(eventId, eventParticipant)

Create or update event participants

### Example

```java
// Import classes:
import school.hei.haapi.endpoint.rest.client.ApiClient;
import school.hei.haapi.endpoint.rest.client.ApiException;
import school.hei.haapi.endpoint.rest.client.Configuration;
import school.hei.haapi.endpoint.rest.client.auth.*;
import school.hei.haapi.endpoint.rest.client.models.*;
import school.hei.haapi.endpoint.rest.api.PlacesApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://api-dev.hei.school");
        
        // Configure HTTP bearer authorization: BearerAuth
        HttpBearerAuth BearerAuth = (HttpBearerAuth) defaultClient.getAuthentication("BearerAuth");
        BearerAuth.setBearerToken("BEARER TOKEN");

        PlacesApi apiInstance = new PlacesApi(defaultClient);
        String eventId = "eventId_example"; // String | 
        List<EventParticipant> eventParticipant = Arrays.asList(); // List<EventParticipant> | Event Participants to create or to update
        try {
            List<EventParticipant> result = apiInstance.createOrUpdateEventParticipants(eventId, eventParticipant);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling PlacesApi#createOrUpdateEventParticipants");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }
}
```

### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **eventId** | **String**|  |
 **eventParticipant** | [**List&lt;EventParticipant&gt;**](EventParticipant.md)| Event Participants to create or to update |

### Return type

[**List&lt;EventParticipant&gt;**](EventParticipant.md)


### Authorization

[BearerAuth](../README.md#BearerAuth)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Created or updated event participants |  -  |
| **400** | Bad request |  -  |
| **403** | Forbidden |  -  |
| **404** | Not found |  -  |
| **429** | Too many requests to the API |  -  |
| **500** | Internal server error |  -  |

## createOrUpdateEventParticipantsWithHttpInfo

> ApiResponse<List<EventParticipant>> createOrUpdateEventParticipants createOrUpdateEventParticipantsWithHttpInfo(eventId, eventParticipant)

Create or update event participants

### Example

```java
// Import classes:
import school.hei.haapi.endpoint.rest.client.ApiClient;
import school.hei.haapi.endpoint.rest.client.ApiException;
import school.hei.haapi.endpoint.rest.client.ApiResponse;
import school.hei.haapi.endpoint.rest.client.Configuration;
import school.hei.haapi.endpoint.rest.client.auth.*;
import school.hei.haapi.endpoint.rest.client.models.*;
import school.hei.haapi.endpoint.rest.api.PlacesApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://api-dev.hei.school");
        
        // Configure HTTP bearer authorization: BearerAuth
        HttpBearerAuth BearerAuth = (HttpBearerAuth) defaultClient.getAuthentication("BearerAuth");
        BearerAuth.setBearerToken("BEARER TOKEN");

        PlacesApi apiInstance = new PlacesApi(defaultClient);
        String eventId = "eventId_example"; // String | 
        List<EventParticipant> eventParticipant = Arrays.asList(); // List<EventParticipant> | Event Participants to create or to update
        try {
            ApiResponse<List<EventParticipant>> response = apiInstance.createOrUpdateEventParticipantsWithHttpInfo(eventId, eventParticipant);
            System.out.println("Status code: " + response.getStatusCode());
            System.out.println("Response headers: " + response.getHeaders());
            System.out.println("Response body: " + response.getData());
        } catch (ApiException e) {
            System.err.println("Exception when calling PlacesApi#createOrUpdateEventParticipants");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Response headers: " + e.getResponseHeaders());
            System.err.println("Reason: " + e.getResponseBody());
            e.printStackTrace();
        }
    }
}
```

### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **eventId** | **String**|  |
 **eventParticipant** | [**List&lt;EventParticipant&gt;**](EventParticipant.md)| Event Participants to create or to update |

### Return type

ApiResponse<[**List&lt;EventParticipant&gt;**](EventParticipant.md)>


### Authorization

[BearerAuth](../README.md#BearerAuth)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Created or updated event participants |  -  |
| **400** | Bad request |  -  |
| **403** | Forbidden |  -  |
| **404** | Not found |  -  |
| **429** | Too many requests to the API |  -  |
| **500** | Internal server error |  -  |


## getEventParticipants

> List<EventParticipant> getEventParticipants(eventId)

Get all the event participants.

### Example

```java
// Import classes:
import school.hei.haapi.endpoint.rest.client.ApiClient;
import school.hei.haapi.endpoint.rest.client.ApiException;
import school.hei.haapi.endpoint.rest.client.Configuration;
import school.hei.haapi.endpoint.rest.client.auth.*;
import school.hei.haapi.endpoint.rest.client.models.*;
import school.hei.haapi.endpoint.rest.api.PlacesApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://api-dev.hei.school");
        
        // Configure HTTP bearer authorization: BearerAuth
        HttpBearerAuth BearerAuth = (HttpBearerAuth) defaultClient.getAuthentication("BearerAuth");
        BearerAuth.setBearerToken("BEARER TOKEN");

        PlacesApi apiInstance = new PlacesApi(defaultClient);
        String eventId = "eventId_example"; // String | 
        try {
            List<EventParticipant> result = apiInstance.getEventParticipants(eventId);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling PlacesApi#getEventParticipants");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }
}
```

### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **eventId** | **String**|  |

### Return type

[**List&lt;EventParticipant&gt;**](EventParticipant.md)


### Authorization

[BearerAuth](../README.md#BearerAuth)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | List of all the participants |  -  |
| **400** | Bad request |  -  |
| **403** | Forbidden |  -  |
| **404** | Not found |  -  |
| **429** | Too many requests to the API |  -  |
| **500** | Internal server error |  -  |

## getEventParticipantsWithHttpInfo

> ApiResponse<List<EventParticipant>> getEventParticipants getEventParticipantsWithHttpInfo(eventId)

Get all the event participants.

### Example

```java
// Import classes:
import school.hei.haapi.endpoint.rest.client.ApiClient;
import school.hei.haapi.endpoint.rest.client.ApiException;
import school.hei.haapi.endpoint.rest.client.ApiResponse;
import school.hei.haapi.endpoint.rest.client.Configuration;
import school.hei.haapi.endpoint.rest.client.auth.*;
import school.hei.haapi.endpoint.rest.client.models.*;
import school.hei.haapi.endpoint.rest.api.PlacesApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://api-dev.hei.school");
        
        // Configure HTTP bearer authorization: BearerAuth
        HttpBearerAuth BearerAuth = (HttpBearerAuth) defaultClient.getAuthentication("BearerAuth");
        BearerAuth.setBearerToken("BEARER TOKEN");

        PlacesApi apiInstance = new PlacesApi(defaultClient);
        String eventId = "eventId_example"; // String | 
        try {
            ApiResponse<List<EventParticipant>> response = apiInstance.getEventParticipantsWithHttpInfo(eventId);
            System.out.println("Status code: " + response.getStatusCode());
            System.out.println("Response headers: " + response.getHeaders());
            System.out.println("Response body: " + response.getData());
        } catch (ApiException e) {
            System.err.println("Exception when calling PlacesApi#getEventParticipants");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Response headers: " + e.getResponseHeaders());
            System.err.println("Reason: " + e.getResponseBody());
            e.printStackTrace();
        }
    }
}
```

### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **eventId** | **String**|  |

### Return type

ApiResponse<[**List&lt;EventParticipant&gt;**](EventParticipant.md)>


### Authorization

[BearerAuth](../README.md#BearerAuth)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | List of all the participants |  -  |
| **400** | Bad request |  -  |
| **403** | Forbidden |  -  |
| **404** | Not found |  -  |
| **429** | Too many requests to the API |  -  |
| **500** | Internal server error |  -  |

