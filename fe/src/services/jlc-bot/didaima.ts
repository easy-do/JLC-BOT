// @ts-ignore
/* eslint-disable */
import { request } from 'umi';

/** 复制节点配置 GET /api/lowcode/copyNodeConf/${param0} */
export async function copyNodeConf(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.copyNodeConfParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.RLong>(`/api/lowcode/copyNodeConf/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 调试节点配置 POST /api/lowcode/debugNodeConf */
export async function debugNodeConf(body: API.DebugDto, options?: { [key: string]: any }) {
  return request<API.RListCmpStepResult>('/api/lowcode/debugNodeConf', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 获取机器人的节点配置 GET /api/lowcode/getBotNode/${param0} */
export async function getBotNode(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getBotNodeParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.RListLong>(`/api/lowcode/getBotNode/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 获取节点配置 GET /api/lowcode/getNodeConf/${param0} */
export async function getNodeConf(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getNodeConfParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.RBotNodeDto>(`/api/lowcode/getNodeConf/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 导入节点配置 POST /api/lowcode/importNodeConf */
export async function importNodeConf(body: {}, options?: { [key: string]: any }) {
  return request<API.RLong>('/api/lowcode/importNodeConf', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 所有节点配置 POST /api/lowcode/listNodeConf */
export async function listNodeConf(options?: { [key: string]: any }) {
  return request<API.RListLowCodeNodeConf>('/api/lowcode/listNodeConf', {
    method: 'POST',
    ...(options || {}),
  });
}

/** 分页查询节点配置 POST /api/lowcode/pageNodeConf */
export async function pageNodeConf(body: API.PageQo, options?: { [key: string]: any }) {
  return request<API.RListLowCodeNodeConf>('/api/lowcode/pageNodeConf', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 删除节点配置 GET /api/lowcode/removeNodeConf/${param0} */
export async function removeNodeConf(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.removeNodeConfParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.RBoolean>(`/api/lowcode/removeNodeConf/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 保存节点配置 POST /api/lowcode/saveNodeConf */
export async function saveNodeConf(body: API.BotNodeDto, options?: { [key: string]: any }) {
  return request<API.RLong>('/api/lowcode/saveNodeConf', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 设置机器人与节点配置关联关系 POST /api/lowcode/setBotNode */
export async function setBotNode(body: API.SetBotConfIdDto, options?: { [key: string]: any }) {
  return request<API.RBoolean>('/api/lowcode/setBotNode', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 更新节点配置 POST /api/lowcode/updateNodeConf */
export async function updateNodeConf(body: API.BotNodeDto, options?: { [key: string]: any }) {
  return request<API.RBoolean>('/api/lowcode/updateNodeConf', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}
