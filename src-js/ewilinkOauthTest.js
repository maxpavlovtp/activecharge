// NodeJS
const crypto = require('crypto');
const clientId = '4t2Xs57iZQRRwDBPWigvi9qHfQeCfg0X';
const clientSecret = 'xxx';
const seq = Date.now();
const buffer = Buffer.from(`${clientId}_${seq}`, "utf-8");
const sign = crypto.createHmac("sha256", clientSecret)
.update(buffer)
.digest(
    "base64");
console.log(sign);
// v1+mfNY2ukxswM8sZOTg99srZsVnUVv9DGXeav1096M=

let callBackTo220 = "http://49.12.19.42:8080/login/ewelinkRedirect";
console.log(
    "https://c2ccdn.coolkit.cc/oauth/index.html?"
    + "state=220UserId"
    + "&clientId=" + clientId
    + "&authorization=" + sign
    + "&seq=" + seq
    + "&redirectUrl=" + callBackTo220
    + "&nonce=zt123456"
    + "&grantType=authorization_code"
)


