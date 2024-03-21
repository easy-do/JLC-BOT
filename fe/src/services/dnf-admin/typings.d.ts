declare namespace API {
  type AccountCargo = {
    money?: number;
    capacity?: number;
    cargo?: string[];
    occTime?: string;
    mid?: number;
  };

  type AccountCargoDto = {
    money?: number;
    capacity?: number;
    mid?: number;
  };

  type Accounts = {
    uid?: number;
    accountname?: string;
    password?: string;
    qq?: string;
    vip?: string;
    admin?: boolean;
  };

  type AccountsQo = {
    current?: number;
    pageSize?: number;
    uid?: number;
    accountname?: string;
    qq?: string;
    vip?: string;
  };

  type allListParams = {
    name?: string;
  };

  type AuthRoleResourceDto = {
    roleId: number;
    resourceIds: number[];
  };

  type BotNodeDto = {
    id?: number;
    eventType?: string;
    confName?: string;
    nodes?: Record<string, any>;
    nodeConf?: Record<string, any>;
  };

  type CaptchaVo = {
    key?: string;
    img?: string;
  };

  type ChannelQo = {
    current?: number;
    pageSize?: number;
    pid?: string;
    channelName?: string;
    channelStatus?: boolean;
    fridaStatus?: boolean;
  };

  type CharacInfo = {
    characNo?: number;
    characName?: string;
    village?: number;
    job?: number;
    lev?: number;
    exp?: number;
    growType?: number;
    hp?: number;
    maxHP?: number;
    maxMP?: number;
    phyAttack?: number;
    phyDefense?: number;
    magAttack?: number;
    magDefense?: number;
    elementResist?: string[];
    specProperty?: string[];
    invenWeight?: number;
    hpRegen?: number;
    mpRegen?: number;
    moveSpeed?: number;
    attackSpeed?: number;
    castSpeed?: number;
    hitRecovery?: number;
    jump?: number;
    characWeight?: number;
    fatigue?: number;
    maxFatigue?: number;
    premiumFatigue?: number;
    maxPremiumFatigue?: number;
    createTime?: string;
    lastPlayTime?: string;
    dungeonClearPoint?: number;
    deleteTime?: string;
    deleteFlag?: number;
    guildId?: number;
    guildRight?: number;
    memberFlag?: number;
    sex?: number;
    expertJob?: number;
    skillTreeIndex?: number;
    linkCharacNo?: number;
    eventCharacLevel?: number;
    guildSecede?: number;
    startTime?: number;
    finishTime?: number;
    competitionArea?: number;
    competitionPeriod?: number;
    mercenaryStartTime?: number;
    mercenaryFinishTime?: number;
    mercenaryArea?: number;
    mercenaryPeriod?: number;
    vip?: string;
    jobName?: string;
    expertJobName?: string;
    mid?: number;
  };

  type CharacInfoQo = {
    current?: number;
    pageSize?: number;
    characNo?: number;
    characName?: string;
    online?: boolean;
    mid?: number;
  };

  type characSignParams = {
    characNo: number;
  };

  type cleanItemsParams = {
    characNo: number;
  };

  type cleanMailParams = {
    characNo: number;
  };

  type cleanRoleItemParams = {
    characNo: number;
    type: number;
  };

  type createAccountCargoParams = {
    characNo: number;
  };

  type CurrentUser = {
    userName?: string;
    menu?: Record<string, any>[];
    resource?: string[];
    mode?: string;
  };

  type DaBotConf = {
    id?: number;
    botNumber?: string;
    platform?: string;
    confKey?: string;
    confValue?: string;
    remark?: string;
  };

  type DaBotEventScript = {
    id?: number;
    scriptName?: string;
    eventType?: string;
    scriptType?: string;
    scriptContent?: string;
    remark?: string;
  };

  type DaBotEventScriptQo = {
    current?: number;
    pageSize?: number;
    scriptName?: string;
    eventType?: string;
    scriptType?: string;
    remark?: string;
  };

  type DaBotInfo = {
    id?: number;
    botNumber?: string;
    botSecret?: string;
    remark?: string;
    botUrl?: string;
    lastHeartbeatTime?: string;
    extData?: string;
  };

  type DaBotMessage = {
    id?: number;
    messageId?: string;
    groupId?: string;
    sendUser?: string;
    selfUser?: string;
    selfTime?: string;
    message?: string;
  };

  type DaBotMessageQo = {
    current?: number;
    pageSize?: number;
    messageId?: string;
    groupId?: string;
    sendUser?: string;
    selfUser?: string;
    selfTime?: string;
    message?: string;
  };

  type DaBotNotice = {
    id?: number;
    noticeType?: string;
    subType?: string;
    selfUser?: string;
    groupId?: string;
    operatorId?: string;
    userId?: string;
    selfTime?: string;
    messageId?: string;
  };

  type DaBotNoticeQo = {
    current?: number;
    pageSize?: number;
    noticeType?: string;
    subType?: string;
    selfUser?: string;
    groupId?: string;
    operatorId?: string;
    userId?: string;
    selfTime?: string;
    messageId?: string;
  };

  type DaBotQo = {
    current?: number;
    pageSize?: number;
    botNumber?: string;
    remark?: string;
    botType?: string;
    botUrl?: string;
  };

  type DaBotRequest = {
    id?: number;
    requestType?: string;
    groupId?: string;
    sendUser?: string;
    selfUser?: string;
    selfTime?: string;
    comment?: string;
    flag?: string;
  };

  type DaBotRequestQo = {
    current?: number;
    pageSize?: number;
    requestType?: string;
    groupId?: string;
    sendUser?: string;
    selfUser?: string;
    selfTime?: string;
    comment?: string;
    flag?: string;
  };

  type DaCdk = {
    cdkCode?: string;
    cdkType?: number;
    cdkConf?: string;
    remark?: string;
    createTime?: string;
    status?: boolean;
    createBy?: number;
    useTime?: string;
    useUser?: number;
    deleteFlag?: boolean;
    number?: number;
  };

  type DaCdkQo = {
    current?: number;
    pageSize?: number;
    cdkCode?: string;
    remark?: string;
    status?: boolean;
    createTime?: string;
    deleteFlag?: boolean;
  };

  type DaChannel = {
    id?: number;
    pid?: string;
    channelName?: string;
    fridaClient?: string;
    scriptContext?: string;
    mainPython?: string;
    channelStatus?: boolean;
    fridaStatus?: boolean;
    fridaJsonContext?: string;
  };

  type DaFridaFunction = {
    id?: number;
    childrenFunction?: string;
    functionName?: string;
    functionKey?: string;
    functionContext?: string;
    remark?: string;
    isSystemFun?: boolean;
  };

  type DaFridaScript = {
    id?: number;
    scriptName?: string;
    scriptContext?: string;
    childrenFunction?: string;
    remark?: string;
  };

  type DaGameConfig = {
    id?: number;
    confName?: string;
    confType?: number;
    confData?: string;
    confKey?: string;
    remark?: string;
    isSystemConf?: boolean;
  };

  type DaGameConfigQo = {
    current?: number;
    pageSize?: number;
    confName?: string;
    confType?: number;
    confKey?: string;
  };

  type DaGameEvent = {
    id?: number;
    accountId?: number;
    fileIndex?: number;
    fileName?: string;
    channel?: string;
    charcaNo?: number;
    charcaName?: string;
    level?: number;
    optionType?: string;
    param1?: string;
    param2?: string;
    param3?: string;
    clientIp?: string;
    optionTime?: string;
    optionInfo?: string;
  };

  type DaGameEventQo = {
    current?: number;
    pageSize?: number;
    accountId?: number;
    fileIndex?: number;
    channel?: string;
    charcaNo?: number;
    charcaName?: string;
    level?: number;
    optionType?: string;
    clientIp?: string;
  };

  type DaItemEntity = {
    id?: number;
    name?: string;
    type?: string;
    rarity?: string;
  };

  type DaItemQo = {
    current?: number;
    pageSize?: number;
    id?: number;
    name?: string;
    type?: string;
    rarity?: string;
  };

  type DaLowCodeNodeConf = {
    id?: number;
    confData?: string;
    confName?: string;
    nodeData?: string;
    eventType?: string;
    createTime?: string;
    updateTime?: string;
    deleteFlag?: boolean;
  };

  type DaLowCodeSysNode = {
    id?: number;
    nodeName?: string;
    groupType?: string;
    nodeCode?: string;
    nodeColor?: string;
    nodeIcon?: string;
    nodePort?: string;
    maxSize?: number;
    formData?: string;
    remark?: string;
    deleteFlag?: boolean;
  };

  type DaMailSendLog = {
    id?: number;
    sendDetails?: string;
    createTime?: string;
  };

  type DaNoticeSendLog = {
    id?: number;
    message?: string;
    createTime?: string;
  };

  type DaRole = {
    id?: number;
    roleName?: string;
    roleKey?: string;
    roleSort?: number;
    isDefault?: boolean;
    status?: boolean;
    remark?: string;
  };

  type DaRoleQo = {
    current?: number;
    pageSize?: number;
    roleName?: string;
    roleKey?: string;
    roleSort?: number;
    isDefault?: boolean;
    status?: boolean;
    remark?: string;
  };

  type DaSignInConf = {
    id?: number;
    configName?: string;
    configDate?: string;
    configJson?: string;
    remark?: string;
    createId?: number;
    createTime?: string;
    updateTime?: string;
    signInTime?: string;
  };

  type DaSignInConfDto = {
    id?: number;
    configName?: string;
    configDate?: string;
    configJson?: SignInConfigDate[];
    remark?: string;
    signInTime?: string;
  };

  type DaSignInConfQo = {
    current?: number;
    pageSize?: number;
    configName?: string;
  };

  type DaSignInConfVo = {
    id?: number;
    configName?: string;
    configDate?: string;
    configJson?: string;
    remark?: string;
    createTime?: string;
    updateTime?: string;
    signInTime?: string;
  };

  type DebugBotNodeDto = {
    id?: number;
    params?: Record<string, any>;
  };

  type DebugFridaDto = {
    channelId?: number;
    debugData?: string;
  };

  type disableAccountsParams = {
    uid: number;
  };

  type EditGameRoleItemDto = {
    characNo?: number;
    type?: string;
    gameItemVo?: GameItemVo;
  };

  type enableAccountsParams = {
    uid: number;
  };

  type EnableBotScriptDto = {
    botId?: number;
    scriptIds?: number[];
  };

  type FridaFunctionQo = {
    current?: number;
    pageSize?: number;
    functionName?: string;
    functionKey?: string;
    remark?: string;
    isSystemFun?: boolean;
  };

  type FridaScriptQo = {
    current?: number;
    pageSize?: number;
    scriptName?: string;
    remark?: string;
  };

  type GameItemVo = {
    index?: number;
    sealType?: number;
    itemType?: number;
    itemId?: number;
    itemName?: string;
    upgrade?: number;
    quality?: number;
    durability?: number;
    enchantment?: number;
    increaseType?: number;
    increaseLevel?: number;
    otherworldly?: number;
    forgeLevel?: number;
    ext20to30?: string[];
    ext33to36?: string[];
    ext37to50?: string[];
    ext52toEnd?: string[];
  };

  type gameRoleInfoParams = {
    characNo: number;
  };

  type gameRoleRecoverParams = {
    characNo: number;
  };

  type gameRoleRemoveParams = {
    characNo: number;
  };

  type generateCaptchaV1Params = {
    key?: string;
  };

  type generateCaptchaV2Params = {
    key?: string;
  };

  type getAccountCargoParams = {
    characNo: number;
  };

  type getAccountsParams = {
    id: number;
  };

  type getBotConfParams = {
    botNumber: string;
  };

  type getBotNodeParams = {
    id: number;
  };

  type getCdkInfoParams = {
    id: string;
  };

  type getChannelInfoParams = {
    id: number;
  };

  type getChildrenFunctionParams = {
    id: number;
  };

  type getConfInfoParams = {
    id: Record<string, any>;
  };

  type getDebugLogParams = {
    id: number;
  };

  type getEnableBotScriptParams = {
    id: number;
  };

  type getFridaFunctionInfoParams = {
    id: number;
  };

  type getFridaLogParams = {
    id: number;
  };

  type getFridaScriptInfoParams = {
    id: Record<string, any>;
  };

  type getNodeConfParams = {
    id: number;
  };

  type getOtherDataParams = {
    characNo: number;
  };

  type getRoleInfoParams = {
    id: Record<string, any>;
  };

  type getRoleItemParams = {
    characNo: number;
    type: string;
  };

  type getSysNodeInfoParams = {
    id: Record<string, any>;
  };

  type infoBotParams = {
    id: number;
  };

  type infoBotScriptParams = {
    id: number;
  };

  type LicenseDetails = {
    type?: number;
    startTime?: string;
    endTime?: string;
  };

  type listItemParams = {
    name?: string;
  };

  type LoginDto = {
    userName: string;
    password: string;
    verificationCode?: string;
    captchaKey?: string;
  };

  type MailItemDto = {
    itemId?: number;
    itemType?: number;
    count?: number;
  };

  type NodeExecuteResult = {
    nodeId?: string;
    nodeName?: string;
    nodeCode?: string;
    data?: Record<string, any>;
    message?: string;
    status?: number;
    executeTime?: number;
  };

  type OnlineCountVo = {
    count?: number;
    online?: number;
  };

  type openDungeonParams = {
    uid: number;
  };

  type openLeftAndRightParams = {
    characNo: number;
  };

  type OtherDataDto = {
    characNo?: number;
    win?: number;
    pvpPoint?: number;
    pvpGrade?: number;
    money?: number;
    sp?: number;
    tp?: number;
    qp?: number;
    avatarCoin?: number;
    oldData?: OtherDataDto;
  };

  type OtherDataVo = {
    characNo?: number;
    win?: number;
    pvpPoint?: number;
    pvpGrade?: number;
    money?: number;
    sp?: number;
    tp?: number;
    qp?: number;
    avatarCoin?: number;
  };

  type pageAccountsParams = {
    page: AccountsQo;
  };

  type PageQo = {
    current?: number;
    pageSize?: number;
  };

  type Postal = {
    postalId?: number;
    itemId?: number;
    occTime?: string;
    receiveCharacNo?: number;
    sendCharacNo?: number;
    sendCharacName?: string;
    addInfo?: number;
    endurance?: number;
    upgrade?: number;
    seperateUpgrade?: number;
    amplifyOption?: number;
    amplifyValue?: number;
    gold?: number;
    avataFlag?: number;
    unlimitFlag?: number;
    sealFlag?: number;
    creatureFlag?: number;
    letterId?: number;
    deleteFlag?: number;
    itemName?: string;
  };

  type RAccountCargo = {
    code?: number;
    data?: AccountCargo;
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: number;
  };

  type RAccounts = {
    code?: number;
    data?: Accounts;
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: number;
  };

  type RBoolean = {
    code?: number;
    data?: boolean;
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: number;
  };

  type RBotNodeDto = {
    code?: number;
    data?: BotNodeDto;
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: number;
  };

  type RCaptchaVo = {
    code?: number;
    data?: CaptchaVo;
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: number;
  };

  type RCharacInfo = {
    code?: number;
    data?: CharacInfo;
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: number;
  };

  type RCurrentUser = {
    code?: number;
    data?: CurrentUser;
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: number;
  };

  type RDaBotEventScript = {
    code?: number;
    data?: DaBotEventScript;
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: number;
  };

  type RDaBotInfo = {
    code?: number;
    data?: DaBotInfo;
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: number;
  };

  type RDaCdk = {
    code?: number;
    data?: DaCdk;
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: number;
  };

  type RDaChannel = {
    code?: number;
    data?: DaChannel;
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: number;
  };

  type RDaFridaFunction = {
    code?: number;
    data?: DaFridaFunction;
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: number;
  };

  type RDaFridaScript = {
    code?: number;
    data?: DaFridaScript;
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: number;
  };

  type RDaGameConfig = {
    code?: number;
    data?: DaGameConfig;
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: number;
  };

  type RDaLowCodeSysNode = {
    code?: number;
    data?: DaLowCodeSysNode;
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: number;
  };

  type RDaRole = {
    code?: number;
    data?: DaRole;
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: number;
  };

  type RDaSignInConfVo = {
    code?: number;
    data?: DaSignInConfVo;
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: number;
  };

  type rechargeBondsParams = {
    type?: number;
    uid: number;
    count?: number;
  };

  type RegDto = {
    userName: string;
    password: string;
    verificationCode?: string;
    captchaKey?: string;
  };

  type RegLicenseDto = {
    license?: string;
    verificationCode?: string;
    captchaKey?: string;
  };

  type removeAccountCargoParams = {
    characNo: number;
  };

  type removeBotConfParams = {
    id: number;
  };

  type removeGameEventParams = {
    id: Record<string, any>;
  };

  type removeItemsParams = {
    uiId: number;
  };

  type removeMailParams = {
    postalId: number;
  };

  type removeNodeConfParams = {
    id: number;
  };

  type removeSysNodeParams = {
    id: Record<string, any>;
  };

  type resetCreateRoleParams = {
    uid: number;
  };

  type resetPasswordParams = {
    uid: number;
    password: string;
  };

  type restartFridaParams = {
    id: number;
  };

  type RLicenseDetails = {
    code?: number;
    data?: LicenseDetails;
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: number;
  };

  type RListAccounts = {
    code?: number;
    data?: Accounts[];
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: number;
  };

  type RListCharacInfo = {
    code?: number;
    data?: CharacInfo[];
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: number;
  };

  type RListDaBotConf = {
    code?: number;
    data?: DaBotConf[];
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: number;
  };

  type RListDaBotEventScript = {
    code?: number;
    data?: DaBotEventScript[];
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: number;
  };

  type RListDaBotInfo = {
    code?: number;
    data?: DaBotInfo[];
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: number;
  };

  type RListDaBotMessage = {
    code?: number;
    data?: DaBotMessage[];
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: number;
  };

  type RListDaBotNotice = {
    code?: number;
    data?: DaBotNotice[];
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: number;
  };

  type RListDaBotRequest = {
    code?: number;
    data?: DaBotRequest[];
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: number;
  };

  type RListDaCdk = {
    code?: number;
    data?: DaCdk[];
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: number;
  };

  type RListDaChannel = {
    code?: number;
    data?: DaChannel[];
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: number;
  };

  type RListDaFridaFunction = {
    code?: number;
    data?: DaFridaFunction[];
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: number;
  };

  type RListDaFridaScript = {
    code?: number;
    data?: DaFridaScript[];
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: number;
  };

  type RListDaGameConfig = {
    code?: number;
    data?: DaGameConfig[];
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: number;
  };

  type RListDaGameEvent = {
    code?: number;
    data?: DaGameEvent[];
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: number;
  };

  type RListDaItemEntity = {
    code?: number;
    data?: DaItemEntity[];
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: number;
  };

  type RListDaLowCodeNodeConf = {
    code?: number;
    data?: DaLowCodeNodeConf[];
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: number;
  };

  type RListDaLowCodeSysNode = {
    code?: number;
    data?: DaLowCodeSysNode[];
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: number;
  };

  type RListDaMailSendLog = {
    code?: number;
    data?: DaMailSendLog[];
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: number;
  };

  type RListDaNoticeSendLog = {
    code?: number;
    data?: DaNoticeSendLog[];
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: number;
  };

  type RListDaRole = {
    code?: number;
    data?: DaRole[];
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: number;
  };

  type RListDaSignInConf = {
    code?: number;
    data?: DaSignInConf[];
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: number;
  };

  type RListDaSignInConfVo = {
    code?: number;
    data?: DaSignInConfVo[];
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: number;
  };

  type RListLong = {
    code?: number;
    data?: number[];
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: number;
  };

  type RListNodeExecuteResult = {
    code?: number;
    data?: NodeExecuteResult[];
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: number;
  };

  type RListPostal = {
    code?: number;
    data?: Postal[];
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: number;
  };

  type RListString = {
    code?: number;
    data?: string[];
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: number;
  };

  type RListTreeLong = {
    code?: number;
    data?: TreeLong[];
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: number;
  };

  type RListUserItems = {
    code?: number;
    data?: UserItems[];
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: number;
  };

  type RLong = {
    code?: number;
    data?: number;
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: number;
  };

  type RMapStringListDaLowCodeSysNode = {
    code?: number;
    data?: Record<string, any>;
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: number;
  };

  type RObject = {
    code?: number;
    data?: Record<string, any>;
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: number;
  };

  type roleItemsParams = {
    characNo: number;
  };

  type RoleItemVo = {
    inventory?: GameItemVo[];
    equipslot?: GameItemVo[];
    creature?: GameItemVo[];
    cargo?: GameItemVo[];
    accountCargo?: GameItemVo[];
  };

  type roleListParams = {
    name?: string;
  };

  type roleMailPageParams = {
    characNo: number;
  };

  type RoleMailPageQo = {
    current?: number;
    pageSize?: number;
    scriptName?: number;
  };

  type roleResourceIdsParams = {
    roleId: number;
  };

  type roleResourceParams = {
    roleId: number;
  };

  type ROnlineCountVo = {
    code?: number;
    data?: OnlineCountVo;
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: number;
  };

  type ROtherDataVo = {
    code?: number;
    data?: OtherDataVo;
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: number;
  };

  type RRoleItemVo = {
    code?: number;
    data?: RoleItemVo;
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: number;
  };

  type RString = {
    code?: number;
    data?: string;
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: number;
  };

  type SendMailDto = {
    characNo?: number;
    title?: string;
    content?: string;
    gold?: number;
    itemList?: MailItemDto[];
  };

  type sendNoticeParams = {
    message: string;
  };

  type SetBotNodeDto = {
    botId?: number;
    confIdList?: number[];
  };

  type setMaxRoleParams = {
    uid: number;
  };

  type SignInConfigDate = {
    name?: string;
    itemId?: number;
    quantity?: number;
    itemType?: number;
  };

  type signInInfoParams = {
    id: number;
  };

  type signListParams = {
    characNo: number;
  };

  type stopFridaParams = {
    id: number;
  };

  type TreeLong = {
    name?: { empty?: boolean };
    id?: number;
    parentId?: number;
    config?: TreeNodeConfig;
    weight?: Record<string, any>;
    empty?: boolean;
  };

  type TreeNodeConfig = {
    idKey?: string;
    parentIdKey?: string;
    weightKey?: string;
    nameKey?: string;
    childrenKey?: string;
    deep?: number;
  };

  type UpdateScriptDto = {
    channelId?: number;
    context?: string;
    restartFrida?: boolean;
  };

  type UseCdkDto = {
    characNo?: number;
    cdks?: string[];
    verificationCode?: string;
    captchaKey?: string;
  };

  type UserItems = {
    uiId?: number;
    characNo?: number;
    slot?: number;
    itId?: number;
    expireDate?: string;
    obtainFrom?: number;
    regDate?: string;
    ipgAgencyNo?: string;
    abilityNo?: number;
    stat?: number;
    clearAvatarId?: number;
    jewelSocket?: string[];
    itemLockKey?: number;
    toIpgAgencyNo?: string;
    hiddenOption?: number;
    emblemEndurance?: number;
    color1?: number;
    color2?: number;
    tradeRestrict?: number;
    itemName?: string;
    mtime?: string;
  };
}
