import { AuthConfig } from 'angular-oauth2-oidc';

export const authConfig: AuthConfig = {
  issuer: 'https://auth.df.dk4max.com/realms/DigitalForms',
  redirectUri: window.location.origin +'/dashboard',
  clientId: 'df',
  responseType: 'code',
  strictDiscoveryDocumentValidation: true,
  scope: 'openid profile email offline_access',
}
