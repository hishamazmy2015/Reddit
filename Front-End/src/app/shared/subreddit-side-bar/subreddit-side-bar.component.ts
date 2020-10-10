import { Component, OnInit } from '@angular/core';
import { SubredditModel } from 'src/app/subreddit/subreddit-response';
import { SubredditService } from 'src/app/subreddit/subreddit.service';

@Component({
  selector: 'app-subreddit-side-bar',
  templateUrl: './subreddit-side-bar.component.html',
  styleUrls: ['./subreddit-side-bar.component.css']
})
export class SubredditSideBarComponent implements OnInit {
  subreddits: Array<SubredditModel> = [];
  displayViewAll: boolean;

  constructor(private subredditSer: SubredditService) {
     this.subredditSer.getAllSubreddit().subscribe(subreddit => {
    if (this.subreddits.length >= 4) {
      this.subreddits = subreddit.splice(0, 3);
      this.displayViewAll = true;
``
    } else
      this.subreddits = subreddit;
  })}


  ngOnInit(): void {
  }




}
