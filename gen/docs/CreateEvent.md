

# CreateEvent


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**name** | **String** |  |  [optional]
**type** | [**TypeEnum**](#TypeEnum) |  |  [optional]
**date** | **java.time.Instant** |  |  [optional]
**startTime** | **String** |  |  [optional]
**finishTime** | **String** |  |  [optional]
**supervisor** | [**SupervisorEnum**](#SupervisorEnum) |  |  [optional]
**status** | [**StatusEnum**](#StatusEnum) |  |  [optional]
**placeId** | **String** |  |  [optional]
**groupId** | **String** |  |  [optional]



## Enum: TypeEnum

Name | Value
---- | -----
COURSE | &quot;COURSE&quot;
EXAMEN | &quot;EXAMEN&quot;
OTHER | &quot;OTHER&quot;



## Enum: SupervisorEnum

Name | Value
---- | -----
TEACHER | &quot;TEACHER&quot;
ADMINISTRATOR | &quot;ADMINISTRATOR&quot;



## Enum: StatusEnum

Name | Value
---- | -----
ONGOING | &quot;ONGOING&quot;
CANCELED | &quot;CANCELED&quot;



