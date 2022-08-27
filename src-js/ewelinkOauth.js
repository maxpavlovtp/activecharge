// NodeJS
const crypto = require('crypto');
const clientId = '4t2Xs57iZQRRwDBPWigvi9qHfQeCfg0X';
const seq = '123';
const clientSecret = '8SKQcsaGbsQMnhiLH3NKdLHNCBt2L8Xz';
const buffer = Buffer.from(`${clientId}_${seq}`, "utf-8");
const sign = crypto.createHmac("sha256", clientSecret).update(buffer).digest(
    "base64");
console.log(sign);
// v1+mfNY2ukxswM8sZOTg99srZsVnUVv9DGXeav1096M=

console.log(
    "https://c2ccdn.coolkit.cc/oauth/index.html?state=XXX&clientId=" + clientId
    + "&authorization=" + sign
    + "&seq=123"
    + "&redirectUrl=http://49.12.19.42/"
    + "&nonce=zt123456&grantType=authorization_code"
)