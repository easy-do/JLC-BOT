// @ts-ignore
/* eslint-disable */
import { request } from 'umi';

/** 创建任务 POST /api/systemJob/addJob */
export async function addJob(body: API.QuartzJob, options?: { [key: string]: any }) {
  return request<API.RBoolean>('/api/systemJob/addJob', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 任务详情 GET /api/systemJob/info/${param0} */
export async function jobInfo(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.jobInfoParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.RQuartzJob>(`/api/systemJob/info/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 分页查询任务 POST /api/systemJob/page */
export async function pageJob(body: API.PageQo, options?: { [key: string]: any }) {
  return request<API.RListQuartzJob>('/api/systemJob/page', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 删除任务 GET /api/systemJob/remove/${param0} */
export async function removeJob(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.removeJobParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.RBoolean>(`/api/systemJob/remove/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 执行一次 GET /api/systemJob/runOne/${param0} */
export async function runOneJob(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.runOneJobParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.RBoolean>(`/api/systemJob/runOne/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 启动任务 GET /api/systemJob/start/${param0} */
export async function startJob(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.startJobParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.RBoolean>(`/api/systemJob/start/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 停止任务 GET /api/systemJob/stop/${param0} */
export async function stopJob(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.stopJobParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.RBoolean>(`/api/systemJob/stop/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 更新任务 POST /api/systemJob/update */
export async function updateJob(body: API.QuartzJob, options?: { [key: string]: any }) {
  return request<API.RBoolean>('/api/systemJob/update', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}
