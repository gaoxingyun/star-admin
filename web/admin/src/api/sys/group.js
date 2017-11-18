import fetch from '@/utils/fetch'

export function getList(params) {
  return fetch({
    url: '/api/groups',
    method: 'get',
    params
  })
}

export function createGroup(group) {
  var name = group.name;
  var detail = group.detail;
  var parent_id = group.parent_id;
  return fetch({
    url: '/api/group',
    method: 'post',
    data: {
      name,
      detail,
      parent_id
    }
  })
}

export function deleteGroup(group) {
  var id = group.id;
  return fetch({
    url: `/api/group/id/${id}`,
    method: 'delete',
    data: {
    }
  })
}

export function updateGroup(group) {
  var id = group.id;
  return fetch({
    url: '/api/group',
    method: 'put',
    data: {
      id
    }
  })
}

export function queryGroup(group) {
  return fetch({
    url: `/api/group/id/${group.id}`,
    method: 'get',
    data: {
    }
  })
}