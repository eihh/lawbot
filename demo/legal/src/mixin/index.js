import api from '../axios/awaitapi'
const mixin = {
  data() {
    return {
   
    };
  },

methods: {
async  quanxian(a){
    let s2 = await api.get("/api/media_employee_role/getRoleByemployeeid", {
      employeeid: a,
    });

    let s1 = await api.get(
      "/api/media_role_permission/getPermissionsByRoleid",
      {
        roleid: s2.data.data.list[0].roleid,
      }
    );
    return s1
  },
  getDanhao() {
    var data = new Date();
    var year = data.getFullYear();
    var month = data.getMonth() + 1;
    var day = data.getDate();
    var h = data.getHours();
    h = h < 10 ? "0" + h : h;
    var m = data.getMinutes();
    m = m < 10 ? "0" + m : m;
    var s = data.getSeconds();
    s = s < 10 ? "0" + s : s;
    return `${year}${month}${day}${h}${m}${s}${
      JSON.parse(sessionStorage.getItem("user")).user.employeeid
    }${Math.random().toString(36).substr(2, 4)}`;
  },

  getuser() {
    return JSON.parse(sessionStorage.getItem("user")).user;
  },
  getdd(a) {
    // 把时间戳转换为时间
    var data;
    if (a == undefined) {
      a = {};
      data = new Date();
    } else {
      data = new Date(a.time);
    }
    var year = data.getFullYear();
    var month = data.getMonth() + 1;
    month = month < 10 ? "0" + month : month;
    var day = data.getDate();
    day = day < 10 ? "0" + day : day;
    var h = data.getHours();
    h = h < 10 ? "0" + h : h;
    var m = data.getMinutes();
    m = m < 10 ? "0" + m : m;
    var s = data.getSeconds();
    s = s < 10 ? "0" + s : s;

    if (a.type == "详细时间") {
      return `${year}-${month}-${day} ${h}:${m}:${s}`;
    } else if (a.type == "零点") {
      return `${year}-${month}-${day} 00:00:00`;
    } else {
      return `${year}-${month}-${day} ${h}:${m}:${s}`;
    }
  },
  getNowDate(a) {
    var that = this;
    if (a == undefined) a = {};
    var oneYear = 1000 * 60 * 60 * 24 * 30 * 12;
    var oneMonth = 1000 * 60 * 60 * 24 * 30;
    var oneDay = 1000 * 60 * 60 * 24;
    function get(time) {
      if (!time && a.type) {
        // 如果只传类型没传时间
        return that.getdd({ time: +new Date(), type: a.type });
      } else if (time && a.type) {
        // 如果传了时间和类型
        return that.getdd({ time, type: a.type });
      } else if (time) {
        // 如果传了时间 没传类型
        return that.getdd({ time });
      } else {
        return that.getdd(); // 啥都不传返回当前详细时间
      }
    }
    if (a.year > 0) {
      let time = +new Date() + oneYear * a.year;
      return get(time);
    } else if (a.year < 0) {
      let time = +new Date() + oneYear * a.year;
      return get(time);
    } else if (a.month > 0) {
      let time = +new Date() + oneMonth * a.month;
      return get(time);
    } else if (a.month < 0) {
      let time = +new Date() + oneMonth * a.month;
      return get(time);
    } else if (a.day > 0) {
      let time = +new Date() + oneDay * a.day;
      return get(time);
    } else if (a.day < 0) {
      let time = +new Date() + oneDay * a.day;
      return get(time);
    } else {
      return get();
    }
  },
},
};

export default mixin;
