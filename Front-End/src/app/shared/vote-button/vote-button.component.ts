import { Component, Input, OnInit } from '@angular/core';
import { PostModel } from '../Post.model';
import { faComments, faArrowUp, faArrowDown } from '@fortawesome/free-solid-svg-icons';
import { VotePayload } from './vote.payload';
import { voteType } from './vote.type';
import { VoteService } from '../vote.service';
import { ToastrService } from 'ngx-toastr';
import { PostService } from '../post.service';
import { AuthService } from 'src/app/auth/shared/auth.service';

@Component({
  selector: 'app-vote-button',
  templateUrl: './vote-button.component.html',
  styleUrls: ['./vote-button.component.css']
})
export class VoteButtonComponent implements OnInit {

  @Input() post: PostModel;
  votePay: VotePayload;
  // faComment:faArrowUp;
  faArrowUp = faArrowUp;
  faArrowDown = faArrowDown;
  isLoggedIn: boolean;


  constructor(private voteSer: VoteService, private toster: ToastrService
    , private postSer: PostService, private authSer: AuthService) {
      this.votePay = {
        voteTypes: undefined,
        postId: undefined
      }
    this.authSer.loggedIn.subscribe(data => {
      this.isLoggedIn = data;
    });

  }
  // private voteSer:
  ngOnInit(): void {
  }

  upvotePost() {
    this.votePay.voteTypes = voteType.UPVOTE;
    this.vote();
  }

  downvotePost() {
    this.votePay.voteTypes = voteType.DOWNVOTE;
    this.vote();

  }
  vote() {
    this.votePay.postId = this.post.id;
    this.voteSer.vote(this.votePay).subscribe(() => {
      this.UpdateVoteDetails();
    }, error => {
      this.toster.error(error.error.message);
    });
  }

  private UpdateVoteDetails() {
    this.postSer.getPost(this.post.id).subscribe(data => {
      this.post = data;
    });
  }


}
