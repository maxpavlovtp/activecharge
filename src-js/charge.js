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

    const christmassTree = "100136f129"

    const status = await connection.setDevicePowerState(christmassTree, 'on');
    console.log(status);

    const usage = await connection.getDevicePowerUsage('100136f129');
    // console.log(usage);

})();