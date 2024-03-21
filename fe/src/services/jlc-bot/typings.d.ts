declare namespace API {
  type BotNodeDto = {
    id?: number;
    eventType?: string;
    confName?: string;
    nodes?: Record<string, any>;
    nodeConf?: Record<string, any>;
  };

  type CurrentUser = {
    userName?: string;
    menu?: Record<string, any>[];
    resource?: Record<string, any>[];
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

  type DaGameConfigQo = {
    current?: number;
    pageSize?: number;
    confName?: string;
    confType?: number;
    confKey?: string;
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

  type DebugBotNodeDto = {
    id?: number;
    params?: Record<string, any>;
  };

  type EnableBotScriptDto = {
    botId?: number;
    scriptIds?: number[];
  };

  type getBotConfParams = {
    botNumber: string;
  };

  type getBotNodeParams = {
    id: number;
  };

  type getConfInfoParams = {
    id: Record<string, any>;
  };

  type getEnableBotScriptParams = {
    id: number;
  };

  type getNodeConfParams = {
    id: number;
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

  type LoginDto = {
    userName: string;
    password: string;
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

  type PageQo = {
    current?: number;
    pageSize?: number;
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

  type RDaLowCodeSysNode = {
    code?: number;
    data?: DaLowCodeSysNode;
    message?: string;
    errorMessage?: string;
    success?: boolean;
    total?: number;
  };

  type removeBotConfParams = {
    id: number;
  };

  type removeNodeConfParams = {
    id: number;
  };

  type removeSysNodeParams = {
    id: Record<string, any>;
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

  type RListSystemConf = {
    code?: number;
    data?: SystemConf[];
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

  type SetBotNodeDto = {
    botId?: number;
    confIdList?: number[];
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
}
