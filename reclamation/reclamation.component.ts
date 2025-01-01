import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatTableModule } from '@angular/material/table';
import { HttpClient } from '@angular/common/http';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';
import { MatButtonModule } from '@angular/material/button';
import { MaterialModule } from 'src/app/material.module';
import { RouterModule } from '@angular/router';

export interface User {
  userid: number;
  email: string;
  name: string;
}

export interface ReclamationData {
  id: number;
  dateSubmitted: string;
  description: string;
  status: string;
  resolution: string;
  dateResolved: string;
  reclamationType: string;
  user: User; // Added user field
}

@Component({
  selector: 'app-reclamation',
  standalone: true,
  imports: [
    RouterModule,
    MatTableModule,
    CommonModule,
    MatCardModule,
    MaterialModule,
    MatIconModule,
    MatMenuModule,
    MatButtonModule,
  ],
  templateUrl: './reclamation.component.html',
  styleUrls: ['./reclamation.component.scss'],
})
export class ReclamationComponent implements OnInit {
  // Updated columns to include userName, reclamationType, dateResolved
  displayedColumns1: string[] = [
    'userName',
    'reclamationType',
    'description',
    'status',
    'dateSubmitted',
    'dateResolved',
    'actions',
  ];
  dataSource1: ReclamationData[] = [];

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.fetchReclamations();
  }

  fetchReclamations(): void {
    this.http
      .get<ReclamationData[]>('http://localhost:8084/api/reclamations')
      .subscribe(
        (data) => {
          this.dataSource1 = data;
          console.log('Fetched data:', data); // Log for debugging
        },
        (error) => {
          console.error('Error fetching reclamations:', error); // Log errors
        }
      );
  }

  deleteReclamation(id: number): void {
    if (confirm('Are you sure you want to delete this reclamation?')) {
      this.http
        .delete(`http://localhost:8084/api/reclamations/${id}`)
        .subscribe({
          next: () => {
            console.log(`Reclamation with ID ${id} deleted successfully.`);
            // Refresh the dataSource to remove the deleted item
            this.dataSource1 = this.dataSource1.filter(
              (reclamation) => reclamation.id !== id
            );
          },
          error: (error) => {
            console.error(`Error deleting reclamation with ID ${id}:`, error);
          },
        });
    }
  }
  
  
}
