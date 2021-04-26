# Project Estimation  
Authors:
Date:
Version:
# Contents
- [Estimate by product decomposition]
- [Estimate by activity decomposition ]
# Estimation approach
<Consider the EZGas  project as described in YOUR requirement document, assume that you are going to develop the project INDEPENDENT of the deadlines of the course>
# Estimate by product decomposition
### 
|             | Estimate                        |             
| ----------- | ------------------------------- |  
| NC =  Estimated number of classes to be developed   | 21 |             
|  A = Estimated average size per class, in LOC       | 48 | 
| S = Estimated size of project, in LOC (= NC * A) | 1008 |
| E = Estimated effort, in person hours (productivity as 10 LOC per person hour, considering implementation phase only)  | 101 |   
| C = Estimated cost, in euro (here use 1 person hour cost = 30 euro) | €3030 | 
| Estimated calendar time, in calendar weeks (Assume team of 4 people, 8 hours per day, 5 days per week ) | 3 weeks (including all phases) |     

# Estimate by activity decomposition
### 
|         Activity name    | Estimated effort (person hours)   |             
| ----------- | ------------------------------- |
| Planning | 4 |
| Requirements | 125 |
| Design | 50 |
| Implementation | 101 |
| Testing | 135 |
###

´´´plantuml
@startgantt
title Gantt's diagram
project starts the 2021/04/05
saturday are closed
sunday are closed 
[Planning] lasts 15 days
[Requirements] lasts 4 days
[Design] lasts 2 days
[Design] starts at [Requirements]'s end
[Implementation] lasts 4 days
[Implementation] starts at [Design]'s end
[Testing] lasts 5 days
[Testing] starts at [Implementation]'s end
@endgantt
´´´