// @ts-ignore
/* eslint-disable */
import { request } from 'umi';

/** 调试配置 POST /api/highLevelDevelop/debug */
export async function debugHighLevelDevelop(body: API.DebugDto, options?: { [key: string]: any }) {
  return request<API.RCmpStepResult>('/api/highLevelDevelop/debug', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 获取机器人的节点配置 GET /api/highLevelDevelop/getBotHighLevelDevelop/${param0} */
export async function getBotHighLevelDevelop(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getBotHighLevelDevelopParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.RListLong>(`/api/highLevelDevelop/getBotHighLevelDevelop/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 GET /api/highLevelDevelop/getInfo/${param0} */
export async function getHighLevelDevInfo(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getHighLevelDevInfoParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.RHighLevelDevelopConf>(`/api/highLevelDevelop/getInfo/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 导入配置 POST /api/highLevelDevelop/importConf */
export async function importHighLevelDevelop(body: {}, options?: { [key: string]: any }) {
  return request<API.RLong>('/api/highLevelDevelop/importConf', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 所有列表 GET /api/highLevelDevelop/list */
export async function highLevelDevList(options?: { [key: string]: any }) {
  return request<API.RListHighLevelDevelopConf>('/api/highLevelDevelop/list', {
    method: 'GET',
    ...(options || {}),
  });
}

/** 分页查询 GET /api/highLevelDevelop/page */
export async function highLevelDevPage(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.highLevelDevPageParams,
  options?: { [key: string]: any },
) {
  return request<API.RListHighLevelDevelopConf>('/api/highLevelDevelop/page', {
    method: 'GET',
    params: {
      ...params,
      pageQo: undefined,
      ...params['pageQo'],
    },
    ...(options || {}),
  });
}

/** 删除 GET /api/highLevelDevelop/remove/${param0} */
export async function removeHighLevelDev(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.removeHighLevelDevParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.RBoolean>(`/api/highLevelDevelop/remove/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 添加 POST /api/highLevelDevelop/save */
export async function saveHighLevelDev(
  body: API.HighLevelDevelopConf,
  options?: { [key: string]: any },
) {
  return request<API.RBoolean>('/api/highLevelDevelop/save', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 设置机器人与节点配置关联关系 POST /api/highLevelDevelop/setBotHighLevelDevelop */
export async function setBotHighLevelDevelop(
  body: API.SetBotConfIdDto,
  options?: { [key: string]: any },
) {
  return request<API.RBoolean>('/api/highLevelDevelop/setBotHighLevelDevelop', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 更新 PUT /api/highLevelDevelop/update */
export async function updateHighLevelDev(
  body: API.HighLevelDevelopConf,
  options?: { [key: string]: any },
) {
  return request<API.RBoolean>('/api/highLevelDevelop/update', {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}
