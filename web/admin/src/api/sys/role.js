import fetch from '@/utils/fetch'

export function getList(params) {
  return fetch({
    url: '/api/roles',
    method: 'get',
    params
  })
}

export function createRole(role){
  var name = role.name;
  var detail = role.detail;
  var parent_id = role.parent_id;
  return fetch({
    url: '/api/role',
    method: 'post',
    data: {
      name,
      detail,
      parent_id
    }
  })
}

export function deleteRole(role){
  var id = role.id;
  return fetch({
    url: `/api/role/name/${role.name}`,
    method: 'delete',
    data: {
    }
  })
}

export function updateRole(role){
  var id = role.id;
  return fetch({
    url: '/api/role',
    method: 'put',
    data: {
      id,
    }
  })
}


export function queryRole(role){
  var id = role.id;
  return fetch({
    url: `/api/role/id/${role.id}`,
    method: 'get',
    data: {
    }
  })
}