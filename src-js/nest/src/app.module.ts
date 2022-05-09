import { Module } from '@nestjs/common';
import { AppController } from './app.controller';
import {ChargeController} from "./charge.controller";
import { AppService } from './app.service';

@Module({
  imports: [],
  controllers: [AppController, ChargeController],
  providers: [AppService],
})
export class AppModule {}