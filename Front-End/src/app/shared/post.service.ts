import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CreatePostPayload } from '../post/create-post/create.post.payload';
import { PostModel } from './Post.model';

@Injectable({
  providedIn: 'root'
})
export class PostService {

  constructor(private httpClient: HttpClient) { }




  getAllPosts(): Observable<Array<PostModel>> {
    return this.httpClient.get<Array<PostModel>>('http://localhost:8080/api/posts/');
  }



  createPost(post: CreatePostPayload): Observable<any> {
    return this.httpClient.post('http://localhost:8080/api/posts', post);
  }


  getPost(postId: number): Observable<PostModel> {
    return this.httpClient.get<PostModel>('http://localhost:8080/api/posts/'+postId);
  }
}
