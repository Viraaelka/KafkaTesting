Meta:
@TEST CC_Kafka
Scenario: КЦ интеграционный тест нового сервиса обращений

When подключаемся к кафке и подписываемся на топики "CC_CREATE_HANDLING,CC_CREATE_HANDLING_RESPONSE,CC_SAVE_NEW_HANDLING"
When в переменную "response" записывается результат Get запроса к сервису "Новый сервис причин и категорий" на выполнение метода "CreateHandling" c параметрами:
|parameterName|value|
|CallAdminInteractionAddress|<CallAdminInteractionAddress>|
|ChannelId|<ChannelId>|
|DirectionId|<DirectionId>|
|ClientCategoryId|<ClientCategoryId>|
|VdnIvr|<VdnIvr>|
|ClientId|<ClientId>|
|ClientBranchId|<ClientBranchId>|
|ClientTypeId|<ClientTypeId>|
|ClientJurTypeId|<ClientJurTypeId>|
|ClientServiceMethod|<ClientServiceMethod>|
|ClientStatusId|<ClientStatusId>|
|PersonalAccountNumber|<PersonalAccountNumber>|
|LastIvrNode|<LastIvrNode>|
|ServiceLineId|<ServiceLineId>|
|ServiceLineName|<ServiceLineName>|
|HandlingBranchId|<HandlingBranchId>|

Then выполняется проверка равенства значений:
|value1|value2|
|#jsonResponse{response.$['Data']['IsSuccess']}|true|
|#jsonResponse{response.$['IsSuccess']}|true|

When ловим новые сообщения в топиках
Then в переменную "message1" записывается сообщение из топика "CC_CREATE_HANDLING" для брокера
Then выполняется проверка равенства значений:
|value1|value2|
|#jsonResponse{message1.$['Msisdn']}|<CallAdminInteractionAddress>|
|#jsonResponse{message1.$['ServiceChannelId']}|<ChannelId>|
|#jsonResponse{message1.$['InteractionDirectionId']}|<DirectionId>|
|#jsonResponse{message1.$['ClientCategoryId']}|<ClientCategoryId>|
|#jsonResponse{message1.$['VdnIvr']}|<VdnIvr>|
|#jsonResponse{message1.$['ClientId']}|<ClientId>|
|#jsonResponse{message1.$['ClientBranchId']}|<ClientBranchId>|
|#jsonResponse{message1.$['ClientTypeId']}|<ClientTypeId>|
|#jsonResponse{message1.$['ClientJurTypeId']}|<ClientJurTypeId>|
|#jsonResponse{message1.$['ClientServiceMethodId']}|<ClientServiceMethod>|
|#jsonResponse{message1.$['ClientStatusId']}|<ClientStatusId>|
|#jsonResponse{message1.$['PersonalAccount']}|<PersonalAccountNumber>|
|#jsonResponse{message1.$['LastIvrNode']}|<LastIvrNode>|
|#jsonResponse{message1.$['ServiceLineId']}|<ServiceLineId>|
|#jsonResponse{message1.$['ServiceLineName']}|<ServiceLineName>|
|#jsonResponse{message1.$['HandlingBranchId']}|<HandlingBranchId>|
|#jsonResponse{message1.$['CallAdminInteractionType']}|null|
|#jsonResponse{message1.$['CallAdminRegionName']}|null|
|#jsonResponse{message1.$['SystemId']}|null|
|#jsonResponse{message1.$['SalePointId']}|null|
|#jsonResponse{message1.$['LinkedHandlingId']}|null|
|#jsonResponse{message1.$['LinkedHandlingTechId']}|null|
|-- #jsonResponse{message1.$['CallCenterCode']}|КЦ Ростов|

