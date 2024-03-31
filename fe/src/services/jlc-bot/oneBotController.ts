// @ts-ignore
/* eslint-disable */
import { request } from 'umi';

/** 此处后端没有提供注释 POST /api/oneBot/v11/post */
export async function v11Post(options?: { [key: string]: any }) {
  return request<any>('/api/oneBot/v11/post', {
    method: 'POST',
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 GET /api/oneBot/wcfPost */
export async function wcfPost(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.wcfPostParams,
  options?: { [key: string]: any },
) {
  return request<API.RBoolean>('/api/oneBot/wcfPost', {
    method: 'GET',
    params: {
      ...params,
      postData: undefined,
      ...params['postData'],
    },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 PUT /api/oneBot/wcfPost */
export async function wcfPost3(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.wcfPost3Params,
  body: API.JSONObject,
  options?: { [key: string]: any },
) {
  return request<API.RBoolean>('/api/oneBot/wcfPost', {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
    },
    params: {
      ...params,
    },
    data: body,
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 POST /api/oneBot/wcfPost */
export async function wcfPost2(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.wcfPost2Params,
  body: API.JSONObject,
  options?: { [key: string]: any },
) {
  return request<API.RBoolean>('/api/oneBot/wcfPost', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    params: {
      ...params,
    },
    data: body,
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 DELETE /api/oneBot/wcfPost */
export async function wcfPost5(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.wcfPost5Params,
  body: API.JSONObject,
  options?: { [key: string]: any },
) {
  return request<API.RBoolean>('/api/oneBot/wcfPost', {
    method: 'DELETE',
    headers: {
      'Content-Type': 'application/json',
    },
    params: {
      ...params,
    },
    data: body,
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 PATCH /api/oneBot/wcfPost */
export async function wcfPost4(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.wcfPost4Params,
  body: API.JSONObject,
  options?: { [key: string]: any },
) {
  return request<API.RBoolean>('/api/oneBot/wcfPost', {
    method: 'PATCH',
    headers: {
      'Content-Type': 'application/json',
    },
    params: {
      ...params,
    },
    data: body,
    ...(options || {}),
  });
}
