import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { error } from 'protractor';
import { throwError } from 'rxjs';
import { PostModel } from 'src/app/shared/Post.model';
import { PostService } from 'src/app/shared/post.service';
import { SubredditModel } from 'src/app/subreddit/subreddit-response';
import { SubredditService } from 'src/app/subreddit/subreddit.service';
import { CreatePostPayload } from './create.post.payload';

@Component({
  selector: 'app-create-post',
  templateUrl: './create-post.component.html',
  styleUrls: ['./create-post.component.css']
})
export class CreatePostComponent implements OnInit {

  createPostForm: FormGroup;
  subreddits: SubredditModel[];

  postPayloadCreate: CreatePostPayload;
  postName: string;
  url: string;
  subredditName: string;
  description: string;

  postModel: PostModel;

  constructor(private postSer: PostService, private route: Router,
    private subredditSer: SubredditService) {

    this.postPayloadCreate = {
      postName: '',
      url: '',
      description: '',
      subredditName: '',
    }

  }

  ngOnInit(): void {
    this.createPostForm = new FormGroup({
      postName: new FormControl('', Validators.required),
      subredditName: new FormControl('', Validators.required),
      url: new FormControl('', Validators.required),
      description: new FormControl('', Validators.required)
    });
    this.subredditSer.getAllSubreddit().subscribe(data => {
      this.subreddits = data;
    }, error => {
      throwError(error);
    })
  }


  createPost() {
    // this.ppayloadCreate
    this.postPayloadCreate.postName = this.createPostForm.get('postName').value;
    this.postPayloadCreate.subredditName = this.createPostForm.get('subredditName').value;
    this.postPayloadCreate.url = this.createPostForm.get('url').value;
    this.postPayloadCreate.description = this.createPostForm.get('description').value;

    this.postSer.createPost(this.postPayloadCreate).subscribe((data) => {
      console.log("the error is"+data);
      
      this.route.navigateByUrl('/');

    }, error => {
      console.log(error);
      
      throwError(error);
    })
  }
  discardPost() {
    this.route.navigateByUrl('/');
  }
}
