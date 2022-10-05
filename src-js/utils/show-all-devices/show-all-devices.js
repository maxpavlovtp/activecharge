const ewelink = require('ewelink-api');

(async () => {

    const connection = new ewelink({
        email: 'km220.com@gmail.com',
        password: 'Nopassword1',
        region:  'eu',
    });

    /* get all devices */
    const devices = await connection.getDevices();
    console.log(connection)
    console.log(devices);

    // const status = await connection.setDevicePowerState("100136f1a4", 'on');
    // console.log(status);
})();
