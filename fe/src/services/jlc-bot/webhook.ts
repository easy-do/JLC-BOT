// @ts-ignore
/* eslint-disable */
import { request } from 'umi';

/** webhook post请求 GET /api/webhooks/call/${param1}/${param0} */
export async function hookPost(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.hookPostParams,
  options?: { [key: string]: any },
) {
  const { action: param0, secret: param1, ...queryParams } = params;
  return request<API.RObject>(`/api/webhooks/call/${param1}/${param0}`, {
    method: 'GET',
    params: {
      ...queryParams,
      paramsJson: undefined,
      ...queryParams['paramsJson'],
    },
    ...(options || {}),
  });
}

/** 调试配置 POST /api/webhooks/debug */
export async function debugWebhooks(body: API.DebugDto, options?: { [key: string]: any }) {
  return request<API.RCmpStepResult>('/api/webhooks/debug', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 生成hookUrl GET /api/webhooks/generateHookUrl/${param0}/${param1} */
export async function generateHookUrl(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.generateHookUrlParams,
  options?: { [key: string]: any },
) {
  const { botId: param0, confId: param1, ...queryParams } = params;
  return request<API.RString>(`/api/webhooks/generateHookUrl/${param0}/${param1}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 详情 GET /api/webhooks/getInfo/${param0} */
export async function getWebhooksConfInfo(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getWebhooksConfInfoParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.RWebhooksConf>(`/api/webhooks/getInfo/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 查询所有 GET /api/webhooks/list */
export async function listWebhooksConf(options?: { [key: string]: any }) {
  return request<API.RListWebhooksConf>('/api/webhooks/list', {
    method: 'GET',
    ...(options || {}),
  });
}

/** 分页查询 GET /api/webhooks/page */
export async function pageWebhooksConf(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.pageWebhooksConfParams,
  options?: { [key: string]: any },
) {
  return request<API.RListWebhooksConf>('/api/webhooks/page', {
    method: 'GET',
    params: {
      ...params,
      webhooksConfQo: undefined,
      ...params['webhooksConfQo'],
    },
    ...(options || {}),
  });
}

/** 根据主键删除 GET /api/webhooks/remove/${param0} */
export async function removeWebhooksConf(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.removeWebhooksConfParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.RBoolean>(`/api/webhooks/remove/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 添加 POST /api/webhooks/save */
export async function saveWebhooksConf(body: API.WebhooksConf, options?: { [key: string]: any }) {
  return request<API.RBoolean>('/api/webhooks/save', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 根据主键更新 POST /api/webhooks/update */
export async function updateWebhooksConf(body: API.WebhooksConf, options?: { [key: string]: any }) {
  return request<API.RBoolean>('/api/webhooks/update', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}
