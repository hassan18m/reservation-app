import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort, Sort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { LocalStorageService } from 'src/app/services/localstorage.service';
import { UserService } from 'src/app/services/user.service';
import { User, UserData } from 'src/app/types/user';
import { LiveAnnouncer } from '@angular/cdk/a11y';

@Component({
  selector: 'app-users-table',
  templateUrl: './users-table.component.html',
  styleUrls: ['./users-table.component.css'],
})
export class UsersTableComponent implements AfterViewInit, OnInit {
  users!: User[];
  usersData!: UserData[];
  displayedColumns: string[] = ['id', 'firstName', 'lastName', 'email'];
  dataSource: MatTableDataSource<UserData> = new MatTableDataSource();

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(
    private userService: UserService,
    private localStorage: LocalStorageService,
    private _liveAnnouncer: LiveAnnouncer
  ) {
    // Create 100 users
    // const users = Array.from({length: 100}, (_, k) => createNewUser(k + 1));
    // Assign the data to the data source for the table to render
    // this.dataSource = new MatTableDataSource(users);
  }

  ngOnInit() {
    const token = this.localStorage.getUser();
    this.userService.getUsers(token).subscribe((data) => {
      this.users = data.users;
      const dataSourceUsers = data.users.map((user: User) => {
        return {
          id: user.id,
          firstName: user.firstName,
          lastName: user.lastName,
          email: user.email,
        } as UserData;
      });
      this.usersData = dataSourceUsers;
      this.dataSource = new MatTableDataSource(dataSourceUsers);
    });
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  announceSortChange(sortState: Sort) {
    console.log(sortState);
    const sortedUsers = this.usersData.sort((a: UserData, b: UserData) =>
      a.email.localeCompare(b.email)
    );
    this.dataSource = new MatTableDataSource(sortedUsers);
  }
}