Then в переменную "message2" записывается сообщение из топика "CC_CREATE_HANDLING_RESPONSE" для брокера
Then выполняется проверка равенства значений:
|value1|value2|
|#jsonResponse{message2.$['HandlingId']}|#jsonResponse{response.$['Data']['Id']}|
|#jsonResponse{message2.$['HandlingTechId']}|#jsonResponse{message1.$['HandlingTechId']}|
|#jsonResponse{message2.$['ClosedOn']}|null
|#jsonResponse{message2.$['IsSuccess']}|true|
|#jsonResponse{message2.$['MessageText']}|null|
|#jsonResponse{message2.$['ErrorCode']}|null|
|#jsonResponse{message2.$['ErrorStackTrace']}|null|
|--#jsonResponse{message2.$['WarningMessage']}|null|
|--#jsonResponse{message2.$['HasWarning']}|false|

!-- Then в переменную "message3" записывается сообщение из топика "CC_SAVE_NEW_HANDLING" для брокера
!-- Then выполняется проверка равенства значений:
!-- |value1|value2|
!-- |HandlingId|#jsonResponse{response.$['Data']['Id']}|
!-- |Msisdn|<CallAdminInteractionAddress>|
!-- |ServiceChannelId|<ChannelId>|
!-- |InteractionDirectionId|<DirectionId>|
!-- |ClientCategoryId|<ClientCategoryId>|
!-- |VdnIvr|<VdnIvr>|
!-- |ClientId|<ClientId>|
!-- |ClientBranchId|<ClientBranchId>|
!-- |ClientTypeId|<ClientTypeId>|
!-- |ClientJurTypeId|<ClientJurTypeId>|
!-- |ClientServiceMethodId|<ClientServiceMethod>|
!-- |ClientStatusId|<ClientStatusId>|
!-- |PersonalAccount|<PersonalAccountNumber>|
!-- |LastIvrNode|<LastIvrNode>|
!-- |ServiceLineId|<ServiceLineId>|
!-- |ServiceLineName|<ServiceLineName>|
!-- |HandlingBranchId|<HandlingBranchId>|
!-- |SystemId|null|
!-- |SalePointId|null|
!-- |LinkedHandlingId|null|
!-- |LinkedHandlingTechId|null|
!-- |CallAdminRegionName|null|

When в переменную "redis2" записывается значение из БД Redis "Redis_10-12-59-115" для ключа "Tele2.Crm.Redis.Cache:Handlings:HandlingCache:#jsonResponse{response.$['Data']['Id']}"
When считать значение из переменной "redis2" по индексу "0" и записать в переменную "rdsvalue2"
!-- Then выполняется проверка равенства значений:
!-- |value1|value2|
!-- |#{rdsvalue2}|#jsonResponse{message1.$['HandlingTechId']}|

