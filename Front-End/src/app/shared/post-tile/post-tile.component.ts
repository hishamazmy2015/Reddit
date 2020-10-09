import { Component, OnInit } from '@angular/core';
import { PostModel } from '../Post.model';
import { PostService } from '../post.service';
import { faComments } from '@fortawesome/free-solid-svg-icons';


@Component({
  selector: 'app-post-tile',
  templateUrl: './post-tile.component.html',
  styleUrls: ['./post-tile.component.css']
})
export class PostTileComponent implements OnInit {

  posts: Array<PostModel>;
  faComments = faComments;


  constructor(private postSer: PostService) {
    postSer.getAllPosts().subscribe(post => {
      post = this.posts;
    });

  }

  ngOnInit(): void {
  }

}
