import {Body, Controller, Post} from '@nestjs/common';

var propertiesReader = require('properties-reader');
var props = new propertiesReader('./props.properties');

class StartChargingDto {
}

@Controller('charge')
export class ChargeController {
    @Post()
    async startCharging(@Body() startChargingDto: StartChargingDto) {
        console.log(startChargingDto)
        if (startChargingDto.checkout_url == null) return

        const ewelink = require('ewelink-api');
        const connection = new ewelink({
            email: props.get('email'),
            password: '891234567',
            region: props.get('region'),
        });

        /* get all devices */
        // const devices = await connection.getDevices();
        // console.log(devices);

        const christmassTree = "100136f129"

        const status = await connection.setDevicePowerState(christmassTree, 'on');
        console.log(status);
    }
}
