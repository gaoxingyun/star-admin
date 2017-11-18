import fetch from '@/utils/fetch'

export function getList(params) {
  return fetch({
    url: '/api/users',
    method: 'get',
    params
  })
}

export function createUser(user){
  var name = user.name;
  var password = user.password;
  var status = user.status;
  return fetch({
    url: '/api/user',
    method: 'post',
    data: {
      name,
      password,
      status
    }
  })
}

export function deleteUser(user){
  var name = user.name;
  return fetch({
    url: `/api/user/name/${user.name}`,
    method: 'delete',
    data: {
    }
  })
}

export function updateUser(user){
  var name = user.name;
  var status = user.status;
  return fetch({
    url: '/api/user',
    method: 'put',
    data: {
      name,
      status
    }
  })
}


export function queryUser(user){
  var name = user.name;
  return fetch({
    url: `/api/user/name/${user.name}`,
    method: 'get',
    data: {
    }
  })
}