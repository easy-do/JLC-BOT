// @ts-ignore
/* eslint-disable */
import { request } from 'umi';

/** 清空 GET /api/nodeExecuteLog/clean */
export async function cleanNodeExecuteLog(options?: { [key: string]: any }) {
  return request<API.RBoolean>('/api/nodeExecuteLog/clean', {
    method: 'GET',
    ...(options || {}),
  });
}

/** 分页查询 POST /api/nodeExecuteLog/page */
export async function pageNodeExecuteLog(body: API.PageQo, options?: { [key: string]: any }) {
  return request<API.RListBotNodeExecuteLog>('/api/nodeExecuteLog/page', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}
