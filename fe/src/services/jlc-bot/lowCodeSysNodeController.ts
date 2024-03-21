// @ts-ignore
/* eslint-disable */
import { request } from 'umi';

/** 详细信息 GET /api/sysNode/info/${param0} */
export async function getSysNodeInfo(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getSysNodeInfoParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.RDaLowCodeSysNode>(`/api/sysNode/info/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 所有数据 GET /api/sysNode/list */
export async function listSysNode(options?: { [key: string]: any }) {
  return request<API.RMapStringListDaLowCodeSysNode>('/api/sysNode/list', {
    method: 'GET',
    ...(options || {}),
  });
}

/** 分页 POST /api/sysNode/page */
export async function pageSysNode(body: API.PageQo, options?: { [key: string]: any }) {
  return request<API.RListDaLowCodeSysNode>('/api/sysNode/page', {
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
export async function saveSysNode(body: API.DaLowCodeSysNode, options?: { [key: string]: any }) {
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
export async function updateSysNode(body: API.DaLowCodeSysNode, options?: { [key: string]: any }) {
  return request<API.RBoolean>('/api/sysNode/update', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}
