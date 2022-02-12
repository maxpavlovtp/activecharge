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
            password: '891234567',
            region: props.get('region'),
        });

        const stationA1 = "1000d61c41"

        const status = await connection.setDevicePowerState(stationA1, 'on');
        console.log(status);

        response.status(HttpStatus.OK).send("charging ");
    }
}
