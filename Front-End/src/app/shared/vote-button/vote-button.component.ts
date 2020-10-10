import { Component, Input, OnInit } from '@angular/core';
import { PostModel } from '../Post.model';
import { faComments,faArrowUp, faArrowDown } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-vote-button',
  templateUrl: './vote-button.component.html',
  styleUrls: ['./vote-button.component.css']
})
export class VoteButtonComponent implements OnInit {

  @Input() post:PostModel; 
  // faComment:faArrowUp;
  faArrowUp = faArrowUp;
  faArrowDown = faArrowDown;


  constructor() { }
  // private voteSer:
  ngOnInit(): void {
  }

  upvotePost(){

  }

  downvotePost(){

  }

}