When в переменную "redis1" записывается значение из БД Redis "Redis_10-12-59-115" для ключа "Tele2.Crm.Redis.Cache:Handlings:HandlingCache"
When считать значение из переменной "redis1" по ключу "HandlingCache:#{rdsvalue2}" и записать в переменную "rdsvalue1"
Then выполняется проверка равенства значений:
|value1|value2|
|#jsonResponse{rdsvalue1.$['Id']}|#jsonResponse{response.$['Data']['Id']}|
|#jsonResponse{rdsvalue1.$['Msisdn']}|<CallAdminInteractionAddress>|
|#jsonResponse{rdsvalue1.$['ServiceChannelId']}|<ChannelId>|
|#jsonResponse{rdsvalue1.$['InteractionDirectionId']}|<DirectionId>|
|#jsonResponse{rdsvalue1.$['ClientCategoryId']}|<ClientCategoryId>|
|#jsonResponse{rdsvalue1.$['VdnIvr']}|<VdnIvr>|
|#jsonResponse{rdsvalue1.$['ClientId']}|<ClientId>|
|#jsonResponse{rdsvalue1.$['ClientBranchId']}|<ClientBranchId>|
|#jsonResponse{rdsvalue1.$['ClientTypeId']}|<ClientTypeId>|
|#jsonResponse{rdsvalue1.$['ClientJurTypeId']}|<ClientJurTypeId>|
|#jsonResponse{rdsvalue1.$['ClientStatusId']}|<ClientStatusId>|
|#jsonResponse{rdsvalue1.$['PersonalAccount']}|<PersonalAccountNumber>|
|#jsonResponse{rdsvalue1.$['LastIvrNode']}|<LastIvrNode>|
|#jsonResponse{rdsvalue1.$['ServiceLineId']}|<ServiceLineId>|
|#jsonResponse{rdsvalue1.$['ServiceLineName']}|<ServiceLineName>|
|#jsonResponse{rdsvalue1.$['HandlingBranchId']}|<HandlingBranchId>|
|#jsonResponse{rdsvalue1.$['SystemId']}|null|
|#jsonResponse{rdsvalue1.$['LinkedHandlingId']}|null|
|#jsonResponse{rdsvalue1.$['LinkedHandlingTechId']}|null|
|#jsonResponse{rdsvalue1.$['CallAdminRegionName']}|null|
|#jsonResponse{rdsvalue1.$['CallAdminInteractionTypeId']}|null|
|#jsonResponse{rdsvalue1.$['CallAdminInteractionId']}|null|
|#jsonResponse{rdsvalue1.$['RegisteringCaseId']}|null|
|#jsonResponse{rdsvalue1.$['RegionId']}|null|
|#jsonResponse{rdsvalue1.$['SalePointCode']}|null|
|#jsonResponse{rdsvalue1.$['IsFalse']}|false|
|#jsonResponse{rdsvalue1.$['IsClosed']}|false|
|#jsonResponse{rdsvalue1.$['IsEmpty']}|false|
|#jsonResponse{rdsvalue1.$['IsDeleted']}|false|
|--#jsonResponse{rdsvalue1.$['HandlingTechId']}|#jsonResponse{message1.$['HandlingTechId']}|
|--#jsonResponse{rdsvalue1.$['ClientServiceMethodId']}|<ClientServiceMethod>|
|--#jsonResponse{rdsvalue1.$['SalePointId']}|null|
|--#jsonResponse{rdsvalue1.$['CallCenterCode']}|КЦ Ростов|

When в переменную "redis3" записывается значение из БД Redis "Redis_10-12-59-115" для ключа "Tele2.Crm.Redis.Cache:Handlings:OpenedHandling"
When считать значение из переменной "redis3" по ключу "OpenedHandling:#jsonResponse{response.$['Data']['Id']}" и записать в переменную "rdsvalue3"
Then выполняется проверка равенства значений:
|value1|value2|
|#jsonResponse{rdsvalue1.$['HandlingTechId']}|#jsonResponse{rdsvalue3.$['HandlingTechId']}|
|#jsonResponse{rdsvalue1.$['Id']}|#jsonResponse{rdsvalue3.$['HandlingId']}|

When в переменную "redis4" записывается значение из БД Redis "Redis_10-12-59-115" для ключа "Tele2.Crm.Redis.Cache:Handlings:OpenedHandling:#jsonResponse{rdsvalue1.$['Msisdn']}"
When считать значение из переменной "redis4" по индексу "0" и записать в переменную "rdsvalue4"
Then выполняется проверка равенства значений:
|value1|value2|
|#{rdsvalue4}|#jsonResponse{response.$['Data']['Id']}|

When в переменную "redis5" записывается значение из БД Redis "Redis_10-12-59-115" для ключа "Tele2.Crm.Redis.Cache:Handlings:RepeatedHandling:#jsonResponse{rdsvalue1.$['Msisdn']}"
When считать значение из переменной "redis5" по индексу "0" и записать в переменную "rdsvalue5"
Then выполняется проверка равенства значений:
|value1|value2|
|#jsonResponse{rdsvalue5.$['HandlingId']}|#jsonResponse{response.$['Data']['Id']}|

Examples:
|CallAdminInteractionAddress|ChannelId|DirectionId|ClientCategoryId|VdnIvr|ClientId|ClientBranchId|ClientTypeId|ClientJurTypeId|ClientServiceMethod|ClientStatusId|PersonalAccountNumber|LastIvrNode|ServiceLineId|ServiceLineName|HandlingBranchId|
|79001247683|2e6f7c14-ac89-e811-8110-00155df43732|9a4b3e83-47b7-e811-8112-00155df43732|eac4997b-933d-e811-8119-001dd8b71d47|70461|59734090|3|1|1|COTPYDHUK_TELE2|1|63956973|Другие услуги|2|611|3|