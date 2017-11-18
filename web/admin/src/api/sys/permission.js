import fetch from '@/utils/fetch'

export function getList(params) {
  return fetch({
    url: '/api/permissions',
    method: 'get',
    params
  })
}

export function createPermission(permission){
  var name = permission.name;
  var detail = permission.detail;
  var parent_id = permission.parent_id;
  return fetch({
    url: '/api/permission',
    method: 'post',
    data: {
      name,
      detail,
      parent_id,
    }
  })
}

export function deletePermission(permission){
  var id = permission.id;
  return fetch({
    url: `/api/permission/id/${id}`,
    method: 'delete',
    data: {
    }
  })
}

export function updatePermission(permission){
  var id = permission.id;
  var name = permission.name;
  var detail = permission.detail;
  return fetch({
    url: '/api/permission',
    method: 'put',
    data: {
      id,
      name,
      detail,
    }
  })
}


export function queryPermission(permission){
  var id = permission.id;
  return fetch({
    url: `/api/permission/id/${id}`,
    method: 'get',
    data: {
    }
  })
}