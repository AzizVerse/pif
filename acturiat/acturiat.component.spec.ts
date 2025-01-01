import { TestBed, ComponentFixture } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ActuriatComponent } from './acturiat.component';
import { ReactiveFormsModule } from '@angular/forms';
import { By } from '@angular/platform-browser';

describe('ActuriatComponent', () => {
  let component: ActuriatComponent;
  let fixture: ComponentFixture<ActuriatComponent>;
  let httpMock: HttpTestingController;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReactiveFormsModule, HttpClientTestingModule],
      declarations: [ActuriatComponent]
    }).compileComponents();

    fixture = TestBed.createComponent(ActuriatComponent);
    component = fixture.componentInstance;
    httpMock = TestBed.inject(HttpTestingController);
    fixture.detectChanges();
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize the form with all controls', () => {
    const form = component.form;
    expect(form.contains('Premiums_Collected')).toBeTruthy();
    expect(form.contains('Client_Coverage')).toBeTruthy();
    expect(form.contains('Coverage_Duration')).toBeTruthy();
    expect(form.contains('Number_of_Claims')).toBeTruthy();
    expect(form.contains('Severity_of_Claims')).toBeTruthy();
    expect(form.contains('Total_Claims_Cost')).toBeTruthy();
    expect(form.contains('Risk_Factor')).toBeTruthy();
    expect(form.contains('Frequency')).toBeTruthy();
  });

  it('should call the predict method and make an HTTP request', () => {
    const testData = { Premiums_Collected: 1000, Client_Coverage: 200 };
    component.form.setValue({
      Premiums_Collected: 1000,
      Client_Coverage: 200,
      Coverage_Duration: 12,
      Number_of_Claims: 5,
      Severity_of_Claims: 2.5,
      Total_Claims_Cost: 5000,
      Risk_Factor: 1.5,
      Frequency: 10
    });

    component.predict(1);

    const req = httpMock.expectOne('http://localhost:8084/api/models/1/predict');
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(component.form.value);

    req.flush({ prediction: 'test prediction' });
    fixture.detectChanges();

    expect(component.predictionResults[1]).toEqual({ prediction: 'test prediction' });
  });

  it('should display prediction results in the template', () => {
    component.formattedResults[1] = [
      { key: 'Prediction', value: 'Test Prediction' }
    ];
    fixture.detectChanges();

    const resultElement = fixture.debugElement.query(By.css('div')).nativeElement;
    expect(resultElement.textContent).toContain('Test Prediction');
  });

  afterEach(() => {
    httpMock.verify();
  });
});
