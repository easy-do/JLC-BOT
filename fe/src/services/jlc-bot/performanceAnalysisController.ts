// @ts-ignore
/* eslint-disable */
import { request } from 'umi';

/** 所有数据 GET /api/pa/nodeExecute */
export async function nodeExecute(options?: { [key: string]: any }) {
  return request<API.RMapStringListNodePAVo>('/api/pa/nodeExecute', {
    method: 'GET',
    ...(options || {}),
  });
}
