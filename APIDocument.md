## API Endpoints

### 1. Set Availability (Calendar Owner)

- **URL**: `/set_availability`
- **Method**: POST
- **Body**: 
  ```json
  {
    "name": "Kumar",
    "emailId": "xyz@gmail.com"
    "start_time": "09:00",
    "end_time": "17:00",
    "start_date" : "02/10/2024",
    "end_date" : "10/10/2024"
  }

- **Response**:
  ```json
  {
    "message": "Availability set successfully"
  }
  ```

### 2. Search Available Time Slots (Invitee)

- **URL**: `/available_slots?date=2024-09-30&userId=1`
- **Method**: GET
- **Response**:
  ```json
  {
    "available_slots": ["09:00", "10:00", "11:00"]
  }
  ```
  ### 3. Book Appointment (Invitee)

- **URL**: `/book_appointment`
- **Method**: POST
- **Body**:
  ```json
  {
    "date": "2024-09-30",
    "start_time": "10:00",
    "duration_in_hrs" : "1",
    "invitee_name": "Rakesh",
    "comment": "Interview R1",
    "emailId": "abc@gmail.com"
  }
  ```
- **Response**:
  ```json
  {
    "message": "Appointment booked successfully"
  }

### 4. List Upcoming Appointments (Calendar Owner)

- **URL**: `/upcoming_appointments`
- **Method**: GET
- **Response**:
  ```json
  {
    "upcoming_appointments": [
      {
        "date": "2024-09-30",
        "start_time": "10:00",
        "end_time": "11:00",
        "invitee_name": "Rakesh",
        "invitee_email": "abc@gmail.com",
        "comment" : "Interview R1" 
      }
    ]
  }
  ```