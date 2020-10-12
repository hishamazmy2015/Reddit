import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { throwError } from 'rxjs';
import { CommentPayload } from 'src/app/comment/comment.payload';
import { CommentService } from 'src/app/comment/comment.service';
import { PostModel } from 'src/app/shared/Post.model';
import { PostService } from 'src/app/shared/post.service';

@Component({
  selector: 'app-view-post',
  templateUrl: './view-post.component.html',
  styleUrls: ['./view-post.component.css']
})
export class ViewPostComponent implements OnInit {

  post: PostModel;
  commentForm: FormGroup;
  postId: number;
  commentPayload: CommentPayload;
  comments: CommentPayload[];



  constructor(private postSer: PostService
    , private activeRoute: ActivatedRoute
    , private commentSer: CommentService) {
    this.postId = this.activeRoute.snapshot.params.id;
    this.getPostById();
    this.commentPayload = {
      text: '',
      postId: this.postId
    };
    this.commentForm = new FormGroup({
      text: new FormControl('', Validators.required)
    });

  }
  ngOnInit(): void {
    this.getPostById();
    this.getCommentsForPost();
  }


  getPostById() {
    this.postSer.getPost(this.postId).subscribe(data => {
      this.post = data;

    }, error => {
      throwError(error);
    });
  }



  postComment() {
    this.commentPayload.text = this.commentForm.get('text').value;
    this.commentPayload.postId = this.postId;

    this.commentSer.addComment(this.commentPayload).subscribe(data => {
      this.commentForm.get('text').setValue('');
      this.getCommentsForPost();
    });

  }

  getCommentsForPost() {
    this.commentSer.getCommentsByPostId(this.postId).subscribe(data => {
      this.comments = data;
    });
  }


}
