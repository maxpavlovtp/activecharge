var propertiesReader = require('properties-reader');
var props = new propertiesReader('./props.properties');

const ewelink = require('ewelink-api');

const deviceId = '100136f129';
(async () => {
    const connectionWithRegionOnly = new ewelink({
        email: props.get('email'),
        password: props.get('password'),
    });
    const region = await connectionWithRegionOnly.getRegion();
    console.log(region);


    const connection = new ewelink({
        email: props.get('email'),
        password: props.get('password'),
        region:  props.get('region'),
    });



    /* get all devices */
    const devices = await connection.getDevices();
    console.log(devices);

    /* get specific devide info */
    const device = await connection.getDevice(deviceId);
    console.log(device);

    /* toggle device */
     await connection.toggleDevice(deviceId);

    // const usage = await connection.getDevicePowerUsage(deviceId)
    // console.log(usage);

})();