// @ts-ignore
/* eslint-disable */
import { request } from 'umi';

/** 聊天 GET /api/ollama/ai/chat */
export async function chat(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.chatParams,
  options?: { [key: string]: any },
) {
  return request<API.RString>('/api/ollama/ai/chat', {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}
