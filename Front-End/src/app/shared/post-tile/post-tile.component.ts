import { Component, Input, OnInit } from '@angular/core';
import { PostModel } from '../Post.model';
import { PostService } from '../post.service';
import { faComments } from '@fortawesome/free-solid-svg-icons';
import { Router } from '@angular/router';


@Component({
  selector: 'app-post-tile',
  templateUrl: './post-tile.component.html',
  styleUrls: ['./post-tile.component.css']
})
export class PostTileComponent implements OnInit {

//  @Input() posts: Array<PostModel>;
 @Input() posts: PostModel[]; 

  faComments = faComments;


  constructor(private postSer: PostService, private router: Router) {
    postSer.getAllPosts().subscribe(post => {
      this.posts =post;
    });

  }

  ngOnInit(): void {
  }

  goToPost(id: number) {
    this.router.navigateByUrl('/view-post/' + id);
  }


}
