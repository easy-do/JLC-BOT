// @ts-ignore
/* eslint-disable */
import { request } from 'umi';

/** 获取脚本信息 GET /api/sysNode/getScriptData/${param0} */
export async function getSysNodeScriptData(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getSysNodeScriptDataParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.RLiteFlowScript>(`/api/sysNode/getScriptData/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 详细信息 GET /api/sysNode/info/${param0} */
export async function getSysNodeInfo(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getSysNodeInfoParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.RLowCodeSysNode>(`/api/sysNode/info/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 所有数据 GET /api/sysNode/list */
export async function listSysNode(options?: { [key: string]: any }) {
  return request<API.RMapStringListLowCodeSysNode>('/api/sysNode/list', {
    method: 'GET',
    ...(options || {}),
  });
}

/** 分页 POST /api/sysNode/page */
export async function pageSysNode(body: API.PageQo, options?: { [key: string]: any }) {
  return request<API.RListLowCodeSysNode>('/api/sysNode/page', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 删除 GET /api/sysNode/remove/${param0} */
export async function removeSysNode(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.removeSysNodeParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.RBoolean>(`/api/sysNode/remove/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 添加 POST /api/sysNode/save */
export async function saveSysNode(body: API.LowCodeSysNode, options?: { [key: string]: any }) {
  return request<API.RBoolean>('/api/sysNode/save', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 更新 POST /api/sysNode/update */
export async function updateSysNode(body: API.LowCodeSysNode, options?: { [key: string]: any }) {
  return request<API.RBoolean>('/api/sysNode/update', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 更新表单配置 POST /api/sysNode/updateFormData */
export async function updateSysNodeFormData(
  body: API.LowCodeSysNode,
  options?: { [key: string]: any },
) {
  return request<API.RBoolean>('/api/sysNode/updateFormData', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 更新脚本配置 POST /api/sysNode/updateScriptData */
export async function updateSysNodeScriptData(
  body: API.LiteFlowScript,
  options?: { [key: string]: any },
) {
  return request<API.RBoolean>('/api/sysNode/updateScriptData', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}
