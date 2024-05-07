import { TestBed } from '@angular/core/testing';
import { AppComponent } from './app.component';
import {BrowserModule} from '@angular/platform-browser';
import {AppRoutingModule} from './app-routing.module';
import { OAuthModule } from 'angular-oauth2-oidc';
import {HttpClientModule} from "@angular/common/http";

describe('AppComponent', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AppComponent,BrowserModule, AppRoutingModule, HttpClientModule,OAuthModule.forRoot({
        resourceServer: {
          allowedUrls: ['https://auth.df.dk4max.com/realms/DigitalForms/.well-known/openid-configuration'],
          sendAccessToken: true
        }
      })],
    }).compileComponents();
  });

  it('should create the app', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });

  it(`should have the 'DigitalForms' title`, () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app.title).toEqual('DigitalForms');
  });

  it('should render title', () => {
    const fixture = TestBed.createComponent(AppComponent);
    fixture.detectChanges();
    const compiled = fixture.nativeElement as HTMLElement;
    expect(compiled.querySelector('h1')?.textContent).toContain('Hello, DigitalForms');
  });
});
