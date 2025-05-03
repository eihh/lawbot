import axios from "axios";
let httpUrl = "http://localhost:8080"; // 注意这里结尾没有/ 所以请求的时候都要加/



axios.defaults.headers.post["Content-Type"] = "application/json;charset=utf-8";
axios.interceptors.request.use(
  function (config) {
    config.params = {
      ...config.params,
    }
    return config;
  },
  function (error) {
    return Promise.reject(error);
  }
);
// 响应拦截器
axios.interceptors.response.use(
  function (response) {
    return response;
  },
  function (error) {
    if (axios.isCancel(error)) {
      return new Promise(() => { })
    } else {
      return Promise.reject(error);
    }

  }
);

const test = {
  post(url, data) {
    return axios.post(`${httpUrl}${url}`, data);
  },
  delete(url, data) {
    return axios({
      method: "delete",
      url: `${httpUrl}${url}`,
      data: data,
      timeout: 8000,
    });
  },

  put(url, data) {
    return axios({
      method: "put",
      url: `${httpUrl}${url}`,
      data: data,
      timeout: 8000,
    });
  },
  get(url, data) {
    return axios.get(`${httpUrl}${url}`, {
      params: data,
      
    });
  },
  httpUrl,
};

export default test;
