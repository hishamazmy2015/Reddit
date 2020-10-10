import { Component, OnInit } from '@angular/core';
import { SubredditModel } from '../subreddit-response';

@Component({
  selector: 'app-list-subreddits',
  templateUrl: './list-subreddits.component.html',
  styleUrls: ['./list-subreddits.component.css']
})
export class ListSubredditsComponent implements OnInit {

  subreddits: SubredditModel[];
  constructor() { }

  ngOnInit(): void {
  }

}
