import {Body, Controller, HttpStatus, Post, Res} from '@nestjs/common';
import { Response } from 'express';

var propertiesReader = require('properties-reader');
var props = new propertiesReader('./props.properties');

class StartChargingDto {
    checkout_url
}

@Controller('charge')
export class ChargeController {
    @Post()
    async startCharging(@Res() response:Response, @Body() startChargingDto: StartChargingDto) {
        console.log(startChargingDto)

        const ewelink = require('ewelink-api');
        const connection = new ewelink({
            email: props.get('email'),
            password: props.get('password'),
            region: props.get('region'),
        });

        /* get all devices */
        const devices = await connection.getDevices();
        // console.log(devices);

        const status = await connection.setDevicePowerState(props.get('a36_1'), 'on');
        console.log(status);

        response.status(HttpStatus.OK).send("charging ");
    }
}
