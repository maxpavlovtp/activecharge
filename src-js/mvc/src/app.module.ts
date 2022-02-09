import { Module } from '@nestjs/common';
import { AppController } from './app.controller';
import {ChargeController} from "./charge.controller";
import { AppService } from './app.service';
import { ServeStaticModule } from '@nestjs/serve-static';
import { join } from 'path';

@Module({
  imports: [
    ServeStaticModule.forRoot({
      rootPath: join(__dirname, '..', 'static-files'),   // <-- path to the static files
    }),
  ],
  controllers: [AppController, ChargeController],
  providers: [AppService],
})
export class AppModule {}