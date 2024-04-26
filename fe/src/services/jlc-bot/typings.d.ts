declare namespace API {
  type BotConf = {
    id?: number;
    botNumber?: string;
    platform?: string;
    confKey?: string;
    confValue?: string;
    remark?: string;
  };

  type BotInfo = {
    id?: number;
    botNumber?: string;
    botSecret?: string;
    remark?: string;
    botUrl?: string;
    lastHeartbeatTime?: string;
    extData?: string;
    postType?: string;
    invokeType?: string;
    platform?: string;
  };

  type BotMessage = {
    id?: number;
    messageId?: string;
    groupId?: string;
    sendUser?: string;
    selfUser?: string;
    selfTime?: string;
    message?: string;
    messageFormat?: string;
  };

  type BotMessageQo = {
    current?: number;
    pageSize?: number;
    messageId?: string;
    groupId?: string;
    sendUser?: string;
    selfUser?: string;
    selfTime?: string;
    message?: string;
    messageFormat?: string;
  };

  type BotNodeConfExecuteLog = {
    confId?: number;
    confName?: string;
    executeTime?: number;
    createTime?: string;
  };

  type BotNodeDto = {
    id?: number;
    eventType?: string;
    confName?: string;
    nodes?: Record<string, any>;
    nodeConf?: Record<string, any>;
  };

  type BotNodeExecuteLog = {
    confId?: number;
    confName?: string;
    nodeCode?: string;
    nodeName?: string;
    executeTime?: number;
    createTime?: string;
  };

  type BotNotice = {
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

  type BotNoticeQo = {
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

  type BotPostLog = {
    id?: number;
    postTime?: string;
    message?: string;
    platform?: string;
  };

  type BotQo = {
    current?: number;
    pageSize?: number;
    botNumber?: string;
    remark?: string;
    botType?: string;
    botUrl?: string;
  };

  type BotRequest = {
    id?: number;
    requestType?: string;
    groupId?: string;
    sendUser?: string;
    selfUser?: string;
    selfTime?: string;
    comment?: string;
    flag?: string;
  };

  type BotRequestQo = {
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

  type chatParams = {
    message: string;
    baseUrl: string;
    model: string;
  };

  type CmpContextBean = {
    name?: string;
    desc?: string;
    methods?: CmpContextBeanMethod[];
  };

  type CmpContextBeanMethod = {
    name?: string;
    returnType?: string;
    demo?: string;
    desc?: string;
  };

  type CmpLog = {
    type?: string;
    context?: string;
  };

  type CmpStepResult = {
    nodeId?: string;
    nodeName?: string;
    tag?: string;
    startTime?: string;
    endTime?: string;
    timeSpent?: number;
    success?: boolean;
    message?: string;
    param?: { raw?: Record<string, any>; config?: JSONConfig; empty?: boolean };
    rollbackTimeSpent?: number;
    contextBeanList?: CmpContextBean[];
    logs?: CmpLog[];
  };

  type copyNodeConfParams = {
    id: number;
  };

  type CurrentUser = {
    userName?: string;
    menu?: Record<string, any>[];
    resource?: Record<string, any>[];
    mode?: string;
  };

  type DebugDto = {
    id?: number;
    params?: Record<string, any>;
  };

  type generateHookUrlParams = {
    botId: number;
    confId: number;
  };

  type getBotConfParams = {
    botNumber: string;
  };

  type getBotHighLevelDevelopParams = {
    id: number;
  };

  type getBotNodeParams = {
    id: number;
  };

  type getBotSimpleCmdDevelopParams = {
    id: number;
  };

  type getConfInfoParams = {
    id: Record<string, any>;
  };

  type getHighLevelDevInfoParams = {
    id: number;
  };

  type getNodeConfParams = {
    id: number;
  };

  type getSimpleDevelopInfoParams = {
    id: number;
  };

  type getSysNodeInfoParams = {
    id: Record<string, any>;
  };

  type getSysNodeScriptDataParams = {
    id: Record<string, any>;
  };

  type getWebhooksConfInfoParams = {
    id: number;
  };

  type HighLevelDevelopConf = {
    id?: number;
    confName?: string;
    eventType?: string;
    remark?: string;
    scriptLanguage?: string;
    script?: LiteFlowScript;
  };

  type HighLevelDevelopConfQo = {
    current?: number;
    pageSize?: number;
    confName?: string;
    eventType?: string;
    remark?: string;
  };

  type highLevelDevPageParams = {
    highLevelDevelopConfQo: HighLevelDevelopConfQo;
  };

  type hookPostParams = {
    action: number;
    secret: string;
    paramsJson: JSONObject;
  };

  type infoBotParams = {
    id: number;
  };

  type jobInfoParams = {
    id: number;
  };

  type JSONConfig = {
    keyComparator?: Record<string, any>;
    ignoreError?: boolean;
    ignoreCase?: boolean;
    dateFormat?: string;
    ignoreNullValue?: boolean;
    transientSupport?: boolean;
    stripTrailingZeros?: boolean;
    checkDuplicate?: boolean;
    order?: boolean;
  };

  type JSONObject = {
    raw?: Record<string, any>;
    config?: JSONConfig;
    empty?: boolean;
  };

  type LiteFlowScript = {
    id?: number;
    applicationName?: string;
    scriptId?: string;
    scriptName?: string;
    scriptData?: string;
    scriptType?: string;
    scriptLanguage?: string;
    enable?: boolean;
  };

  type LoginDto = {
    userName: string;
    password: string;
  };

  type LowCodeNodeConf = {
    id?: number;
    confData?: string;
    confName?: string;
    nodeData?: string;
    eventType?: string;
    createTime?: string;
    updateTime?: string;
    deleteFlag?: boolean;
  };

  type LowCodeSysNode = {
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
    systemNode?: boolean;
    deleteFlag?: boolean;
    script?: LiteFlowScript;
  };

  type NodeConfExecuteLogQo = {
    current?: number;
    pageSize?: number;
    confId?: number;
    confName?: string;
    executeTime?: number;
  };

  type NodeConfQo = {
    current?: number;
    pageSize?: number;
    confName?: string;
    eventType?: string;
  };

  type NodeExecuteLogQo = {
    current?: number;
    pageSize?: number;
    confId?: number;
    confName?: string;
    nodeCode?: string;
    nodeName?: string;
    executeTime?: number;
  };

  type NodePAVo = {
    nodeName?: string;
    confName?: string;
    executeTime?: number;
    count?: number;
  };

  type PageQo = {
    current?: number;
    pageSize?: number;
  };

  type pageSimpleDevelopParams = {
    simpleCmdDevelopConfQo: SimpleCmdDevelopConfQo;
  };

  type pageWebhooksConfParams = {
    webhooksConfQo: WebhooksConfQo;
  };

  type PostLogQo = {
    current?: number;
    pageSize?: number;
    message?: string;
    platform?: string;
  };

  type QuartzJob = {
    id?: number;
    jobName?: string;
    cronExpression?: string;
    jobClass?: string;
    jobParam?: string;
    status?: boolean;
    remark?: string;
  };

  type RBoolean = {
    code?: number;
    data?: boolean;
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: number;
  };

  type RBotInfo = {
    code?: number;
    data?: BotInfo;
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

  type RCmpStepResult = {
    code?: number;
    data?: CmpStepResult;
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

  type removeBotConfParams = {
    id: number;
  };

  type removeHighLevelDevParams = {
    id: number;
  };

  type removeJobParams = {
    id: number;
  };

  type removeNodeConfParams = {
    id: number;
  };

  type removeSimpleDevelopParams = {
    id: number;
  };

  type removeSysNodeParams = {
    id: Record<string, any>;
  };

  type removeWebhooksConfParams = {
    id: number;
  };

  type RHighLevelDevelopConf = {
    code?: number;
    data?: HighLevelDevelopConf;
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: number;
  };

  type RListBotConf = {
    code?: number;
    data?: BotConf[];
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: number;
  };

  type RListBotInfo = {
    code?: number;
    data?: BotInfo[];
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: number;
  };

  type RListBotMessage = {
    code?: number;
    data?: BotMessage[];
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: number;
  };

  type RListBotNodeConfExecuteLog = {
    code?: number;
    data?: BotNodeConfExecuteLog[];
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: number;
  };

  type RListBotNodeExecuteLog = {
    code?: number;
    data?: BotNodeExecuteLog[];
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: number;
  };

  type RListBotNotice = {
    code?: number;
    data?: BotNotice[];
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: number;
  };

  type RListBotPostLog = {
    code?: number;
    data?: BotPostLog[];
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: number;
  };

  type RListBotRequest = {
    code?: number;
    data?: BotRequest[];
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: number;
  };

  type RListCmpStepResult = {
    code?: number;
    data?: CmpStepResult[];
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: number;
  };

  type RListHighLevelDevelopConf = {
    code?: number;
    data?: HighLevelDevelopConf[];
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

  type RListLowCodeNodeConf = {
    code?: number;
    data?: LowCodeNodeConf[];
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: number;
  };

  type RListLowCodeSysNode = {
    code?: number;
    data?: LowCodeSysNode[];
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: number;
  };

  type RListQuartzJob = {
    code?: number;
    data?: QuartzJob[];
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: number;
  };

  type RListSimpleCmdDevelopConf = {
    code?: number;
    data?: SimpleCmdDevelopConf[];
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: number;
  };

  type RListSystemConf = {
    code?: number;
    data?: SystemConf[];
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: number;
  };

  type RListWebhooksConf = {
    code?: number;
    data?: WebhooksConf[];
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: number;
  };

  type RLiteFlowScript = {
    code?: number;
    data?: LiteFlowScript;
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

  type RLowCodeSysNode = {
    code?: number;
    data?: LowCodeSysNode;
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: number;
  };

  type RMapStringListLowCodeSysNode = {
    code?: number;
    data?: Record<string, any>;
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: number;
  };

  type RMapStringListNodePAVo = {
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

  type RQuartzJob = {
    code?: number;
    data?: QuartzJob;
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: number;
  };

  type RSimpleCmdDevelopConf = {
    code?: number;
    data?: SimpleCmdDevelopConf;
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

  type RSystemConf = {
    code?: number;
    data?: SystemConf;
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: number;
  };

  type runOneJobParams = {
    id: number;
  };

  type RWebhooksConf = {
    code?: number;
    data?: WebhooksConf;
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: number;
  };

  type SandboxMessage = {
    messageId?: string;
    isSelf?: boolean;
    type?: string;
    message?: string;
    time?: string;
    confId?: number;
    confType?: string;
  };

  type SetBotConfIdDto = {
    botId?: number;
    confIdList?: number[];
  };

  type SimpleCmdDevelopConf = {
    id?: number;
    confName?: string;
    cmd?: string;
    cmdType?: string;
    remark?: string;
    scriptLanguage?: string;
    script?: LiteFlowScript;
  };

  type SimpleCmdDevelopConfQo = {
    current?: number;
    pageSize?: number;
    confName?: string;
    cmd?: string;
    cmdType?: string;
    remark?: string;
  };

  type startJobParams = {
    id: number;
  };

  type stopJobParams = {
    id: number;
  };

  type SysNodeQo = {
    current?: number;
    pageSize?: number;
    nodeName?: string;
    groupType?: string;
    nodeCode?: string;
    remark?: string;
    systemNode?: boolean;
  };

  type SystemConf = {
    id?: number;
    confName?: string;
    confType?: number;
    confData?: string;
    confKey?: string;
    remark?: string;
    isSystemConf?: boolean;
  };

  type SystemConfigQo = {
    current?: number;
    pageSize?: number;
    confName?: string;
    confType?: number;
    confKey?: string;
  };

  type wcfPost2Params = {
    token: string;
  };

  type wcfPost3Params = {
    token: string;
  };

  type wcfPost4Params = {
    token: string;
  };

  type wcfPost5Params = {
    token: string;
  };

  type wcfPostParams = {
    postData: JSONObject;
    token: string;
  };

  type WebhooksConf = {
    id?: number;
    confName?: string;
    remark?: string;
    scriptLanguage?: string;
    script?: LiteFlowScript;
  };

  type WebhooksConfQo = {
    current?: number;
    pageSize?: number;
    id?: number;
    confName?: string;
    remark?: string;
  };
}
