// @ts-ignore
/* eslint-disable */
import { request } from 'umi';

/** 调试配置 POST /api/simpleCmdDevelop/debug */
export async function debugSimpleCmdDevelop(body: API.DebugDto, options?: { [key: string]: any }) {
  return request<API.RCmpStepResult>('/api/simpleCmdDevelop/debug', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 GET /api/simpleCmdDevelop/getInfo/${param0} */
export async function getSimpleDevelopInfo(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getSimpleDevelopInfoParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.RSimpleCmdDevelopConf>(`/api/simpleCmdDevelop/getInfo/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 导入配置 POST /api/simpleCmdDevelop/importConf */
export async function importSimpleCmdDevelop(body: {}, options?: { [key: string]: any }) {
  return request<API.RLong>('/api/simpleCmdDevelop/importConf', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 所有列表 GET /api/simpleCmdDevelop/list */
export async function listSimpleDevelop(options?: { [key: string]: any }) {
  return request<API.RListSimpleCmdDevelopConf>('/api/simpleCmdDevelop/list', {
    method: 'GET',
    ...(options || {}),
  });
}

/** 分页查询 GET /api/simpleCmdDevelop/page */
export async function pageSimpleDevelop(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.pageSimpleDevelopParams,
  options?: { [key: string]: any },
) {
  return request<API.RListSimpleCmdDevelopConf>('/api/simpleCmdDevelop/page', {
    method: 'GET',
    params: {
      ...params,
      pageQo: undefined,
      ...params['pageQo'],
    },
    ...(options || {}),
  });
}

/** 删除 DELETE /api/simpleCmdDevelop/remove/${param0} */
export async function removeSimpleDevelop(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.removeSimpleDevelopParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.RBoolean>(`/api/simpleCmdDevelop/remove/${param0}`, {
    method: 'DELETE',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 添加 POST /api/simpleCmdDevelop/save */
export async function saveSimpleDevelop(
  body: API.SimpleCmdDevelopConf,
  options?: { [key: string]: any },
) {
  return request<API.RBoolean>('/api/simpleCmdDevelop/save', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 更新 PUT /api/simpleCmdDevelop/update */
export async function updateSimpleDevelop(
  body: API.SimpleCmdDevelopConf,
  options?: { [key: string]: any },
) {
  return request<API.RBoolean>('/api/simpleCmdDevelop/update', {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}
