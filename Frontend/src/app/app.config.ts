import { ApplicationConfig, importProvidersFrom } from '@angular/core';
import { provideRouter } from '@angular/router';
import { provideHttpClient } from '@angular/common/http';
import { routes } from './app.routes';
import { OAuthModule } from 'angular-oauth2-oidc';

export const appConfig: ApplicationConfig = {
  providers: [provideRouter(routes), provideHttpClient(), importProvidersFrom(
    OAuthModule.forRoot({
      resourceServer: {
        allowedUrls: ["http://localhost:8080/*"],
        sendAccessToken: true,
      },
    })
  )]
};