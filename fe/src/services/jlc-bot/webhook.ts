// @ts-ignore
/* eslint-disable */
import { request } from 'umi';

/** webhook get请求 GET /api/wh/get/${param0}/${param1} */
export async function hookGet(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.hookGetParams,
  options?: { [key: string]: any },
) {
  const { action: param0, secret: param1, ...queryParams } = params;
  return request<API.RObject>(`/api/wh/get/${param0}/${param1}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** webhook post请求 GET /api/wh/post/${param0}/${param1} */
export async function hookPost(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.hookPostParams,
  options?: { [key: string]: any },
) {
  const { action: param0, secret: param1, ...queryParams } = params;
  return request<API.RObject>(`/api/wh/post/${param0}/${param1}`, {
    method: 'GET',
    params: {
      ...queryParams,
      paramJson: undefined,
      ...queryParams['paramJson'],
    },
    ...(options || {}),
  });
}
