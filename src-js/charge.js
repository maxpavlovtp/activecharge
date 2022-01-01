var propertiesReader = require('properties-reader');
var props = new propertiesReader('./props.properties');

const ewelink = require('ewelink-api');

(async () => {

    const connection = new ewelink({
        email: props.get('email'),
        password: '891234567',
        region:  props.get('region'),
    });

    /* get all devices */
    const devices = await connection.getDevices();
    console.log(devices);


    // const actionParams = {
    //     apiUrl: this.getApiWebSocket(),
    //     at: this.at,
    //     apiKey: this.apiKey,
    //     deviceId,
    //     appid: this.APP_ID,
    // };

    const usage = await connection.getDevicePowerUsage('1001323420');
    console.log(usage);

})();