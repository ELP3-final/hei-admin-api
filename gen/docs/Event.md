

# Event


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**id** | **String** |  |  [optional]
**place** | [**Place**](Place.md) |  |  [optional]
**group** | [**Group**](Group.md) |  |  [optional]
**name** | **String** |  |  [optional]
**type** | [**TypeEnum**](#TypeEnum) |  |  [optional]
**date** | **java.time.Instant** |  |  [optional]
**startTime** | **String** |  |  [optional]
**finishTime** | **String** |  |  [optional]
**supervisor** | [**SupervisorEnum**](#SupervisorEnum) |  |  [optional]
**status** | [**StatusEnum**](#StatusEnum) |  |  [optional]



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



