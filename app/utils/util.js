// 引入请求模块
const request = require('./request')


const formatTime = date => {
  const year = date.getFullYear()
  const month = date.getMonth() + 1
  const day = date.getDate()
  const hour = date.getHours()
  const minute = date.getMinutes()
  const second = date.getSeconds()

  return [year, month, day].map(formatNumber).join('/') + ' ' + [hour, minute, second].map(formatNumber).join(':')
}

const formatNumber = n => {
  n = n.toString()
  return n[1] ? n : '0' + n
}


const transToCnDay = (n) => {
  switch (n) {
    case 1:
      return '一'
      break;
    case 2:
      return '二'
      break;
    case 3:
      return '三'
      break;
    case 4:
      return '四'
      break;
    case 5:
      return '五'
      break;
    case 6:
      return '六'
      break;
    case 0:
      return '日'
      break;
  }
}

function now() {
  return +new Date()
}

const debounce = (func, wait = 500, immediate = false) => {
  var timeout;
  return function () {
      var context = this;
      var args = arguments;

      if (timeout) clearTimeout(timeout);
      if (immediate) {
          // 如果已经执行过，不再执行
          var callNow = !timeout;
          timeout = setTimeout(function(){
              timeout = null;
          }, wait)
          if (callNow) func.apply(context, args)
      }
      else {
          timeout = setTimeout(function(){
              func.apply(context, args)
          }, wait);
      }
  }
}

const throttle = (fn, wait) => {
  let timer = null,
    previous = 0,
    ctx,
    args;

  function later() {
    previous = +new Date();
    timer = null;
    fn.apply(ctx, args);
  }

  let throttled = function() {
    let now = +new Date();
    ctx = this,
      args = [...arguments];
    // 剩余时间
    let remaining = wait - (now - previous);
    if (remaining <= 0 || remaining > wait) {
      // 到了执行的时间或者是系统时间被修改
      if (timer) {
        clearTimeout(timer);
        timer = null;
      }
      previous = now;
      fn.call(ctx, ...args);
    } else if (!timer) {
      timer = setTimeout(later, remaining)
    }
  }

  return throttled;
}

const countDate = (date1, date2) => {
  var date = Math.floor((date2.getTime() - date1.getTime()) / (1000 * 60 * 60 * 24)); /*不用考虑闰年否*/
  return date
}

//计算两个日期相差的天数
function DateDiff(sDate1, sDate2) {
  var aDate, oDate1, oDate2, iDays;
  aDate = sDate1.split("-");
  oDate1 = new Date(aDate[0] + '-' + aDate[1] + '-' + aDate[2]);
  aDate = sDate2.split("-");
  oDate2 = new Date(aDate[0] + '-' + aDate[1] + '-' + aDate[2]);

  iDays = parseInt(Math.abs(oDate1 - oDate2) / 1000 / 60 / 60 / 24);
  return iDays;
}
//验证身份证号码格式是否正确
function isCardID(sId) {
  var aCity = {
    11: "北京",
    12: "天津",
    13: "河北",
    14: "山西",
    15: "内蒙古",
    21: "辽宁",
    22: "吉林",
    23: "黑龙江",
    31: "上海",
    32: "江苏",
    33: "浙江",
    34: "安徽",
    35: "福建",
    36: "江西",
    37: "山东",
    41: "河南",
    42: "湖北",
    43: "湖南",
    44: "广东",
    45: "广西",
    46: "海南",
    50: "重庆",
    51: "四川",
    52: "贵州",
    53: "云南",
    54: "西藏",
    61: "陕西",
    62: "甘肃",
    63: "青海",
    64: "宁夏",
    65: "新疆",
    71: "台湾",
    81: "香港",
    82: "澳门",
    91: "国外"
  }
  var iSum = 0;
  var info = "";
  if (!/^\d{17}(\d|x)$/i.test(sId)) return "证件号码位数不正确";
  sId = sId.replace(/x$/i, "a");
  if (aCity[parseInt(sId.substr(0, 2))] == null) return "证件号码格式不正确";
  let sBirthday = sId.substr(6, 4) + "-" + Number(sId.substr(10, 2)) + "-" + Number(sId.substr(12, 2));
  var d = new Date(sBirthday.replace(/-/g, "/"));
  if (sBirthday != (d.getFullYear() + "-" + (d.getMonth() + 1) + "-" + d.getDate())) return "证件号码格式不正确";
  for (var i = 17; i >= 0; i--) iSum += (Math.pow(2, i) % 11) * parseInt(sId.charAt(17 - i), 11);
  if (iSum % 11 != 1) return "证件号码格式不正确";
  return true;
}
//身份证号，手机号加密
function plusXing(str, frontLen, endLen) {
  var len = str.length - frontLen - endLen;
  var xing = '';
  for (var i = 0; i < len; i++) {
    xing += '*';
  }
  return str.substr(0, frontLen) + xing + str.substr(str.length - endLen);
}

// 转化时间戳为 yyyy-MM-dd hh:mm:ss的格式
function formatUnixtimestamp(timestamp) {
  var timestamp = new Date(timestamp);
  var year = 1900 + timestamp.getYear();
  var month = "0" + (timestamp.getMonth() + 1);
  var date = "0" + timestamp.getDate();
  var hour = "0" + timestamp.getHours();
  var minute = "0" + timestamp.getMinutes();
  var second = "0" + timestamp.getSeconds();
  return year + "-" + month.substring(month.length-2, month.length)  + "-" + date.substring(date.length-2, date.length)
      + " " + hour.substring(hour.length-2, hour.length) + ":"
      + minute.substring(minute.length-2, minute.length) + ":"
      + second.substring(second.length-2, second.length);
}  

// 转化时间戳为 yyyy-MM-dd 的格式
function formatimestamp(timestamp) {
  var timestamp = new Date(timestamp);
  var year = 1900 + timestamp.getYear();
  var month = "0" + (timestamp.getMonth() + 1);
  var date = "0" + timestamp.getDate();
  var hour = "0" + timestamp.getHours();
  var minute = "0" + timestamp.getMinutes();
  var second = "0" + timestamp.getSeconds();
  return year + "-" + month.substring(month.length-2, month.length)  + "-" + date.substring(date.length-2, date.length);
}  


module.exports = {
  formatTime: formatTime,
  url: request.url,
  get: request.get,
  post: request.post,
  debounce,
  throttle,
  formatUnixtimestamp,
  formatimestamp,
  transToCnDay,
  countDate,
  DateDiff,
  isCardID,
  plusXing
}