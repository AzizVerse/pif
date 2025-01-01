import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-acturiat',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './acturiat.component.html',
  styleUrls: ['./acturiat.component.scss']
})
export class ActuriatComponent {
  form: FormGroup;
  provisionForm: FormGroup;
  claimForm: FormGroup; // New form for models 10 through 14
  predictionResults: { [key: number]: any } = {};
  formattedResults: { [key: number]: any[] } = {};
  formattedProvisionResults: { [key: number]: any[] } = {};
  formattedClaimResults: { [key: number]: any[] } = {}; // New for claim results
  insuranceTypes = [
    "Garantie de Contrepartie",
    "Couverture de Change",
    "Volatilité et Marché",
    "Produit Spécifique",
    "Cyber et Opérations"
  ];

  constructor(private fb: FormBuilder, private http: HttpClient) {
    this.form = this.fb.group({
      Premiums_Collected: [null],
      Client_Coverage: [null],
      Coverage_Duration: [null],
      Number_of_Claims: [null],
      Severity_of_Claims: [null],
      Total_Claims_Cost: [null],
      Risk_Factor: [null],
      Frequency: [null],
    });

    this.provisionForm = this.fb.group({
      Insurance_Type: [null],
      Premiums_Collected: [null],
      Coverage_Duration: [null],
      Risk_Factor: [null],
      Client_Coverage: [null],
      Remaining_Years: [null],
      Policy_Adjustment: [null],
    });

    this.claimForm = this.fb.group({
      Insurance_Type: [null],
      Premiums_Collected: [null],
      Coverage_Duration: [null],
      Number_of_Claims: [null],
      Severity_of_Claims: [null],
      Risk_Factor: [null],
      Total_Claims_Cost: [null],
      Client_Coverage: [null],
      Profit_Margin: [null],
      Remaining_Years: [null],
      Policy_Adjustment: [null],
    });
  }

  predict(modelId: number): void {
    const apiUrl = `http://localhost:8084/api/models/${modelId}/predict`;

    this.http.post(apiUrl, this.form.value).subscribe({
      next: (response) => {
        console.log(`Prediction Response for Model ${modelId}:`, response);
        this.predictionResults[modelId] = response;

        this.formattedResults[modelId] = this.formatResult(response);
      },
      error: (error) => {
        console.error(`Prediction Error for Model ${modelId}:`, error);
        this.formattedResults[modelId] = [{ key: 'Error', value: 'Failed to fetch prediction' }];
      }
    });
  }

  predictProvision(modelId: number): void {
    const apiUrl = `http://localhost:8084/api/models/${modelId}/predict`;

    const provisionData = { ...this.provisionForm.value };
    provisionData.Insurance_Type = parseInt(provisionData.Insurance_Type, 10);
    this.http.post(apiUrl, provisionData).subscribe({
      next: (response) => {
        console.log(`Provision Prediction Response for Model ${modelId}:`, response);
        this.predictionResults[modelId] = response;

        this.formattedProvisionResults[modelId] = this.formatResult(response);
      },
      error: (error) => {
        console.error(`Prediction Error for Model ${modelId}:`, error);
        this.formattedProvisionResults[modelId] = [{ key: 'Error', value: 'Failed to fetch prediction' }];
      }
    });
  }

  predictClaim(modelId: number): void {
    const apiUrl = `http://localhost:8084/api/models/${modelId}/predict`;

    const claimData = { ...this.claimForm.value };
    claimData.Insurance_Type = parseInt(claimData.Insurance_Type, 10);
    this.http.post(apiUrl, claimData).subscribe({
      next: (response) => {
        console.log(`Claim Prediction Response for Model ${modelId}:`, response);
        this.predictionResults[modelId] = response;

        this.formattedClaimResults[modelId] = this.formatResult(response);
      },
      error: (error) => {
        console.error(`Prediction Error for Model ${modelId}:`, error);
        this.formattedClaimResults[modelId] = [{ key: 'Error', value: 'Failed to fetch prediction' }];
      }
    });
  }

  private formatResult(result: any): { key: string; value: any }[] {
    const formatted: { key: string; value: any }[] = [];
    for (const [key, value] of Object.entries(result)) {
      if (Array.isArray(value)) {
        formatted.push({ key, value: value.join(', ') });
      } else {
        formatted.push({ key, value });
      }
    }
    return formatted;
  }

  updateDerivedFields(): void {
    const formValues = this.form.value;

    const coverageDuration = formValues.Coverage_Duration || 1;
    const numberOfClaims = formValues.Number_of_Claims || 0;
    this.form.patchValue({
      Frequency: numberOfClaims / coverageDuration,
    });

    const severityOfClaims = formValues.Severity_of_Claims || 0;
    this.form.patchValue({
      Total_Claims_Cost: numberOfClaims * severityOfClaims,
    });
  }
  eq(): void {
    const formValues = this.claimForm.value;

    
    const numberOfClaims = formValues.Number_of_Claims || 0;
    
    const severityOfClaims = formValues.Severity_of_Claims || 0;
    this.claimForm.patchValue({
      Total_Claims_Cost: numberOfClaims * severityOfClaims,
    });
  }
}
