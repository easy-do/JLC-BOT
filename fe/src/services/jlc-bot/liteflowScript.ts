// @ts-ignore
/* eslint-disable */
import { request } from 'umi';

/** 更新脚本配置 POST /api/fls/update */
export async function updateLiteFlowScript(
  body: API.LiteFlowScript,
  options?: { [key: string]: any },
) {
  return request<API.RBoolean>('/api/fls/update', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}
