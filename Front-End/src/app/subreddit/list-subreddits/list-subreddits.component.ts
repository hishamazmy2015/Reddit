import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { throwError } from 'rxjs';
import { SubredditModel } from '../subreddit-response';
import { SubredditService } from '../subreddit.service';

@Component({
  selector: 'app-list-subreddits',
  templateUrl: './list-subreddits.component.html',
  styleUrls: ['./list-subreddits.component.css']
})
export class ListSubredditsComponent implements OnInit {

  subreddits: SubredditModel[];
  constructor(private router: Router, private subredditSer: SubredditService) { }

  ngOnInit(): void {
    this.subredditSer.getAllSubreddit().subscribe(data => {
      this.subreddits = data;
    }, error => {
      throwError(error);
    });
  }




}
