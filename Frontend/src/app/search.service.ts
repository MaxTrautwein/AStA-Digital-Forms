import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SearchService {

  private search: string = "";

  updateSearch(event: any){
    this.search = event.target.value;
    this.search = this.search.toLowerCase();
  }

  isMatch(data: string): boolean{
    return this.search === "" || data.toLowerCase().includes(this.search)
  }

}
