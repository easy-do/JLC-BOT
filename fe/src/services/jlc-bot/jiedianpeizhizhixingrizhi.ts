// @ts-ignore
/* eslint-disable */
import { request } from 'umi';

/** 清空 GET /api/nodeConfExecuteLog/clean */
export async function cleanNodeConfExecuteLog(options?: { [key: string]: any }) {
  return request<API.RBoolean>('/api/nodeConfExecuteLog/clean', {
    method: 'GET',
    ...(options || {}),
  });
}

/** 分页查询 POST /api/nodeConfExecuteLog/page */
export async function pageNodeConfExecuteLog(body: API.PageQo, options?: { [key: string]: any }) {
  return request<API.RListBotNodeConfExecuteLog>('/api/nodeConfExecuteLog/page', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}
