import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { SubredditModel } from './subreddit-response';

@Injectable({
  providedIn: 'root'
})
export class SubredditService {

  constructor(private httpClient: HttpClient) { }




  getAllSubreddit(): Observable<Array<SubredditModel>> {
    return this.httpClient.get<Array<SubredditModel>>('http://localhost:8080/api/subreddit');
  }

  createSubreddit(subredditModel:SubredditModel): Observable<Array<SubredditModel>> {
    return this.httpClient.post<Array<SubredditModel>>('http://localhost:8080/api/subreddit',subredditModel);
  }

  

}
