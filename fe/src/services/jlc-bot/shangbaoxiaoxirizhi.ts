// @ts-ignore
/* eslint-disable */
import { request } from 'umi';

/** 清空 GET /api/botPostLog/clean */
export async function cleanPostLog(options?: { [key: string]: any }) {
  return request<API.RBoolean>('/api/botPostLog/clean', {
    method: 'GET',
    ...(options || {}),
  });
}

/** 分页查询 POST /api/botPostLog/page */
export async function pagePostLog(body: API.PostLogQo, options?: { [key: string]: any }) {
  return request<API.RListBotPostLog>('/api/botPostLog/page', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}
