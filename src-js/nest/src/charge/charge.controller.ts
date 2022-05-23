import { Controller, Get, HttpStatus, Res } from '@nestjs/common';

var propertiesReader = require('properties-reader');
var props = new propertiesReader('./props.properties');

@Controller('charge')
export class ChargeController {
  @Get('/charging')
  async startFreeCharging(@Res() res) {
    const ewelink = require('ewelink-api');
    const connection = new ewelink({
      email: props.get('email'),
      password: props.get('password'),
      region: props.get('region'),
    });
    const device = await connection.getDevice(props.get('a36_1'));
    console.log(device);

    if (device.online) {
      const status = await connection.setDevicePowerState(
        props.get('a36_1'),
        'on',
      );
      console.log(status);
      return res.status(HttpStatus.OK).json({
        message: 'success',
      });
    } else {
      return res.status(HttpStatus.OK).json({
        message: 'error',
      });
    }
  }

  @Get('/statistic')
  async usageStatistics(@Res() res) {
    const ewelink = require('ewelink-api');
    const connection = new ewelink({
      email: props.get('email'),
      password: props.get('password'),
      region: props.get('region'),
    });
    const powerUsage = await connection.getDevicePowerUsage(props.get('a36_1'));
    console.log(powerUsage);

    return res.status(HttpStatus.OK).json({
      usage: powerUsage,
    });
  }
}
