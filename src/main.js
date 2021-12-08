var propertiesReader = require('properties-reader');
var props = new propertiesReader('./props.properties');

const ewelink = require('ewelink-api');

const deviceId = '1001323420';
(async () => {
    const connection = new ewelink({
        email: 'maxpavlov.dp@gmail.com',
        password: props.get('password'),
        region: 'eu',
    });

    const region = await connection.getRegion();
    console.log(region);


    /* get all devices */
    const devices = await connection.getDevices();
    console.log(devices);

    /* get specific devide info */
    const device = await connection.getDevice(deviceId);
    console.log(device);

    /* toggle device */
    await connection.toggleDevice(deviceId);

    const usage = await connection.getDevicePowerUsage(deviceId)
    console.log(usage);

})();