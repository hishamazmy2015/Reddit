import { Component, OnInit } from '@angular/core';
import { PostModel } from '../shared/Post.model';
import { PostService } from '../shared/post.service';
// import { PostService } from '@fortawesome/free shared/post.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  faArrowUP=faArrowUp
  faComment
  posts: Array<PostModel> = [];

  constructor(private postSer: PostService) {
    this.postSer.getAllPosts().subscribe(post => {
      this.posts = post;
    });

  }


  ngOnInit(): void {
  }

}
