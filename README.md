## **Design a parking lot using object-oriented principles**

User Story 1: Parking Lot Management
------------

As a system, it wants to manage a parking lot.

Assumptions:
- The parking lot can hold motorcycles, cars and vans
- The parking lot has motorcycle spots, car spots and large spots
- A motorcycle can park in any spot
- A car can park in a single compact spot, or a regular spot
- A van can park, but it will take up 3 regular spots

The above assumptions the system must evaluate before the vehicle park and leave. 

Acceptance criteria:
- How many spots are remaining?
- How many total spots are in the parking lot?
- When the parking lot is full?
- When the parking lot is empty?
- When certain spots are full? e.g., when all motorcycle spots are taken
- How many spots vans are taking up?
- How many free spots by vehicle type?
- Report all the answers above

User Story 2: Parking Lot Entrance, Exit, and Cost Calculation
------------
As a system, it wants to assign a fare per hour to vehicle type and calculate the total parking cost.

Assumptions:
- There is a sign indicating if the parking is full (red light) or remaining slots (green light)
- There is a stop barrier, and a camera in the entrance. The camera reads the plate.
- There is a display with a button to check in parking in the entrance. Once pressed the button, the driver gets a control ticket. It will be useful at the exit.
- There is a cash at exist. It scans ticket barcode and driver pays by card. It returns pay ticket and open exit barrier.
- Control ticket contains plate number, barcode, number, date, and hour.
- Pay ticket contains plate number, date, entrance hour, exit hour, vehicle fare, total amount.

Acceptance criteria:
- Display shows red light is parking is full, and green light if parking has remaining slots
- Camera detect a vehicle in front of barrier, and gets plate number.
- Driver pushes button to get ticket, and enter to the parking. System prints control ticket and open barrier. After 10 seconds, system closes barrier if the camera does not detect plate anymore, and repeat the operation.
- Driver goes to exit, scans control ticket. System calculates, and prints pay ticket. Driver pays with card. System accepts pay and open barrier. After 10 seconds, system closes barrier if the camera does not detect plate anymore, and repeat the operation.
