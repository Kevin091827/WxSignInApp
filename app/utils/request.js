const prejudge = require('./prejudge.js')
// request的封装
const url = 'http://m3hngq4tci.51http.tech'


const get = (url, data, isGetToken = false) => {
    return new Promise((resolve, reject) => {
        let header = CreateHeader(url, false);

        // 是否需要重新登录
        // !isGetToken && fTokenOverTime(data.options)

        wx.request({
            url,
            data,
            header,
            method: 'GET',
            success: (res) => {
                resolve(res)
            },
            fail: e => {
                wx.showToast({
                    icon: 'none',
                    title: '请检查一下您的网络状态！',
                    duration: 3000
                })
            }
        })
    })
}

const post = (url, data) => {
    return new Promise((resolve, reject) => {
        let header = CreateHeader(url, true)

        // fTokenOverTime(data.options)

        // delete data.options

        wx.request({
            url,
            data,
            method: 'POST',
            header, // 设置请求的 header
            success: res => {
                // 是否需要重新登录
                resolve(res)
            },
            fail: e => {
                wx.showToast({
                    icon: 'none',
                    title: '请检查一下您的网络状态！',
                    duration: 3000
                })
            }
        })
    })
}

// 请求头
function CreateHeader(url, isPost) {
    // application/x-www-form-urlencoded
    var header = {
        'content-type': 'application/json'
    }
    if (isPost) {
        header['content-type'] = 'application/x-www-form-urlencoded'
    }
    
    // let token = wx.getStorageSync(tokenKey)
    // if (token) {
    //     // 存在token
    //     // header.Authorization = 'Bearer ' + token;
    //     header.Authorization = token;
    // } else {
        // 测试
        // header.Authorization = 'testToken';
    header.Authorization = 'testToken';
    // }
    return header
}

// function fTokenOverTime(options = {}) {
//     let exp = wx.getStorageSync('exp'),
//         now = +new Date();
//     // 过期时间与现在时间之差小于三分钟时重新登录


//     if (exp - now <= 3 * 60 * 1000 || !exp) {
//         wx.removeStorageSync('exp');
//         wx.removeStorageSync('token');
//         // 重新登录 (主动获取token)
//         fGetToken();
//         return true
//     }
//     return false
// }


// // 获取token
// function fGetToken() {
//     const unionId = wx.getStorageSync('unionId');
//     let isSucc = false;

//     get(`${url}/api/token/${unionId}`, {}, true).then(res => {
//         let token = res.data.sData;
//         if (token) {
//             isSucc = true;
//             wx.setStorageSync(tokenKey, token);
//             getExpTime(token);
//         } else {
//             !isInWelcomePage() && wx.navigateTo({
//                 url: "/pages/welcome/welcome",
//             })
//         }
//     })
// }

// // 判断是否处在授权页
// function isInWelcomePage() {
//     let pages = getCurrentPages();
//     if(pages[pages.length - 1].route === 'pages/welcome/welcome') {
//         return true;
//     }
//     return false;
// }

// // 获取过期时间
// function getExpTime(str) {
//     str = prejudge.decode(str);
//     let expTime = str.slice(str.indexOf('exp":') + 5, str.indexOf('"iat') - 1);

//     wx.setStorageSync('exp', expTime * 1000);
// }

module.exports = {
    url,
    get,
    post
}