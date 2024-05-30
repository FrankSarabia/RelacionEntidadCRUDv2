import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private loginUrl = 'http://localhost:8080/login';  // URL de tu API de login

  constructor(private http: HttpClient) { }

  login(username: string, password: string): Observable<boolean> {
    return this.http.post<any>(this.loginUrl, { username, password })
      .pipe(
        map(response => {
          // Suponiendo que el token JWT es parte de la respuesta
          if (response && respon) {
            localStorage.setItem('currentUser', JSON.stringify({ username, token: response.token }));
            return true;
          } else {
            return false;
          }
        }),
        catchError(error => {
          console.error(error);
          return of(false);
        })
      );
  }

  logout() {
    localStorage.removeItem('currentUser');
  }

  isLoggedIn(): boolean {
    return localStorage.getItem('currentUser') !== null;
  }
}
