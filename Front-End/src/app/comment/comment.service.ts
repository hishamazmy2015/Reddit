import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CommentPayload } from './comment.payload';

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  constructor(private httpClient: HttpClient) { }

  getCommentsByPostId(postId: number): Observable<CommentPayload[]> {
    return this.httpClient.get<CommentPayload[]>('/api/comments/by-post/' + postId);
  }

  getCommentsByUserName(UserName: string): Observable<CommentPayload[]> {
    return this.httpClient.get<CommentPayload[]>('/api/comments/by-post/' + UserName);
  }

  addComment(commentPayload: CommentPayload): Observable<any> {
    return this.httpClient.post('/api/comments/by-post/', commentPayload);
  }


}
