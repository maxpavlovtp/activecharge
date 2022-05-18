import { Module } from '@nestjs/common';
import { AppController } from './app.controller';
import {ChargeController} from "./charge/charge.controller";
import { AppService } from './app.service';
import { ChargeModule } from './charge/charge.module';

@Module({
  imports: [ChargeModule],
  controllers: [AppController, ChargeController],
  providers: [AppService],
})
export class AppModule {}