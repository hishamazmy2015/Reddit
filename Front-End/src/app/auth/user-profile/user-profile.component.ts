import { Route } from '@angular/compiler/src/core';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CommentPayload } from 'src/app/comment/comment.payload';
import { CommentService } from 'src/app/comment/comment.service';
import { PostModel } from 'src/app/shared/Post.model';
import { PostService } from 'src/app/shared/post.service';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {

  name: string;
  posts: PostModel[];
  comments: CommentPayload[];
  postLength: number;
  commentLength: number;

  constructor(private postSer: PostService, private commentSer: CommentService, private activeRoute: ActivatedRoute, private route: Router) {

    this.name = this.activeRoute.snapshot.params.name;
    console.log("this.name " + this.name);


    this.postSer.getAllPostsByUser(this.name).subscribe(data => {
      this.posts = data;
      this.postLength = data.length;
      console.log(data[0].userName);

    });
    this.commentSer.getCommentsByUserName(this.name).subscribe(data => {
      this.comments = data;
      this.commentLength = data.length;
    });


  }

  ngOnInit(): void {



  }

}
