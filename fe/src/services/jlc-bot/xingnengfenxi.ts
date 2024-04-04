// @ts-ignore
/* eslint-disable */
import { request } from 'umi';

/** 节点执行分析 GET /api/pa/nodeExecutePa */
export async function nodeExecutePa(options?: { [key: string]: any }) {
  return request<API.RMapStringListNodePAVo>('/api/pa/nodeExecutePa', {
    method: 'GET',
    ...(options || {}),
  });
}
