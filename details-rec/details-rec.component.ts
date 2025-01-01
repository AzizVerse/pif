import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatOptionModule } from '@angular/material/core';
import { MatButtonModule } from '@angular/material/button';
import { Location } from '@angular/common'; 
export interface ReclamationData {
  id: number;
  dateSubmitted: string;
  description: string;
  status: string;
  resolution: string;
  dateResolved: string;
  reclamationType: string;
  user: {
    userid: number;
    email: string;
    name: string;
  };
  insurance?: {
    idInsurance: number;
    startDate: string;
    endDate: string;
    type: string;
    clientcoverageamount: number;
    clientpremium: number;
    policy: string;
  };
  claim?: {
    idClaim: number;
    date: string;
    status: string;
    amount: string;
    documentURL: string;
    details: string;
  };
}

@Component({
  selector: 'app-details-rec',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatOptionModule,
    MatButtonModule,
  ],
  templateUrl: './details-rec.component.html',
  styleUrls: ['./details-rec.component.scss'],
})
export class DetailsRecComponent implements OnInit {
  reclamationId!: number;
  reclamation!: ReclamationData;
  form!: FormGroup;

  constructor(
    private route: ActivatedRoute,
    private location: Location,
    private http: HttpClient,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    // Get the reclamation ID from the route parameters
    this.reclamationId = Number(this.route.snapshot.paramMap.get('id'));

    // Fetch the reclamation details
    this.fetchReclamationDetails();

    // Initialize the reactive form
    this.form = this.fb.group({
      status: [''],
      resolution: [''],
    });
  }

  fetchReclamationDetails(): void {
    this.http
      .get<ReclamationData>(`http://localhost:8084/api/reclamations/${this.reclamationId}`)
      .subscribe({
        next: (data) => {
          this.reclamation = data;
          this.populateForm(data);
        },
        error: (err) => {
          console.error('Error fetching reclamation details:', err);
        },
      });
  }

  populateForm(data: ReclamationData): void {
    this.form.patchValue({
      status: data.status,
      resolution: data.resolution,
    });
  }

  saveChanges(): void {
    if (this.form.valid) {
      const updatedData = {
        status: this.form.value.status,
        resolution: this.form.value.resolution,
      };
  
      this.http
        .put(`http://localhost:8084/api/reclamations/${this.reclamationId}`, updatedData, {
          headers: { 'Content-Type': 'application/json' },
        })
        .subscribe({
          next: (res) => {
            console.log('Reclamation updated successfully:', res);
            alert('Reclamation updated successfully!');
            this.fetchReclamationDetails(); // Refresh data
          },
          error: (err) => {
            console.error('Error updating reclamation:', err);
          },
        });
    }
  }
  goBack(): void {
    this.location.back(); // Use Location to navigate back
  }
  
  
  
}
